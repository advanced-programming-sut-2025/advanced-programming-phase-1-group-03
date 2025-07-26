package com.ap.ui.widget;

import com.ap.Constraints;
import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.audio.AudioService;
import com.ap.items.Inventory;
import com.ap.items.Item;
import com.ap.items.ItemStack;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class InventoryMenu extends Actor {
    private final TextureRegion background;
    private final Stage stage;
    private boolean isShowing = false;
    TabBar tabBar;

    private final AssetService assetService;
    private final Skin skin;
    private final Inventory inventory;
    private final AudioService audioService;

    // Social textures
    private final TextureRegion socialBackground;
    private final TextureRegion socialBar;

    private InventoryMenu instance;

    final float scale = 0.8f;
    final int sizeOfEachColumn = 12;

    public InventoryMenu(AssetService assetService, Skin skin, Stage stage, Inventory inventory, AudioService audioService) {
        this.assetService = assetService;
        this.skin = skin;
        this.stage = stage;
        this.inventory = inventory;
        this.audioService = audioService;
        tabBar = TabBar.Inventory;
        // load social
        socialBackground = new TextureRegion(new Texture(Gdx.files.internal("graphics/Tabs/socialBackground.png")));
        socialBar = new TextureRegion(new Texture(Gdx.files.internal("graphics/Tabs/socialtoggle.png")));


        background = assetService.get(AtlasAsset.UI).findRegion("inventory");
        setX((Constraints.WORLD_WIDTH_RESOLUTION - background.getRegionWidth()) / 2f);
        setY((Constraints.WORLD_HEIGHT_RESOLUTION - background.getRegionHeight()) / 2f);

        System.out.println(background.getRegionWidth() + " " + background.getRegionHeight());
        System.out.println(socialBackground.getRegionWidth() + " " + socialBackground.getRegionHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(background, getX(), getY());
        //batch.draw(socialBar, )
        switch (tabBar) {
            case Inventory -> {
                drawItems(batch);
            }
        }
    }

    private void drawItems(Batch batch) {
        final float offsetX = 76f;
        final float offsetY = 403f;

        int counter = 0;

        for(ItemStack itemStack : inventory.getItems()) {
            Item item = itemStack.getItem();
            final float offsetEachX = 48f;
            final float offsetFirstY = 14f;
            final float offsetEachY = 48f;
            float itemOffsetY = counter > sizeOfEachColumn ? offsetFirstY + offsetEachY * (int)(counter/sizeOfEachColumn) : 0;

            int index = (counter++) % sizeOfEachColumn;
            batch.draw(item.getIcon(),
                    getX() + offsetX + index * offsetEachX, getY() + offsetY - itemOffsetY,
                    item.getIcon().getRegionWidth() / 2f, item.getIcon().getRegionHeight() /2f,
                    item.getIcon().getRegionWidth(), item.getIcon().getRegionHeight(),
                    scale, scale,
                    0);
        }
    }

    public void toggle() {
        if(!isShowing) {
            instance = new InventoryMenu(assetService, skin, stage, inventory, audioService);
            stage.addActor(instance);
        } else {
            stage.getActors().removeValue(instance, true);
        }
        isShowing = !isShowing;
    }
}
