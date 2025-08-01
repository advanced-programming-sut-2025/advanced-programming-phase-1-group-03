package com.ap.ui.widget;

import com.ap.Constraints;
import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.asset.SoundAsset;
import com.ap.audio.AudioService;
import com.ap.items.Inventory;
import com.ap.items.Item;
import com.ap.items.ItemStack;
import com.ap.model.GameData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.StringBuilder;

import java.util.ArrayList;

public class StoreMenu extends Actor {
    private TextureRegion background;
    private final TextureAtlas atlas;
    private TextureRegion character;
    private String characterString;
    private String message;
    private final Stage stage;
    private boolean isShowing = false;

    private final AssetService assetService;
    private final Skin skin;
    private final Inventory inventory;
    private final AudioService audioService;

    private StoreMenu instance;

    private ArrayList<StoreProduct> list;

    private int row = 0;
    private float scale = 2f;

    private int hoverRow = -1;
    private TextureRegion whiteTexture;

    public StoreMenu(AssetService assetService, Skin skin, Stage stage, Inventory inventory, AudioService audioService,
                     String characterString, String message) {
        this.assetService = assetService;
        this.skin = skin;
        this.stage = stage;
        this.inventory = inventory;
        this.audioService = audioService;
        this.atlas = assetService.get(AtlasAsset.Character);
        this.characterString = characterString;
        background = new TextureRegion(new Texture(Gdx.files.internal("graphics/StoreBackground.png")));
        this.message = message;
        setUpUI(characterString);
        list = new ArrayList<>();
        loadProducts();
        createWhiteTexture();

        stage.addListener(new InputListener() {
            @Override
            public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {
                if (amountY > 0)
                    row = Math.min(list.size() - 4, row + 1);
                else
                    row = Math.max(0, row - 1);
                return true;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                float rectX = 425f;
                float rectY = 508f;
                float rectWidth = 544f;

                if (x >= 968.0852f && x <= 985.6384 && y >= 506.383 && y <= 522.34045) {
                    isShowing = false;
                    stage.getActors().removeValue(instance, true);
                }

                if (x >= rectX && x <= rectX + rectWidth) {
                    int rowNum = (int) ((rectY - y) / 53.5f);
                    if (rowNum >= 0 && rowNum <= 3 && row + rowNum < list.size()) {
                        audioService.playSound(SoundAsset.HoverButton);
                        applyItem(list.get(row + rowNum));
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void applyItem(StoreProduct storeProduct) {
        // TODO implement choosing item here
    }

    private void setUpUI(String characterString) {
        setX((Constraints.WORLD_WIDTH_RESOLUTION - background.getRegionWidth()) / 2f);
        setY((Constraints.WORLD_HEIGHT_RESOLUTION - background.getRegionHeight()) / 2f);
        background = new TextureRegion(new Texture(Gdx.files.internal("graphics/StoreBackground.png")));
        character = atlas.findRegion(characterString);
    }

    private void createWhiteTexture() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        whiteTexture = new TextureRegion(new Texture(pixmap));
        pixmap.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(background, getX(), getY());
        drawCharacter(batch);
        drawInventoryItems(batch);
        updateHoverRow(batch);
        drawProducts(batch);
        drawHoverTooltip(batch);
    }

    private void updateHoverRow(Batch batch) {
        Vector2 mousePos = stage.screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        float mouseX = mousePos.x;
        float mouseY = mousePos.y;

        float rectX = 420f;
        float rectY = 508f;
        float rectWidth = 544f;
        float rectHeight = 210f;

        if (mouseX >= rectX && mouseX <= rectX + rectWidth &&
                mouseY >= rectY - rectHeight && mouseY <= rectY) {
            int rowNum = (int) ((rectY - mouseY) / 53.5f);
            if (rowNum >= 0 && rowNum <= 3 && row + rowNum < list.size()) {
                if(rowNum != hoverRow) {
                    audioService.playSound(SoundAsset.HoverButton, 0.7f);
                }
                hoverRow = rowNum;
                return;
            }
        }
        hoverRow = -1;
    }

    private void drawCharacter(Batch batch) {
        float offSetX = 52;
        float offSetY = 242;
        batch.draw(character, offSetX + getX(), offSetY + getY());

        BitmapFont font1 = skin.getFont("myFont22");
        BitmapFont font2 = skin.getFont("font24");

        font1.setColor(Color.BLACK);

        float messageOffsetX = 7;
        float messageOffsetY = 205;

        font1.draw(batch, message, messageOffsetX + getX(), messageOffsetY + getY(), 160f, 0, true);

        int gold = GameData.getInstance().getPlayerGold();
        float goldOffsetY = 127f;
        float goldOffsetX = 316f;
        float widthEachFont = 13.08f;
        String goldStr = new StringBuilder(String.valueOf(gold)).reverse().toString();
        int counter = 0;
        font2.setColor(Color.RED);
        for (char c : goldStr.toCharArray()) {
            font2.draw(batch, String.valueOf(c),
                    getX() + goldOffsetX - counter * widthEachFont, getY() + goldOffsetY);
            counter++;
        }
    }

    private void drawInventoryItems(Batch batch) {
        final float offsetX = 359f;
        final float offsetY = 94f;
        int sizeOfEachColumn = 12;
        float scale = 0.58f;

        int counter = 0;

        for (ItemStack itemStack : inventory.getItems()) {
            Item item = itemStack.getItem();
            final float offsetEachX = 34f;
            final float offsetFirstY = 14f;
            final float offsetEachY = 48f;
            float itemOffsetY = counter > sizeOfEachColumn ? offsetFirstY + offsetEachY * (int) (counter / sizeOfEachColumn) : 0;

            int index = (counter++) % sizeOfEachColumn;
            batch.draw(item.getIcon(),
                    getX() + offsetX + index * offsetEachX, getY() + offsetY - itemOffsetY,
                    0, 0,
                    48, 48,
                    scale, scale,
                    0);
        }
    }

    private void loadProducts() {
        TextureAtlas atlas = assetService.get(AtlasAsset.Crops);
        list.add(new StoreProduct(atlas.findRegion("Poppy"), "Poppy", "description test for using this elemnt on top for using it on fhf=fbkjfb fijkjnkf", 200, 0));
        list.add(new StoreProduct(atlas.findRegion("Parsnip"), "Parsnip", "description test", 80, 1));
        list.add(new StoreProduct(atlas.findRegion("Melon"), "Melon", "description test", 1000, 2));
        list.add(new StoreProduct(atlas.findRegion("Kale"), "Kale", "description test", 5, 3));
        list.add(new StoreProduct(atlas.findRegion("Hot_Pepper"), "Hot_Pepper", "description test", 1000, 4));
    }

    private void drawProducts(Batch batch) {
        if (hoverRow >= 0) {
            float posX = 230;
            float posY = 360;
            float eachY = 57;
            float rectWidth = 546f;
            float rectHeight = eachY - 7;
            batch.setColor(new Color(0.36f, 0.23f, 0.1f, 0.4f)); // قهوه‌ای نیمه‌شفاف
            batch.draw(whiteTexture, getX() + posX - 10, getY() + posY - hoverRow * eachY - 43 + 10, rectWidth, rectHeight);
            batch.setColor(Color.WHITE);
        }

        for (int i = row; i < Math.min(list.size(), row + 4); i++) {
            drawThisProduct(batch, list.get(i), i - row);
        }
    }

    private void drawHoverTooltip(Batch batch) {
        if(hoverRow < 0 || hoverRow > 3)
            return;
        StoreProduct product = list.get(hoverRow);
        BitmapFont font = new BitmapFont();
        font.setColor(Color.WHITE);


        Vector2 mousePos = stage.screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        float tooltipX = mousePos.x + 16;
        float tooltipY = mousePos.y - 16;

        float tooltipWidth = 300f;
        float tooltipHeight = 120f;


        batch.setColor(new Color(0f, 0f, 0f, 0.65f));
        batch.draw(whiteTexture, tooltipX, tooltipY, tooltipWidth, tooltipHeight);
        batch.setColor(Color.WHITE);


        float iconSize = 24f;
        batch.draw(product.texture, tooltipX + 10, tooltipY + tooltipHeight - iconSize - 10, iconSize, iconSize);


        font.draw(batch, product.name, tooltipX + iconSize + 18, tooltipY + tooltipHeight - 14);

        float descY = tooltipY + tooltipHeight - 40;
        font.draw(batch, product.description, tooltipX + 10, descY - 15, tooltipWidth - 20, 1, true);
    }



    private void drawThisProduct(Batch batch, StoreProduct storeProduct, int row) {
        BitmapFont font = skin.getFont("Mill24");
        font.setColor(Color.BROWN);
        float posX = 230;
        float posY = 360;
        float eachY = 57;
        batch.draw(storeProduct.texture, getX() + posX, getY() + posY - row * eachY - 20, 24, 24);
        font.draw(batch, storeProduct.name, getX() + posX + 30, getY() + posY - row * eachY);
        String sellString = Integer.toString(storeProduct.sellPrice);
        font.draw(batch, sellString, getX() + posX + 450 + (4 - sellString.length()) * 13, getY() + posY - row * eachY);
    }

    private static class StoreProduct {
        TextureRegion texture;
        String name;
        String description;
        int sellPrice;
        int row;

        StoreProduct(TextureRegion texture, String name, String description, int sellPrice, int row) {
            this.texture = texture;
            this.name = name;
            this.sellPrice = sellPrice;
            this.row = row;
            this.description = description;
        }
    }

    public void toggle() {
        if (!isShowing) {
            instance = new StoreMenu(assetService, skin, stage, inventory, audioService, characterString, message);
            stage.addActor(instance);
        } else {
            stage.getActors().removeValue(instance, true);
        }
        isShowing = !isShowing;
    }
}
