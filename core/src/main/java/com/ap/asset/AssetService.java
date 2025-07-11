package com.ap.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.github.tommyettinger.freetypist.FreeTypistSkinLoader;

public class AssetService implements Disposable {
    private final AssetManager assetManager;

    public AssetService(FileHandleResolver resolver) {
        assetManager = new AssetManager(resolver);

        // Set .tmx file loader
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(assetManager.getFileHandleResolver()));

        // Load skins with customized loader, because we use TTF fonts in skin
        assetManager.setLoader(Skin.class, new FreeTypistSkinLoader(assetManager.getFileHandleResolver()));
    }

    public <T> T load(Asset<T> asset) {
        // We must make sure that asset loaded completely
        assetManager.load(asset.getAssetDescriptor());
        assetManager.finishLoading();
        return assetManager.get(asset.getAssetDescriptor());
    }

    public <T> T get(Asset<T> asset) {
        if(!assetManager.isLoaded(asset.getAssetDescriptor())) {
            return load(asset);
        }
        return assetManager.get(asset.getAssetDescriptor());
    }

    public <T> void queue(Asset<T> asset) {
        assetManager.load(asset.getAssetDescriptor());
    }

    public boolean update() {
        return assetManager.update();
    }
    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
