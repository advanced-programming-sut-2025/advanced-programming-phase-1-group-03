package com.ap.client.network;

import com.ap.client.database.SqliteConnection;
import com.ap.client.database.UserLoader;
import com.ap.client.model.GameData;
import com.ap.global.requests.CreateRoomRequest;
import com.ap.global.requests.IntroductionRequest;
import com.ap.global.requests.RoomsListRequest;
import com.esotericsoftware.kryonet.Client;

public class Sender {
    private Client client;
    public Sender(Client client) {
        this.client = client;
    }
    public void introduction(SqliteConnection sqlite) {
        var request = new IntroductionRequest();
        request.name = GameData.getInstance().getLoggedUserUsername();
        request.avatar = UserLoader.getLoggedInUserAvatarIndex(sqlite);
        client.sendTCP(request);
    }

    public void requestRoomsList() {
        var request = new RoomsListRequest();
        client.sendTCP(request);
    }

    public void createRoom(String name, String password) {
        var request = new CreateRoomRequest();
        request.name = name;
        request.password = password;
        client.sendTCP(request);
    }
}
