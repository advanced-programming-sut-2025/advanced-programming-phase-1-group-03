package com.ap.screen;

import com.ap.GdxGame;
import com.ap.asset.AssetService;
import com.ap.asset.MapAsset;
import com.ap.audio.AudioService;
import com.ap.input.GameControllerState;
import com.ap.input.KeyboardController;
import com.ap.system.RenderSystem;
import com.ap.tiled.TiledService;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.maps.tiled.TiledMap;

import java.util.function.Consumer;

public class GameScreen extends AbstractScreen {
    private AssetService assetService;
    private AudioService audioService;
    private Engine engine;
    private TiledService tiledService;
    private KeyboardController keyboardController;

    public GameScreen(GdxGame game) {
        super(game);

        engine = new Engine();
        engine.addSystem(new RenderSystem(game.getBatch(), game.getViewport(), game.getCamera()));

        keyboardController = new KeyboardController(GameControllerState.class, engine, stage);

        assetService = game.getAssetService();
        audioService = game.getAudioService();

        tiledService = new TiledService(assetService);
    }

    @Override
    public void show() {
        super.show();

        game.setInputProcessors(stage, keyboardController);

        Consumer<TiledMap> renderConsumer = engine.getSystem(RenderSystem.class)::setMap;

        tiledService.setMapChangeConsumer(renderConsumer);

        TiledMap startMap = tiledService.load(MapAsset.Farm1);
        tiledService.setMap(startMap);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
        engine.update(delta);
        super.render(delta);
    }
}
