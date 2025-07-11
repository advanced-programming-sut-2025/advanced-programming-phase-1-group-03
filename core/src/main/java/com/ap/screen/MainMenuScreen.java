package com.ap.screen;

import com.ap.GdxGame;
import com.ap.ui.model.MainViewModel;
import com.ap.ui.view.MainView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class MainMenuScreen extends AbstractScreen {
    public MainMenuScreen(GdxGame game) {
        super(game);
    }

    @Override
    public void show() {
        this.stage.addActor(new MainView(stage, skin, new MainViewModel(game)));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act(delta);
        stage.draw();
    }
}
