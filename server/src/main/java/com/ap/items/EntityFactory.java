package com.ap.items;

import com.ap.Constraints;
import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.component.*;
import com.ap.component.items.Barn;
import com.ap.component.items.CraftingCmp;
import com.ap.component.items.FarmAnimal;
import com.ap.component.items.Well;
import com.ap.items.animal.Animal;
import com.ap.items.plant.Crop;
import com.ap.model.*;
import com.ap.state.CrowAnimationState;
import com.ap.state.EmoteAnimationState;
import com.ap.tiled.TiledPhysic;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

public class EntityFactory {
    public static EntityFactory instance = new EntityFactory();

    private AssetService assetService;

    public void setup(AssetService asset) {
        assetService = asset;
    }

    public Entity CreatePlowedDirt(Vector2 position, World world) {
        Entity entity = new Entity();
        entity.add(new Graphic());
        entity.add(new SeasonalGraphic(AtlasAsset.SeasonalObjects, "dirt_hoed"));
        entity.add(new Transform(new Vector2((int) position.x, (int) position.y), Constraints.HOE_DIRT_Z
                , new Vector2(1, 1), new Vector2(1, 1), 0, -2));

        Body newBody = TiledPhysic.createBodyForTile((int) position.x, (int) position.y, entity, world, true);
        entity.add(new Physic(newBody, position));
        entity.add(new Dirt(true));
        return entity;
    }

    public Entity CreateGreenhousePlowedDirt(Vector2 position, World world) {
        Entity entity = new Entity();
        entity.add(new Graphic());
        entity.add(new Transform(new Vector2((int) position.x, (int) position.y), Constraints.HOE_DIRT_Z
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

        var graphic = new Graphic(AtlasAsset.Trees, name+"/spring/stage");
        graphic.setRegionIndex(4);
        stumpEntity.add(graphic);
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
        shadowEntity.add(new Graphic(AtlasAsset.Shadows, "shadow"));

        TextureRegion treeStump = assetService.get(AtlasAsset.Trees).findRegion(name+"/spring/stump");
        Vector2 size = new Vector2(treeStump.getRegionWidth(), treeStump.getRegionHeight()).scl(Constraints.UNIT_SCALE);
        Entity entity = new Entity();
        entity.add(new Transform(new Vector2(position.x - size.x/2 +0.5f, position.y),
                Constraints.TREE_STUMP_Z,
                new Vector2(1, 1),
                size,
                0, 0));
        entity.add(new Graphic(AtlasAsset.Trees, name+"/spring/stump"));
        entity.add(new SeasonalGraphic(AtlasAsset.Trees, name+"/{season}/stump"));
        var body = TiledPhysic.createBodyForTile((int) position.x, (int) position.y, entity, world, false);
        entity.add(new Physic(body, position));
        entity.add(new Container(stumpEntity, shadowEntity));
        entity.add(new ItemHolder(ItemFactory.instance.CreateTree()));
        entity.add(new TreeComponent(true, Constraints.NUMBER_OF_AXE_NEED_TO_CUT_DOWN_TREE));
        return entity;
    }

    public Entity CreateStoneEntity(Vector2 position, int type, World world) {
        Vector2 size = new Vector2(1, 1);
        Entity entity = new Entity();
        entity.add(new Transform(new Vector2((int) position.x, (int) position.y),
                Constraints.STONE_Z,
                new Vector2(1, 1),
                size,
                0, 0));
        entity.add(new Graphic(AtlasAsset.Environment, "stone/regular"));
        var body = TiledPhysic.createBodyForTile((int) position.x, (int) position.y, entity, world, false);
        entity.add(new Physic(body, position));
        entity.add(new ItemHolder(ItemFactory.instance.CreateStone()));
        return entity;
    }

    public Entity CreateGrassEntity(Vector2 position, int type, World world) {
        String atlasKey = "grass/type_"+type+"_color";
        Vector2 size = new Vector2(1, 1);
        Entity entity = new Entity();
        entity.add(new Transform(new Vector2((int) position.x, (int) position.y),
                Constraints.GRASS_Z,
                new Vector2(1, 1),
                size,
                0, 0));
        entity.add(new Graphic(AtlasAsset.Environment, atlasKey));
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
        shadowEntity.add(new Graphic(AtlasAsset.Shadows, "shadow"));

        TextureRegion texture = assetService.get(AtlasAsset.Environment).findRegion("wood/regular");
        Vector2 size = new Vector2(1, 1);
        Entity entity = new Entity();

        entity.add(new Transform(new Vector2(position.x, position.y),
                Constraints.WOOD_Z,
                new Vector2(0.8f, 0.8f),
                size,
                -deg, 0));
        entity.add(new Graphic(AtlasAsset.Environment, "wood/regular"));
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
        entity.add(new Graphic());
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

        entity.add(new Graphic(AtlasAsset.Crops, type.name() + "_Giant"));

        var body = TiledPhysic.createRectagleBody((int) position.x, (int) position.y, 3, 3, entity, world, false, BodyDef.BodyType.StaticBody);
        entity.add(new Physic(body, position));

        var crop = (Crop) ItemFactory.instance.CreateCrop(type);
        crop.setGiant(true);

        entity.add(new ItemHolder(crop));
        return entity;
    }
//
    public Entity CreateCrowEntity(Entity purposeEntity) {
        Entity entity = new Entity();
        Vector2 pos = Transform.mapper.get(purposeEntity).getPosition();

        entity.add(new Transform(new Vector2(pos.x, pos.y), Constraints.CROW_Z, new Vector2(1f, 1), new Vector2(1, 1), 0, 0));
        entity.add(new Facing(Facing.FacingDirection.Left));
        entity.add(new Move(Crow.Situation.Fly.speed));
        entity.add(new Graphic(AtlasAsset.Crow, "voice_left"));
        entity.add(new Fsm(entity, CrowAnimationState.Voice));
        entity.add(new Animation2D(AtlasAsset.Crow, "", Animation2D.AnimationType.Voice, Animation.PlayMode.LOOP, 0.5f));
//        entity.add(new Crow(purposeEntity, entity));

        return entity;
    }
//
    public Entity CreateMineralNodeEntity(Vector2 position, MineralNodes type, World world) {
        Entity entity = new Entity();
        entity.add(new Transform(new Vector2(position.x, position.y),
                Constraints.MINERAL_Z,
                new Vector2(1, 1),
                new Vector2(1, 1),
                0, 2));
        entity.add(new Graphic(AtlasAsset.Mineral, type.name()));
        var body = TiledPhysic.createBodyForTile((int) position.x, (int) position.y, entity, world, false);
        entity.add(new Physic(body, position));
        entity.add(new MineralNode(type));
        return entity;
    }
    private Entity CreateCarrierEntity(AtlasAsset atlas, String atlasKey, float width, float height) {
        Entity entity = new Entity();
        Vector2 size = new Vector2(width, height);
        entity.add(new Transform(new Vector2(0,0),
                1000,
                new Vector2(1, 1),
                size,
                0, 4));
        entity.add(new Graphic(atlas, atlasKey, new Color(1f, 1f, 1f, 0.5f)));
        entity.add(new Carrier());
        return entity;
    }

    public Entity CreateCarrierFarmAnimalEntity(FarmAnimalTypes type) {
        Entity entity = CreateCarrierEntity(AtlasAsset.Animals,
                type.getAtlasKey() + "/idle_down",
                type.getWidthInPx() * Constraints.UNIT_SCALE,
                type.getHeightInPx() * Constraints.UNIT_SCALE);
        entity.add(new FarmAnimal(type, null));
        return entity;
    }

    public Entity CreateCarrierBarnEntity(BarnsType type) {
        var texture = assetService.get(AtlasAsset.Barns).findRegion(type.name());
        Entity entity = CreateCarrierEntity(
                AtlasAsset.Barns,
                type.name(),
                texture.getRegionWidth() * Constraints.UNIT_SCALE,
                texture.getRegionHeight() * Constraints.UNIT_SCALE
        );
        entity.add(new Barn(type));
        return entity;
    }
    public Entity CreateCarrierWellEntity() {
        var texture = assetService.get(AtlasAsset.Barns).findRegion("Well_Complete");
        Entity entity = CreateCarrierEntity(
                AtlasAsset.Barns,
                "Well_Complete",
                texture.getRegionWidth() * Constraints.UNIT_SCALE,
                texture.getRegionHeight() * Constraints.UNIT_SCALE);
        entity.add(new Well());
        return entity;
    }
    public Entity CreateBarnEntity(BarnsType type, Vector2 position, World world, Engine engine) {
        Entity entity = new Entity();
        var texture = assetService.get(AtlasAsset.Barns).findRegion(type.name());
        Vector2 size = new Vector2(texture.getRegionWidth(), texture.getRegionHeight()).scl(Constraints.UNIT_SCALE);
        entity.add(new Transform(position,
                Constraints.BARN_Z,
                new Vector2(1, 1),
                size,
                0, 5));
        entity.add(new Graphic(AtlasAsset.Barns, type.name()));
        var body = TiledPhysic.createRectagleBody((int) position.x, (int) position.y, size.x, size.y, entity, world, false, BodyDef.BodyType.StaticBody);
        entity.add(new Physic(body, position));
        entity.add(new Barn(type));

        Entity spawner = new Entity();
        var bodySpawner = TiledPhysic.createRectagleBody(position.x , position.y - 1, size.x, 1, type.name(), world,true, BodyDef.BodyType.StaticBody);
        spawner.add(new Physic(bodySpawner, position));
        Helper.addEntity(spawner, engine);

        return entity;
    }


    public Entity CreateWellEntity(Vector2 position, World world) {
        Entity entity = new Entity();
        var bottomTexture = assetService.get(AtlasAsset.Barns).findRegion("Well_Bottom");
        Vector2 size = new Vector2(bottomTexture.getRegionWidth(), bottomTexture.getRegionHeight()).scl(Constraints.UNIT_SCALE);
        entity.add(new Transform(position,
                Constraints.WELL_BOTTOM_Z,
                new Vector2(1, 1),
                size,
                0, 4));
        entity.add(new Graphic(AtlasAsset.Barns, "Well_Bottom"));
        var body = TiledPhysic.createRectagleBody((int) position.x, (int) position.y, size.x, size.y, entity, world, false, BodyDef.BodyType.StaticBody);
        entity.add(new Physic(body, position));

        var topTexture = assetService.get(AtlasAsset.Barns).findRegion("Well_Top");
        Vector2 topSize = new Vector2(topTexture.getRegionWidth(), topTexture.getRegionHeight()).scl(Constraints.UNIT_SCALE);
        Entity topEntity = new Entity();
        topEntity.add(new Transform(position.cpy().add(0, 1.3f),
                Constraints.WELL_TOP_Z,
                new Vector2(1, 1),
                topSize,
                0, 0));
        topEntity.add(new Graphic(AtlasAsset.Barns, "Well_Top"));

        entity.add(new Container(topEntity));
        entity.add(new Well());
        return entity;
    }

    public Entity CreateFarmAnimalEntity(Vector2 position, World world, Animal animal) {
        Entity entity = new Entity();

        FarmAnimalTypes type = animal.getType();

        float realW = type.getWidthInPx() * Constraints.UNIT_SCALE;
        float realH = type.getHeightInPx() * Constraints.UNIT_SCALE;

        float bodyW = realW * 1f;
        float bodyH = realH * 0.6f;

        var body = TiledPhysic.createRectagleBody(position.x, position.y, bodyW, bodyH, entity, world, false, BodyDef.BodyType.DynamicBody);

        entity.add(new Transform(position, Constraints.Animal_Z, new Vector2(1f, 1f),
                new Vector2(realW, realH),
                0, 0));
        entity.add(new Physic(body, position));
        entity.add(new Move(1));
        entity.add(new Facing(Facing.FacingDirection.Down));
        entity.add(new Graphic(AtlasAsset.Animals, type.getAtlasKey() + "/idle_down"));
        entity.add(new Fsm(entity, FarmAnimal.Situation.Idle.animationState));
        entity.add(new Animation2D(AtlasAsset.Animals, type.getAtlasKey(), Animation2D.AnimationType.Idle, Animation.PlayMode.LOOP, 0.5f));
        entity.add(new FarmAnimal(type, animal));
        entity.add(new Clickable());

        return entity;
    }

    public Entity CreateEmoteEntity(Transform target, EmoteType emoteType, float duration) {
        Entity entity = new Entity();

        entity.add(new Transform(new Vector2(target.getPosition().x, target.getPosition().y), Constraints.Emote_Z, new Vector2(1f, 1f),
                new Vector2(1f, 1),
                0, 0));
        entity.add(new Graphic(AtlasAsset.Emotes, "emote_0"));
        entity.add(new Facing(Facing.FacingDirection.Down));
        entity.add(new Fsm(entity, EmoteAnimationState.Opening));
        entity.add(new Animation2D(AtlasAsset.Emotes, "", Animation2D.AnimationType.Emote_Opening, Animation.PlayMode.NORMAL, 0.5f));
        entity.add(new Emote(target, emoteType, duration));

        return entity;
    }

    public Entity CreateTextEmoteEntity(Transform target, String text, float duration) {
        Entity entity = new Entity();

        entity.add(new Transform(new Vector2(target.getPosition().x, target.getPosition().y), Constraints.Emote_Z, new Vector2(1f, 1f),
                new Vector2(1f, 1),
                0, 0));
        entity.add(new Graphic());
        entity.add(new Facing(Facing.FacingDirection.Down));
        entity.add(new Emote(target, text, duration));

        return entity;
    }

    public Entity CreateCollectableItemEntity(Vector2 position, ItemStack item) {
        Entity entity = new Entity();
        Vector2 size = new Vector2(1, 1);
        entity.add(new Transform(new Vector2(position),
                Constraints.Collectable_Z,
                new Vector2(1.5f, 1.5f),
                size,
                0, 0));
        entity.add(new Graphic(item.getItem().atlasAsset, item.getItem().atlasKey));
        entity.add(new Collectable(item));

        return entity;
    }

    public Entity CreateCraftingCarrier(Crafting crafting) {
        var texture = assetService.get(AtlasAsset.Crafting).findRegion(crafting.name());
        Entity entity = CreateCarrierEntity(AtlasAsset.Crafting, crafting.name(), texture.getRegionWidth() * Constraints.UNIT_SCALE,
                texture.getRegionHeight() * Constraints.UNIT_SCALE);
        entity.add(new CraftingCmp(crafting));
        return entity;
    }

    public Entity CreateCrafting(Vector2 position, Crafting craft, World world) {
        Entity entity = new Entity();
        var texture = assetService.get(AtlasAsset.Crafting).findRegion(craft.name());
        Vector2 size = new Vector2(texture.getRegionWidth(), texture.getRegionHeight()).scl(Constraints.UNIT_SCALE);
        entity.add(new Transform(position,
                Constraints.BARN_Z,
                new Vector2(1, 1),
                size,
                0, 5));
        entity.add(new Graphic(AtlasAsset.Crafting, craft.name()));
        var body = TiledPhysic.createRectagleBody((int) position.x, (int) position.y, size.x, size.y, entity, world, false, BodyDef.BodyType.StaticBody);
        entity.add(new Physic(body, position));
        return entity;
    }

}
