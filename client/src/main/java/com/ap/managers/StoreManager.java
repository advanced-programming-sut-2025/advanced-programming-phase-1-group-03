package com.ap.managers;

import com.ap.asset.SoundAsset;
import com.ap.audio.AudioService;
import com.ap.model.Menus;
import com.ap.model.StoreProduct;
import com.ap.network.GameClient;

public class StoreManager {
    private final AudioService audioService;
    private final GameClient gameClient;
    public StoreManager(AudioService audioService, GameClient gameClient) {
        this.audioService = audioService;
        this.gameClient = gameClient;
    }

    public void onBuy(StoreProduct storeProduct, Menus menu) {
        var response = gameClient.getSender().BuyItem(menu, storeProduct.enumName);
        if(response.success) {
            audioService.playSound(SoundAsset.Purchase);
        } else {
            GameUIManager.instance.showMessageDialog(response.message);
        }
    }

}
