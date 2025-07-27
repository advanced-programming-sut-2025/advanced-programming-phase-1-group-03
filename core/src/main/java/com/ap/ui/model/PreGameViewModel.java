package com.ap.ui.model;

import com.ap.GdxGame;
import com.ap.database.SqliteConnection;

public class PreGameViewModel extends ViewModel{
    private SqliteConnection sqlite;
    public PreGameViewModel(GdxGame game, SqliteConnection sqlite) {
        super(game);
        this.sqlite = sqlite;
    }
}
