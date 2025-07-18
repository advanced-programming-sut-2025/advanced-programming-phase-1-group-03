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
}
