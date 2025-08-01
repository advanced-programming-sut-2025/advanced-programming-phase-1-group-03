package com.ap.screen.maps;

import com.ap.Constraints;
import com.ap.GdxGame;
import com.ap.asset.MapAsset;
import com.ap.system.GrowSystem;
import com.ap.model.Season;
import com.ap.screen.GameScreen;
import com.ap.system.*;
import com.ap.system.universal.ITimeListener;
import com.ap.ui.widget.*;
import com.ap.ui.widget.cheatCode.CheatCodeBox;
import com.ap.utils.Helper;

public class House extends MapAdaptor{
    private final CookingMenu cookingMenu;
    private CheatCodeBox cheatCodeBox;

    private GrowSystem growSystem;

    public House(GdxGame game, GameScreen gameScreen) {
        super(game, gameScreen);
        cheatCodeBox = gameScreen.getCheatCodeBox();

        cookingMenu = gameScreen.getCookingMenu();
    }

    @Override
    public void setup(MapAsset map) {
        super.setup(map);

        // Adding systems to the engine
       addSystems();

        // Setup consumers
        super.setupMap();

        timeSystem.addTimeListener(new TimeListener());

        if(map == MapAsset.Greenhouse) {
            tiledMapGenerator.makingGreenhouseFloor(this.map);
        }
    }

    @Override
    public void load() {
        super.load();
        Helper.playMusicOfSeason(audioService, timeSystem.getSeason());
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
        growSystem = new GrowSystem(assetService, weatherSystem);
        engine.addSystem(growSystem);
        engine.addSystem(new RenderSystem(batch, viewport, camera));

        // It'd be better we create separate class for green house, but we hard code it :)
        if(mapAsset == MapAsset.Greenhouse) {
            engine.addSystem(new TileSelectionSystem(batch, itemContainer, stage, engine, world, gameScreen));
        }

        engine.addSystem(new ControllerSystem(tabManager, craftingMenu, cookingMenu, cheatCodeBox, engine));
        engine.addSystem(new PlayerCoinSystem(clock));

    }

    @Override
    public void update(float delta) {
        super.update(delta);
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
        }
    }
}
