package com.ap.client.managers;

import com.ap.client.audio.AudioService;
import com.ap.client.model.Menus;
import com.ap.client.model.store.CarpenterShop;
import com.ap.client.model.store.StardropSaloonProducts;
import com.ap.client.screen.GameScreen;
import com.ap.client.system.ControllerSystem;
import com.ap.client.ui.widget.DecisionDialog;
import com.ap.client.ui.widget.MessageDialog;
import com.ap.client.ui.widget.StoreMenu;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class GameUIManager {
    public static GameUIManager instance = new GameUIManager();
    private Stage stage;
    private Skin skin;
    private Engine engine;
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
        var controller = engine.getSystem(ControllerSystem.class);
        if(controller != null) {
            controller.reset();
        }
        DecisionDialog dialog = new DecisionDialog("",question, skin, whenOk, whenCancel, audioService);
        stage.addActor(dialog);
        return dialog;
    }

    public void showMessageDialog(String text) {
        var controller = engine.getSystem(ControllerSystem.class);
        if(controller != null) {
            controller.reset();
        }
        MessageDialog dialog = new MessageDialog("",text, skin,stage, audioService);
        stage.addActor(dialog);
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
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
                        "Hungry? Thirsty? I've got just the thing.", menu,
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
}
