package com.ap.screen;

import com.ap.GdxGame;
import com.ap.ui.model.LoadingViewModel;
import com.ap.ui.view.LoadingView;
import com.ap.utils.PreferencesManager;
import com.ap.asset.*;

public class LoadingScreen extends AbstractScreen {
    private final AssetService assetService;
    private final PreferencesManager preferencesManager;
    private boolean loaded = false;

    public LoadingScreen(GdxGame game) {
        super(game);
        assetService = game.getAssetService();
        preferencesManager = game.getPreferencesManager();
    }

    @Override
    public void show() {
        // Loading the view
        stage.addActor(new LoadingView(stage, skin, new LoadingViewModel(game)));

        // Load preferences
     //   preferencesManager.load();

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
        for(MapAsset map : MapAsset.values()) {
            assetService.queue(map);
        }
        for(MusicAsset musicAsset : MusicAsset.values()) {
            assetService.queue(musicAsset);
        }
    }
    @Override
    public void render(float delta) {
        super.render(delta);

        stage.act(delta);
        stage.draw();
        if(assetService.update() && !loaded) {
            loaded = true;

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
        game.addScreen(new PreGameScreen(game));
        game.addScreen(new LobbyScreen(game));
        game.addScreen(new JoiningScreen(game));
    }
}
