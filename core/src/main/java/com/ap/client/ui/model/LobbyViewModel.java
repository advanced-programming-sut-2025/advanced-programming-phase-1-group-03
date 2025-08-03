package com.ap.ui.model;

import com.ap.GdxGame;
import com.ap.asset.MapAsset;
import com.ap.database.SqliteConnection;
import com.ap.model.GameData;
import com.ap.screen.GameScreen;

import javax.swing.text.View;

public class LobbyViewModel extends ViewModel {
    private SqliteConnection sqlite;
    public LobbyViewModel(GdxGame game, SqliteConnection sqlite) {
        super(game);
        this.sqlite = sqlite;
    }
}
