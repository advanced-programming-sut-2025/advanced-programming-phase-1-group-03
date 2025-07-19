package com.ap.ui.view;

import com.ap.audio.AudioService;
import com.ap.ui.model.GameViewModel;
import com.ap.ui.model.MainViewModel;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
