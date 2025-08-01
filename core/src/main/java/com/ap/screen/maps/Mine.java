package com.ap.screen.maps;

import box2dLight.RayHandler;
import com.ap.Constraints;
import com.ap.GdxGame;
import com.ap.asset.AssetService;
import com.ap.asset.MapAsset;
import com.ap.asset.MusicAsset;
import com.ap.audio.AudioService;
import com.ap.input.GameControllerState;
import com.ap.input.KeyboardController;
import com.ap.managers.GameUIManager;
import com.ap.managers.MapManager;
import com.ap.managers.MineManager;
import com.ap.managers.WeatherEffects;
import com.ap.model.Season;
import com.ap.screen.GameScreen;
import com.ap.system.*;
import com.ap.system.universal.ITimeListener;
import com.ap.system.universal.TimeSystem;
import com.ap.tiled.TiledAshleyConfigurator;
import com.ap.tiled.TiledMapGenerator;
import com.ap.tiled.TiledService;
import com.ap.ui.widget.Clock;
import com.ap.ui.widget.CookingMenu;
import com.ap.ui.widget.CraftingMenu;
import com.ap.ui.widget.ItemContainer;
import com.ap.ui.widget.tabContents.TabManager;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.function.Consumer;

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
