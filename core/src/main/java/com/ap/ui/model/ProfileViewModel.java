package com.ap.ui.model;

import com.ap.GdxGame;
import com.ap.database.SqliteConnection;
import com.ap.database.UserLoader;
import com.ap.model.GameData;
import com.ap.model.Gender;
import com.ap.model.Result;
import com.ap.screen.ProfileScreen;
import com.ap.utils.Crypto;
import com.ap.utils.RegistrationValidator;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import java.util.Random;

import static com.ap.Constraints.secQuestions;

public class ProfileViewModel extends ViewModel{
    private final SqliteConnection sqlite;
    private final RegistrationValidator validator;

    public ProfileViewModel(GdxGame game, SqliteConnection sqlite) {
        super(game);
        this.sqlite = sqlite;
        validator = new RegistrationValidator(sqlite);
    }

    public String getUsername() {
        return GameData.getInstance().getLoggedUserUsername();
    }

    public int getMaximumCoin() {
        return UserLoader.getLoggedInUserMaximumCoin(sqlite);
    }

    public int getGamesCount() {
        return UserLoader.getLoggedInUserGamesCount(sqlite);
    }

    public String getEmail() {
        return UserLoader.getLoggedInUserEmail(sqlite);
    }

    public String getNickname() {
        return UserLoader.getLoggedInUserNickname(sqlite);
    }

    public int getAvatarIndex() {
        return UserLoader.getLoggedInUserAvatarIndex(sqlite);
    }

    public void chooseAvatar(int i) {
        UserLoader.changeAvatarIndex(sqlite, i);
    }

    public Result<String> changeUsername(String username) {
        if(!validator.usernameValidity(username)) {
            return new Result<>(false, "Username is not valid");
        }
        UserLoader.changeUsername(sqlite, username);
        GameData.getInstance().setLoggedUserUsername(username);
        return new Result<>(true, "Username Changed Successfully");
    }

    public void reload() {
        game.setScreen(ProfileScreen.class);
    }

    public Result<String> changeNickname(String nickname) {
        UserLoader.changeNickname(sqlite, nickname);
        return new Result<>(true, "Nickname Changed Successfully");
    }

    public Result<String> changeEmail(String email) {
        if(!validator.emailValidity(email)) {
            return new Result<>(false, "Email is not valid");
        }
        UserLoader.changeEmail(sqlite, email);
        return new Result<>(true, "Email Changed Successfully");
    }

    public String generatePassword() {
        return Crypto.generatePassword(validator);
    }

    public Result<String> changePassword(String password) {
        if(!validator.passwordValidity(password).isSuccess()) {
            return validator.passwordValidity(password);
        }
        UserLoader.changePassword(sqlite, Crypto.hash(password));
        return new Result<>(true, "Password Changed Successfully");
    }
}
