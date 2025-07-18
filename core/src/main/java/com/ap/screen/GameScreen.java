package com.ap.screen;

import box2dLight.RayHandler;
import com.ap.Constraints;
import com.ap.GdxGame;
import com.ap.asset.AssetService;
import com.ap.asset.MapAsset;
import com.ap.asset.MusicAsset;
import com.ap.audio.AudioService;
import com.ap.component.Facing;
import com.ap.input.GameControllerState;
import com.ap.input.KeyboardController;
import com.ap.system.*;
import com.ap.tiled.TiledAshleyConfigurator;
import com.ap.tiled.TiledService;
import com.ap.ui.model.GameViewModel;
import com.ap.ui.view.GameView;
import com.ap.ui.widget.Clock;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;

import java.util.function.Consumer;

public class GameScreen extends AbstractScreen {
    private AssetService assetService;
    private AudioService audioService;
    private Engine engine;
    private TiledService tiledService;
    private TiledAshleyConfigurator tileConfigurator;
    private KeyboardController keyboardController;
    private World world;
    private Camera camera;

    // Clock UI Component
    private Clock clock;

    // Use to for night darkness
    private RayHandler rayHandler;

    public GameScreen(GdxGame game) {
        super(game);

        camera = game.getCamera();

        engine = new Engine();

        // Setup world with zero gravity
        world = new World(Vector2.Zero, true);
        // Set autoClearForces to false because we want to apply our customized timeStep
        world.setAutoClearForces(false);

        keyboardController = new KeyboardController(GameControllerState.class, engine, stage);

        assetService = game.getAssetService();
        audioService = game.getAudioService();

        tiledService = new TiledService(assetService);
        tileConfigurator = new TiledAshleyConfigurator(engine, world);

        clock = new Clock(assetService, skin);

        RayHandler.useDiffuseLight(true);
        rayHandler = new RayHandler(world);

        // Adding systems to the engine
        engine.addSystem(new RenderSystem(game.getBatch(), game.getViewport(), game.getCamera()));
        engine.addSystem(new ControllerSystem());
        engine.addSystem(new PhysicMoveSystem());
        engine.addSystem(new PhysicDebugRenderSystem(camera, world));
        engine.addSystem(new PhysicSystem(world, Constraints.PHYSIC_STEP_INTERVAL));
        engine.addSystem(new CameraSystem(camera));
        engine.addSystem(new FacingSystem());
        engine.addSystem(new FsmUpdateSystem());
        engine.addSystem(new AnimationSystem(assetService));
        engine.addSystem(new TimeSystem(clock, rayHandler));
    }


    @Override
    public void show() {
        super.show();

        stage.addActor(new GameView(stage, skin, new GameViewModel(game), audioService));
        stage.addActor(clock);

        // Play background music
        audioService.playMusic(MusicAsset.GameMusicDefault);

        game.setInputProcessors(stage, keyboardController);

        // Setup consumers
        Consumer<TiledMap> renderConsumer = engine.getSystem(RenderSystem.class)::setMap;
        Consumer<TiledMap> cameraConsumer = engine.getSystem(CameraSystem.class)::setMap;
        tiledService.setMapChangeConsumer(renderConsumer.andThen(cameraConsumer));

        tiledService.setLoadTileConsumer(tileConfigurator::onLoadTile);
        tiledService.setLoadObjectConsumer(tileConfigurator::onLoadObject);

        TiledMap startMap = tiledService.load(MapAsset.Farm1);
        tiledService.setMap(startMap);


    }

    @Override
    public void render(float delta) {
        engine.update(delta);
        stage.act(delta);
        stage.draw();
        super.render(delta);

        rayHandler.setCombinedMatrix(camera.combined);
        rayHandler.updateAndRender();

    }
}
