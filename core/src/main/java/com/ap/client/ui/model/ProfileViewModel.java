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
        client.getSender().changeNickname(token, nickname);
        return new Result<>(true, "Nickname Changed Successfully");
    }

    public Result<String> changeEmail(String email) {
        var response = client.getSender().changeEmail(token, email);
        if(response == null) {
            return new Result<>(false, "Server is not responding");
        }
        return new Result<>(response.success, response.message);
    }

    public String generatePassword() {
        return Crypto.generatePassword(validator);
    }

    public Result<String> changePassword(String password) {
        var response = client.getSender().changePassword(token, password);
        if(response == null) {
            return new Result<>(false, "server doesn't responding");
        }
        return new Result<>(response.success, response.message);
    }

    public void setViewRunnable(Runnable r) {
        info = client.getSender().userInfo(token);
        Gdx.app.postRunnable(r);
    }

    public void goToMainMenu() {
        game.setScreen(MainMenuScreen.class);
    }
}
