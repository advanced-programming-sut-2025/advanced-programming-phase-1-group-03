package com.ap.ui.model;

import com.ap.GdxGame;
import com.ap.database.SqliteConnection;
import com.ap.model.GameData;
import com.ap.model.Result;
import com.ap.screen.MainMenuScreen;
import com.ap.screen.SignupScreen;
import com.ap.utils.Crypto;
import com.ap.utils.PreferencesManager;
import com.ap.utils.RegistrationValidator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.GdxRuntimeException;

import javax.swing.text.View;
import java.sql.PreparedStatement;

public class LoginViewModel extends ViewModel {
    private final SqliteConnection sqlite;
    private final RegistrationValidator registrationValidator;
    private final PreferencesManager preferencesManager;

    public LoginViewModel(GdxGame game, SqliteConnection sqlite) {
        super(game);
        this.sqlite = sqlite;
        registrationValidator = new RegistrationValidator(sqlite);
        preferencesManager = new PreferencesManager();
    }

    public Result<String> submit(String username, String password) {
        if(!registrationValidator.checkLogin(username, Crypto.hash(password))) {
            return new Result<>(false, "Password is incorrect or username doesn't exist");
        }

        return new Result<>(true, "Login was successful");
    }

    public void loginSuccessful(String username, boolean rememberMe) {
        GameData.getInstance().setLoggedUserUsername(username);

        if(rememberMe) {
            preferencesManager.rememberUsername(username);
        } else {
            game.getPreferencesManager().removeRememberUser();
        }

        game.setScreen(MainMenuScreen.class);
    }

    public void openRegisterPage() {
        game.setScreen(SignupScreen.class);
    }

    public int getSecurityQuestion(String username) {
        var sql = """
                SELECT securityQuestionId FROM users WHERE username = ?;
                """;
        var result = sqlite.runSql(sql, (PreparedStatement ps) -> {
            ps.setString(1, username);
        });
        try {
            if (result.next()) {
                return result.getInt(1);
            }
        }catch (Exception e) {
            throw new GdxRuntimeException(e.getMessage());
        }
        return -1;
    }

    public boolean isSecurityQuestionValid(String securityQuestionAnswer, String username) {
        var sql = """
                SELECT securityQuestion FROM users WHERE username = ?;
                """;
        var result = sqlite.runSql(sql, (PreparedStatement ps) -> {
            ps.setString(1, username);
        });
        try {
            if(result.next()) {
                return result.getString(1).equalsIgnoreCase(securityQuestionAnswer);
            }
        }catch (Exception e) {
            throw new GdxRuntimeException(e.getMessage());
        }
        return false;
    }
    public String getPassword(String securityQuestionAnswer, String username, int securityQuestionId) {
        var sql = """
                SELECT securityQuestion FROM users WHERE username = ? AND securityQuestionId = ?;
                """;
        var result = sqlite.runSql(sql, (PreparedStatement ps) -> {
            ps.setString(1, username);
            ps.setInt(2, securityQuestionId);
        });
        try {
            if(result.next()) {
                return result.getString(1);
            }
        }catch (Exception e) {
            throw new GdxRuntimeException(e.getMessage());
        }
        return null;
    }

    public Result<String> changePassword(String username, String password) {
        var sql = """
                UPDATE users SET password = ? WHERE username = ?;
                """;
        if(!registrationValidator.passwordValidity(password).isSuccess()) {
            return registrationValidator.passwordValidity(password);
        }
        sqlite.runSqlWithoutResult(sql, (PreparedStatement ps) -> {
            ps.setString(1, Crypto.hash(password));
            ps.setString(2, username);
        });
        return new Result<>(true, "Password changed successfully");
    }
}
