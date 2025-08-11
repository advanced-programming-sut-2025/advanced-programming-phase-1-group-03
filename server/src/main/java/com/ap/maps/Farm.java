package com.ap.maps;

import com.ap.Constraints;
import com.ap.asset.AtlasAsset;
import com.ap.asset.MapAsset;
import com.ap.asset.SoundAsset;
import com.ap.audio.AudioService;
import com.ap.component.Graphic;
import com.ap.component.GreenhouseCmp;
import com.ap.items.ItemFactory;
import com.ap.managers.MapManager;
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

public class Farm extends MapAdaptor {
//
    private GrowSystem growSystem;
//    private GiantCropManager giantCropManager;
//    private CrowAttackSystem crowAttackSystem;

    public Farm(GameManager gameManager, PlayerManager playerManager, ServerPlayer player, MapManager mapManager) {
        super(gameManager, playerManager, player, mapManager);

    }

    @Override
    public void addSystems() {
        engine.addSystem(new NetworkEntitySystem(player));

        engine.addSystem(new SeasonalGraphicSystem(assetService, timeSystem));

        growSystem = new GrowSystem(weatherSystem);
        engine.addSystem(growSystem);

        engine.addSystem(new PhysicMoveSystem());
        engine.addSystem(new PhysicSystem(world, Constraints.PHYSIC_STEP_INTERVAL, mapManager, engine, playerManager));
        engine.addSystem(new FacingSystem());
        engine.addSystem(new FsmUpdateSystem());
        engine.addSystem(new AnimationSystem(player));
        engine.addSystem(new AdjustAlphaSystem(engine));
    }

    @Override
    public void setup(MapAsset map) {
        super.setup(map);

        // Send to player to create this map
        player.connection.sendTCP(new CreateMapNotifier(Helper.getEngineId(engine), map, true, true));

        // Adding systems to the engine
        addSystems();

        // Setup consumers
        setupMap();

       // giantCropManager = new GiantCropManager(this.map, world, engine);
      //  timeSystem.addTimeListener(new TimeListener());

        gameManager.getTimeSystem().addTimeListener(new TimeListener());
    }

    @Override
    public void load() {
        super.load();
    }

    @Override
    public void leave() {
        super.leave();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    public void buildGreenhouse() {
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
        player.gold -= Constraints.GREEN_HOUSE_GOLD_NEEDED;

        audioService.playSound(SoundAsset.Gift, 0.5f);

        Entity greenhouse = engine.getEntitiesFor(Family.all(GreenhouseCmp.class).get()).first();
        Graphic.mapper.get(greenhouse).setAtlas(AtlasAsset.MapObjects);
        Graphic.mapper.get(greenhouse).setAtlasKey("greenhouse_built");
        playerManager.setBuildGreenhouse(true);
    }

    class TimeListener implements ITimeListener {

        @Override
        public void onSeasonChanged(Season season) {
            Helper.playMusicOfSeason(audioService, season);
        }
        @Override
        public void onDayChanged(int day) {
            growSystem.dayPassed();
//            giantCropManager.checkGiant();
//            if(map.getProperties().get("generateItems", false, Boolean.class)) {
//                tiledMapGenerator.generateForagingTree(map);
//            }
//            crowAttackSystem.onDayChanged();
        }
    }
}
