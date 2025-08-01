package com.ap.items.food;

import com.ap.asset.SoundAsset;
import com.ap.items.Inventory;
import com.ap.items.Item;
import com.ap.managers.GameUIManager;
import com.ap.model.Foods;
import com.ap.model.GameData;
import com.ap.screen.GameScreen;
import com.ap.ui.widget.DecisionDialog;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class Food extends Item {
    int energy;
    public Food(String name, int maxStackSize, TextureRegion icon, int sellPrice, int energy) {
        super(name, maxStackSize, icon, sellPrice);
        this.energy = energy;
    }

    DecisionDialog eatDialog;

    @Override
    public void applyItem(WorldObject body, Engine engine, GameScreen game, World world) {
        eatDialog = GameUIManager.instance.showDecisionDialog("Would you like to eat " + name + "?",
                () -> {
                    eatFood(game.getInventory());
                    game.getAudioService().playSound(SoundAsset.Eat);
                }, () -> {
                    eatDialog.remove();
                });
    }

    private void eatFood(Inventory inventory) {
        inventory.removeItem(this, 1);
        eatDialog.remove();

        //TODO add energy
    }
}
