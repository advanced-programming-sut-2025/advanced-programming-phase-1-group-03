package com.ap.client.screen;

import com.ap.client.GdxGame;
import com.ap.client.audio.AudioService;
import com.ap.client.input.KeyboardController;
import com.ap.client.input.UIControllerState;
import com.ap.client.ui.common.BackButtonLayer;
import com.ap.client.ui.model.SignupViewModel;
import com.ap.client.ui.view.SignupView;

public class SignupScreen extends AbstractScreen{
    private AudioService audioService;
    private final KeyboardController controller;

    public SignupScreen(GdxGame game) {
        super(game);
        audioService = game.getAudioService();
        controller = new KeyboardController(UIControllerState.class, null, stage);
    }

    @Override
    public void show() {
        this.stage.addActor(new SignupView(stage, skin, new SignupViewModel(game), audioService));
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
