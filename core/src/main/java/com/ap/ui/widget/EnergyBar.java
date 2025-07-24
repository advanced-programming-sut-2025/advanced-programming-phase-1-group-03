package com.ap.ui.widget;

import com.ap.Constraints;
import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class EnergyBar extends Actor {
    private final TextureAtlas atlas;
    private final AssetService assetService;
    private final Skin skin;

    private final float scale = 0.5f;
    private TextureRegion energyBarTexture;

    private float energyPercent = 1f; // 1 = 100%
    private final ShapeRenderer shapeRenderer;

    public EnergyBar(AssetService assetService, Skin skin) {
        this.assetService = assetService;
        this.atlas = assetService.get(AtlasAsset.UI);
        this.skin = skin;
        this.shapeRenderer = new ShapeRenderer();
        setupUI();
    }

    private void setupUI() {
        energyBarTexture = assetService.get(AtlasAsset.UI).findRegion("EnergyBar");
        setX(Constraints.WORLD_WIDTH_RESOLUTION - energyBarTexture.getRegionWidth() * scale - 80);
        setY(Constraints.WORLD_HEIGHT_RESOLUTION - energyBarTexture.getRegionHeight() * scale - 645);
    }

    public void setEnergyPercent(float percent) {
        this.energyPercent = Math.max(0f, Math.min(1f, percent)); // Clamp between 0 and 1
    }

    public float getEnergyPercent() {
        return energyPercent;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float energyBarXOffset = 72.7f;
        float energyBarYOffset = 87.7f;

        float x = getX() + energyBarXOffset;
        float y = getY() + energyBarYOffset;

        // 1. Draw the background (wooden frame)
        batch.draw(
                energyBarTexture,
                x, y,
                0, 0,
                energyBarTexture.getRegionWidth(),
                energyBarTexture.getRegionHeight(),
                scale, scale,
                0
        );

        // 2. Draw the energy bar using ShapeRenderer
        batch.end();

        Gdx.gl.glEnable(Gdx.gl.GL_BLEND);

        // 👇 IMPORTANT: align ShapeRenderer with Stage camera
        shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        float barWidth = 13;
        float barHeight = 83;

        float barX = x + (energyBarTexture.getRegionWidth() * scale / 2f) - barWidth / 2f;
        float barY = y + 3.5f;

        float filledHeight = barHeight * energyPercent;

        // Color from red to green depending on energy
        Color barColor = new Color(1 - energyPercent, energyPercent, 0, 1);
        shapeRenderer.setColor(barColor);
        shapeRenderer.rect(barX, barY, barWidth, filledHeight);

        shapeRenderer.end();
        Gdx.gl.glDisable(Gdx.gl.GL_BLEND);

        batch.begin();
    }

}