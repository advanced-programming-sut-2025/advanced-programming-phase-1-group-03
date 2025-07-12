package com.ap.screen;

import com.ap.GdxGame;
import com.ap.asset.MusicAsset;
import com.ap.audio.AudioService;
import com.ap.ui.model.MainViewModel;
import com.ap.ui.model.SignupViewModel;
import com.ap.ui.view.MainView;
import com.ap.ui.view.SignupView;
import com.badlogic.gdx.Gdx;

public class SignupScreen extends AbstractScreen{
    private AudioService audioService;

    public SignupScreen(GdxGame game) {
        super(game);
        audioService = game.getAudioService();
    }

    @Override
    public void show() {
        this.stage.addActor(new SignupView(stage, skin, new SignupViewModel(game), audioService));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act(delta);
        stage.draw();
    }
}
