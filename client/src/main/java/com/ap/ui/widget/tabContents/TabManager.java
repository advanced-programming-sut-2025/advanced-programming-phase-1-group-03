
package com.ap.ui.widget.tabContents;

import com.ap.Constraints;
import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.asset.SoundAsset;
import com.ap.audio.AudioService;
import com.ap.screen.GameScreen;
import com.ap.ui.widget.TooltipHelper;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

public class TabManager {

    protected final Stage stage;
    protected final AssetService assetService;
    protected final Skin skin;
    protected final AudioService audioService;
    private final TextureAtlas toggleAtlas;
    private boolean isShowing;


    private Group ui;


    private Array <AbstractContent> contentArray;
    private Array <ImageButton> iconArray;

    private AbstractContent currentContent = null;
    private int currentTab;

    private final float iconWidth = 48;
    private final float iconHeight = 48;

    private float iconY;
    private float iconX;


    public TabManager(GameScreen gameScreen) {
        this.stage = gameScreen.getStage();
        this.assetService = gameScreen.getAssetService();
        this.skin = gameScreen.getSkin();
        this.audioService = gameScreen.getAudioService();
        this.toggleAtlas = assetService.get(AtlasAsset.Toggles);

        ui = new Group();
        ui.setSize(Constraints.WORLD_WIDTH_RESOLUTION, Constraints.WORLD_HEIGHT_RESOLUTION);
        ui.setPosition(0, 0);
        contentArray = new Array<>();
        iconArray = new Array<>();

        int bgWidth = Constraints.tabWidth;
        int bgHeight = Constraints.tabHeight;
        contentArray.add(new InventoryTab(gameScreen, bgWidth, bgHeight, Tabs.Inventory));
        contentArray.add(new SkillTab(gameScreen, bgWidth, bgHeight, Tabs.Skill));
        contentArray.add(new SocialTab(gameScreen, bgWidth, bgHeight, Tabs.Social));
        contentArray.add(new MapTab(gameScreen, bgWidth, bgHeight, Tabs.Map));
        contentArray.add(new CraftingTab(gameScreen, bgWidth, bgHeight, Tabs.Crafting));
        contentArray.add(new OptionsTab(gameScreen, bgWidth, bgHeight, Tabs.Options));
        contentArray.add(new ExitTab(gameScreen, bgWidth, bgHeight, Tabs.Exit));

        iconY = Constraints.WORLD_HEIGHT_RESOLUTION / 2f;
        iconX = (Constraints.WORLD_WIDTH_RESOLUTION - contentArray.size * iconWidth) / 2f;

        for (AbstractContent content : contentArray) {
            ui.addActor(content);
            TextureRegion region = toggleAtlas.findRegion(content.icon.path);
            TextureRegionDrawable drawable = new TextureRegionDrawable(region);
            drawable.setMinSize(iconWidth, iconHeight);
            ImageButton button = new ImageButton(drawable);
            iconArray.add(button);
            iconY = content.getY() + content.getHeight();
            content.setVisible(false);
        }
        int tabIndex = 0;
        iconY -= 6;

        for (ImageButton button : iconArray) {
            button.setSize(iconWidth, iconHeight);
            final int idx = tabIndex;
            button.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    setCurrentContent(idx);
                    audioService.playSound(SoundAsset.HoverButton);
                }
            });
            button.setTransform(true);
            button.setOrigin(Align.center);

            button.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    button.addAction(Actions.scaleTo(1.2f, 1.2f, 0.1f));
                    audioService.playSound(SoundAsset.HoverButton);
                    TooltipHelper tooltipHelper = TooltipHelper.getTooltip();
                    Vector2 stageCoords = button.localToStageCoordinates(new Vector2(x, y));
                    tooltipHelper.setVisible(true);
                    tooltipHelper.getTitle().setText(contentArray.get(idx).icon.name());
                    tooltipHelper.getTitle().setFontScale(1f);
                    tooltipHelper.pack();
                    tooltipHelper.setPosition(stageCoords.x, stageCoords.y);
                }
                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    button.addAction(Actions.scaleTo(1f, 1f, 0.1f));
                    TooltipHelper.getTooltip().setVisible(false);
                }
            });
            button.setPosition(tabIndex * iconWidth + iconX, iconY);
            ui.addActor(button);
            ++tabIndex;
        }


        stage.addActor(ui);

        ui.addListener(new InputListener() {
            boolean shiftPressed = false;
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (Input.Keys.TAB == keycode) {
                    if (shiftPressed) previousTab();
                    else nextTab();
                    return true;
                } else if (Input.Keys.SHIFT_LEFT == keycode || Input.Keys.SHIFT_RIGHT == keycode) {
                    shiftPressed = true;
                    return true;
                }
                return false;
            }

            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT) {
                    shiftPressed = false;
                    return true;
                }
                return false;
            }
        });


        currentTab = 0;
        ui.setVisible(false);
    }

    public void toggle() {
        ui.setVisible(!ui.isVisible());
        if (ui.isVisible()) setCurrentContent(currentTab);
        if (ui.isVisible()) {
            stage.setKeyboardFocus(ui);
        }
        else {
            stage.unfocus(ui);
            for (AbstractContent content : contentArray) {
                content.setVisible(false);
            }
        }
        isShowing = !isShowing;

    }

    public void setCurrentContent(int idx) {
        AbstractContent content = contentArray.get(idx);
        if (currentContent != null) {
            currentContent.setVisible(false);
            iconArray.get(currentTab).setY(iconY);
        }
        currentContent = content;
        content.setVisible(true);
        currentTab = idx;
        iconArray.get(idx).setY(iconY - 5);
        ui.toFront();
    }

    public void setCurrentContent(Tabs tabType) {
        int idx = 0;
        for (AbstractContent abstractContent : contentArray) {
            if (abstractContent.icon == tabType) {
                setCurrentContent(idx);
                break;
            }
            ++idx;
        }
    }

    public void nextTab() {
        audioService.playSound(SoundAsset.HoverButton);
        setCurrentContent((currentTab + 1) % contentArray.size);
    }

    public void previousTab() {
        audioService.playSound(SoundAsset.HoverButton);
        setCurrentContent((currentTab - 1 + contentArray.size) % contentArray.size);
    }

    public boolean isShowing() {
        return isShowing;
    }

}