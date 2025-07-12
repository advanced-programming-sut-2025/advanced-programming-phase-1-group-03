package com.ap.screen;

import com.ap.GdxGame;
import com.ap.asset.MusicAsset;
import com.ap.audio.AudioService;
import com.ap.input.KeyboardController;
import com.ap.input.UIControllerState;
import com.ap.ui.model.MainViewModel;
import com.ap.ui.view.MainView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class MainMenuScreen extends AbstractScreen {

    private final AudioService audioService;
    private final KeyboardController controller;

    public MainMenuScreen(GdxGame game) {
        super(game);
        audioService = game.getAudioService();
        controller = new KeyboardController(UIControllerState.class, null, stage);
    }

    @Override
    public void show() {
        this.stage.addActor(new MainView(stage, skin, new MainViewModel(game), audioService));
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
