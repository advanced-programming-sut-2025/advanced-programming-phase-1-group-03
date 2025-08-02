package com.ap.items.tools;

import com.ap.Constraints;
import com.ap.asset.SoundAsset;
import com.ap.component.*;
import com.ap.items.Item;
import com.ap.items.ItemFactory;
import com.ap.items.ItemNames;
import com.ap.items.plant.Crop;
import com.ap.items.plant.Tree;
import com.ap.model.AbilityType;
import com.ap.screen.GameScreen;
import com.ap.system.universal.TimeSystem;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;


public class Axe extends Tool {
    public Axe(TextureRegion icon) {
        super("Axe", icon, AbilityType.Foraging);
    }

    @Override
    int getEnergyConsumption() {
        return 0;
    }

    @Override
    public void applyItem(WorldObject body, Engine engine, GameScreen game, World world) {
        if(!(body.getUserData() instanceof Entity entity)) {
            return;
        }
        if(!ItemHolder.mapper.has(entity)) {
            return;
        }
        game.getAudioService().playSound(SoundAsset.Axe);
        Item item = ItemHolder.mapper.get(entity).getItem();
        Item wood = ItemFactory.instance.CreateWood();

        if(item.getName().equals(ItemNames.Wood.name())) {
            Helper.removeEntity(entity, engine, world);
            game.getInventory().addItem(wood, 1);
        } else if(item instanceof Tree tree) {
            handleTree(engine, game, world, entity, wood);
        } else if(item instanceof Crop crop) {
            if(!crop.isGiant()) {
                return;
            }
            if(crop.getAxeHit() == Constraints.NUMBER_OF_AXE_NEED_TO_DESTROY_GIANT) {
                int itemAmount = Helper.random(Constraints.MIN_ITEM_GIANT_GIVE, Constraints.MAX_ITEM_GIANT_GIVE);
                for(Item gain : crop.produceItems()) {
                    game.getInventory().addItem(gain, itemAmount);
                }
                Helper.removeEntity(entity, engine, world);
            } else {
                crop.setAxeHit(crop.getAxeHit() + 1);
            }
        }
    }

    private void handleTree(Engine engine, GameScreen game, World world, Entity entity, Item wood) {
        var treeComp = TreeComponent.mapper.get(entity);

        if(!treeComp.hasLeaf()) {
            game.getInventory().addItem(wood, Constraints.STUMP_GIVEN_WOOD);
            Helper.removeEntity(entity, engine, world);
            return;
        }

        if(treeComp.getNumberOfAxeNeededToCut() > 0) {
            treeComp.setNumberOfAxeNeededToCut(treeComp.getNumberOfAxeNeededToCut() - 1);
            return;
        }
        treeComp.setHasLeaf(false);

        var leafEntity = Container.mapper.get(entity).getChildren().first();
        Transform transform = Transform.mapper.get(leafEntity);

        float[] degree = new float[1];

        Timer.Task fallTree = new Timer.Task() {
            @Override
            public void run() {
                transform.setRotationDeg(degree[0] * degree[0]);
                degree[0] += 0.1f;
                if(degree[0] * degree[0] >= 85f) {
                    cancel();
                    removeTree(leafEntity, engine, world);
                    game.getInventory().addItem(wood,
                            Helper.random(Constraints.MINIMUM_WOOD_TREE_GIVE, Constraints.MAXIMUM_WOOD_TREE_GIVE));
                }
            }
        };
        new Timer().scheduleTask(fallTree, 0f, 0.02f);
    }

    private void removeTree(Entity tree, Engine engine, World world) {
        Helper.removeEntity(tree, engine, world);
    }
}
