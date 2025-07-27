package com.ap.ui.widget;

import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

public class Journal extends Actor {
    private final AssetService assetService;
    private final Skin skin;
    private final Stage stage;
    private final BitmapFont font;

    private TextureAtlas atlas;

    private TextureRegion journalToggle;
    private TextureRegion journalBackground;
    private TextureRegion journalBox;
    private TextureRegion journalHeader;
    private TextureRegion journalExit;

    private final float toggleScale = 0.5f;
    private final float backgroundScale = 0.7f;
    private final float exitScale = 0.6f;
    private final float headerScale = 0.7f;

    private final float togglePosX = 1137;
    private final float togglePosY = 483;

    private final float backgroundPosX;
    private final float backgroundPosY;

    private final float exitPosX;
    private final float exitPosY;

    private final float headerPosX;
    private final float headerPosY;

    private boolean isToggled = false;

    private final Array<JournalBoxWidget> journalBoxes = new Array<>();

    public Journal(AssetService assetService, Skin skin, Stage stage) {
        this.assetService = assetService;
        this.skin = skin;
        this.stage = stage;
        this.atlas = assetService.get(AtlasAsset.Journal);
        this.font = new BitmapFont();

        setUpUI();

        backgroundPosX = 310.2f;
        backgroundPosY = 136.6f;

        exitPosX = backgroundPosX + (journalBackground.getRegionWidth() * backgroundScale) - (journalExit.getRegionWidth() * exitScale) + 15;
        exitPosY = backgroundPosY + (journalBackground.getRegionHeight() * backgroundScale) - (journalExit.getRegionHeight() * exitScale);

        headerPosX = backgroundPosX + (journalBackground.getRegionWidth() * backgroundScale - journalHeader.getRegionWidth()) / 2f + 45;
        headerPosY = backgroundPosY + (journalBackground.getRegionHeight() * backgroundScale) - (journalHeader.getRegionHeight() * headerScale) + 60;

        createJournalBoxes();

        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (isOnTarget(journalToggle, toggleScale, togglePosX, togglePosY, x, y)) {
                    isToggled = !isToggled;
                    return true;
                }
                if (isOnTarget(journalExit, exitScale, exitPosX, exitPosY, x, y)) {
                    isToggled = false;
                    return true;
                }
                return false;
            }
        });
    }

    private boolean isOnTarget(TextureRegion textureRegion, float scale, float PosX, float PosY, float x, float y) {
        float width = textureRegion.getRegionWidth() * scale;
        float height = textureRegion.getRegionHeight() * scale;
        return x >= PosX && x <= PosX + width && y >= PosY && y <= PosY + height;
    }

    private void setUpUI() {
        journalToggle = atlas.findRegion("Journal");
        journalBackground = atlas.findRegion("JournalBackground");
        journalBox = atlas.findRegion("JournalBox");
        journalHeader = atlas.findRegion("JournalHeader");
        journalExit = atlas.findRegion("Exit");
    }

    private void createJournalBoxes() {
        float boxScale = 0.714f;
        float startX = backgroundPosX + 10;
        float startY = backgroundPosY + journalBackground.getRegionHeight() - 240;

        float currentY = startY;

        for (int i = 0; i < 3; i++) {
            String text = "Journal Entry " + (i + 1);
            JournalBoxWidget box = new JournalBoxWidget(
                    journalBox,
                    text,
                    startX,
                    currentY,
                    boxScale,
                    font
            );
            journalBoxes.add(box);
            currentY -= box.getHeight();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isToggled) {
            batch.draw(journalBackground,
                    backgroundPosX, backgroundPosY,
                    0, 0,
                    journalBackground.getRegionWidth(), journalBackground.getRegionHeight(),
                    backgroundScale, backgroundScale,
                    0);

            batch.draw(journalExit,
                    exitPosX, exitPosY,
                    0, 0,
                    journalExit.getRegionWidth(), journalExit.getRegionHeight(),
                    exitScale, exitScale,
                    0);

            batch.draw(journalHeader,
                    headerPosX, headerPosY,
                    0, 0,
                    journalHeader.getRegionWidth(), journalHeader.getRegionHeight(),
                    headerScale, headerScale,
                    0);

            for (JournalBoxWidget box : journalBoxes) {
                box.draw(batch);
            }
        }

        batch.draw(journalToggle,
                togglePosX, togglePosY,
                0, 0,
                journalToggle.getRegionWidth(), journalToggle.getRegionHeight(),
                toggleScale, toggleScale,
                0);
    }
}
