package com.ap.items.environment;

import com.ap.Constraints;
import com.ap.asset.AssetService;
import com.ap.asset.SoundAsset;
import com.ap.asset.TextureAsset;
import com.ap.audio.AudioService;
import com.ap.component.Graphic;
import com.ap.items.Inventory;
import com.ap.items.Item;
import com.ap.items.ItemFactory;
import com.ap.items.ItemNames;
import com.ap.managers.GameUIManager;
import com.ap.model.GameData;
import com.ap.screen.GameScreen;
import com.ap.ui.widget.DecisionDialog;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

public class Greenhouse extends Item {
    DecisionDialog dialog;
    public Greenhouse() {
        super(ItemNames.Greenhouse.name(), 0, null, 0);
    }

    @Override
    public void interact(Body body, Engine engine, GameScreen game) {
        if(GameData.getInstance().isGreenhouseBuilt()) {
            return;
        }
        Entity greenHouse = (Entity) body.getUserData();
        dialog = GameUIManager.instance.showDecisionDialog(
                "Would you like to build greenhouse with "+
                        Constraints.GREEN_HOUSE_WOOD_NEEDED +" amount of wood and "+
                        Constraints.GREEN_HOUSE_GOLD_NEEDED+" golds?",
                () -> buildGreenhouse(game.getInventory(), greenHouse, game.getAssetService(), game.getAudioService()), this::refuseBuild);
    }

    private void refuseBuild() {
        dialog.remove();
    }

    private void buildGreenhouse(Inventory inventory, Entity greenHouse,
                                 AssetService assetService, AudioService audioService) {
        dialog.remove();
        if(!inventory.have(ItemFactory.instance.CreateWood(), Constraints.GREEN_HOUSE_WOOD_NEEDED)) {
            GameUIManager.instance.showMessageDialog("We don't have enough wood to build greenhouse!");
            return;
        }
        if(GameData.getInstance().getPlayerGold() < Constraints.GREEN_HOUSE_GOLD_NEEDED) {
            GameUIManager.instance.showMessageDialog("We don't have enough gold to build greenhouse!");
            return;
        }
        // reduce coin and wood
        inventory.removeItem(ItemFactory.instance.CreateWood(), Constraints.GREEN_HOUSE_WOOD_NEEDED);
        GameData.getInstance().setPlayerGold(GameData.getInstance().getPlayerGold() - Constraints.GREEN_HOUSE_GOLD_NEEDED);

        audioService.playSound(SoundAsset.Gift, 0.5f);
        Graphic.mapper.get(greenHouse).setTexture(new TextureRegion(assetService.get(TextureAsset.GreenHouse)));
        GameData.getInstance().setGreenhouseBuilt(true);
    }
}
