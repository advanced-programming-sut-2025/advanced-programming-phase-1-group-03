package com.ap.model;

import com.ap.Constraints;
import com.ap.items.Inventory;
import com.ap.items.ItemStack;
import com.ap.managers.PlayerManager;
import com.ap.notifiers.TradeCommandNotifier;
import com.ap.packet.PlayerInfo;
import com.ap.packet.TradeRoomStarter;
import com.ap.requests.TradeCommandRequest;
import com.ap.responses.SuccessResponse;

import java.util.ArrayList;

public class TradeRoom {

    private final int roomId;

    private Player[] players;

    public TradeRoom(PlayerManager player1, PlayerManager player2, int roomId) {
        players = new Player[2];
        players[0] = new Player(player1);
        players[1] = new Player(player2);
        this.roomId = roomId;
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
        TradeHistory history = new TradeHistory();
        history.items1 = new ArrayList<>();
        history.items2 = new ArrayList<>();
        history.player1 = new PlayerInfo(players[0].playerManager.getPlayer().username, players[0].playerManager.getPlayer().avatarIndex);
        history.player2 = new PlayerInfo(players[1].playerManager.getPlayer().username, players[1].playerManager.getPlayer().avatarIndex);

        ArrayList<NetworkItemStack>[] netInv = new ArrayList[2];
        netInv[0] = players[0].playerManager.getInventory().getNetworkItems();
        netInv[1] = players[0].playerManager.getInventory().getNetworkItems();

        for (Integer i : players[0].buffer) {
            history.items1.add(netInv[0].get(i));
        }
        for (Integer i : players[1].buffer) {
            history.items2.add(netInv[1].get(i));
        }


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
        players[0].playerManager.getGameManager().getTradeHistory().add(history);
        return true;
    }

    public void refresh() {
        for (int i = 0; i < 2; i++) {
            players[i].buffer.clear();
        }
    }

    public int getRoomId() {
        return roomId;
    }

    public Player[] getPlayers() {
        return players;
    }

    //in case of ending trade return true;
    public boolean processReq(ServerPlayer senderPlayer, TradeCommandRequest req) {

        var otherPlayer = getOtherPlayer(senderPlayer);
        if (req.isAddToBuff) {
            var success = new SuccessResponse(addToBuffer(req.playerIndex, req.itemIndex));
            if (success.success) {
                senderPlayer.connection.sendTCP(new TradeCommandNotifier(req.playerIndex, req.isAddToBuff, req.isRemoveToBuff, req.itemIndex));
                otherPlayer.connection.sendTCP(new TradeCommandNotifier(req.playerIndex, req.isAddToBuff, req.isRemoveToBuff, req.itemIndex));
            }
        } else if (req.isRemoveToBuff) {
            var success = new SuccessResponse(removeFromBuffer(req.playerIndex, req.itemIndex));
            if (success.success) {
                senderPlayer.connection.sendTCP(new TradeCommandNotifier(req.playerIndex, req.isAddToBuff, req.isRemoveToBuff, req.itemIndex));
                otherPlayer.connection.sendTCP(new TradeCommandNotifier(req.playerIndex, req.isAddToBuff, req.isRemoveToBuff, req.itemIndex));
            }
        } else if(req.quit) {
            var notifier = new TradeCommandNotifier();
            notifier.close = true;
            senderPlayer.connection.sendTCP(notifier);
            notifier.message = "other player left trading";
            otherPlayer.connection.sendTCP(notifier);
            return true;
        } else if(req.acceptOffer) {
            var notifier = new TradeCommandNotifier();
            notifier.close = true;
            notifier.message = "trade was successful!";
            senderPlayer.connection.sendTCP(notifier);
            otherPlayer.connection.sendTCP(notifier);
            checkout();
            return true;
        } else if(req.rejectOffer) {
            var notifier1 = new TradeCommandNotifier();
            var notifier2 = new TradeCommandNotifier();
            notifier1.waiting = true;
            notifier2.editing = true;
            notifier2.message = "make your offer";
            senderPlayer.connection.sendTCP(notifier2);
            notifier1.message = "your offer rejected";
            otherPlayer.connection.sendTCP(notifier1);
        } else if(req.submitOffer) {
            var notifier1 = new TradeCommandNotifier();
            var notifier2 = new TradeCommandNotifier();
            notifier1.deciding = true;
            notifier2.waiting = true;
            notifier1.message = "other player sent new offer";
            otherPlayer.connection.sendTCP(notifier1);
            notifier2.message = "your offer submitted successfully";
            senderPlayer.connection.sendTCP(notifier2);
        }
        return false;
    }

    public int getPlayerIndex(ServerPlayer player) {
        if (players[0].playerManager.getPlayer().username.equals(player.username)) return 0;
        if (players[1].playerManager.getPlayer().username.equals(player.username)) return 1;
        return -1;
    }

    public boolean isPlayerExist(ServerPlayer player) {
        for (Player p : players) {
            if (p.playerManager.getPlayer().username.equals(player.username)) return true;
        }
        return false;
    }

    public ServerPlayer getOtherPlayer(ServerPlayer player) {
        if (players[0].playerManager.getPlayer().username.equals(player.username)) return players[1].playerManager.getPlayer();
        else return players[0].playerManager.getPlayer();
    }

    public TradeRoomStarter getStarter() {
        return new TradeRoomStarter(
                new PlayerInfo(players[0].playerManager.getPlayer().username, players[0].playerManager.getPlayer().avatarIndex),
                players[0].inventory.getNetworkItems(),
                players[0].inventory.getMaxSize(),
                new PlayerInfo(players[1].playerManager.getPlayer().username, players[1].playerManager.getPlayer().avatarIndex),
                players[1].inventory.getNetworkItems(),
                players[1].inventory.getMaxSize());
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
