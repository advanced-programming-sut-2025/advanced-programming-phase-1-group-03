package com.ap.screen;

import com.ap.GdxGame;
import com.ap.audio.AudioService;

public class GameScreen extends AbstractScreen {
    private AudioService audioService;

    public GameScreen(GdxGame game) {
        super(game);
        audioService = game.getAudioService();
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}
