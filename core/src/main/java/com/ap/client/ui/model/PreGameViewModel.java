package com.ap.client.ui.model;

import com.ap.client.GdxGame;
import com.ap.client.asset.MapAsset;
import com.ap.client.model.GameData;
import com.ap.client.screen.GameScreen;

public class PreGameViewModel extends ViewModel{
    public PreGameViewModel(GdxGame game) {
        super(game);
    }

    public void startNewGame(String selectedMapName, String player1, String player2, String player3) {
        MapAsset map;
        try {
            map = MapAsset.valueOf(selectedMapName);
        }catch(Exception e) {
            return;
        }
        if(map == MapAsset.Farm1) {
            GameData.getInstance().setFarmIndex(1);
        } else if(map == MapAsset.Farm2) {
            GameData.getInstance().setFarmIndex(2);
        }
        GameData.getInstance().setStartMap(map);
        game.setScreen(GameScreen.class);
    }
}
