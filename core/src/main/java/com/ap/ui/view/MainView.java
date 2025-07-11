package com.ap.ui.view;

import com.ap.ui.model.MainViewModel;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;

public class MainView extends AbstractView<MainViewModel>{
    public MainView(Stage stage, Skin skin, MainViewModel viewModel) {
        super(stage, skin, viewModel);
    }

    @Override
    protected void setupUI() {
        setFillParent(true);
        setBackground(skin.getDrawable("Panorama"));

        Image logo = new Image(skin.getDrawable("Logo"));
        logo.setScaling(Scaling.fit);

        add(logo).padTop(20).colspan(4).fillX().top().row();

        add().colspan(4).pad(150).row();

        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();

        style.down =  skin.getDrawable("NewButton");
        style.up = skin.getDrawable("NewButton");
        style.over = skin.getDrawable("NewButtonHover");
        ImageButton newButton = new ImageButton(style);

        style = new ImageButton.ImageButtonStyle();
        style.down =  skin.getDrawable("LoadButton");
        style.up = skin.getDrawable("LoadButton");
        style.over = skin.getDrawable("LoadButtonHover");
        ImageButton loadButton = new ImageButton(style);

        style = new ImageButton.ImageButtonStyle();
        style.down =  skin.getDrawable("CoOpButton");
        style.up = skin.getDrawable("CoOpButton");
        style.over = skin.getDrawable("CoOpButtonHover");
        ImageButton coOpButton = new ImageButton(style);

        style = new ImageButton.ImageButtonStyle();
        style.down =  skin.getDrawable("ExitButton");
        style.up = skin.getDrawable("ExitButton");
        style.over = skin.getDrawable("ExitButtonHover");
        ImageButton exitButton = new ImageButton(style);


        add(newButton).pad(50);
        add(loadButton).pad(50);
        add(coOpButton).pad(50);
        add(exitButton).pad(50);

        OnEnter(newButton, bnt -> {
        });
        // Arrange everything from top to bottom
        top();
    }
}
