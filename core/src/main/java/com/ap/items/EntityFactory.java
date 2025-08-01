package com.ap.items;

import com.ap.Constraints;
import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.audio.AudioService;
import com.ap.component.*;
import com.ap.items.plant.Crop;
import com.ap.model.CropsType;
import com.ap.state.CrowAnimationState;
import com.ap.model.MineralNodes;
import com.ap.tiled.TiledPhysic;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

public class EntityFactory {
    public static EntityFactory instance = new EntityFactory();

    private AssetService assetService;
    private AudioService audioService;

    public void setup(AssetService asset, AudioService audioService) {
        assetService = asset;
        this.audioService = audioService;
    }

    public Entity CreatePlowedDirt(Vector2 position, World world) {
        Entity entity = new Entity();
        entity.add(new Graphic(null));
        entity.add(new SeasonalGraphic(AtlasAsset.SeasonalObjects, "dirt_hoed"));
        entity.add(new Transform(position, Constraints.HOE_DIRT_Z
                , new Vector2(1, 1), new Vector2(1, 1), 0, -2));

        Body newBody = TiledPhysic.createBodyForTile((int) position.x, (int) position.y, entity, world, true);
        entity.add(new Physic(newBody, position));
        entity.add(new Dirt(true));
        return entity;
    }

    public Entity CreateGreenhousePlowedDirt(Vector2 position, World world) {
        Entity entity = new Entity();
        entity.add(new Graphic(null));
        entity.add(new Transform(position, Constraints.HOE_DIRT_Z
                , new Vector2(1, 1), new Vector2(1, 1), 0, -2));

        Body newBody = TiledPhysic.createBodyForTile((int) position.x, (int) position.y, entity, world, true);
        entity.add(new Physic(newBody, position));
        entity.add(new Dirt(true));
        return entity;
    }
    public Entity CreateTreeEntity(Vector2 position, String name, World world) {
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
        entity.add(new ItemHolder(ItemFactory.instance.CreateTree()));
        entity.add(new TreeComponent(true, Constraints.NUMBER_OF_AXE_NEED_TO_CUT_DOWN_TREE));
        return entity;
    }

    public Entity CreateStoneEntity(Vector2 position, int type, World world) {
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
        entity.add(new ItemHolder(ItemFactory.instance.CreateStone()));
        return entity;
    }

    public Entity CreateGrassEntity(Vector2 position, int type, World world) {
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

        entity.add(new ItemHolder(ItemFactory.instance.CreateGrass()));
        return entity;
    }

    public Entity CreateWoodEntity(Vector2 position, World world) {
        int deg = new Random().nextInt(90);

        TextureRegion shadowTexture = assetService.get(AtlasAsset.Shadows).findRegion("shadow");
        Vector2 shadowSize = new Vector2(shadowTexture.getRegionWidth(), shadowTexture.getRegionHeight()).scl(Constraints.UNIT_SCALE);
        Entity shadowEntity = new Entity();
        shadowEntity.add(new Transform(new Vector2(position.x - shadowSize.x/2 +0.5f, position.y -0.5F),
                Constraints.SHADOW_Z,
                new Vector2(0.25f, 0.25f),
                shadowSize,
                -deg + 26, -1));
        shadowEntity.add(new Shadow());
        shadowEntity.add(new Graphic(shadowTexture));

        TextureRegion texture = assetService.get(AtlasAsset.Environment).findRegion("wood/regular");
        Vector2 size = new Vector2(1, 1);
        Entity entity = new Entity();

        entity.add(new Transform(new Vector2(position.x, position.y),
                Constraints.WOOD_Z,
                new Vector2(0.8f, 0.8f),
                size,
                -deg, 0));
        entity.add(new Graphic(texture));
        var body = TiledPhysic.createBodyForTile((int) position.x, (int) position.y, entity, world, false);
        entity.add(new Physic(body, position));
        entity.add(new ItemHolder(ItemFactory.instance.CreateWood()));
        entity.add(new Container(shadowEntity));
        return entity;
    }

    public Entity CreateCropEntity(Vector2 position, CropsType type, World world, Entity dirtEntity) {
        Entity entity = new Entity();
        entity.add(new Transform(new Vector2(position.x, position.y),
                Constraints.CROPS_Z,
                new Vector2(1, 1),
                new Vector2(1, 1),
                0, 2));

        entity.add(new Graphic(null));
        var body = TiledPhysic.createBodyForTile((int) position.x, (int) position.y, entity, world, true);
        entity.add(new Physic(body, position));
        entity.add(new ItemHolder(ItemFactory.instance.CreateCrop(type)));
        entity.add(new Growable(type.getStage(), AtlasAsset.Crops, type.name(), dirtEntity));
        return entity;
    }

    public Entity CreateGiantCropEntity(Vector2 position, CropsType type, World world) {
        Entity entity = new Entity();
        entity.add(new Transform(new Vector2(position.x, position.y),
                Constraints.CROPS_Z,
                new Vector2(1, 1),
                new Vector2(3, 3),
                0, 2));

        var texture = assetService.get(AtlasAsset.Crops).findRegion(type.getName() + "_Giant");
        entity.add(new Graphic(texture));

        var body = TiledPhysic.createRectagleBody((int) position.x, (int) position.y, 3, 3, entity, world, false);
        entity.add(new Physic(body, position));

        var crop = (Crop) ItemFactory.instance.CreateCrop(type);
        crop.setGiant(true);

        entity.add(new ItemHolder(crop));
        return entity;
    }

    public Entity CreateCrowEntity(Entity purposeEntity) {
        Entity entity = new Entity();
        Vector2 pos = Transform.mapper.get(purposeEntity).getPosition();

        entity.add(new Transform(new Vector2(pos.x, pos.y), Constraints.CROW_Z, new Vector2(1f, 1), new Vector2(1, 1), 0, 0));
        entity.add(new Facing(Facing.FacingDirection.Left));
        entity.add(new Move(Crow.Situation.Fly.speed));
        entity.add(new Graphic(null));
        entity.add(new Fsm(entity, CrowAnimationState.Voice));
        entity.add(new Animation2D(AtlasAsset.Crow, "", Animation2D.AnimationType.Voice, Animation.PlayMode.LOOP, 0.5f));
        entity.add(new Crow(purposeEntity, entity, audioService));

        return entity;
    }

    public Entity CreateMineralNodeEntity(Vector2 position, MineralNodes type, World world) {
        Entity entity = new Entity();
        entity.add(new Transform(new Vector2(position.x, position.y),
                Constraints.MINERAL_Z,
                new Vector2(1, 1),
                new Vector2(1, 1),
                0, 2));
        var texture = assetService.get(AtlasAsset.Mineral).findRegion(type.name());
        entity.add(new Graphic(texture));
        var body = TiledPhysic.createBodyForTile((int) position.x, (int) position.y, entity, world, false);
        entity.add(new Physic(body, position));
        entity.add(new MineralNode(type));
        return entity;
    }
}
