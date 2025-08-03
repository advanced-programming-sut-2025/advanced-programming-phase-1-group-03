package com.ap.ui.widget.tabContents;

import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.audio.AudioService;
import com.ap.managers.AbilityManager;
import com.ap.model.AbilityType;
import com.ap.screen.GameScreen;
import com.ap.ui.widget.TooltipHelper;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class SkillTab extends AbstractContent{

    private final GameScreen gameScreen;

    private TextureAtlas skill_icons_atlas;
    private TextureRegion[] iconsTexture;
    private final TextureRegion starEmptyRegion;
    private final TextureRegion starFilledRegion;
    private AbilityManager abilityManager;
    private Group skillsGroup;

    private final float starWidth = 32;
    private final float iconWidth = 48;

    private Star[][] stars;
    private Image[] icons;
    private final int n;
    private final int m;

    public SkillTab(GameScreen gameScreen, int width, int height, Tabs icon) {
        super(gameScreen.getStage(), gameScreen.getAssetService(), gameScreen.getSkin(), gameScreen.getAudioService(), width, height, icon);
        this.gameScreen = gameScreen;
        this.abilityManager = gameScreen.getAbilityManager();
        n = AbilityType.values().length;
        m = AbilityType.maxLevel;
        skill_icons_atlas = assetService.get(AtlasAsset.SkillIcons);
        iconsTexture = new TextureRegion[n];
        starEmptyRegion = new TextureRegion(skill_icons_atlas.findRegion("star_empty"));
        starFilledRegion = new TextureRegion(skill_icons_atlas.findRegion("star_filled"));
        for (int i = 0; i < n; i++) {
            iconsTexture[i] = new TextureRegion(skill_icons_atlas.findRegion(AbilityType.values()[i].name().toLowerCase()));
        }

        makeStructure();
        loadData();
    }

    @Override
    public void makeStructure() {
        skillsGroup = new Group();
        stars = new Star[n][m];
        icons = new Image[n];

        float padBottom = 20;
        float padRightStar = 10;
        float padRightIcon = 15;

        for (int i = 0; i < n; i++) {
            icons[i] = getNewImage(iconsTexture[i], iconWidth, iconWidth);
            icons[i].setSize(iconWidth, iconWidth);
            Group row = new Group();
            row.setPosition(0,(n - 2 - i) * (iconWidth + padBottom) + iconWidth);
            row.setSize(iconWidth + padRightIcon + (m - 1) * (starWidth + padRightStar) + starWidth, iconWidth);
            icons[i].setPosition(0, 0);
            row.addActor(icons[i]);
            for (int j = 0; j < m; j++) {
                stars[i][j] = new Star();
                stars[i][j].setSize(starWidth, starWidth);
                stars[i][j].setPosition(iconWidth + padRightIcon + j * (starWidth + padRightStar) , (iconWidth - starWidth) / 2);
                row.addActor(stars[i][j]);
            }
            skillsGroup.addActor(row);
        }
        skillsGroup.setSize(iconWidth * n + padBottom * (n - 1),
                iconWidth + padRightIcon + m * starWidth + (m - 1) * padRightStar);
        skillsGroup.setPosition((width - skillsGroup.getWidth()) / 2, (height - skillsGroup.getHeight()) / 2);

        addActor(skillsGroup);

        for (int i = 0 ; i < n; i++) {
            Image image = icons[i];
            AbilityType abilityType = AbilityType.values()[i];
            TooltipHelper tooltipHelper = TooltipHelper.getTooltip();
            Label label = tooltipHelper.getTitle();
            label.setText(abilityType.name() + "\n" +
                    "current level: " + abilityManager.getAbility(abilityType).getLevel() + "\n" +
                    "note: \n" + abilityType.getDescription());
            image.addListener(new InputListener() {
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Vector2 stageCoords = image.localToStageCoordinates(new Vector2(x, y));
                        tooltipHelper.setVisible(true);
                        tooltipHelper.getTitle().setText(abilityType.name() + "\n\n" +
                                "current level: " + abilityManager.getAbility(abilityType).getLevel() + "\n\n" +
                                "note: \n" + abilityType.getDescription());
                        tooltipHelper.getTitle().setFontScale(1f);
                        tooltipHelper.pack();
                        tooltipHelper.setPosition(stageCoords.x - 200, stageCoords.y);

                        image.addAction(Actions.scaleTo(1.2f, 1.2f, 0.1f));

                }
                public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    tooltipHelper.setVisible(false);
                        image.addAction(Actions.scaleTo(1f, 1f, 0.1f));
                }
            });
        }

    }

    @Override
    public void loadData() {
        for (int i = 0; i < n; i++) {
            AbilityType type = AbilityType.values()[i];
            AbilityManager.Ability ability = gameScreen.getAbilityManager().getAbility(type);
            for (int j = 0; j < m; j++) {
                stars[i][j].setFilled(j < ability.getLevel());
            }
        }
    }

    private class Star extends Group {
        private Image empty;
        private Image filled;

        public Star() {
            empty = getNewImage(starEmptyRegion, starWidth, starWidth);
            filled = getNewImage(starFilledRegion, starWidth, starWidth);
            empty.setPosition(0, 0);
            filled.setPosition(0, 1);
            empty.setSize(starWidth, starWidth);
            filled.setSize(starWidth, starWidth);
            setSize(starWidth, starWidth);
            addActor(empty);
            addActor(filled);
            empty.setVisible(true);
            filled.setVisible(false);
        }

        public void setFilled(boolean filled) {
            this.filled.setVisible(filled);
            empty.setVisible(!filled);
        }
    }
}
