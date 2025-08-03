package com.ap.client.items.tools;

import com.ap.client.Constraints;
import com.ap.client.asset.SoundAsset;
import com.ap.client.component.Growable;
import com.ap.client.component.ItemHolder;
import com.ap.client.items.Item;
import com.ap.client.items.ItemFactory;
import com.ap.client.items.ItemNames;
import com.ap.client.items.plant.Crop;
import com.ap.client.items.plant.Plant;
import com.ap.client.model.AbilityType;
import com.ap.client.screen.GameScreen;
import com.ap.client.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

public class Scythe extends Tool{
    private BasicToolLevels currentLevel = BasicToolLevels.Normal;
    public Scythe(TextureRegion icon) {
        super("Scythe", icon, AbilityType.Farming);
    }

    @Override
    public int getEnergyConsumption() {
        return 2;
    }

    @Override
    public void applyItem(WorldObject body, Engine engine, GameScreen game, World world) {
        super.applyItem(body, engine, game, world);
        game.getAudioService().playSound(SoundAsset.Scythe);
        if(!(body.getUserData() instanceof Entity entity)) {
            return;
        }
        if(!ItemHolder.mapper.has(entity)) {
            return;
        }
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
