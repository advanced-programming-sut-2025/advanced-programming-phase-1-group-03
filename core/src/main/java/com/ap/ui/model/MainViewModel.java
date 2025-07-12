package com.ap.ui.model;

import com.ap.GdxGame;
import com.ap.screen.GameScreen;
import com.ap.screen.SignupScreen;

public class MainViewModel extends ViewModel{
    public MainViewModel(GdxGame game) {
        super(game);
    }

    public void clickSignupButton() {
        game.setScreen(SignupScreen.class);
    }

    public void clickNewGameButton() {
        game.setScreen(GameScreen.class);
    }
}
