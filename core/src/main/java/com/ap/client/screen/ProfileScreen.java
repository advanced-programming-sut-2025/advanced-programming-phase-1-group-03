package com.ap.client.screen;

import com.ap.client.GdxGame;
import com.ap.client.asset.AssetService;
import com.ap.client.audio.AudioService;
import com.ap.client.input.KeyboardController;
import com.ap.client.input.UIControllerState;
import com.ap.client.ui.common.BackButtonLayer;
import com.ap.client.ui.model.ProfileViewModel;
import com.ap.client.ui.view.ProfileView;

public class ProfileScreen extends AbstractScreen{
    private AudioService audioService;
    private final KeyboardController controller;
    private final AssetService assetService;

    public ProfileScreen(GdxGame game) {
        super(game);
        audioService = game.getAudioService();
        controller = new KeyboardController(UIControllerState.class, null, stage);
        assetService = game.getAssetService();
    }

    @Override
    public void show() {
        this.stage.addActor(new ProfileView(stage, skin, new ProfileViewModel(game, game.getSqlite()), audioService, assetService));
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
