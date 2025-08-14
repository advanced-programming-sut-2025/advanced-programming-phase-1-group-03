package com.ap.items.food;

import com.ap.asset.AtlasAsset;
import com.ap.asset.SoundAsset;
import com.ap.items.Inventory;
import com.ap.items.Item;
import com.ap.managers.EnergyManager;
import com.ap.managers.PlayerManager;
import com.ap.rmi.Ask;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.physics.box2d.World;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;
import com.esotericsoftware.kryonet.rmi.RemoteObject;

public class Food extends Item {
    int energy;
    public Food(String name, int maxStackSize, AtlasAsset atlasAsset, String atlasKey, int sellPrice, int energy) {
        super(name, maxStackSize, atlasAsset, atlasKey, sellPrice);
        this.energy = energy;
    }

    @Override
    public void applyItem(WorldObject body, Engine engine, PlayerManager playerManager, World world) {
        new Thread(() -> {
            Ask eatFood = ObjectSpace.getRemoteObject(playerManager.getPlayer().connection, 1, Ask.class);
            ((RemoteObject)eatFood).setResponseTimeout(30000);
            if (eatFood.ask("Would you like to eat " + name + "?")) {
                playerManager.getAudioService().playSound(SoundAsset.Eat);
                eatFood(playerManager.getInventory(), playerManager.getEnergyManager());
            }
        }).start();
    }

    private void eatFood(Inventory inventory, EnergyManager energyManager) {
        inventory.removeItem(this, 1);
        energyManager.advance(energy);
    }
}
