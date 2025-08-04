package com.ap.client.ui.model;

import com.ap.client.GdxGame;
import com.ap.client.model.GameData;
import com.ap.client.network.GameClient;
import com.ap.client.screen.MainMenuScreen;
import com.ap.global.model.Result;
import com.ap.client.screen.ProfileScreen;
import com.ap.global.responses.GetUserInfoResponse;
import com.ap.global.utils.Crypto;
import com.ap.global.utils.RegistrationValidator;
import com.badlogic.gdx.Gdx;

public class ProfileViewModel extends ViewModel{
    private final RegistrationValidator validator;
    private final GameClient client;
    private String token;

    private GetUserInfoResponse info;

    public ProfileViewModel(GdxGame game) {
        super(game);
        token = game.getPreferencesManager().getToken();
        client = game.getClient();
        validator = new RegistrationValidator();
    }

    public String getUsername() {
        return info.username;
    }

    public int getMaximumCoin() {
        return info.maximumCoin;
    }

    public int getGamesCount() {
        return info.gamesCount;
    }

    public String getEmail() {
        return info.email;
    }

    public String getNickname() {
        return info.nickname;
    }

    public int getAvatarIndex() {
        return info.avatarIndex;
    }

    public void chooseAvatar(int i) {
        client.getSender().changeAvatar(token, i);
    }

    public Result<String> changeUsername(String username) {
        var response =  client.getSender().changeUsername(token, username);
        if(response == null) {
            return new Result<>(false, "server doesn't responding");
        }
        return new Result<>(response.success, response.message);
    }

    public void reload() {
        game.setScreen(ProfileScreen.class);
    }

    public Result<String> changeNickname(String nickname) {
    //    UserLoader.changeNickname(sqlite, nickname);
        return new Result<>(true, "Nickname Changed Successfully");
    }

    public Result<String> changeEmail(String email) {
//        if(!validator.emailValidity(email)) {
//            return new Result<>(false, "Email is not valid");
//        }
//        UserLoader.changeEmail(sqlite, email);
        return new Result<>(true, "Email Changed Successfully");
    }

    public String generatePassword() {
        return Crypto.generatePassword(validator);
    }

    public Result<String> changePassword(String password) {
//        if(!validator.passwordValidity(password).isSuccess()) {
//            return validator.passwordValidity(password);
//        }
//        UserLoader.changePassword(sqlite, Crypto.hash(password));
        return new Result<>(true, "Password Changed Successfully");
    }

    public void setViewRunnable(Runnable r) {
        info = client.getSender().userInfo(token);
        Gdx.app.postRunnable(r);
    }

    public void goToMainMenu() {
        game.setScreen(MainMenuScreen.class);
    }
}
