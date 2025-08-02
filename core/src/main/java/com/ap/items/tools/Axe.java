package com.ap.items.tools;

import com.ap.Constraints;
import com.ap.asset.SoundAsset;
import com.ap.component.*;
import com.ap.items.Item;
import com.ap.items.ItemFactory;
import com.ap.items.ItemNames;
import com.ap.items.plant.Crop;
import com.ap.items.plant.Tree;
import com.ap.managers.AbilityManager;
import com.ap.model.AbilityType;
import com.ap.model.Weather;
import com.ap.screen.GameScreen;
import com.ap.system.universal.EnergyManager;
import com.ap.system.universal.TimeSystem;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;


public class Axe extends Tool {
    private BasicToolLevels currentLevel = BasicToolLevels.Normal;

    public Axe(TextureRegion icon) {
        super("Axe", icon, AbilityType.Foraging);
    }

    @Override
    int getEnergyConsumption(GameScreen gameScreen, boolean successful) {
        int amount = 0;
        switch(currentLevel) {
            case Normal : {
                amount = 5;
                break;
            }
            case Copper : {
                amount = 4;
                break;
            }
            case Iron : {
                amount = 3;
                break;
            }
            case Gold : {
                amount = 2;
                break;
            }
            case Iridium : {
                amount = 1;
                break;
            }
        }
        System.out.println("&& " + amount);
        if(successful)
            amount -= 1;
        System.out.println("&& " + amount);
        if(AbilityManager.getInstance().getAbility(AbilityType.Foraging).getLevel() == relatedAbility.maxLevel)
            amount -= 1;
        System.out.println("&& " + amount);
        if(gameScreen.getWeatherSystem().getCurrentWeather().equals(Weather.Rain))
            amount = (int)(amount * 1.5f);
        System.out.println("&& " + amount);
        if(gameScreen.getWeatherSystem().getCurrentWeather().equals(Weather.Snow))
            amount *= 2;
        System.out.println("&& " + amount);
        System.out.println("!! " + amount);
        return Math.min(0, -amount);
    }

    @Override
    public void applyItem(WorldObject body, Engine engine, GameScreen game, World world) {
        if(!(body.getUserData() instanceof Entity entity)) {
            EnergyManager.getInstance().advance(getEnergyConsumption(game, false));
            return;
        }
        if(!ItemHolder.mapper.has(entity)) {
            EnergyManager.getInstance().advance(getEnergyConsumption(game, false));
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
                EnergyManager.getInstance().advance(getEnergyConsumption(game, false));
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
        EnergyManager.getInstance().advance(getEnergyConsumption(game, true));

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
