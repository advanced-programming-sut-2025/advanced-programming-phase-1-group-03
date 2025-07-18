package com.ap.ui.model;

import com.ap.Constraints;
import com.ap.GdxGame;
import com.ap.database.SqliteConnection;
import com.ap.model.GameData;
import com.ap.model.Gender;
import com.ap.model.Result;
import com.ap.screen.LoginScreen;
import com.ap.screen.MainMenuScreen;
import com.ap.utils.Crypto;
import com.ap.utils.RegistrationValidator;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static com.ap.Constraints.secQuestions;

public class SignupViewModel extends ViewModel {
    private Gender gender;
    private final SqliteConnection sqlite;
    private final RegistrationValidator validator;
    private int selectedSecQuestionId;


    private final int secQuestionNumber;

    public SignupViewModel(GdxGame game, SqliteConnection sqlite) {
        super(game);
        this.sqlite = sqlite;
        validator = new RegistrationValidator(sqlite);
        secQuestionNumber = new Random().nextInt(secQuestions.length);
    }

    public String getSecQuestion() {
        return secQuestions[secQuestionNumber];
    }
    public Result<String> submit(
            String username, String email, String password, String confPassword, String nickname, String securityQuestion) {
        if(validator.duplicateUsername(username)) {
            return new Result<>(false, "A user with username already exists");
        }
        if(!validator.usernameValidity(username)) {
            return new Result<>(false, "Username is not valid");
        }
        if(!validator.emailValidity(email)) {
            return new Result<>(false, "Email is not valid");
        }
        if(!validator.passwordValidity(password).isSuccess()) {
            return validator.passwordValidity(password);
        }
        if(!password.equals(confPassword)) {
            return new Result<>(false, "Password and its confirmation don't match");
        }
        if(gender == null) {
            return new Result<>(false, "Please select gender");
        }

        var sql = """
                INSERT INTO users(username, password, email, gender, nickname, securityQuestionId, securityQuestion) VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        sqlite.runSqlWithoutResult(sql, (PreparedStatement stm) -> {
            stm.setString(1, username);
            stm.setString(2, Crypto.hash(password));
            stm.setString(3, email);
            stm.setString(4, gender.name());
            stm.setString(5, nickname);
            stm.setInt(6, secQuestionNumber);
            stm.setString(7, securityQuestion);
        });
        return new Result<>(true, "Registration was successful");
    }


    public void femaleButtonClicked(boolean isChecked) {
        if(isChecked)
            gender = Gender.Female;
        else
            gender = null;
    }

    public void maleButtonClicked(boolean isChecked) {
        if(isChecked)
            gender = Gender.Male;
        else
            gender = null;
    }


    /**
     * This method called when registration was successfully
     */
    public void registerSuccessful(String username) {
        game.getPreferencesManager().removeRememberUser();
        game.setScreen(MainMenuScreen.class);
    }

    public void openLoginPage() {
        game.setScreen(LoginScreen.class);
    }

    public String generatePassword() {
        return Crypto.generatePassword(validator);
    }
}
