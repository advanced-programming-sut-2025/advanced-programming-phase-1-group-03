package com.ap.network.listeners;

import com.ap.managers.GameUIManager;
import com.ap.model.FarmAnimalTypes;
import com.ap.notifiers.*;
import com.ap.packet.VoiceNetData;
import com.ap.screen.GameScreen;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class GameListener extends Listener {
    private GameScreen gameScreen;

    @Override
    public void received(Connection connection, Object object) {
        if(gameScreen == null) {
            return;
        }

        if(object instanceof TimeNotifier timeNotifier) {
            gameScreen.getTimeSystem().receive(timeNotifier.time);
        } else if(object instanceof WeatherNotifier weatherNotifier) {
            gameScreen.getWeatherSystem().receive(weatherNotifier);
        } else if(object instanceof ItemNotifier itemNotifier) {
            gameScreen.updateEntity(itemNotifier.engineId, itemNotifier.itemId, itemNotifier.components);
        } else if(object instanceof AnimationNotifier animationNotifier) {
            gameScreen.setAnimation(animationNotifier.atlasKey, animationNotifier.atlasAsset,
                    animationNotifier.entityId, animationNotifier.engineId, animationNotifier.speed, animationNotifier.playMode);
        }else if(object instanceof CreateMapNotifier createMapNotifier) {
            gameScreen.createMap(createMapNotifier);
        } else if(object instanceof ChangeMapNotifier changeMapNotifier) {
            gameScreen.changeMap(changeMapNotifier.engineId);
        } else if(object instanceof PlayMusicNotifier playMusicNotifier) {
            gameScreen.getAudioService().playMusic(playMusicNotifier.musicAsset, playMusicNotifier.looping, playMusicNotifier.volume);
        } else if(object instanceof PlaySoundNotifier playSoundNotifier) {
            gameScreen.getAudioService().playSound(playSoundNotifier.soundAsset, playSoundNotifier.volume);
        } else if(object instanceof RemoveEntityNotifier removeEntityNotifier) {
            gameScreen.removeEntity(removeEntityNotifier.entityId, removeEntityNotifier.engineId);
        } else if(object instanceof ShowMessageNotifier showMessageNotifier) {
            GameUIManager.instance.showMessageDialog(showMessageNotifier.message);
        } else if(object instanceof InventoryNotifier inventoryNotifier) {
            if(inventoryNotifier.isRefrigerator)
                gameScreen.getRefrigerator().load(inventoryNotifier);
            else
                gameScreen.getInventory().load(inventoryNotifier);
        } else if(object instanceof BuildGreenhouseMsgNotifier buildGreenhouseMsgNotifier) {
            GameUIManager.instance.showGreenhouseMessage(
                    buildGreenhouseMsgNotifier.goldNeeded,
                    buildGreenhouseMsgNotifier.woodNeeded,
                    gameScreen.getGameClient().getSender()::buildGreenhouse);
        } else if(object instanceof ChangeSeasonNotifier changeSeasonNotifier) {
            gameScreen.seasonChanged(changeSeasonNotifier);
        } else if(object instanceof ChatNotifier chatNotifier) {
            gameScreen.getCheatCodeBox().receivedChat(chatNotifier.isPrivate, chatNotifier.message, chatNotifier.senderName);
        } else if(object instanceof PopupNotifier popupNotifier) {
            GameUIManager.instance.showPopup(popupNotifier.message, popupNotifier.sender);
        } else if(object instanceof VoteNotifier voteNotifier) {
            GameUIManager.instance.showVotePopUp(voteNotifier.userName, voteNotifier.senderUserName, voteNotifier.id, voteNotifier.voteRequest);
        } else if(object instanceof SendGoldNotifier sendGoldNotifier) {
            gameScreen.updateGold(sendGoldNotifier.gold);
        } else if(object instanceof StoreMenuOpenOrExitNotifier request) {
            if(request.open) {
                GameUIManager.instance.displayMenu(request.menu, gameScreen.getStoreManager()::onBuy);
            } else {
                GameUIManager.instance.exitMenu(request.menu);
            }
        } else if(object instanceof TradeRoomStarterNotifier notifier) {
            gameScreen.getTradeMenu().makeInstance(notifier.starter, notifier.roomId);
        } else if(object instanceof TradeCommandNotifier notifier) {
            if(gameScreen.getTradeMenu().getInstance() != null) gameScreen.getTradeMenu().getInstance().processCommand(notifier);
        } else if(object instanceof EnergyNotifier energyNotifier) {
            gameScreen.updateEnergy(energyNotifier.amount);
        } else if(object instanceof ShowAnimalStatNotifier stat) {
            if(stat.typeOrdinal < 0 || stat.typeOrdinal >= FarmAnimalTypes.values().length) return;
            gameScreen.getAnimalStatMenu().show(stat);
        }
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }
}
