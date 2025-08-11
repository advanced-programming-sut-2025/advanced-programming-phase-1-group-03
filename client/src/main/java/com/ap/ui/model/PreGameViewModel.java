package com.ap.ui.model;

import com.ap.GdxGame;
import com.ap.network.GameClient;
import com.ap.asset.MapAsset;
import com.ap.model.GameData;
import com.ap.screen.GameScreen;

public class PreGameViewModel extends ViewModel{
    private final GameClient client;

    public PreGameViewModel(GdxGame game) {
        super(game);
        client = game.getClient();
    }

    public void startNewGame(String selectedMapName) {
        new Thread(() -> {
            MapAsset map;
            try {
                map = MapAsset.valueOf(selectedMapName);
            }catch(Exception e) {
                return;
            }
            var response = client.getSender().setMap(map);
            GameData.getInstance().setStartMap(map);
            GameData.getInstance().setFarmIndex(map.ordinal());
            game.setScreen(GameScreen.class);
        }).start();
    }
}
