package com.ap.items.food;

import com.ap.asset.AtlasAsset;
import com.ap.items.Item;
import com.ap.managers.PlayerManager;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class Food extends Item {
    int energy;
    public Food(String name, int maxStackSize, AtlasAsset atlasAsset, String atlasKey, int sellPrice, int energy) {
        super(name, maxStackSize, atlasAsset, atlasKey, sellPrice);
        this.energy = energy;
    }

  //  DecisionDialog eatDialog;

    @Override
    public void applyItem(WorldObject body, Engine engine, PlayerManager playerManager, World world) {
//        eatDialog = GameUIManager.instance.showDecisionDialog("Would you like to eat " + name + "?",
//                () -> {
//                    eatFood(game.getInventory(),  game.getEnergyManager());
//                    game.getAudioService().playSound(SoundAsset.Eat);
//                }, () -> {
//                    eatDialog.remove();
//                });
    }

//    private void eatFood(Inventory inventory, EnergyManager energyManager) {
//        inventory.removeItem(this, 1);
//        eatDialog.remove();
//        energyManager.advance(energy);
//    }
}
