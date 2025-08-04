package com.ap.client.screen;

import com.ap.client.GdxGame;
import com.ap.client.asset.AssetService;
import com.ap.client.audio.AudioService;
import com.ap.client.input.KeyboardController;
import com.ap.client.input.UIControllerState;
import com.ap.client.network.GameClient;
import com.ap.client.network.listeners.LobbyListener;
import com.ap.client.screen.AbstractScreen;
import com.ap.client.screen.MainMenuScreen;
import com.ap.client.ui.common.BackButtonLayer;
import com.ap.client.ui.model.LobbyViewModel;
import com.ap.client.ui.model.PreGameViewModel;
import com.ap.client.ui.view.LobbyView;
import com.ap.client.ui.view.PreGameView;

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
