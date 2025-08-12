package com.ap.managers;

import com.ap.Constraints;
import com.ap.audio.AudioService;
import com.ap.model.Menus;
import com.ap.model.store.CarpenterShop;
import com.ap.model.store.StardropSaloonProducts;
import com.ap.screen.GameScreen;
import com.ap.system.ControllerSystem;
import com.ap.ui.widget.DecisionDialog;
import com.ap.ui.widget.MessageDialog;
import com.ap.ui.widget.MessagePopup;
import com.ap.ui.widget.StoreMenu;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class GameUIManager {
    public static GameUIManager instance = new GameUIManager();
    private Stage stage;
    private Skin skin;
    private AudioService audioService;
    private GameScreen gameScreen;
    private Map<Menus, StoreMenu> menus = new HashMap<>();

    public void setup(Stage stage, Skin skin, AudioService audioService, GameScreen game) {
        this.stage = stage;
        this.skin = skin;
        this.audioService = audioService;
        this.gameScreen = game;
    }

    public DecisionDialog showDecisionDialog(String question, Runnable whenOk, Runnable whenCancel) {
        DecisionDialog dialog = new DecisionDialog("",question, skin, whenOk, whenCancel, audioService);
        stage.addActor(dialog);
        return dialog;
    }

    public void showMessageDialog(String text) {
        MessageDialog dialog = new MessageDialog("",text, skin,stage, audioService);
        stage.addActor(dialog);
    }

    public void displayMenu(Menus menu, BiConsumer<StoreMenu.StoreProduct, Menus> onBuy) {
        switch (menu) {
            case StardropSaloonMenu:
                menus.put(menu, (new StoreMenu(
                        gameScreen.getAssetService(), skin, stage, gameScreen.getInventory(),
                        gameScreen.getAudioService(),"Gus",
                        "Hungry? Thirsty? I've got just the thing.", menu,
                        StardropSaloonProducts.buildStoreItems(gameScreen.getAssetService()), onBuy)
                ));
            case CarpenterShopMenu:
                menus.put(menu, (new StoreMenu(
                        gameScreen.getAssetService(), skin, stage, gameScreen.getInventory(),
                        gameScreen.getAudioService(),"Robin",
                        "Carpenter's Shop!", menu,
                        CarpenterShop.buildStoreItems(gameScreen.getAssetService()), onBuy)
                ));
        }
        stage.addActor(menus.get(menu));
    }

    public void exitMenu(Menus menu) {
        if(!menus.containsKey(menu)) {
            return;
        }
        StoreMenu storeMenu = menus.get(menu);
        storeMenu.remove();
        menus.remove(menu);
    }

    private DecisionDialog greenhouseDialog;

    public void showGreenhouseMessage(int goldNeeded, int woodNeeded, Runnable whenOk) {
        greenhouseDialog = showDecisionDialog(
                "Would you like to build greenhouse with "+
                        woodNeeded +" amount of wood and "+
                        goldNeeded+" golds?",
                () -> {
                    buildGreenHouse(whenOk);
                }, this::removeGreenhouseMessage);
    }

    private void buildGreenHouse(Runnable whenOk) {
        greenhouseDialog.remove();
        whenOk.run();
    }

    private void removeGreenhouseMessage() {
        greenhouseDialog.remove();
    }

    public void showPopup(String message, String sender) {
        MessagePopup.show(stage, skin, sender, message);
    }

}
