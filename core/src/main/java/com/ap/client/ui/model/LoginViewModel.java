package com.ap.client.ui.model;

import com.ap.client.GdxGame;
import com.ap.client.model.GameData;
import com.ap.client.network.GameClient;
import com.ap.client.screen.MainMenuScreen;
import com.ap.client.screen.SignupScreen;
import com.ap.client.utils.PreferencesManager;
import com.ap.global.utils.RegistrationValidator;
import com.ap.global.model.Result;

public class LoginViewModel extends ViewModel {
    private final PreferencesManager preferencesManager;
    private final GameClient client;

    public LoginViewModel(GdxGame game) {
        super(game);
        client = game.getClient();
        preferencesManager = new PreferencesManager();
    }

    public Result<String> submit(String username, String password, boolean stayLoggedIn) {
        var response = client.getSender().login(username, password, stayLoggedIn);
        if(response == null) {
            return new Result<>(false, "Server doesn't respond.");
        }
        if(response.success) {
            preferencesManager.rememberToken(response.token);
            System.out.println(response.token);
        }
        return new Result<>(response.success, response.message);
    }

    public void successfulLogin() {
        game.setScreen(MainMenuScreen.class);
    }

    public void openRegisterPage() {
        game.setScreen(SignupScreen.class);
    }

    public int getSecurityQuestion(String username) {
        var response = client.getSender().getSecurityQuestion(username);
        if(response == null) {
            return -1;
        }
        // It will be -1 if user doesn't exist
        return response.securityId;
    }

    public boolean isSecurityQuestionValid(String securityQuestionAnswer, String username) {
        var response = client.getSender().securityQPass(securityQuestionAnswer, username);
        if(response == null) {
            return false;
        }

        return response.success;
    }


    public Result<String> changePassword(String username, String password, String secQAns) {
        var response = client.getSender().changePassword(username, password, secQAns);

        if(response == null) {
            return new Result<>(false, "Server doesn't respond.");
        }
        return new Result<>(response.success, response.message);
    }

}
