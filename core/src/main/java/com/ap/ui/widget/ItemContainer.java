package com.ap.ui.widget;

import com.ap.Constraints;
import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ItemContainer extends Actor {
    private final TextureAtlas atlas;
    private final AssetService assetService;
    private final Skin skin;

    private TextureRegion texture;
    private TextureRegion selectedItem;

    private int size = 1;

    private float scale = 0.4f;
    public int selectedIndex = 0;

    public ItemContainer(AssetService assetService, Skin skin, Stage stage) {
        this.assetService = assetService;
        atlas = assetService.get(AtlasAsset.UI);
        this.skin = skin;
        setupUI();
        stage.addListener(new InputListener() {
            @Override
            public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {
                boolean up = amountY < 0;
                if(up) {
                    selectedIndex ++;
                } else {
                    selectedIndex --;
                }
                selectedIndex = MathUtils.clamp(selectedIndex, 0, size - 1);
                return true;
            }
        });
    }

    private void setupUI() {
        texture = atlas.findRegion("ItemsContainer");
        selectedItem = atlas.findRegion("SelectedItem");
        setX(Constraints.WORLD_WIDTH_RESOLUTION / 2f - texture.getRegionWidth() * scale / 2f);
        setY(Constraints.WORLD_HEIGHT_RESOLUTION / 12f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture,
                getX(), getY(),
                0, 0,
                texture.getRegionWidth(), texture.getRegionHeight(),
                scale, scale,
                0);

        final float borderOffset = 16f * scale;
        float widthEach = selectedItem.getRegionWidth() * scale;
        batch.draw(selectedItem,
                getX() + widthEach * selectedIndex + borderOffset, getY() + borderOffset,
                0, 0,
                selectedItem.getRegionWidth(), selectedItem.getRegionHeight(),
                scale, scale,
                0);
    }
}
