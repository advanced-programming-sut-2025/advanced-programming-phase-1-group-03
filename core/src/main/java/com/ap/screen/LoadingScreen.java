package com.ap.screen;

import com.ap.GdxGame;
import com.ap.asset.*;
import com.ap.database.SqliteConnection;
import com.ap.ui.model.LoadingViewModel;
import com.ap.ui.view.LoadingView;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LoadingScreen extends AbstractScreen {
    private final AssetService assetService;
    private final SqliteConnection sqlite;

    public LoadingScreen(GdxGame game) {
        super(game);
        assetService = game.getAssetService();
        sqlite = game.getSqlite();
    }

    @Override
    public void show() {
        // Loading the view
        stage.addActor(new LoadingView(stage, skin, new LoadingViewModel(game)));

        // Connect to database
        if(! sqlite.connect())
            throw new GdxRuntimeException("Failed to establish a connection with database");
        sqlite.createTables();

        // Loading all the assets
        // .
        // .
        for(AtlasAsset asset : AtlasAsset.values()) {
            assetService.queue(asset);
        }
        for(SoundAsset sound : SoundAsset.values()) {
            assetService.queue(sound);
        }
    }
    @Override
    public void render(float delta) {
        super.render(delta);

        stage.act(delta);
        stage.draw();
        if(assetService.update()) {
            createScreens();

            // Remove the loading screen, we don't need it anymore :)
            game.removeScreen(this);
            this.dispose();

            // Change screen to MainMenu
            game.setScreen(MainMenuScreen.class);
        }

    }

    /**
     * Create all the screens
     */
    private void createScreens() {
        game.addScreen(new MainMenuScreen(game));
        game.addScreen(new SignupScreen(game));
        game.addScreen(new GameScreen(game));
    }
}
