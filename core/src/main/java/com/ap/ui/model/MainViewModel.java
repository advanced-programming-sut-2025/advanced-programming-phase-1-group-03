package com.ap.ui.model;

import com.ap.GdxGame;
import com.ap.database.SqliteConnection;
import com.ap.database.UserLoader;
import com.ap.model.GameData;
import com.ap.screen.GameScreen;
import com.ap.screen.PreGameScreen;
import com.ap.screen.ProfileScreen;
import com.ap.screen.SignupScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.sql.PreparedStatement;

public class MainViewModel extends ViewModel{
    private SqliteConnection sqlite;
    public MainViewModel(GdxGame game, SqliteConnection sqlite) {
        super(game);
        this.sqlite = sqlite;
    }

    public void clickSignupButton() {
        game.setScreen(SignupScreen.class);
    }

    public void clickNewGameButton() {
        game.setScreen(PreGameScreen.class);
    }

    public void exitGame() {
        game.getPreferencesManager().save();
        Gdx.app.exit();
    }

    public String getLoggedInUserNickname() {
        return UserLoader.getLoggedInUserNickname(sqlite);
    }

    public void openProfilePage() {
        game.setScreen(ProfileScreen.class);
    }

    public int getAvatarIndex() {
        return UserLoader.getLoggedInUserAvatarIndex(sqlite);
    }
}
