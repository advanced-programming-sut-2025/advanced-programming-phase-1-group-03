package com.ap.client.ui.model;

import com.ap.client.GdxGame;
import com.ap.client.asset.MapAsset;
import com.ap.client.model.GameData;
import com.ap.client.network.GameClient;
import com.ap.client.screen.GameScreen;
import com.ap.client.screen.JoiningScreen;
import com.ap.client.ui.view.LobbyView;
import com.ap.global.model.Result;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class LobbyViewModel extends ViewModel {
    private GameClient client;

    private Runnable roomsObserver;

    private List<LobbyView.ServerEntry> rooms = new ArrayList<>();
    public LobbyViewModel(GdxGame game) {
        super(game);
        this.client = game.getClient();
        client.getSender().requestRoomsList();
    }

    public List<LobbyView.ServerEntry> getRooms() {
        return rooms;
    }

    public void setRoomsObserver(Runnable runnable) {
        this.roomsObserver = runnable;
    }


    public void hostServer(String name, String password, boolean isVisible) {
        client.getSender().createRoom(name, password, isVisible);
        game.setScreen(JoiningScreen.class);
    }

    public void setRooms(List<LobbyView.ServerEntry> rooms) {
        this.rooms = rooms;
        updateView();
    }
    public void updateView() {
        Gdx.app.postRunnable(roomsObserver);
    }
    public void refresh() {
        client.getSender().requestRoomsList();
    }

    public Result<String> joinRoom(int roomId, String password) {
        var response = client.getSender().joinRoom(roomId, password);
        if(response == null) {
            return new Result<>(false, "server didn't respond");
        }
        return new Result<>(response.success, response.message);
    }

    public void loadJoinScreen() {
        game.setScreen(JoiningScreen.class);
    }
}
