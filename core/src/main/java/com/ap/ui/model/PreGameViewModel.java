package com.ap.ui.model;

import com.ap.GdxGame;
import com.ap.asset.MapAsset;
import com.ap.database.SqliteConnection;
import com.ap.model.GameData;
import com.ap.screen.GameScreen;

public class PreGameViewModel extends ViewModel{
    private SqliteConnection sqlite;
    public PreGameViewModel(GdxGame game, SqliteConnection sqlite) {
        super(game);
        this.sqlite = sqlite;
    }

    public void startNewGame(String selectedMapName, String player1, String player2, String player3) {
        MapAsset map;
        try {
            map = MapAsset.valueOf(selectedMapName);
        }catch(Exception e) {
            return;
        }
        GameData.getInstance().setStartMap(map);
        game.setScreen(GameScreen.class);
    }
}
