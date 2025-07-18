package com.ap.ui.view;

import com.ap.asset.SoundAsset;
import com.ap.audio.AudioService;
import com.ap.ui.model.MainViewModel;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;

public class MainView extends AbstractView<MainViewModel>{

    private final AudioService audioService;
    public MainView(Stage stage, Skin skin, MainViewModel viewModel, AudioService audioService) {
        super(stage, skin, viewModel);
        this.audioService = audioService;
        setupUI();
    }

    @Override
    protected void setupUI() {
        setFillParent(true);
        setBackground(skin.getDrawable("Panorama"));

        drawHeader();

        // Adding empty space
        add().colspan(4).pad(120).row();

        drawButtons();

        // Arrange everything from top to bottom
        top();
    }

    private void drawHeader() {
        add(new Label("Welcome  " + viewModel.getLoggedInUserNickname(), skin)).colspan(1).pad(10);
        TextButton loginButton = new TextButton("Signup/Login", skin);
        add().colspan(2).expandX().fillX();
        add(loginButton).colspan(1).pad(10).row();

        Image logo = new Image(skin.getDrawable("Logo"));
        logo.setScaling(Scaling.fit);
        add(logo).padTop(20).colspan(4).fillX().top().row();

        // Setup events
        OnClick(loginButton, viewModel::clickSignupButton);

    }

    private void drawButtons() {
        ImageButton newButton = createImageButton(
                skin.getDrawable("NewButton"), skin.getDrawable("NewButtonHover"));
        ImageButton loadButton = createImageButton(
                skin.getDrawable("LoadButton"), skin.getDrawable("LoadButtonHover"));
        ImageButton coOpButton = createImageButton(
                skin.getDrawable("CoOpButton"), skin.getDrawable("CoOpButtonHover"));
        ImageButton exitButton = createImageButton(
                skin.getDrawable("ExitButton"), skin.getDrawable("ExitButtonHover"));
        Table buttonsTable = new Table();
        buttonsTable.add(newButton).pad(20);
        buttonsTable.add(loadButton).pad(20);
        buttonsTable.add(coOpButton).pad(20);
        buttonsTable.add(exitButton).pad(20);
        add(buttonsTable).colspan(4);

        // Setup events
        OnClick(newButton, viewModel::clickNewGameButton);
        OnClick(exitButton, viewModel::exitGame);
    }

    private ImageButton createImageButton(Drawable drawable, Drawable hoverDrawable) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.down =  drawable;
        style.up = drawable;
        style.over = hoverDrawable;
        ImageButton newButton = new ImageButton(style);
        newButton.setTransform(true);
        newButton.setOrigin(newButton.getWidth() / 2, newButton.getHeight() / 2);
        OnEnter(newButton, (ImageButton btn) -> {
            audioService.playSound(SoundAsset.HoverButton);
            btn.addAction(Actions.scaleTo(2f, 2f, 0.3f));
        });
        OnExit(newButton, (ImageButton btn) -> {
            btn.addAction(Actions.scaleTo(1f, 1f, 0.3f));
        });
        return newButton;
    }
}
