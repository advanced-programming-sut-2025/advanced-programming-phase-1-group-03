package com.ap.screen.maps;

import com.ap.Constraints;
import com.ap.GdxGame;
import com.ap.asset.MapAsset;
import com.ap.model.Season;
import com.ap.screen.GameScreen;
import com.ap.system.*;

public class Store extends MapAdaptor {

    public Store(GdxGame game, GameScreen gameScreen) {
        super(game, gameScreen);
    }

    @Override
    public void setup(MapAsset map) {
        super.setup(map);

        // Adding systems to the engine
         addSystems();

     //   timeSystem.addTimeListener(new TimeListener());

        // Setup consumers
        super.setupMap();
    }

    @Override
    public void load() {
        super.load();
        itemContainer.setScrollable(false);
    }

    @Override
    public void leave() {
        super.leave();
        itemContainer.setScrollable(true);
    }

    @Override
    public void addSystems() {
       // engine.addSystem(new PhysicMoveSystem());
       // engine.addSystem(new PhysicSystem(world, Constraints.PHYSIC_STEP_INTERVAL, mapManager, engine, gameScreen, storeManager));
    //    engine.addSystem(new FacingSystem());
        engine.addSystem(new FsmUpdateSystem());
        engine.addSystem(new AnimationSystem(assetService));
        engine.addSystem(new CameraSystem(camera));
     //   engine.addSystem(new RenderSystem(batch, viewport, camera));
       // engine.addSystem(new ControllerSystem(tabManager, craftingMenu, cheatCodeBox, engine));
        engine.addSystem(new PlayerCoinSystem(clock));
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

//    class TimeListener implements ITimeListener {
//
//        @Override
//        public void onSeasonChanged(Season season) {
//            Helper.playMusicOfSeason(audioService, season);
//            tiledService.changeSeasonTileset(season);
//        }
//        @Override
//        public void onDayChanged(int day){
//        }
//    }
}
