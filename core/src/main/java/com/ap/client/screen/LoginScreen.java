package com.ap.client.screen;

import com.ap.client.GdxGame;
import com.ap.client.audio.AudioService;
import com.ap.client.input.KeyboardController;
import com.ap.client.input.UIControllerState;
import com.ap.client.ui.common.BackButtonLayer;
import com.ap.client.ui.model.LoginViewModel;
import com.ap.client.ui.view.LoginView;

public class LoginScreen extends AbstractScreen {
    private AudioService audioService;
    private final KeyboardController controller;

    public LoginScreen(GdxGame game) {
        super(game);
        audioService = game.getAudioService();
        controller = new KeyboardController(UIControllerState.class, null, stage);
    }

    @Override
    public void show() {
        this.stage.addActor(new LoginView(stage, skin, new LoginViewModel(game), audioService));
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
