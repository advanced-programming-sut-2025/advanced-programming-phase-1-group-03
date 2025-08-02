package com.ap.items.tools;

import com.ap.Constraints;
import com.ap.asset.SoundAsset;
import com.ap.component.Growable;
import com.ap.component.ItemHolder;
import com.ap.items.Item;
import com.ap.items.ItemFactory;
import com.ap.items.ItemNames;
import com.ap.items.plant.Crop;
import com.ap.items.plant.Plant;
import com.ap.model.AbilityType;
import com.ap.screen.GameScreen;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

public class Scythe extends Tool{
    public Scythe(TextureRegion icon) {
        super("Scythe", icon, AbilityType.Farming);
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
        game.getAudioService().playSound(SoundAsset.Scythe);
        Item item = ItemHolder.mapper.get(entity).getItem();
        if(item.getName().equals(ItemNames.Grass.name())) {
            Helper.removeEntity(entity, engine, world);
            if(new Random().nextInt(10) < Constraints.PROB_OF_GRASS_GIVE_FIBBER) {
                Item fiber = ItemFactory.instance.CreateFiber();
                game.getInventory().addItem(fiber, 1);
            }
        }
        if(item instanceof Plant plant) {
            Growable growable = Growable.mapper.get(entity);
            if(growable.canProduce()) {
                for(Item gathered : plant.produceItems()) {
                    game.getInventory().addItem(gathered, 1);
                }

                if(plant instanceof Crop crop) {
                    if(crop.getType().getRegrowthTime() == null) {
                        Helper.removeEntity(entity, engine, world);
                    } else {
                        crop.setupRegrowth();
                        growable.setCurrentStage(growable.getCurrentStage() - 1);
                    }
                }

            }
        }
    }
}
