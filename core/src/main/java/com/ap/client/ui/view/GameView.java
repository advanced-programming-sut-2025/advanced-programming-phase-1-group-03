package com.ap.client.ui.view;

import com.ap.client.audio.AudioService;
import com.ap.client.ui.model.GameViewModel;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameView extends AbstractView<GameViewModel> {
    private AudioService audioService;

    public GameView(Stage stage, Skin skin, GameViewModel viewModel, AudioService audioService) {
        super(stage, skin, viewModel);
        this.audioService = audioService;
        setupUI();
    }

    @Override
    protected void setupUI() {
    }
}
