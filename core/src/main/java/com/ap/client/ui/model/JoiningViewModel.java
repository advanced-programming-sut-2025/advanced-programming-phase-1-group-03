package com.ap.client.ui.model;

import com.ap.client.GdxGame;
import com.ap.client.network.GameClient;
import com.ap.client.screen.GameScreen;
import com.ap.client.ui.view.JoiningView;
import com.ap.client.ui.view.LobbyView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;

import java.util.ArrayList;
import java.util.List;

public class JoiningViewModel extends ViewModel{
    private GameClient client;
    private Runnable uiRunnable;
    private List<JoiningView.Player> players = new ArrayList<>();

    public JoiningViewModel(GdxGame game) {
        super(game);
        this.client = game.getClient();
    }

    public void setUIRunnable(Runnable o) {
        this.uiRunnable = o;
        client.requestMyRoommatesInfo();
    }

    public List<JoiningView.Player> getPlayers() {
        return players;
    }
    public List<JoiningView.Player> getJoiningPlayers() {
        return players;
    }

    public void refresh() {
        Gdx.app.postRunnable(uiRunnable);
    }

    public boolean amIHost() {
        var response = client.getSender().amIHost();
        return response != null && response.result;
    }

    public void startGame() {
        game.setScreen(GameScreen.class);
    }

    public void sendStartGame() {
        client.getSender().startGame();
    }
}
