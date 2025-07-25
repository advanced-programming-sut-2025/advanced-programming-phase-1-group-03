package com.ap.items;

import com.ap.Constraints;
import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.component.*;
import com.ap.tiled.TiledPhysic;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

public class EntityFactory {
    public static Entity CreatePlowedDirt(Body body, World world) {
        Entity entity = new Entity();
        entity.add(new Graphic(null));
        entity.add(new SeasonalGraphic(AtlasAsset.SeasonalObjects, "dirt_hoed"));
        entity.add(new Transform(body.getPosition(), Constraints.HOE_DIRT_Z
                , new Vector2(1, 1), new Vector2(1, 1), 0, -2));

        Body newBody = TiledPhysic.createBodyForTile((int) body.getPosition().x, (int) body.getPosition().y, entity, world, true);
        entity.add(new Physic(newBody, body.getPosition()));
        entity.add(new Dirt(true));
        return entity;
    }

    public static Entity CreateTreeEntity(Vector2 position, String name, AssetService assetService, World world) {
        TextureRegion fullTreeTexture = assetService.get(AtlasAsset.Trees).findRegions(name+"/spring/stage").get(4);
        Vector2 fullTreeSize = new Vector2(fullTreeTexture.getRegionWidth(), fullTreeTexture.getRegionHeight()).scl(Constraints.UNIT_SCALE);
        Entity stumpEntity = new Entity();
        stumpEntity.add(new Transform(new Vector2(position.x - fullTreeSize.x/2 +0.5f, position.y+0.5f),
                Constraints.TREE_LEAF_Z,
                new Vector2(1, 1),
                fullTreeSize,
                0, 0,
                new Vector2(fullTreeSize.x/2, 0.3f)));

        stumpEntity.add(new Graphic(fullTreeTexture));
        stumpEntity.add(new SeasonalGraphic(AtlasAsset.Trees, name+"/{season}/stage", 4));

        TextureRegion shadowTexture = assetService.get(AtlasAsset.Shadows).findRegion("shadow");
        Vector2 shadowSize = new Vector2(shadowTexture.getRegionWidth(), shadowTexture.getRegionHeight()).scl(Constraints.UNIT_SCALE);
        Entity shadowEntity = new Entity();
        shadowEntity.add(new Transform(new Vector2(position.x - shadowSize.x/2 +0.5f, position.y -0.5F),
                Constraints.TREE_STUMP_Z,
                new Vector2(0.8f, 0.8f),
                shadowSize,
                0, -1));
        shadowEntity.add(new Shadow());
        shadowEntity.add(new Graphic(shadowTexture));

        TextureRegion treeStump = assetService.get(AtlasAsset.Trees).findRegion(name+"/spring/stump");
        Vector2 size = new Vector2(treeStump.getRegionWidth(), treeStump.getRegionHeight()).scl(Constraints.UNIT_SCALE);
        Entity entity = new Entity();
        entity.add(new Transform(new Vector2(position.x - size.x/2 +0.5f, position.y),
                Constraints.TREE_STUMP_Z,
                new Vector2(1, 1),
                size,
                0, 0));
        entity.add(new Graphic(treeStump));
        entity.add(new SeasonalGraphic(AtlasAsset.Trees, name+"/{season}/stump"));
        var body = TiledPhysic.createBodyForTile((int) position.x, (int) position.y, entity, world, false);
        entity.add(new Physic(body, position));
        entity.add(new Container(stumpEntity, shadowEntity));
        entity.add(new ItemHolder(ItemFactory.CreateTree()));
        return entity;
    }

    public static Entity CreateStoneEntity(Vector2 position, int type, AssetService assetService, World world) {
        TextureRegion texture = assetService.get(AtlasAsset.Environment).findRegions("stone/regular").get(type);
        Vector2 size = new Vector2(1, 1);
        Entity entity = new Entity();
        entity.add(new Transform(new Vector2(position.x, position.y),
                Constraints.STONE_Z,
                new Vector2(1, 1),
                size,
                0, 0));
        entity.add(new Graphic(texture));
        var body = TiledPhysic.createBodyForTile((int) position.x, (int) position.y, entity, world, false);
        entity.add(new Physic(body, position));
        entity.add(new ItemHolder(ItemFactory.CreateStone(assetService)));
        return entity;
    }

    public static Entity CreateGrassEntity(Vector2 position, int type, AssetService assetService, World world) {
        TextureRegion texture = assetService.get(AtlasAsset.Environment).
                findRegions("grass/type_"+type+"_color").get(new Random().nextInt(2));

        Vector2 size = new Vector2(1, 1);
        Entity entity = new Entity();
        entity.add(new Transform(new Vector2(position.x, position.y),
                Constraints.GRASS_Z,
                new Vector2(1, 1),
                size,
                0, 0));
        entity.add(new Graphic(texture));
        var body = TiledPhysic.createBodyForTile((int) position.x, (int) position.y, entity, world, true);
        entity.add(new Physic(body, position));

        entity.add(new ItemHolder(ItemFactory.CreateGrass()));
        return entity;
    }

    public static Entity CreateWoodEntity(Vector2 position, AssetService assetService, World world) {
        TextureRegion texture = assetService.get(AtlasAsset.Environment).findRegion("wood/regular");
        Vector2 size = new Vector2(1, 1);
        Entity entity = new Entity();

        int deg = new Random().nextInt(90);
        entity.add(new Transform(new Vector2(position.x, position.y),
                Constraints.WOOD_Z,
                new Vector2(0.8f, 0.8f),
                size,
                -deg, 0));
        entity.add(new Graphic(texture));
        var body = TiledPhysic.createBodyForTile((int) position.x, (int) position.y, entity, world, false);
        entity.add(new Physic(body, position));
        entity.add(new ItemHolder(ItemFactory.CreateWood(assetService)));
        return entity;
    }
}
