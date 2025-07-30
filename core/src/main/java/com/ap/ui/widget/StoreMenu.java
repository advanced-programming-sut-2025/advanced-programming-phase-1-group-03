package com.ap.ui.widget;

import com.ap.Constraints;
import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.audio.AudioService;
import com.ap.items.Inventory;
import com.ap.items.Item;
import com.ap.items.ItemStack;
import com.ap.model.GameData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.StringBuilder;

public class StoreMenu extends Actor {
    private TextureRegion background;
    private final TextureAtlas atlas;
    private TextureRegion character;
    private String message;
    private final Stage stage;
    private boolean isShowing = false;

    private final AssetService assetService;
    private final Skin skin;
    private final Inventory inventory;
    private final AudioService audioService;

    private StoreMenu instance;

    private int row = 0;
    private float scale = 2f;

    public StoreMenu(AssetService assetService, Skin skin, Stage stage, Inventory inventory, AudioService audioService,
                     String characterString, String message) {
        this.assetService = assetService;
        this.skin = skin;
        this.stage = stage;
        this.inventory = inventory;
        this.audioService = audioService;
        this.atlas = assetService.get(AtlasAsset.Character);
        background = new TextureRegion(new Texture(Gdx.files.internal("graphics/StoreBackground.png")));
        this.message = message;
        setUpUI(characterString);

        stage.addListener(new InputListener() {
            @Override
            public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {
                if(amountY > 0)
                    row ++;
                else
                    row--;
                return true;
            }
        });
    }

    private void setUpUI(String characterString) {
        setX((Constraints.WORLD_WIDTH_RESOLUTION - background.getRegionWidth()) / 2f);
        setY((Constraints.WORLD_HEIGHT_RESOLUTION - background.getRegionHeight()) / 2f);

        background = new TextureRegion(new Texture(Gdx.files.internal("graphics/StoreBackground.png")));
        character = atlas.findRegion(characterString);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(background, getX(), getY());
        drawCharacter(batch);
        drawInventoryItems(batch);
        drawProducts(batch);
    }

    private void drawCharacter(Batch batch) {
        float offSetX = 52;
        float offSetY = 242;
        batch.draw(character, offSetX + getX(), offSetY + getY());

        // drawing the message

        BitmapFont font1 = skin.getFont("myFont22");
        BitmapFont font2 = skin.getFont("font24");

        font1.setColor(Color.BLACK);

        float messageOffsetX = 7;
        float messageOffsetY = 205;

        font1.draw(batch, message, messageOffsetX + getX(), messageOffsetY + getY(), 160f, 0, true);

        // drawing gold;

        int gold = GameData.getInstance().getPlayerGold();
        float goldOffsetY = 127f;
        float goldOffsetX = 316f;
        float widthEachFont = 13.08f;
        String goldStr = new StringBuilder(String.valueOf(gold)).reverse().toString();
        int counter = 0;
        font2.setColor(Color.RED);
        for(char c : goldStr.toCharArray()) {
            font2.draw(batch, String.valueOf(c),
                    getX() + goldOffsetX - counter * widthEachFont, getY() + goldOffsetY);
            counter ++;
        }
    }

    private void drawInventoryItems(Batch batch) {
        final float offsetX = 359f;
        final float offsetY = 94f;
        int sizeOfEachColumn = 12;
        float scale = 0.58f;

        int counter = 0;

        for(ItemStack itemStack : inventory.getItems()) {
            Item item = itemStack.getItem();
            final float offsetEachX = 34f;
            final float offsetFirstY = 14f;
            final float offsetEachY = 48f;
            float itemOffsetY = counter > sizeOfEachColumn ? offsetFirstY + offsetEachY * (int)(counter/sizeOfEachColumn) : 0;

            int index = (counter++) % sizeOfEachColumn;
            batch.draw(item.getIcon(),
                    getX() + offsetX + index * offsetEachX, getY() + offsetY - itemOffsetY,
                    0, 0,
                    48, 48,
                    scale, scale,
                    0);
        }
    }

    private void drawProducts(Batch batch) {
        TextureAtlas atlas = assetService.get(AtlasAsset.Crops);
        if(row == 0) {
            drawThisProduct(batch, atlas.findRegion("Poppy"), "Poppy", 200, 0);
            drawThisProduct(batch, atlas.findRegion("Parsnip"), "Parsnip", 80, 1);
            drawThisProduct(batch, atlas.findRegion("Melon"), "Melon", 1000, 2);
            drawThisProduct(batch, atlas.findRegion("Kale"), "Kale", 5, 3);
        }
        else {
            drawThisProduct(batch, atlas.findRegion("Parsnip"), "Parsnip", 80, 0);
            drawThisProduct(batch, atlas.findRegion("Melon"), "Melon", 1000, 1);
            drawThisProduct(batch, atlas.findRegion("Kale"), "Kale", 5, 2);
            drawThisProduct(batch, atlas.findRegion("Hot_Pepper"), "Hot_Pepper", 1000, 3);
        }
    }

    private void drawThisProduct(Batch batch, TextureRegion texture, String name, int sellPrice, int row) {
        BitmapFont font = skin.getFont("Mill24");
        font.setColor(Color.BROWN);
        float posX = 230;
        float posY = 360;
        float eachY = 57;
        batch.draw(texture, getX() + posX, getY() + posY - row * eachY - 20);
        font.draw(batch, name, getX() + posX + 30, getY() + posY - row * eachY);
        String sellString = Integer.toString(sellPrice);
        font.draw(batch, sellString, getX() + posX + 450 + (4 - sellString.length()) * 13, getY() + posY - row * eachY);
    }
}
