package com.ap.ui.model;

import com.ap.GdxGame;
import com.ap.network.GameClient;
import com.ap.screen.LoginScreen;
import com.ap.screen.MainMenuScreen;
import com.ap.model.Gender;
import com.ap.model.Result;
import com.ap.utils.Crypto;
import com.ap.utils.RegistrationValidator;

import java.util.Random;

import static com.ap.Constraints.secQuestions;

public class SignupViewModel extends ViewModel {
    private Gender gender;
    private final RegistrationValidator validator;
    private final GameClient client;
    private final int secQuestionNumber;

    public SignupViewModel(GdxGame game) {
        super(game);
        this.client = game.getClient();
        validator = new RegistrationValidator();
        secQuestionNumber = new Random().nextInt(secQuestions.length);
    }

    public String getSecQuestion() {
        return secQuestions[secQuestionNumber];
    }

    public Result<String> submit(
            String username, String email, String password, String confPassword, String nickname, String securityQuestion) {

        var result =
                client.getSender().signup(
                        username, email, password, confPassword, nickname, secQuestionNumber, securityQuestion, gender);
        if(result == null) {
            return new Result<>(false, "Server doesn't respond.");
        }
        return new Result<>(result.success, result.message);
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
        game.setScreen(MainMenuScreen.class);
    }

    public void openLoginPage() {
        game.setScreen(LoginScreen.class);
    }

    public String generatePassword() {
        return Crypto.generatePassword(validator);
    }
}
