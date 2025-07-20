package com.ap.screen;

import com.ap.GdxGame;
import com.ap.asset.*;
import com.ap.database.SqliteConnection;
import com.ap.ui.model.LoadingViewModel;
import com.ap.ui.view.LoadingView;
import com.ap.utils.PreferencesManager;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class LoadingScreen extends AbstractScreen {
    private final AssetService assetService;
    private final SqliteConnection sqlite;
    private final PreferencesManager preferencesManager;

    public LoadingScreen(GdxGame game) {
        super(game);
        assetService = game.getAssetService();
        preferencesManager = game.getPreferencesManager();
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

        // Load preferences
        preferencesManager.load();
        // Loading all the assets
        // .
        // .
        for(AtlasAsset asset : AtlasAsset.values()) {
            assetService.queue(asset);
        }
        for(SoundAsset sound : SoundAsset.values()) {
            assetService.queue(sound);
        }
        for(TilesetAsset tileset : TilesetAsset.values()) {
            assetService.queue(tileset);
        }
        for(TextureAsset texture : TextureAsset.values()) {
            assetService.queue(texture);
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
        game.addScreen(new LoginScreen(game));
        game.addScreen(new ProfileScreen(game));
    }
}
