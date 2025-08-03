package com.ap.client.screen.maps;

import com.ap.client.Constraints;
import com.ap.client.GdxGame;
import com.ap.client.asset.MapAsset;
import com.ap.client.asset.MusicAsset;
import com.ap.client.managers.MineManager;
import com.ap.client.model.Season;
import com.ap.client.screen.GameScreen;
import com.ap.client.system.*;
import com.ap.client.system.universal.ITimeListener;

public class Mine extends MapAdaptor{
    private MineManager mineManager;

    public Mine(GdxGame game, GameScreen gameScreen) {
        super(game, gameScreen);
    }

    @Override
    public void setup(MapAsset map) {
        super.setup(map);

        // Adding systems to the engine
        addSystems();

        // Setup consumers
        super.setupMap();

        mineManager = new MineManager(engine, this.map, world);

        timeSystem.addTimeListener(new TimeListener());
    }

    @Override
    public void load() {
        super.load();
        audioService.playMusic(MusicAsset.Mine);
    }

    @Override
    public void leave() {
        super.leave();
    }

    @Override
    public void addSystems() {
        engine.addSystem(new PhysicMoveSystem());
        engine.addSystem(new PhysicSystem(world, Constraints.PHYSIC_STEP_INTERVAL, mapManager, engine, gameScreen, storeManager));
        engine.addSystem(new FacingSystem());
        engine.addSystem(new FsmUpdateSystem());
        engine.addSystem(new AnimationSystem(assetService));
        engine.addSystem(new CameraSystem(camera));
        engine.addSystem(new RenderSystem(batch, viewport, camera));
        engine.addSystem(new TileSelectionSystem(batch, itemContainer, stage, engine, world, gameScreen));

        engine.addSystem(new ControllerSystem(tabManager, craftingMenu, cheatCodeBox, engine));
        engine.addSystem(new PlayerCoinSystem(clock));
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
