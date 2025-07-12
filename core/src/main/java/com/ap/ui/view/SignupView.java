package com.ap.ui.view;

import com.ap.audio.AudioService;
import com.ap.ui.model.MainViewModel;
import com.ap.ui.model.SignupViewModel;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SignupView extends AbstractView<SignupViewModel> {
    private final AudioService audioService;
    public SignupView(Stage stage, Skin skin, SignupViewModel viewModel, AudioService audioService) {
        super(stage, skin, viewModel);
        this.audioService = audioService;
    }


    @Override
    protected void setupUI() {

    }
}
