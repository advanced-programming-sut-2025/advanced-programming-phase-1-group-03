package com.ap.ui.widget;

import com.ap.Constraints;
import com.ap.GdxGame;
import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.asset.SoundAsset;
import com.ap.audio.AudioService;
import com.ap.items.Inventory;
import com.ap.items.Item;
import com.ap.screen.GameScreen;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ItemContainer extends Actor {
    private final TextureAtlas atlas;
    private final AssetService assetService;
    private final Skin skin;
    private final Inventory inventory;
    private final AudioService audioService;

    private TextureRegion texture;
    private TextureRegion selectedItem;

    // It shows the maximum amount of items can be shown on item container
    public final static int maxSize = 12;

    private final float scale = 0.25f * 2;
    private final float itemScale = (1 / 3f) * 2;
    private boolean scrollable = true;

    public int selectedIndex = 0;

    public ItemContainer(AssetService assetService, Skin skin, Stage stage, Inventory inventory, AudioService audioService) {
        this.assetService = assetService;
        this.inventory = inventory;
        atlas = assetService.get(AtlasAsset.UI);
        this.skin = skin;
        this.audioService = audioService;
        setupUI();
        stage.addListener(new InputListener() {
            @Override
            public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {
                if(!scrollable) {
                    return true;
                }
                boolean up = amountY < 0;
                int prev = selectedIndex;
                if(up) {
                    selectedIndex ++;
                } else {
                    selectedIndex --;
                }
                int numberOfAvailableItems = Math.min(inventory.getSize(), maxSize);
                selectedIndex = (selectedIndex + numberOfAvailableItems) % numberOfAvailableItems;
                if(selectedIndex != prev) {
                    audioService.playSound(SoundAsset.Beep, 0.2f);
                }
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
        // Preventing selectedIndex exceed the inventory size
        if(selectedIndex >= inventory.getSize()) {
            selectedIndex = inventory.getSize() - 1;
        }
        batch.draw(texture,
                getX(), getY(),
                0, 0,
                texture.getRegionWidth(), texture.getRegionHeight(),
                scale, scale,
                0);

        final float borderOffset = 16f * scale;
        float widthEach = selectedItem.getRegionWidth() * itemScale * 3;

        var font = skin.getFont("font20");
        font.setColor(Color.RED);
        for(int i = 0; i < Math.min(inventory.getSize(), maxSize); i++) {
            var itemStack = inventory.getItems().get(i);
            Item item = itemStack.getItem();
            batch.draw(item.getIcon(),
                    getX() + widthEach * i + borderOffset, getY() + borderOffset,
                    0, 0,
                    48, 48,
                    itemScale, itemScale,
                    0);
        }

        batch.draw(selectedItem,
                getX() + widthEach * selectedIndex + borderOffset, getY() + borderOffset,
                0, 0,
                selectedItem.getRegionWidth(), selectedItem.getRegionHeight(),
                itemScale * 3, itemScale * 3,
                0);

        for(int i = 0; i < Math.min(inventory.getSize(), maxSize); i++) {
            var itemStack = inventory.getItems().get(i);
            if(itemStack.getAmount() <= 1) {
                continue;
            }
            font.draw(batch, String.valueOf(itemStack.getAmount()),
                    getX() + widthEach * i + borderOffset + widthEach/2-5, getY() + borderOffset + 10);
        }
    }

    public void useSelectedItem(Item.WorldObject body, Engine engine, GameScreen game, World world) {
        if (isSelectedEmpty()) return;
        inventory.getItems().get(selectedIndex).getItem().applyItem(body, engine, game, world);
    }

    public boolean isSelectedEmpty() {
        if (selectedIndex > inventory.getSize()) return true;
        return (inventory.getItems().get(selectedIndex) == null);
    }

    public boolean isScrollable() {
        return scrollable;
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }
}
