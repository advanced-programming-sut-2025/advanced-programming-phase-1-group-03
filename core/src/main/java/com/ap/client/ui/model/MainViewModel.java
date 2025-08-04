package com.ap.client.ui.model;

import com.ap.client.GdxGame;
import com.ap.client.network.GameClient;
import com.ap.client.screen.*;
import com.ap.global.requests.GetUserInfoRequest;
import com.ap.global.responses.GetUserInfoResponse;
import com.badlogic.gdx.Gdx;

public class MainViewModel extends ViewModel{
    private GameClient gameClient;
    private Thread connectThread;
    private String token;
    private Runnable connectRunnable;

    private GetUserInfoResponse info;

    public MainViewModel(GdxGame game) {
        super(game);
        token = game.getPreferencesManager().getToken();
        this.gameClient = game.getClient();
    }

    private void connectionEstablished() {
        info = gameClient.getSender().userInfo(token);
        if(info == null) {
            info = new GetUserInfoResponse(false);
        }
        Gdx.app.postRunnable(connectRunnable);
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
        return info.success ? info.username : "Guest";
    }

    public void openProfilePage() {
        game.setScreen(ProfileScreen.class);
    }

    public int getAvatarIndex() {
        return info.avatarIndex;
    }

    public void tryingToConnect() {
        connectThread = new Thread(() -> {
            while(true) {
                gameClient.tryingToConnect();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                if(gameClient.isConnected()) {
                    connectionEstablished();
                    break;
                }
            }
        });
        connectThread.start();
    }


    public void setConnectRunnable(Runnable runnable) {
        connectRunnable = runnable;
    }
}
