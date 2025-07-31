package com.ap.screen.maps;

import box2dLight.RayHandler;
import com.ap.Constraints;
import com.ap.GdxGame;
import com.ap.asset.MapAsset;
import com.ap.system.GrowSystem;
import com.ap.managers.WeatherEffects;
import com.ap.model.Season;
import com.ap.screen.GameScreen;
import com.ap.system.*;
import com.ap.system.universal.ITimeListener;
import com.ap.utils.Helper;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class Farm extends MapAdaptor {

    // Use to for night darkness
    private final RayHandler rayHandler;

    private final WeatherEffects weatherEffects;

    private GrowSystem growSystem;
    private GiantCropManager giantCropManager;
    private CrowAttackSystem crowAttackSystem;

    public Farm(GdxGame game, GameScreen gameScreen) {
        super(game, gameScreen);

        weatherEffects = new WeatherEffects(assetService, engine, stage, audioService);

        RayHandler.useDiffuseLight(true);
        rayHandler = new RayHandler(world);
    }

    @Override
    public void addSystems() {
        engine.addSystem(new PhysicMoveSystem());
        engine.addSystem(new PhysicSystem(world, Constraints.PHYSIC_STEP_INTERVAL, mapManager, engine, gameScreen));
        engine.addSystem(new FacingSystem());
        engine.addSystem(new FsmUpdateSystem());
        engine.addSystem(new AnimationSystem(assetService));
        engine.addSystem(new PlayerAudioSystem(assetService));
        engine.addSystem(new ScreenBrightnessSystem(rayHandler, engine, timeSystem, weatherSystem));
        engine.addSystem(new CameraSystem(camera));
        engine.addSystem(new SeasonalGraphicSystem(assetService, timeSystem));
        engine.addSystem(new AdjustAlphaSystem(engine));


        growSystem = new GrowSystem(assetService, weatherSystem);
        engine.addSystem(growSystem);
        engine.addSystem(new RenderSystem(batch, viewport, camera));

        // Actually it would be better we create a class for forest, but because of simplicity just hardcode it
        if(mapAsset == MapAsset.Farm1 || mapAsset == MapAsset.Farm2) {
            engine.addSystem(new TileSelectionSystem(batch, itemContainer, stage, engine, world, gameScreen));
        }

        engine.addSystem(new ControllerSystem(tabManager, craftingMenu, cheatCodeBox, engine));
        engine.addSystem(new PlayerCoinSystem(clock));
        crowAttackSystem = new CrowAttackSystem(engine, world);
        engine.addSystem(crowAttackSystem);
    }

    @Override
    public void setup(MapAsset map) {
        super.setup(map);
        // Adding systems to the engine
        addSystems();

        // Setup consumers
        setupMap();

        giantCropManager = new GiantCropManager(this.map, world, engine);
        timeSystem.addTimeListener(new TimeListener());
    }

    @Override
    public void load() {
        super.load();
        weatherSystem.setWeatherConsumer(weatherEffects::onWeatherChanged);
        Helper.playMusicOfSeason(audioService, timeSystem.getSeason());
    }

    @Override
    public void leave() {
        super.leave();
        weatherEffects.removeActors();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        rayHandler.setCombinedMatrix(camera.combined);
        rayHandler.updateAndRender();
    }

    class TimeListener implements ITimeListener {

        @Override
        public void onSeasonChanged(Season season) {
            Helper.playMusicOfSeason(audioService, season);
            tiledService.changeSeasonTileset(season);
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
