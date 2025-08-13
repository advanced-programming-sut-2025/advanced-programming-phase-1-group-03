package com.ap.maps;

import com.ap.Constraints;
import com.ap.asset.MapAsset;
import com.ap.managers.MapManager;
import com.ap.managers.StoreManager;
import com.ap.managers.GameManager;
import com.ap.model.Season;
import com.ap.model.ServerPlayer;
import com.ap.notifiers.CreateMapNotifier;
import com.ap.requests.BuyItemRequest;
import com.ap.responses.BuyItemResponse;
import com.ap.system.*;
import com.ap.system.universal.ITimeListener;
import com.ap.system.universal.NetworkEntitySystem;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Entity;

public class Store extends MapAdaptor {

    private StoreManager storeManager;

    public Store(GameManager gameManager, MapManager mapManager, int playerId) {
        super(gameManager, mapManager, playerId);

        storeManager = new StoreManager();
    }

    @Override
    public void addSystems() {
        engine.addSystem(new NetworkEntitySystem(players));

        engine.addSystem(new EmoteSystem());
        engine.addSystem(new PhysicMoveSystem());
        engine.addSystem(new PhysicSystem(world, Constraints.PHYSIC_STEP_INTERVAL, mapManager, engine, players));
        engine.addSystem(new FacingSystem());
        engine.addSystem(new FsmUpdateSystem());
        engine.addSystem(new AnimationSystem(players));
    }

    @Override
    public void setup(MapAsset map) {
        super.setup(map);

        // Adding systems to the engine
        addSystems();

        // Setup consumers
        setupMap();

        gameManager.getTimeSystem().addTimeListener(new TimeListener());
    }
    @Override
    public void addPlayer(ServerPlayer player, MapAsset map) {
        // Send to player to create this map
        player.connection.sendTCP(new CreateMapNotifier(Helper.getEngineId(engine), map, false, false));

        engine.getSystem(NetworkEntitySystem.class).shouldSend();

        players.add(player);
    }

    @Override
    public void load(Entity player) {
        super.load(player);
    }

    @Override
    public Entity leave(ServerPlayer player) {
        super.leave(player);

        return null;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    public BuyItemResponse buyItem(int id, BuyItemRequest buyRequest) {
        var player = Helper.findPlayer(players, id);
        return storeManager.buy(player, buyRequest.name, buyRequest.menu);
    }

    static class TimeListener implements ITimeListener {

        @Override
        public void onSeasonChanged(Season season) {

        }
        @Override
        public void onDayChanged(int day) {

        }
    }
}
