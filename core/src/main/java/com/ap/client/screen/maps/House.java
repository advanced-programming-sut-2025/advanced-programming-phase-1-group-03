package com.ap.client.screen.maps;

import com.ap.client.Constraints;
import com.ap.client.GdxGame;
import com.ap.client.asset.MapAsset;
import com.ap.client.system.GrowSystem;
import com.ap.client.model.Season;
import com.ap.client.screen.GameScreen;
import com.ap.client.system.*;
import com.ap.client.system.universal.ITimeListener;
import com.ap.client.ui.widget.*;
import com.ap.client.ui.widget.cheatCode.CheatCodeBox;
import com.ap.client.utils.Helper;

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
        engine.addSystem(new EmoteSystem());
        // It'd be better we create separate class for green house, but we hard code it :)
        if(mapAsset == MapAsset.Greenhouse) {
            engine.addSystem(new TileSelectionSystem(batch, itemContainer, stage, engine, world, gameScreen));
        }

        switch (mapAsset) {
            case Barn, BigBarn, DeluxeBarn, Coop, DeluxeCoop, BigCoop -> {
                engine.addSystem(new TileSelectionSystem(batch, itemContainer, stage, engine, world, gameScreen));
                engine.addSystem(new CarrierSystem(engine, assetService, batch, world, audioService, this));
                engine.addSystem(new FarmAnimalSystem(engine, world));
            }
        }

        engine.addSystem(new ControllerSystem(tabManager, craftingMenu, cookingMenu, cheatCodeBox, engine, gameScreen.getAnimalStatMenu()));
        engine.addSystem(new PlayerCoinSystem(clock));

        clickSystem = new ClickSystem(game.getCamera());

        engine.addSystem(clickSystem);
        engine.addSystem(new CollectingSystem(engine, world, playerEntity, audioService));


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
