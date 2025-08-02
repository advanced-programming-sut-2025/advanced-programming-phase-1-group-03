package com.ap.ui.widget;

import com.ap.Constraints;
import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.audio.AudioService;
import com.ap.items.Inventory;
import com.ap.items.Item;
import com.ap.items.ItemStack;
import com.ap.system.universal.EnergyManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

public class CookingMenu extends Actor {
    private final TextureRegion background;
    private final Stage stage;
    private boolean isShowing = false;

    private final AssetService assetService;
    private final Skin skin;
    private final Inventory inventory;
    private final AudioService audioService;

    private CookingMenu instance;
    private final Array<CookingItem> cookings = new Array<>();

    final float scale = 0.68f;
    private final float cookingScale = 1.1f;
    final int sizeOfEachColumn = 12;

    private EventListener clickListener;

    public CookingMenu(AssetService assetService, Skin skin, Stage stage, Inventory inventory, AudioService audioService) {
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

        loadCookingItems();

        stage.addListener(clickListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Vector2 stageCoords = stage.screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
                float worldX = stageCoords.x;
                float worldY = stageCoords.y;
                for (CookingMenu.CookingItem item : cookings) {
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
        Dialog dialog = new Dialog("Error", skin) {
            @Override
            protected void result(Object object) {
            }
        };

        dialog.text(message);
        dialog.button("Ok", true);
        dialog.show(stage);
    }


    private void loadCookingItems() {
        TextureAtlas cookingAtlas = assetService.get(AtlasAsset.Cooking);

        cookings.add(new CookingItem("Baked Fish",
                cookingAtlas.findRegion("Baked_Fish"),
                new String[]{"Sunfish x1", "Bream x1", "Wheat Flour x1"},
                "Baked fish on a bed of herbs."));

        cookings.add(new CookingItem("Bread",
                cookingAtlas.findRegion("Bread"),
                new String[]{"Wheat Flour x1"},
                "Bread! Breadmaking can be a very complex form of art, but I'll make it simple for you."));

        cookings.add(new CookingItem("Cookie",
                cookingAtlas.findRegion("Cookie"),
                new String[]{"Wheat Flour x1", "Sugar x1", "Egg x1"},
                "Cookie! Sweet treat that boosts morale."));

        cookings.add(new CookingItem("Dish O' The Sea",
                cookingAtlas.findRegion("Dish_O%27_The_Sea"),
                new String[]{"Sardine x2", "Hashbrowns x1"},
                "Dish O' The Sea boosts fishing skill."));

        cookings.add(new CookingItem("Farmer's Lunch",
                cookingAtlas.findRegion("Farmer%27s_Lunch"),
                new String[]{"Omelet x1", "Parsnip x1"},
                "Farmer's Lunch boosts farming."));

        cookings.add(new CookingItem("Fried Egg",
                cookingAtlas.findRegion("Fried_Egg"),
                new String[]{"Egg x1"},
                "Fried Egg is known by the player upon starting a new save file."));

        cookings.add(new CookingItem("Fruit Salad",
                cookingAtlas.findRegion("Fruit_Salad"),
                new String[]{"Melon x1", "Blueberry x1", "Apricot x1"},
                "Fruit Salad! Here's a healthy and delicious treat to brighten up your day."));

        cookings.add(new CookingItem("Hashbrowns",
                cookingAtlas.findRegion("Hashbrowns"),
                new String[]{"Potato x1", "Oil x1"},
                "Hashbrowns! This one's simple, but that's a good thing!" ));

        cookings.add(new CookingItem("Maki Roll",
                cookingAtlas.findRegion("Maki_Roll"),
                new String[]{"Fish x1", "Seaweed x1", "Rice x1"},
                "Maki Roll! The delicate flavor of the ocean, sealed within a pillowy cloud of rice."));

        cookings.add(new CookingItem("Miner's Treat",
                cookingAtlas.findRegion("Miner%27s_Treat"),
                new String[]{"Cave Carrot x2", "Sugar x1", "Milk x1"},
                "Miners Treat boosts mining."));

        cookings.add(new CookingItem("Omelet",
                cookingAtlas.findRegion("Omelet"),
                new String[]{"Egg x1", "Milk x1"},
                "Omelet! This is such a simple dish, but so often done incorrectly!" ));

        cookings.add(new CookingItem("Pancakes",
                cookingAtlas.findRegion("Pancakes"),
                new String[]{"Egg x1", "Wheat Flour x1"},
                "Pancakes! Sometimes I get carried away... but there's something comforting about a simple pancake."));

        cookings.add(new CookingItem("Pizza",
                cookingAtlas.findRegion("Pizza"),
                new String[]{"Wheat Flour x1", "Tomato x1", "Cheese x1"},
                "Pizza! There's a reason pizza is a timeless culinary classic."));

        cookings.add(new CookingItem("Pumpkin Pie",
                cookingAtlas.findRegion("Pumpkin_Pie"),
                new String[]{"Pumpkin x1", "Milk x1", "Sugar x1", "Wheat Flour x1"},
                "Pumpkin Pie! In my house, it's a tradition to eat pumpkin pie during the Feast of the Winter Star."));

        cookings.add(new CookingItem("Red Plate",
                cookingAtlas.findRegion("Red_Plate"),
                new String[]{"Radish x1", "Red Cabbage x1"},
                "Red Plate! Vegetable‑rich dish for stamina boost."));

        cookings.add(new CookingItem("Salad",
                cookingAtlas.findRegion("Salad"),
                new String[]{"Lettuce x1", "Cucumber x1"},
                "Salad! No bland salad here—fresh lemony greens for energy."));
        cookings.add(new CookingItem("Salmon Dinner",
                cookingAtlas.findRegion("Salmon_Dinner"),
                new String[]{"Salmon x1", "Amaranth x1"},
                "Salmon Dinner boosts fishing."));

        cookings.add(new CookingItem("Seafoam Pudding",
                cookingAtlas.findRegion("Seafoam_Pudding"),
                new String[]{"Flounder x1", "Midnight Carp x1", "Squid Ink x1"},
                "Seafoam Pudding maximizes fishing skill."));

        cookings.add(new CookingItem("Spaghetti",
                cookingAtlas.findRegion("Spaghetti"),
                new String[]{"Wheat Flour x1", "Tomato x1"},
                "Spaghetti restores energy and health."));

        cookings.add(new CookingItem("Survival Burger",
                cookingAtlas.findRegion("Survival_Burger"),
                new String[]{"Bread x1", "Cave Carrot x1", "Eggplant x1"},
                "Survival Burger boosts foraging."));

        cookings.add(new CookingItem("Tortilla",
                cookingAtlas.findRegion("Tortilla"),
                new String[]{"Corn x1"},
                "Tortillas! How many of you are gnawing on a convenience‑burrito?" ));

        cookings.add(new CookingItem("Triple Shot Espresso",
                cookingAtlas.findRegion("Triple_Shot_Espresso"),
                new String[]{"Coffee x3"},
                "Triple Shot Espresso generates massive speed for a full day."));

        cookings.add(new CookingItem("Vegetable Medley",
                cookingAtlas.findRegion("Vegetable_Medley"),
                new String[]{"Tomato x1", "Beet x1"},
                "Vegetable Medley restores health."));
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
        final float offsetY = 360f;
        final float offsetEachX = 70f;
        final float offsetEachY = 77f;

        int columns = 9;
        int i = 0;

        Vector2 mousePos = stage.screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        float mouseX = mousePos.x;
        float mouseY = mousePos.y;

        for (CookingMenu.CookingItem item : cookings) {
            int col = i % columns;
            int row = i / columns;

            if(row == 2)
                col += 2;

            float drawX = getX() + offsetX + col * offsetEachX;
            float drawY = getY() + offsetY - row * offsetEachY;

            item.x = drawX;
            item.y = drawY;
            item.width = item.icon.getRegionWidth() * scale;
            item.height = item.icon.getRegionHeight() * scale;

            float drawScale = cookingScale;

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


        for (CookingMenu.CookingItem item : cookings) {
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
        EnergyManager.getInstance().advance(-3);
        // TODO : add the item to inventory
    }

    public void toggle() {
        if (!isShowing) {
            instance = new CookingMenu(assetService, skin, stage, inventory, audioService);
            instance.setupUI();
            stage.addActor(instance);
        } else {
            stage.removeListener(instance.clickListener);
            stage.getActors().removeValue(instance, true);
        }
        isShowing = !isShowing;
    }

    private static class CookingItem {
        public final String name;
        public final TextureRegion icon;
        public final String[] ingredients;
        public final String description;

        public float x, y, width, height;

        public CookingItem(String name, TextureRegion icon, String[] ingredients, String description) {
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
