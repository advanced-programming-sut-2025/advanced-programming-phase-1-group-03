package com.ap.screen;

import com.ap.GdxGame;
import com.ap.asset.AssetService;
import com.ap.audio.AudioService;
import com.ap.input.KeyboardController;
import com.ap.input.UIControllerState;
import com.ap.network.GameClient;
import com.ap.network.listeners.LobbyListener;
import com.ap.ui.common.BackButtonLayer;
import com.ap.ui.model.LobbyViewModel;
import com.ap.ui.view.LobbyView;

public class LobbyScreen extends AbstractScreen {
    private AudioService audioService;
    private final KeyboardController controller;
    private final AssetService assetService;
    private GameClient client;

    public LobbyScreen(GdxGame game) {
        super(game);
        audioService = game.getAudioService();
        controller = new KeyboardController(UIControllerState.class, null, stage);
        assetService = game.getAssetService();
        client = game.getClient();
    }

    @Override
    public void show() {
        var viewModel = new LobbyViewModel(game);
        client.getListener(LobbyListener.class).setLobbyViewModel(viewModel);
        this.stage.addActor(new LobbyView(stage, skin, viewModel, audioService, assetService));
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
