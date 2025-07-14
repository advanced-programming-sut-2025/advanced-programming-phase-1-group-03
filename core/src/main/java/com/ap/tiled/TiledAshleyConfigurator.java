package com.ap.tiled;

import com.ap.Constraints;
import com.ap.component.*;
import com.ap.component.Transform;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class TiledAshleyConfigurator {
    private final Engine engine;
    private World world;

    public TiledAshleyConfigurator(Engine engine, World world) {
        this.engine = engine;
        this.world = world;
    }

    public void onLoadTile(TiledMapTile tile, int x, int y) {
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

        entity.add(new Graphic(textureRegion, Color.WHITE.cpy()));

        engine.addEntity(entity);
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
