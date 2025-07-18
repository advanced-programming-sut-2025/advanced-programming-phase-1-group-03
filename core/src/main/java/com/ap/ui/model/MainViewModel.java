package com.ap.ui.model;

import com.ap.GdxGame;
import com.ap.database.SqliteConnection;
import com.ap.model.GameData;
import com.ap.screen.GameScreen;
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
        game.setScreen(GameScreen.class);
    }

    public void exitGame() {
        game.getPreferencesManager().save();
        Gdx.app.exit();
    }

    public String getLoggedInUserNickname() {
        if(GameData.getInstance().getLoggedUserUsername() == null) {
            return "Guest";
        }
        var sql = """
                SELECT nickname FROM users WHERE username = ?;
                """;
        var result = sqlite.runSql(sql, (PreparedStatement ps) -> {
           ps.setString(1, GameData.getInstance().getLoggedUserUsername());
        });
        try {
            if (result.next()) {
                return result.getString(1);
            } else {
                GameData.getInstance().setLoggedUserUsername(null);
                return "Guest";
            }
        }catch (Exception e) {
            throw new GdxRuntimeException(e.getMessage());
        }
    }
}
