package com.ap.ui.view;

import com.ap.ui.model.LoadingViewModel;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class LoadingView extends AbstractView<LoadingViewModel> {
    public LoadingView(Stage stage, Skin skin, LoadingViewModel viewModel) {
        super(stage, skin, viewModel);
        setupUI();
    }

    @Override
    protected void setupUI() {
        setFillParent(true);

        Label loadingLabel = new Label("Loading...", skin, "font48");
        add(loadingLabel);

        // Adding blink effect to the loading label
        loadingLabel.addAction(Actions.forever(Actions.sequence(
            Actions.fadeOut(1),
            Actions.fadeIn(1)
        )));
    }
}
