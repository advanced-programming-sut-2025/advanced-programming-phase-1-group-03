package com.ap.client.ui.model;

import com.ap.client.GdxGame;
import com.ap.client.database.SqliteConnection;
import com.ap.client.network.GameClient;
import com.ap.client.ui.view.LobbyView;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

public class JoiningViewModel extends ViewModel{
    private SqliteConnection sqlite;
    private GameClient client;
    public JoiningViewModel(GdxGame game, SqliteConnection sqlite) {
        super(game);
        this.sqlite = sqlite;
        this.client = game.getClient();
        client.getSender().requestRoomsList();
    }
}
