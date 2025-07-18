package com.ap.screen;

import com.ap.GdxGame;
import com.ap.audio.AudioService;
import com.ap.input.KeyboardController;
import com.ap.input.UIControllerState;
import com.ap.ui.common.BackButtonLayer;
import com.ap.ui.model.LoginViewModel;
import com.ap.ui.view.LoginView;

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
        this.stage.addActor(new LoginView(stage, skin, new LoginViewModel(game, game.getSqlite()), audioService));
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
