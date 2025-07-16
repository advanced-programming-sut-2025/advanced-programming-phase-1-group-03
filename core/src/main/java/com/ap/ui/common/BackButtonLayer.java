package com.ap.ui.common;

import com.ap.GdxGame;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class BackButtonLayer extends Table {
    private Skin skin;
    private GdxGame game;
    private Class<? extends Screen> parentScreen;

    public BackButtonLayer(GdxGame game, Skin skin, Class<? extends Screen> parentScreen) {
        this.skin = skin;
        this.parentScreen = parentScreen;
        this.game = game;
        setupUI();
    }

    private void setupUI() {
        setFillParent(true);

        var style = new ImageButton.ImageButtonStyle();
        style.over = skin.getDrawable("backbtn_hover");
        style.down = skin.getDrawable("backbtn");
        style.up = skin.getDrawable("backbtn");

        ImageButton backButton = new ImageButton(style);
        backButton.setTransform(true);
        add(backButton).pad(40);
        bottom();
        right();

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(parentScreen);
            }
        });
    }
}
