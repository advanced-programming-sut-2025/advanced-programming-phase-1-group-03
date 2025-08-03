package com.ap.client.ui.view;

import com.ap.client.asset.AssetService;
import com.ap.client.asset.AtlasAsset;
import com.ap.client.asset.SoundAsset;
import com.ap.client.audio.AudioService;
import com.ap.client.ui.actor.SimpleDialog;
import com.ap.client.ui.model.MainViewModel;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.particles.ParticleShader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

import javax.swing.*;

public class MainView extends AbstractView<MainViewModel>{

    private final AudioService audioService;
    private final AssetService assetService;

    public MainView(Stage stage, Skin skin, MainViewModel viewModel, AudioService audioService, AssetService assetService) {
        super(stage, skin, viewModel);
        this.audioService = audioService;
        this.assetService = assetService;
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
        boolean isLoggedIn = !viewModel.getLoggedInUserNickname().equals("Guest");

        if(isLoggedIn) {
            TextureAtlas avatarAtlas = assetService.get(AtlasAsset.Avatars);
            Image avatar = new Image(avatarAtlas.findRegion("avatar" + viewModel.getAvatarIndex()));
            add(avatar).maxWidth(40).maxHeight(40).pad(10);
        }
        add(new Label("Welcome  " + viewModel.getLoggedInUserNickname(), skin)).colspan(1).padLeft(10).padTop(10);

        add().colspan(2).expandX().fillX().center();

        if(isLoggedIn) {
            TextButton profileButton = new TextButton("Profile", skin);
            add(profileButton).pad(5).row();
            OnClick(profileButton, viewModel::openProfilePage);
        } else {
            TextButton loginButton = new TextButton("Signup/Login", skin);
            add(loginButton).colspan(1).padTop(10).padRight(10).row();

            // Setup events
            OnClick(loginButton, viewModel::clickSignupButton);
        }


        Image logo = new Image(skin.getDrawable("Logo"));
        logo.setScaling(Scaling.fit);
        add(logo).padTop(20).colspan(4).fillX().top().row();
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
        OnClick(coOpButton, this::onlineButton);
    }

    private void onlineButton() {
        SimpleDialog dialog = new SimpleDialog("", "Connecting to the server...", skin);
        viewModel.tryingToConnect();
        dialog.setupEvent(viewModel::stopConnecting);
        dialog.show(stage);
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
