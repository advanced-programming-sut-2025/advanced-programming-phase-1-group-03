package com.ap.model;

import com.ap.Constraints;
import com.ap.items.Inventory;
import com.ap.items.ItemStack;
import com.ap.managers.PlayerManager;

import java.util.ArrayList;
import java.util.Comparator;

public class TradeRoom {


    private Player[] players;
    private int turnIndex;

    public TradeRoom(PlayerManager player1, PlayerManager player2) {
        players = new Player[2];
        players[0] = new Player(player1);
        players[1] = new Player(player2);
    }
    public boolean addToBuffer(int playerIndex, int itemIndex) {
        if (playerIndex >= players.length || playerIndex < 0) return false;
        return players[playerIndex].addToBuffer(itemIndex);
    }
    public boolean removeFromBuffer(int playerIndex, int itemIndex) {
        if (playerIndex >= players.length || playerIndex < 0) return false;
        return players[playerIndex].removeFromBuffer(itemIndex);
    }

    public boolean checkout() {
        if (players[0].buffer.isEmpty() && players[1].buffer.isEmpty()) return false;
        if (players[0].inventory.getSize() - players[0].buffer.size() + players[1].buffer.size() > players[0].inventory.getMaxSize()) return false;
        if (players[1].inventory.getSize() - players[1].buffer.size() + players[0].buffer.size() > players[1].inventory.getMaxSize()) return false;

        ArrayList<ItemStack>[] list = new ArrayList[2];
        for (int i = 0; i < list.length; i++) {
            list[i] = new ArrayList<>();
            for (Integer index : players[i].buffer) {
                list[i].add(players[i].inventory.getItems().get(index));
            }
            for (ItemStack itemStack : list[i]) {
                players[i].inventory.removeItem(itemStack.getItem(), itemStack.getAmount());
            }
        }
        for (ItemStack itemStack : list[0]) {
            players[1].inventory.addItem(itemStack);
        }
        for (ItemStack itemStack : list[1]) {
            players[0].inventory.addItem(itemStack);
        }
        return true;
    }

    public void refresh() {
        for (int i = 0; i < 2; i++) {
            players[i].buffer.clear();
        }
    }

    private class Player{
        PlayerManager playerManager;
        Inventory inventory;
        ArrayList<Integer> buffer;

        public Player(PlayerManager playerManager) {
            this.playerManager = playerManager;
            inventory = playerManager.getInventory();
            buffer = new ArrayList<>();
        }

        public boolean addToBuffer(Integer index) {
            if (buffer.size() >= Constraints.MAX_TRADE_BUFFER_SIZE) {
                return false;
            }
            if (buffer.contains(index)) return false;
            if (index >= inventory.getSize() || index < 0) return false;
            buffer.add(index);
            return true;
        }

        public boolean removeFromBuffer(Integer index) {
            if (!buffer.contains(index)) return false;
            buffer.remove(index);
            return true;
        }
    }
}
