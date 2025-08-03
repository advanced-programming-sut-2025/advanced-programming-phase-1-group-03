package com.ap.client.network.listeners;

import com.ap.client.managers.GameUIManager;
import com.ap.global.responses.IntroductionResponse;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class LobbyListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof IntroductionResponse introductionResponse) {
        }
    }
}
