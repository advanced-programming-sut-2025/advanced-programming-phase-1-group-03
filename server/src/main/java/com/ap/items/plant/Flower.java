package com.ap.items.plant;

import com.ap.asset.AtlasAsset;
import com.ap.component.Player;
import com.ap.items.Item;
import com.ap.managers.PlayerManager;
import com.ap.model.CropsType;
import com.ap.model.EmoteType;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;

public class Flower extends Item {
    public Flower(String name,  AtlasAsset atlasAsset, String atlasKey, int sellPrice) {
        super(name, 10, atlasAsset, atlasKey, sellPrice);
    }

    @Override
    public void applyItem(WorldObject body, Engine engine, PlayerManager playerManager, World world) {
        if(!(body.getUserData() instanceof Entity player)) {
            return;
        }
        if(!Player.mapper.has(player)) {
            return;
        }
        playerManager.getInventory().removeItem(this, 1);
        var map = playerManager.getGameManager().getMapManager().currentMaps.get(playerManager.getPlayer());
        var otherPlayer = Helper.findPlayer(map.getPlayers(), Player.mapper.get(player).id);

        float friendShip = otherPlayer.playerManager.getFriendShips().getOrDefault(playerManager.getPlayer(), 0f);
        friendShip = MathUtils.clamp(friendShip + 0.5f, 0, 3f);

        otherPlayer.playerManager.getFriendShips().put(playerManager.getPlayer(), friendShip);
        playerManager.getFriendShips().put(otherPlayer, friendShip);
        playerManager.applyReaction(EmoteType.Heart.index);
        otherPlayer.playerManager.applyReaction(EmoteType.Heart.index);
    }
}
