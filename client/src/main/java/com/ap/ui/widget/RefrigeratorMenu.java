package com.ap.ui.widget;

import com.ap.Constraints;
import com.ap.asset.AssetService;
import com.ap.asset.TextureAsset;
import com.ap.audio.AudioService;
import com.ap.items.Inventory;
import com.ap.items.Item;
import com.ap.items.ItemStack;
import com.ap.model.NetworkItemStack;
import com.ap.screen.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;

public class RefrigeratorMenu extends Actor {
    private final TextureRegion background;
    private final Stage stage;
    private boolean isShowing = false;

    private final AssetService assetService;
    private final Skin skin;
    private final Inventory inventory;
    private final Inventory refrigerator;
    private final AudioService audioService;
    private final GameScreen gameScreen;

    private RefrigeratorMenu instance;

    final float scale = 0.68f;
    final int sizeOfEachColumn = 12;

    private EventListener clickListener;

    // Drag & Drop fields
    private ItemStack draggedItem = null;
    private Vector2 dragPosition = new Vector2();
    private boolean draggedFromInventory = false;

    // Layout constants for drawing
    private final float invOffsetX = 60f;
    private final float invOffsetY = 115f;
    private final float refOffsetX = 60f;
    private final float refOffsetY = 326f;
    private final float itemSpacingX = 42.8f;
    private final float itemFirstRowY = 14f;
    private final float itemSpacingY = 48f;

    public RefrigeratorMenu(AssetService assetService, Skin skin, Stage stage, Inventory inventory,
                            Inventory refrigerator, AudioService audioService, GameScreen gameScreen) {
        this.assetService = assetService;
        this.skin = skin;
        this.gameScreen = gameScreen;
        this.stage = stage;
        this.inventory = inventory;
        this.refrigerator = refrigerator;
        this.audioService = audioService;
        background = new TextureRegion(assetService.get(TextureAsset.Refrigerator));

        setupClickAndDrag();
    }

    public void setupUI() {
        setX((Constraints.WORLD_WIDTH_RESOLUTION - background.getRegionWidth()) / 2f);
        setY((Constraints.WORLD_HEIGHT_RESOLUTION - background.getRegionHeight()) / 2f);
        setBounds(getX(), getY(), background.getRegionWidth(), background.getRegionHeight());
    }

    /** Setup mouse listeners for drag & drop between inventory and refrigerator */
    private void setupClickAndDrag() {
        clickListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float stageX, float stageY, int pointer, int button) {
                Vector2 localCoords = stageToLocalCoordinates(new Vector2(stageX, stageY));
                draggedItem = getItemAtPosition(localCoords.x, localCoords.y);
                if (draggedItem != null) {
                    dragPosition.set(localCoords.x, localCoords.y);
                    return true;
                }
                return false;
            }

            @Override
            public void touchDragged(InputEvent event, float stageX, float stageY, int pointer) {
                if (draggedItem != null) {
                    Vector2 localCoords = stageToLocalCoordinates(new Vector2(stageX, stageY));
                    dragPosition.set(localCoords.x, localCoords.y);
                }
            }

            @Override
            public void touchUp(InputEvent event, float stageX, float stageY, int pointer, int button) {
                if (draggedItem != null) {
                    Vector2 localCoords = stageToLocalCoordinates(new Vector2(stageX, stageY));
                    if (isOverRefrigeratorArea(localCoords.x, localCoords.y) && draggedFromInventory) {
                        if(!gameScreen.getGameClient().getSender().sendIsFood(draggedItem.getItem().getName()).answer) {
                            Dialog errorDialog = new Dialog("Error", skin);
                            errorDialog.text("This item is not food.");
                            errorDialog.button("OK");
                            errorDialog.show(stage);
                            draggedItem = null;
                            return;
                        }
                        ArrayList<NetworkItemStack> networkItems = new ArrayList<>();
                            networkItems.add(new NetworkItemStack(
                                    draggedItem.getItem().getName(),
                                    draggedItem.getAmount(),
                                    draggedItem.getItem().atlasAsset(),
                                    draggedItem.getItem().getAtlasKey()));
                        System.out.println(draggedItem.getItem().getName() + " " +
                                draggedItem.getAmount() + " " +
                                draggedItem.getItem().atlasAsset() + " " +
                                draggedItem.getItem().getAtlasKey());
                            gameScreen.getGameClient().getSender().sendItem(networkItems.toArray(
                                    new NetworkItemStack[0]), 12, true);
                    } else if (isOverInventoryArea(localCoords.x, localCoords.y) && !draggedFromInventory) {
                        ArrayList<NetworkItemStack> networkItems = new ArrayList<>();
                        networkItems.add(new NetworkItemStack(
                                draggedItem.getItem().getName(),
                                draggedItem.getAmount(),
                                draggedItem.getItem().atlasAsset(),
                                draggedItem.getItem().getAtlasKey()));
                        System.out.println(draggedItem.getItem().getName() + " " +
                                draggedItem.getAmount() + " " +
                                draggedItem.getItem().atlasAsset() + " " +
                                draggedItem.getItem().getAtlasKey());
                        gameScreen.getGameClient().getSender().sendItem(networkItems.toArray(
                                new NetworkItemStack[0]), 12, false);
                    }
                    draggedItem = null;
                }
            }
        };
        stage.addListener(clickListener);
    }

    /** Find which item is at the clicked position (local coordinates to Actor) */
    private ItemStack getItemAtPosition(float lx, float ly) {
        // Check inventory items
        int counter = 0;
        for (ItemStack itemStack : inventory.getItems()) {
            int col = counter % sizeOfEachColumn;
            int row = counter / sizeOfEachColumn;
            float itemX = invOffsetX + col * itemSpacingX;
            float itemY = invOffsetY - (row > 0 ? itemFirstRowY + row * itemSpacingY : 0);

            float width = itemStack.getItem().getIcon().getRegionWidth() * scale;
            float height = itemStack.getItem().getIcon().getRegionHeight() * scale;

            if (lx >= itemX && lx <= itemX + width &&
                    ly >= itemY && ly <= itemY + height) {
                draggedFromInventory = true;
                return itemStack;
            }
            counter++;
        }

        // Check refrigerator items
        counter = 0;
        for (ItemStack itemStack : refrigerator.getItems()) {
            int col = counter % sizeOfEachColumn;
            int row = counter / sizeOfEachColumn;
            float itemX = refOffsetX + col * itemSpacingX;
            float itemY = refOffsetY - (row > 0 ? itemFirstRowY + row * itemSpacingY : 0);

            float width = itemStack.getItem().getIcon().getRegionWidth() * scale;
            float height = itemStack.getItem().getIcon().getRegionHeight() * scale;

            if (lx >= itemX && lx <= itemX + width &&
                    ly >= itemY && ly <= itemY + height) {
                draggedFromInventory = false;
                return itemStack;
            }
            counter++;
        }

        return null;
    }

    /** Check if mouse is over the refrigerator area */
    private boolean isOverRefrigeratorArea(float lx, float ly) {
        return lx >= refOffsetX && lx <= refOffsetX + sizeOfEachColumn * itemSpacingX &&
                ly >= refOffsetY - 60 && ly <= refOffsetY + 100;
    }

    /** Check if mouse is over the inventory area */
    private boolean isOverInventoryArea(float lx, float ly) {
        return lx >= invOffsetX && lx <= invOffsetX + sizeOfEachColumn * itemSpacingX &&
                ly >= invOffsetY - 60 && ly <= invOffsetY + 100;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(background, getX(), getY());
        drawInventoryItems(batch);
        drawRefrigeratorItems(batch);

        // Draw dragged item following mouse
        if (draggedItem != null) {
            batch.draw(draggedItem.getItem().getIcon(),
                    getX() + dragPosition.x - 16,
                    getY() + dragPosition.y - 16,
                    draggedItem.getItem().getIcon().getRegionWidth() / 2f,
                    draggedItem.getItem().getIcon().getRegionHeight() / 2f,
                    draggedItem.getItem().getIcon().getRegionWidth(),
                    draggedItem.getItem().getIcon().getRegionHeight(),
                    scale, scale, 0);
        }
    }

    private void drawInventoryItems(Batch batch) {
        int counter = 0;
        for (ItemStack itemStack : inventory.getItems()) {
            Item item = itemStack.getItem();
            int col = counter % sizeOfEachColumn;
            int row = counter / sizeOfEachColumn;
            float itemOffsetY = row > 0 ? itemFirstRowY + row * itemSpacingY : 0;

            batch.draw(item.getIcon(),
                    getX() + invOffsetX + col * itemSpacingX, getY() + invOffsetY - itemOffsetY,
                    item.getIcon().getRegionWidth() / 2f, item.getIcon().getRegionHeight() / 2f,
                    item.getIcon().getRegionWidth(), item.getIcon().getRegionHeight(),
                    scale, scale, 0);
            counter++;
        }
    }

    private void drawRefrigeratorItems(Batch batch) {
        int counter = 0;
        for (ItemStack itemStack : refrigerator.getItems()) {
            Item item = itemStack.getItem();
            int col = counter % sizeOfEachColumn;
            int row = counter / sizeOfEachColumn;
            float itemOffsetY = row > 0 ? itemFirstRowY + row * itemSpacingY : 0;

            batch.draw(item.getIcon(),
                    getX() + refOffsetX + col * itemSpacingX, getY() + refOffsetY - itemOffsetY,
                    item.getIcon().getRegionWidth() / 2f, item.getIcon().getRegionHeight() / 2f,
                    item.getIcon().getRegionWidth(), item.getIcon().getRegionHeight(),
                    scale, scale, 0);
            counter++;
        }
    }

    public void toggle() {
        if (!isShowing) {
            instance = new RefrigeratorMenu(assetService, skin, stage, inventory, refrigerator, audioService, gameScreen);
            instance.setupUI();
            stage.addActor(instance);
        } else {
            stage.removeListener(instance.clickListener);
            stage.getActors().removeValue(instance, true);
        }
        isShowing = !isShowing;
    }
}
