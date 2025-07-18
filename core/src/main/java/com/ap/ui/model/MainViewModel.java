package com.ap.ui.model;

import com.ap.GdxGame;
import com.ap.model.GameData;
import com.ap.screen.GameScreen;
import com.ap.screen.SignupScreen;
import com.badlogic.gdx.Gdx;

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

    public String getLoggedInUsername() {
        if(GameData.getInstance().getLoggedUserUsername() == null) {
            return "Guest";
        }
        return GameData.getInstance().getLoggedUserUsername();
    }

    public void exitGame() {
        game.getPreferencesManager().save();
        Gdx.app.exit();
    }
}
