package com.ap.ui.model;

import com.ap.GdxGame;
import com.ap.network.GameClient;
import com.ap.screen.JoiningScreen;
import com.ap.ui.view.LobbyView;
import com.ap.model.Result;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

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

    public void whenBack() {
        client.getSender().quitRoom();
    }

    public void joinById(int i) {
    }
}
