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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import org.w3c.dom.ls.LSOutput;

public class CraftingMenu extends Actor {

    private final TextureRegion background;
    private final Stage stage;
    private boolean isShowing = false;

    private final AssetService assetService;
    private final Skin skin;
    private final Inventory inventory;
    private final AudioService audioService;

    private CraftingMenu instance;
    private final Array<CraftableItem> craftables = new Array<>();

    final float scale = 0.68f;
    private final float craftScale = 1.1f;
    final int sizeOfEachColumn = 12;

    private EventListener clickListener;

    public CraftingMenu(AssetService assetService, Skin skin, Stage stage, Inventory inventory, AudioService audioService) {
        this.assetService = assetService;
        this.skin = skin;
        this.stage = stage;
        this.inventory = inventory;
        this.audioService = audioService;
        background = assetService.get(AtlasAsset.Crafting).findRegion("CraftingBackground");
    }

    public void setupUI() {
        setX((Constraints.WORLD_WIDTH_RESOLUTION - background.getRegionWidth()) / 2f);
        setY((Constraints.WORLD_HEIGHT_RESOLUTION - background.getRegionHeight()) / 2f);

        loadCraftableItems();

        stage.addListener(clickListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Vector2 stageCoords = stage.screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
                float worldX = stageCoords.x;
                float worldY = stageCoords.y;
                for (CraftableItem item : craftables) {
                    if (item.isMouseOver(worldX, worldY)) {
                        Gdx.app.log("Crafting", "Clicked on: " + item.name);

                        if (!checkAvailable()) {
                            showErrorDialog("This craft item is not available!");
                        } else {
                            addItem();
                        }
                        break;
                    }
                }
                return true;
            }
        });
    }
    private void showErrorDialog(String message) {
        Dialog dialog = new Dialog("خطا", skin) {
            @Override
            protected void result(Object object) {
            }
        };

        dialog.text(message);
        dialog.button("Ok", true);
        dialog.show(stage);
    }


    private void loadCraftableItems() {
        craftables.add(new CraftableItem("Bee House",
                assetService.get(AtlasAsset.Crafting).findRegion("Bee_House"),
                new String[]{"Wood x40", "Coal x8", "Iron Bar x1", "Maple Syrup x1"},
                "Produces honey. Place outside and wait a few days."));

        craftables.add(new CraftableItem("Bomb",
                assetService.get(AtlasAsset.Crafting).findRegion("Bomb"),
                new String[]{"Iron Ore x4", "Coal x1"},
                "Creates a small explosion."));

        craftables.add(new CraftableItem("Charcoal Kiln",
                assetService.get(AtlasAsset.Crafting).findRegion("Charcoal_Kiln"),
                new String[]{"Wood x20", "Stone x30"},
                "Turns wood into coal."));

        craftables.add(new CraftableItem("Cheese Press",
                assetService.get(AtlasAsset.Crafting).findRegion("Cheese_Press"),
                new String[]{"Wood x45", "Stone x45", "Hardwood x10", "Copper Bar x1"},
                "Turns milk into cheese."));

        craftables.add(new CraftableItem("Cherry Bomb",
                assetService.get(AtlasAsset.Crafting).findRegion("Cherry_Bomb"),
                new String[]{"Copper Ore x4"},
                "Small explosion, good for mining."));

        craftables.add(new CraftableItem("Dehydrator",
                assetService.get(AtlasAsset.Crafting).findRegion("Dehydrator"),
                new String[]{"Wood x30", "Coal x5"},
                "Dries food ingredients."));

        craftables.add(new CraftableItem("Deluxe Scarecrow",
                assetService.get(AtlasAsset.Crafting).findRegion("Deluxe_Scarecrow"),
                new String[]{"Wood x100", "Fiber x50", "Rarecrow x1"},
                "Covers more area than standard scarecrow."));

        craftables.add(new CraftableItem("Fish Smoker",
                assetService.get(AtlasAsset.Crafting).findRegion("Fish_Smoker"),
                new String[]{"Wood x60", "Iron Bar x2"},
                "Turns fish into smoked delicacy."));

        craftables.add(new CraftableItem("Furnace",
                assetService.get(AtlasAsset.Crafting).findRegion("Furnace"),
                new String[]{"Copper Ore x20", "Stone x25"},
                "Smelts ore into bars. Fuel with coal."));

        craftables.add(new CraftableItem("Grass Starter",
                assetService.get(AtlasAsset.Crafting).findRegion("Grass_Starter"),
                new String[]{"Fiber x10"},
                "Grows grass for animals."));

        craftables.add(new CraftableItem("Iridium Sprinkler",
                assetService.get(AtlasAsset.Crafting).findRegion("Iridium_Sprinkler"),
                new String[]{"Gold Bar x1", "Iridium Bar x1", "Battery Pack x1"},
                "Waters 24 adjacent tiles daily."));

        craftables.add(new CraftableItem("Keg",
                assetService.get(AtlasAsset.Crafting).findRegion("Keg"),
                new String[]{"Wood x30", "Copper Bar x1", "Iron Bar x1", "Oak Resin x1"},
                "Turns fruit into wine, vegetables into juice."));

        craftables.add(new CraftableItem("Loom",
                assetService.get(AtlasAsset.Crafting).findRegion("Loom"),
                new String[]{"Wood x60", "Fiber x30", "Pine Tar x1"},
                "Turns wool into cloth."));

        craftables.add(new CraftableItem("Mayonnaise Machine",
                assetService.get(AtlasAsset.Crafting).findRegion("Mayonnaise_Machine"),
                new String[]{"Wood x15", "Stone x15", "Earth Crystal x1", "Copper Bar x1"},
                "Turns eggs into mayonnaise."));

        craftables.add(new CraftableItem("Mega Bomb",
                assetService.get(AtlasAsset.Crafting).findRegion("Mega_Bomb"),
                new String[]{"Gold Ore x4", "Solar Essence x1", "Void Essence x1"},
                "Massive explosion. Use with caution!"));

        craftables.add(new CraftableItem("Mystic Tree Seed",
                assetService.get(AtlasAsset.Crafting).findRegion("Mystic_Tree_Seed"),
                new String[]{"Sap x10", "Magic Sapling x1"},
                "Grows into a mystic tree."));

        craftables.add(new CraftableItem("Oil Maker",
                assetService.get(AtlasAsset.Crafting).findRegion("Oil_Maker"),
                new String[]{"Slime x50", "Hardwood x20", "Gold Bar x1"},
                "Turns vegetables into oil."));

        craftables.add(new CraftableItem("Preserves Jar",
                assetService.get(AtlasAsset.Crafting).findRegion("Preserves_Jar"),
                new String[]{"Wood x50", "Stone x40", "Coal x8"},
                "Turns vegetables into pickles and fruit into jam."));

        craftables.add(new CraftableItem("Quality Sprinkler",
                assetService.get(AtlasAsset.Crafting).findRegion("Quality_Sprinkler"),
                new String[]{"Iron Bar x1", "Gold Bar x1", "Refined Quartz x1"},
                "Waters 8 adjacent tiles daily."));

        craftables.add(new CraftableItem("Scarecrow",
                assetService.get(AtlasAsset.Crafting).findRegion("Scarecrow"),
                new String[]{"Wood x50", "Coal x1", "Fiber x20"},
                "Prevents crows from eating crops."));

        craftables.add(new CraftableItem("Sprinkler",
                assetService.get(AtlasAsset.Crafting).findRegion("Sprinkler"),
                new String[]{"Copper Bar x1", "Iron Bar x1"},
                "Waters 4 adjacent tiles daily."));
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(background, getX(), getY());
        drawInventoryItems(batch);
        drawItems(batch);
        drawTooltip(batch);
    }

    private void drawInventoryItems(Batch batch) {
        final float offsetX = 60f;
        final float offsetY = 115f;

        int counter = 0;

        for(ItemStack itemStack : inventory.getItems()) {
            Item item = itemStack.getItem();
            final float offsetEachX = 42.8f;
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

    private void drawItems(Batch batch) {
        final float offsetX = 20f;
        final float offsetY = 345f;
        final float offsetEachX = 70f;
        final float offsetEachY = 77f;

        int columns = 9;
        int i = 0;

        Vector2 mousePos = stage.screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        float mouseX = mousePos.x;
        float mouseY = mousePos.y;

        for (CraftableItem item : craftables) {
            int col = i % columns;
            int row = i / columns;

            if(row == 2)
                col += 3;

            float drawX = getX() + offsetX + col * offsetEachX;
            float drawY = getY() + offsetY - row * offsetEachY;

            item.x = drawX;
            item.y = drawY;
            item.width = item.icon.getRegionWidth() * scale;
            item.height = item.icon.getRegionHeight() * scale;

            float drawScale = craftScale;

            if (item.isMouseOver(mouseX, mouseY)) {
                drawScale = 1.3f;
            }
            if(checkAvailable()) {
                batch.draw(item.icon, drawX, drawY, item.width * drawScale, item.height * drawScale);
            } else {
                batch.setColor(1f, 1f, 1f, 0.4f);
                batch.draw(item.icon, drawX, drawY, item.width * drawScale, item.height * drawScale);
                batch.setColor(1f, 1f, 1f, 1f);
            }
            i++;
        }
    }


    private void drawTooltip(Batch batch) {
        Vector2 mousePos = stage.screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        float mouseX = mousePos.x;
        float mouseY = mousePos.y;


        for (CraftableItem item : craftables) {
            if (item.isMouseOver(mouseX, mouseY)) {
                float tooltipX = mouseX + 20;
                float tooltipY = mouseY - 20;

                String title = item.name;
                String ing = String.join("\n", item.ingredients);
                String desc = item.description;

                String fullText = title + "\n\nIngredients:\n" + ing + "\n\n" + desc;

                BitmapFont font = new BitmapFont();
                GlyphLayout layout = new GlyphLayout(font, fullText);
                float padding = 10f;

                batch.setColor(0, 0, 0, 0.75f);
                batch.draw(
                        assetService.getWhitePixel(),
                        tooltipX, tooltipY - layout.height - padding * 2,
                        layout.width + padding * 2,
                        layout.height + padding * 2
                );
                batch.setColor(1, 1, 1, 1);

                font.draw(batch, layout, tooltipX + padding, tooltipY - padding);
                break;
            }
        }
    }

    private boolean checkAvailable() {
        //TODO : implement availability of the craft machine
        return false;
    }

    private void addItem() {
        // TODO : add the item to inventory
    }

    public void toggle() {
        if (!isShowing) {
            instance = new CraftingMenu(assetService, skin, stage, inventory, audioService);
            instance.setupUI();
            stage.addActor(instance);
        } else {
            stage.removeListener(instance.clickListener);
            stage.getActors().removeValue(instance, true);
        }
        isShowing = !isShowing;
    }

    private static class CraftableItem {
        public final String name;
        public final TextureRegion icon;
        public final String[] ingredients;
        public final String description;

        public float x, y, width, height;

        public CraftableItem(String name, TextureRegion icon, String[] ingredients, String description) {
            this.name = name;
            this.icon = icon;
            this.ingredients = ingredients;
            this.description = description;
        }

        public boolean isMouseOver(float mouseX, float mouseY) {
            return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        }
    }

}