package com.ap.screen;

import com.ap.GdxGame;
import com.ap.asset.AssetService;
import com.ap.audio.AudioService;
import com.ap.input.KeyboardController;
import com.ap.input.UIControllerState;
import com.ap.ui.common.BackButtonLayer;
import com.ap.ui.model.LoginViewModel;
import com.ap.ui.model.PreGameViewModel;
import com.ap.ui.view.LoginView;
import com.ap.ui.view.PreGameView;

public class PreGameScreen extends AbstractScreen{
    private AudioService audioService;
    private final KeyboardController controller;
    private final AssetService assetService;

    public PreGameScreen(GdxGame game) {
        super(game);
        audioService = game.getAudioService();
        controller = new KeyboardController(UIControllerState.class, null, stage);
        assetService = game.getAssetService();
    }

    @Override
    public void show() {
        this.stage.addActor(new PreGameView(stage, skin, new PreGameViewModel(game, game.getSqlite()), audioService, assetService));
        this.stage.addActor(new BackButtonLayer(game, skin, MainMenuScreen.class));
        game.setInputProcessors(stage, controller);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act(delta);
        stage.draw();
    }
}
