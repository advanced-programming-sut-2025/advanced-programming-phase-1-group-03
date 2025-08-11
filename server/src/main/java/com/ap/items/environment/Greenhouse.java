package com.ap.items.environment;

import com.ap.Constraints;
import com.ap.asset.AssetService;
import com.ap.asset.SoundAsset;
import com.ap.audio.AudioService;
import com.ap.component.Move;
import com.ap.component.Player;
import com.ap.items.Inventory;
import com.ap.items.Item;
import com.ap.items.ItemNames;
import com.ap.managers.PlayerManager;
import com.ap.notifiers.BuildGreenhouseMsgNotifier;
import com.ap.system.PhysicMoveSystem;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Greenhouse extends Item {
    public Greenhouse() {
        super(ItemNames.Greenhouse.name(), 0, null, null,0);
    }

    @Override
    public void interact(Body body, Engine engine, PlayerManager playerManager) {
        if(playerManager.isGreenhouseBuilt()) {
            return;
        }

        playerManager.getPlayer().connection.sendTCP(
                new BuildGreenhouseMsgNotifier(Constraints.GREEN_HOUSE_WOOD_NEEDED ,Constraints.GREEN_HOUSE_GOLD_NEEDED));

        engine.getSystem(PhysicMoveSystem.class).stopPlayer();
    }

}
