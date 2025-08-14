package com.ap.maps;

import com.ap.Constraints;
import com.ap.asset.AtlasAsset;
import com.ap.asset.MapAsset;
import com.ap.asset.SoundAsset;
import com.ap.component.Graphic;
import com.ap.component.GreenhouseCmp;
import com.ap.items.ItemFactory;
import com.ap.managers.MapManager;
import com.ap.managers.GameManager;
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

public class Farm extends MapAdaptor {
//
    private GrowSystem growSystem;
    private CrowAttackSystem crowAttackSystem;
   private GiantCropManager giantCropManager;

    public Farm(GameManager gameManager, MapManager mapManager, int playerId) {
        super(gameManager, mapManager, playerId);

    }

    @Override
    public void addSystems() {
        engine.addSystem(new NetworkEntitySystem(players));

        engine.addSystem(new SeasonalGraphicSystem(assetService, timeSystem));

        growSystem = new GrowSystem(weatherSystem);
        engine.addSystem(growSystem);

        engine.addSystem(new CarrierSystem(
                engine,
                world,
                players,
                playerId));
        engine.addSystem(new CollectingSystem(engine, world, players));
        engine.addSystem(new EmoteSystem());
        engine.addSystem(new PhysicMoveSystem());
        engine.addSystem(new PhysicSystem(world, Constraints.PHYSIC_STEP_INTERVAL, mapManager, engine, players));
        engine.addSystem(new FacingSystem());
        engine.addSystem(new FsmUpdateSystem());
        engine.addSystem(new AnimationSystem(players));
        engine.addSystem(new AdjustAlphaSystem(engine));
        crowAttackSystem = new CrowAttackSystem(engine, world);
        engine.addSystem(crowAttackSystem);
        engine.addSystem(new FarmAnimalSystem(engine, world));
    }

    @Override
    public void addPlayer(ServerPlayer player, MapAsset map) {
        // Send to player to create this map
        player.connection.sendTCP(new CreateMapNotifier(Helper.getEngineId(engine), map, true, true));

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

        giantCropManager = new GiantCropManager(this.map, world, engine);
      //  timeSystem.addTimeListener(new TimeListener());

        gameManager.getTimeSystem().addTimeListener(new TimeListener());
    }

    @Override
    public void load(Entity player) {
        super.load(player);
        Helper.playMusicOfSeason(players, timeSystem.getSeason());
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

    public void buildGreenhouse(ServerPlayer player) {
        var inventory = player.playerManager.getInventory();

        if(!inventory.have(ItemFactory.instance.CreateWood(), Constraints.GREEN_HOUSE_WOOD_NEEDED)) {
            player.connection.sendTCP(new ShowMessageNotifier("We don't have enough wood to build greenhouse!"));
            return;
        }
        if(player.gold < Constraints.GREEN_HOUSE_GOLD_NEEDED) {
            player.connection.sendTCP(new ShowMessageNotifier("We don't have enough gold to build greenhouse!"));
            return;
        }
        // reduce coin and wood
        inventory.removeItem(ItemFactory.instance.CreateWood(), Constraints.GREEN_HOUSE_WOOD_NEEDED);
        player.advanceGold(-Constraints.GREEN_HOUSE_GOLD_NEEDED);

        player.playerManager.getAudioService().playSound(SoundAsset.Gift, 0.5f);

        Entity greenhouse = engine.getEntitiesFor(Family.all(GreenhouseCmp.class).get()).first();
        Graphic.mapper.get(greenhouse).setAtlas(AtlasAsset.MapObjects);
        Graphic.mapper.get(greenhouse).setAtlasKey("greenhouse_built");
        player.playerManager.setBuildGreenhouse(true);
    }

    public void placeCarrier(ServerPlayer player) {
        engine.getSystem(CarrierSystem.class).place(player);
    }

    class TimeListener implements ITimeListener {

        @Override
        public void onSeasonChanged(Season season) {
            Helper.playMusicOfSeason(players, season);
        }
        @Override
        public void onDayChanged(int day) {
            growSystem.dayPassed();
            giantCropManager.checkGiant();
            if(map.getProperties().get("generateItems", false, Boolean.class)) {
                tiledMapGenerator.generateForagingTree(map);
            }
            crowAttackSystem.onDayChanged();
        }
    }
}
