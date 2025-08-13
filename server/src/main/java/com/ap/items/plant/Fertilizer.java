package com.ap.items.plant;

import com.ap.asset.AtlasAsset;
import com.ap.asset.SoundAsset;
import com.ap.component.Growable;
import com.ap.component.ItemHolder;
import com.ap.items.Item;
import com.ap.managers.PlayerManager;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.World;

public class Fertilizer extends Item {
    public Fertilizer(String name, AtlasAsset atlasAsset, String atlasKey) {
        super(name, 10, atlasAsset, atlasKey, 0);
    }

    @Override
    public void applyItem(WorldObject body, Engine engine, PlayerManager playerManager, World world) {
        super.applyItem(body, engine, playerManager, world);
        if(!(body.getUserData() instanceof Entity entity)) {
            return;
        }
        if(!ItemHolder.mapper.has(entity)) {
            return;
        }
        Item item = ItemHolder.mapper.get(entity).getItem();
        if(item instanceof Plant plant) {
            Growable growable = Growable.mapper.get(entity);
            if(!growable.canProduce()) {
                growable.setCurrentStage(growable.getCurrentStage() + 1);
                playerManager.getAudioService().playSound(SoundAsset.Gift);
                playerManager.getInventory().removeItem(this, 1);
            }
        }
    }
}
