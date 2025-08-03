package com.ap.client.ui.model;

import com.ap.client.GdxGame;
import com.ap.client.asset.MapAsset;
import com.ap.client.database.SqliteConnection;
import com.ap.client.model.GameData;
import com.ap.client.screen.GameScreen;

public class LobbyViewModel extends ViewModel {
    private SqliteConnection sqlite;
    public LobbyViewModel(GdxGame game, SqliteConnection sqlite) {
        super(game);
        this.sqlite = sqlite;
    }
}
