package com.ap.maps;

import com.ap.Constraints;
import com.ap.asset.MapAsset;
import com.ap.asset.MusicAsset;
import com.ap.component.Player;
import com.ap.managers.GameManager;
import com.ap.managers.MapManager;
import com.ap.managers.MineManager;
import com.ap.model.Season;
import com.ap.model.ServerPlayer;
import com.ap.notifiers.CreateMapNotifier;
import com.ap.system.*;
import com.ap.system.universal.ITimeListener;
import com.ap.system.universal.NetworkEntitySystem;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Entity;

public class House extends MapAdaptor{
    public House(GameManager gameManager, MapManager mapManager, int playerId) {
        super(gameManager, mapManager, playerId);
    }

    @Override
    public void addSystems() {
        engine.addSystem(new NetworkEntitySystem(players));

        engine.addSystem(new PhysicMoveSystem());
        engine.addSystem(new PhysicSystem(world, Constraints.PHYSIC_STEP_INTERVAL, mapManager, engine, players));
        engine.addSystem(new FacingSystem());
        engine.addSystem(new FsmUpdateSystem());
        engine.addSystem(new AnimationSystem(players));
        engine.addSystem(new FarmAnimalSystem(engine, world));
        engine.addSystem(new CarrierSystem(engine, world, players, playerId));

    }

    @Override
    public void addPlayer(ServerPlayer player, MapAsset map) {
        // Send to player to create this map
        player.connection.sendTCP(new CreateMapNotifier(Helper.getEngineId(engine), map, false, false));

        engine.getSystem(NetworkEntitySystem.class).shouldSend();

        players.add(player);
    }

    @Override
    public void setup(MapAsset map) {
        super.setup(map);

        // Adding systems to the engine
        addSystems();

        // Setup consumers
        setupMap();

        // giantCropManager = new GiantCropManager(this.map, world, engine);
        //  timeSystem.addTimeListener(new TimeListener());

        gameManager.getTimeSystem().addTimeListener(new TimeListener());
    }

    @Override
    public void load(Entity player) {
        super.load(player);
        players.first().playerManager.getAudioService().playMusic(MusicAsset.House);
    }

    public void placeCarrier(ServerPlayer player) {
        engine.getSystem(CarrierSystem.class).place(player);
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

    class TimeListener implements ITimeListener {

        @Override
        public void onSeasonChanged(Season season) {
        }
        @Override
        public void onDayChanged(int day) {
        }
    }
}
