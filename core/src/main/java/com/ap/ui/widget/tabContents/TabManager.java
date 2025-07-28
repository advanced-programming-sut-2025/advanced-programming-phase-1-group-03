package com.ap.ui.widget.tabContents;

import com.ap.Constraints;
import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.asset.SoundAsset;
import com.ap.audio.AudioService;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class TabManager {

    protected final Stage stage;
    protected final AssetService assetService;
    protected final Skin skin;
    protected final AudioService audioService;
    private final TextureAtlas toggleAtlas;
    private boolean isShowing;

    private HorizontalGroup tabIcons;
    private Group ui;
    private Stack contents;


    private Array <AbstractContent> contentArray;
    private Array <ImageButton> iconArray;

    private AbstractContent currentContent = null;
    private int currentTab;

    private final float upPadding;
    private final float iconWidth = 48;
    private final float iconHeight = 48;

    private float iconY;


    public TabManager(Stage stage, AssetService assetService, Skin skin, AudioService audioService) {
        this.stage = stage;
        this.assetService = assetService;
        this.skin = skin;
        this.audioService = audioService;
        this.toggleAtlas = assetService.get(AtlasAsset.Toggles);

        ui = new Group();
        contents = new Stack();
        contents.pack();
        tabIcons = new HorizontalGroup();
        tabIcons.setFillParent(true);
        contentArray = new Array<>();
        iconArray = new Array<>();

        contentArray.add(new InventoryTab(stage, assetService, skin, audioService, 600, 300, Tabs.Inventory));
        contentArray.add(new SkillTab(stage, assetService, skin, audioService, 600, 300, Tabs.Skill));
        contentArray.add(new SocialTab(stage, assetService, skin, audioService, 600, 300, Tabs.Social));
        contentArray.add(new MapTab(stage, assetService, skin, audioService, 600, 300, Tabs.Map));
        contentArray.add(new OptionsTab(stage, assetService, skin, audioService, 600, 300, Tabs.Options));
        contentArray.add(new ExitTab(stage, assetService, skin, audioService, 600, 300, Tabs.Exit));

        for (AbstractContent content : contentArray) {
            contents.addActor(content);
            content.setVisible(false);
            TextureRegion region = toggleAtlas.findRegion(content.icon.path);
            TextureRegionDrawable drawable = new TextureRegionDrawable(region);
            drawable.setMinSize(iconWidth, iconHeight);
            ImageButton button = new ImageButton(drawable);
            iconArray.add(button);
        }

        int tabIndex = 0;
        for (ImageButton button : iconArray) {
            button.setSize(iconWidth, iconHeight);
            final int idx = tabIndex;
            button.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    setCurrentContent(idx);
                }
            });
            tabIcons.addActor(button);
            ++tabIndex;
        }

        upPadding = (Constraints.WORLD_HEIGHT_RESOLUTION - contentArray.get(0).getHeight()) / 2f;

        ui.addActor(contents);
        tabIcons.pack();
        tabIcons.setPosition((Constraints.WORLD_WIDTH_RESOLUTION - tabIcons.getWidth()) / 2f, Constraints.WORLD_HEIGHT_RESOLUTION - upPadding + 16);
        ui.addActor(tabIcons);

        stage.addActor(ui);

        stage.addListener(new InputListener() {
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

        iconY = tabIcons.getChild(0).getY() - 2;

        currentTab = 0;
        ui.setVisible(false);
    }

    public void toggle() {
        ui.setVisible(!ui.isVisible());
        setCurrentContent(currentTab);
        nextTab();
        previousTab();
    }

    public void setCurrentContent(int idx) {
        AbstractContent content = contentArray.get(idx);
        if (currentContent != null) {
            currentContent.setVisible(false);
        }
        for (Actor child : tabIcons.getChildren()) {
            child.setY(iconY);
        }
        currentContent = content;
        content.setVisible(true);
        currentTab = idx;
        System.out.println("idx: " + idx);
        tabIcons.getChild(currentTab).moveBy(0, -5);

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

    public void setShowing(boolean showing) {
        isShowing = showing;
    }
}
