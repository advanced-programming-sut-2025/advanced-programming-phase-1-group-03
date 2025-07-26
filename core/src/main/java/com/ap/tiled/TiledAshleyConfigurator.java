package com.ap.tiled;

import com.ap.Constraints;
import com.ap.asset.AtlasAsset;
import com.ap.component.*;
import com.ap.component.Transform;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class TiledAshleyConfigurator {
    private final Engine engine;
    private final World world;

    public TiledAshleyConfigurator(Engine engine, World world) {
        this.engine = engine;
        this.world = world;
    }

    public void onLoadTile(TiledMapTile tile, int x, int y) {
        TiledPhysic.createBodyForTile(x, y, tile, world, true);
    }


    public void onLoadSpawner(MapObject object) {
        MapObjects objects = new MapObjects();
        objects.add(object);
        createBody(objects,
                Vector2.Zero,
                new Vector2(1, 1),
                BodyDef.BodyType.StaticBody,
                Vector2.Zero, object.getProperties().get("spawner", String.class));
    }
    public void onLoadBoundary(MapObject object) {
        MapObjects objects = new MapObjects();
        objects.add(object);
        createBody(objects,
                Vector2.Zero,
                new Vector2(1, 1),
                BodyDef.BodyType.StaticBody,
                Vector2.Zero, "");
    }

    public void onLoadObject(TiledMapTileMapObject tileObject) {
        Entity entity = engine.createEntity();

        TiledMapTile tile = tileObject.getTile();
        TextureRegion textureRegion = tile.getTextureRegion();

        float sortOffsetY = tile.getProperties().get("sortOffsetY", 0.0f, Float.class);
        sortOffsetY *= Constraints.UNIT_SCALE;
        int z = tile.getProperties().get("z", 0, Integer.class);

        addEntityTransform(
                tileObject.getX(), tileObject.getY(), z,
                textureRegion.getRegionWidth(), textureRegion.getRegionHeight(),
                tileObject.getScaleX(), tileObject.getScaleY(),
                sortOffsetY,
                entity
        );

        BodyDef.BodyType bodyType = getBodyType(tile);
        addEntityPhysic(
                tile.getObjects(),
                bodyType,
                Vector2.Zero,
                entity);
        addEntityMove(tile, entity);
        addEntityController(tile, entity);
        addEntityCameraFollow(tileObject, entity);
        addEntityGraphic(entity, textureRegion);
        addEntityFacing(entity);
        addEntityAnimation(tile, entity);
        addEntityFsm(entity);
        addEntityPlayer(entity, tile);
        engine.addEntity(entity);
    }

    private void addEntityPlayer(Entity entity, TiledMapTile tile) {
        boolean isPlayer = tile.getProperties().get("player", false, Boolean.class);
        if (isPlayer) {
            entity.add(new Player());
        }
    }

    private void addEntityFsm(Entity entity) {
        entity.add(new Fsm(entity));
    }

    private void addEntityAnimation(TiledMapTile tile, Entity entity) {
        String defaultAnimation = tile.getProperties().get("animation", "", String.class);
        if(defaultAnimation.isBlank()) {
            return;
        }
        var type = Animation2D.AnimationType.valueOf(defaultAnimation);

        String atlasKey = tile.getProperties().get("atlasKey", String.class);
        String atlasAssetStr = tile.getProperties().get("atlas", String.class);
        float speed = tile.getProperties().get("animationSpeed", Float.class);

        entity.add(new Animation2D(AtlasAsset.valueOf(atlasAssetStr), atlasKey, type, Animation.PlayMode.LOOP, speed));
    }

    private static void addEntityFacing(Entity entity) {
        // Every object has face direction even if it's static
        entity.add(new Facing(Facing.FacingDirection.Down));
    }

    private static void addEntityGraphic(Entity entity, TextureRegion textureRegion) {
        entity.add(new Graphic(textureRegion, Color.WHITE.cpy()));
    }

    private void addEntityCameraFollow(TiledMapTileMapObject tile, Entity entity) {
        boolean cameraFollow = tile.getProperties().get("cameraFollow", false, Boolean.class);
        if(!cameraFollow) {
            return;
        }
        entity.add(new CameraFollow());
    }

    // Add "controller" boolean to the tile of object to control it via keyboard
    private void addEntityController(TiledMapTile tile, Entity entity) {
        boolean controller = tile.getProperties().get("controller", false, Boolean.class);
        if(!controller)
            return;
        entity.add(new Controller());
    }

    // Add "speed" property to the tile to set speed and make the object movable
    private void addEntityMove(TiledMapTile tile, Entity entity) {
        float speed = tile.getProperties().get("speed", 0f, Float.class);

        // This object is not movable
        if(speed == 0)
            return;

        entity.add(new Move(speed));
    }

    private void addEntityTransform(
            float x, float y, int z,
            float w, float h,
            float scaleX, float scaleY,
            float sortOffsetY,
            Entity entity) {

        Vector2 position = new Vector2(x, y);
        Vector2 size = new Vector2(w, h);
        Vector2 scale = new Vector2(scaleX, scaleY);

        // Transform pixels to real world units
        position.scl(Constraints.UNIT_SCALE);
        size.scl(Constraints.UNIT_SCALE);

        entity.add(new Transform(position, z, scale, size, 0, sortOffsetY));
    }

    private void addEntityPhysic(MapObjects mapObjects,
                                 BodyDef.BodyType bodyType,
                                 Vector2 relativeTo,
                                 Entity entity) {

        if(mapObjects.getCount() == 0)
            return;

        Transform transform = entity.getComponent(Transform.class);
        Body body = createBody(
                mapObjects,
                transform.getPosition(),
                transform.getScaling(),
                bodyType,
                relativeTo,
                entity);
        entity.add(new Physic(body, transform.getPosition()));
    }

    private Body createBody(MapObjects mapObjects,
                            Vector2 position,
                            Vector2 scaling,
                            BodyDef.BodyType bodyType,
                            Vector2 relativeTo,
                            Object userData) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = bodyType;
        bodyDef.fixedRotation = true;

        Body body = world.createBody(bodyDef);
        body.setUserData(userData);

        for(MapObject mapObject : mapObjects) {
            FixtureDef fixtureDef = TiledPhysic.fixtureDefOf(mapObject, relativeTo, scaling);
            Fixture fixture = body.createFixture(fixtureDef);
            fixture.setUserData(mapObject.getName());
            fixtureDef.shape.dispose();
        }
        return body;
    }
    /**
     * Use "bodyType" key in tile properties to set bodyType
     */
    private BodyDef.BodyType getBodyType(TiledMapTile tile) {
        String type = tile.getProperties().get("type", String.class);
        if("Prop".equals(type)) {
            return BodyDef.BodyType.StaticBody;
        }
        String bodyType = tile.getProperties().get("bodyType", "DynamicBody",String.class);
        return BodyDef.BodyType.valueOf(bodyType);
    }
}
