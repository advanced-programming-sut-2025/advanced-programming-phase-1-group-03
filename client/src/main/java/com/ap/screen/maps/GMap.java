package com.ap.screen.maps;

import box2dLight.RayHandler;
import com.ap.GdxGame;
import com.ap.asset.AssetService;
import com.ap.asset.MapAsset;
import com.ap.input.GameControllerState;
import com.ap.input.KeyboardController;
import com.ap.managers.WeatherEffects;
import com.ap.model.Season;
import com.ap.screen.GameScreen;
import com.ap.system.*;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GMap {
    private Engine engine;
    private MapAsset mapAsset;
    private TiledMap map;

    private AssetService assetService;
    private int engineId;

    private GameScreen gameScreen;
    private GdxGame game;
    private final KeyboardController keyboardController;

    private final WeatherEffects weatherEffects;
    private RayHandler rayHandler;

    private boolean showWeather = false;
    private boolean tileSelection = false;

    public GMap(GameScreen gameScreen, GdxGame game, int id, MapAsset mapAsset) {
        this.gameScreen = gameScreen;
        this.game = game;
        this.engineId = id;
        this.mapAsset = mapAsset;
        this.assetService = game.getAssetService();

        engine = new Engine();
        keyboardController = new KeyboardController(GameControllerState.class, engine, gameScreen.getStage());
        this.weatherEffects = new WeatherEffects(game.getAssetService(), engine, gameScreen.getStage(), gameScreen.getAudioService());

        this.rayHandler = gameScreen.getRayHandler();

    }

    private void addSystems() {
        engine.addSystem(new SmoothMovementSystem());
        engine.addSystem(new AnimationSystem(gameScreen.getAssetService()));

        // If we can show weather we apply screen brightness system
        if(showWeather) {
            engine.addSystem(new ScreenBrightnessSystem(
                    rayHandler,
                    engine,
                    gameScreen.getTimeSystem(),
                    gameScreen.getWeatherSystem()
            ));
        }
        engine.addSystem(new RenderSystem(
                gameScreen.getBatch(), gameScreen.getViewport(), gameScreen.getCamera(), gameScreen.getAssetService()
        ));
        engine.addSystem(new CameraSystem(gameScreen.getCamera()));

        if(tileSelection) {
            engine.addSystem(new TileSelectionSystem(gameScreen.getBatch(), gameScreen.getItemContainer(), gameScreen));
        }

        engine.addSystem(new ControllerSystem(
                gameScreen.getTabManager(),
                gameScreen.getCraftingMenu(),
                gameScreen.getCookingMenu(),
                gameScreen.getCheatCodeBox(),
                gameScreen.getLeaderBoard(),
                engine,
                gameScreen.getGameClient(),
                gameScreen)
        );
    }

    public Entity createEntity() {
        var entity = new Entity();
        engine.addEntity(entity);
        return entity;
    }

    public void removeEntity(Entity entity) {
        engine.removeEntity(entity);
    }

    public void setup() {
        addSystems();

        map = assetService.get(mapAsset);
        engine.getSystem(RenderSystem.class).setMap(map);
    }

    public void update(float delta) {
        engine.update(delta);

        var stage = gameScreen.getStage();
        stage.act(delta);
        stage.draw();

        if(showWeather) {
            rayHandler.setCombinedMatrix(stage.getCamera().combined);
            rayHandler.updateAndRender();
        }
    }

    public void leave() {
        weatherEffects.removeActors();
    }

    public void load() {
        game.setInputProcessors(gameScreen.getStage(), keyboardController);
        engine.getSystem(CameraSystem.class).setMap(assetService.get(mapAsset));

        if(showWeather) {
            gameScreen.getWeatherSystem().setWeatherConsumer(weatherEffects::onWeatherChanged);
        } else {
            gameScreen.getWeatherSystem().setWeatherConsumer(null);
        }
    }

    public void showWeather() {
        showWeather = true;
    }
    public void tileSelectionSystem() { tileSelection = true;
    }

    public void changeSeasonTileset(Season newSeason) {
        Helper.changeSeasonTileset(newSeason, map, assetService);
    }
}
