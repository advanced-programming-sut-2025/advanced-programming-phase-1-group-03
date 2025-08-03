package com.ap.client.screen;

import com.ap.client.GdxGame;
import com.ap.client.asset.AssetService;
import com.ap.client.asset.MusicAsset;
import com.ap.client.audio.AudioService;
import com.ap.client.database.SqliteConnection;
import com.ap.client.input.KeyboardController;
import com.ap.client.input.UIControllerState;
import com.ap.client.ui.model.MainViewModel;
import com.ap.client.ui.view.MainView;

public class MainMenuScreen extends AbstractScreen {

    private final AudioService audioService;
    private final KeyboardController controller;
    private final SqliteConnection sqlite;
    private final AssetService assetService;

    public MainMenuScreen(GdxGame game) {
        super(game);
        audioService = game.getAudioService();
        controller = new KeyboardController(UIControllerState.class, null, stage);
        sqlite = game.getSqlite();
        assetService = game.getAssetService();
    }

    @Override
    public void show() {
        this.stage.addActor(new MainView(stage, skin, new MainViewModel(game, sqlite), audioService, assetService));
        audioService.playMusic(MusicAsset.Default);

        game.setInputProcessors(stage, controller);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act(delta);
        stage.draw();
    }
}
