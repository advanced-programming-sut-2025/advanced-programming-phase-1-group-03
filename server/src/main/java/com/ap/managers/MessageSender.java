package com.ap.managers;

import com.ap.model.ServerPlayer;
import com.ap.notifiers.ShowMessageNotifier;

public class MessageSender {
    private final ServerPlayer player;
    public MessageSender(ServerPlayer player) {
        this.player = player;
    }
    public void sendMessage(String message) {
        player.connection.sendTCP(new ShowMessageNotifier(message));
    }
}
