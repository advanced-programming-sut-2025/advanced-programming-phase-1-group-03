package com.ap.client.ui.model;

import com.ap.client.GdxGame;
import com.ap.client.database.SqliteConnection;
import com.ap.client.database.UserLoader;
import com.ap.client.network.GameClient;
import com.ap.client.screen.PreGameScreen;
import com.ap.client.screen.ProfileScreen;
import com.ap.client.screen.SignupScreen;
import com.badlogic.gdx.Gdx;

public class MainViewModel extends ViewModel{
    private SqliteConnection sqlite;
    private GameClient gameClient;
    private Thread connectThread;

    public MainViewModel(GdxGame game, SqliteConnection sqlite) {
        super(game);
        this.gameClient = game.getClient();
        this.sqlite = sqlite;

        connectThread = new Thread(() -> {
            while(true) {
                gameClient.tryingToConnect();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if(gameClient.isConnected()) {
                    connectionEstablished();
                }
            }
        });
    }

    private void connectionEstablished() {
        connectThread.interrupt();
        System.out.println("connected!!");
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
        return UserLoader.getLoggedInUserNickname(sqlite);
    }

    public void openProfilePage() {
        game.setScreen(ProfileScreen.class);
    }

    public int getAvatarIndex() {
        return UserLoader.getLoggedInUserAvatarIndex(sqlite);
    }

    public void tryingToConnect() {
        connectThread.start();
    }

    public void stopConnecting() {
        connectThread.interrupt();
    }
}
