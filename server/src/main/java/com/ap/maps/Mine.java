package com.ap.maps;

import com.ap.Constraints;
import com.ap.asset.AtlasAsset;
import com.ap.asset.MapAsset;
import com.ap.asset.MusicAsset;
import com.ap.asset.SoundAsset;
import com.ap.component.Graphic;
import com.ap.component.GreenhouseCmp;
import com.ap.component.Player;
import com.ap.items.ItemFactory;
import com.ap.managers.MapManager;
import com.ap.managers.MineManager;
import com.ap.managers.PlayerManager;
import com.ap.model.GameManager;
import com.ap.model.Season;
import com.ap.model.ServerPlayer;
import com.ap.notifiers.CreateMapNotifier;
import com.ap.notifiers.ShowMessageNotifier;
import com.ap.system.*;
import com.ap.system.universal.ITimeListener;
import com.ap.system.universal.NetworkEntitySystem;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;

public class Mine extends MapAdaptor {
    private MineManager mineManager;

    public Mine(GameManager gameManager, MapManager mapManager, int playerId) {
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
    }

    @Override
    public void addPlayer(ServerPlayer player, MapAsset map) {
        // Send to player to create this map
        player.connection.sendTCP(new CreateMapNotifier(Helper.getEngineId(engine), map, false, true));

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

        mineManager = new MineManager(engine, this.map, world);

        gameManager.getTimeSystem().addTimeListener(new TimeListener());
    }

    @Override
    public void load(Entity player) {
        super.load(player);
        Helper.findPlayer(players, Player.mapper.get(player).id).playerManager.getAudioService().playMusic(MusicAsset.Mine);
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
            mineManager.spawnMinerals();
        }
    }
}
