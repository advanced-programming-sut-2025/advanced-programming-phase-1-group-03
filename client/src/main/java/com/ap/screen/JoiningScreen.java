package com.ap.screen;

import com.ap.GdxGame;
import com.ap.asset.AssetService;
import com.ap.audio.AudioService;
import com.ap.input.KeyboardController;
import com.ap.input.UIControllerState;
import com.ap.network.GameClient;
import com.ap.network.listeners.JoinGameListener;
import com.ap.ui.common.BackButtonLayer;
import com.ap.ui.model.JoiningViewModel;
import com.ap.ui.view.JoiningView;

public class JoiningScreen extends AbstractScreen{

    private AudioService audioService;
    private final KeyboardController controller;
    private final AssetService assetService;
    private GameClient client;

    public JoiningScreen(GdxGame game) {
        super(game);
        audioService = game.getAudioService();
        controller = new KeyboardController(UIControllerState.class, null, stage);
        assetService = game.getAssetService();
        client = game.getClient();
    }

    @Override
    public void show() {
        var viewModel = new JoiningViewModel(game);
        client.getListener(JoinGameListener.class).setJoiningViewModel(viewModel);
        this.stage.addActor(new JoiningView(stage, skin, viewModel, audioService, assetService));
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
