package com.ap.component;

import com.ap.asset.AtlasAsset;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Objects;

public class Graphic implements Component {
    public static final ComponentMapper<Graphic> mapper = ComponentMapper.getFor(Graphic.class);

    private AtlasAsset atlas;
    private String atlasKey;
    private TextureRegion region = null;
    private Color color = Color.WHITE.cpy();
    private Integer regionIndex;
    private boolean isChanged = true;

    public Graphic() {
    }

    public Graphic(AtlasAsset atlas, String atlasKey, Color color) {
        this.atlas = atlas;
        this.atlasKey = atlasKey;
        this.color = color;
    }

    public Graphic(AtlasAsset atlas, String atlasKey) {
        this(atlas, atlasKey, Color.WHITE.cpy());
    }
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        if(!color.equals(this.color)) {
            setChanged(true);
        }
        this.color = color;
    }

    public AtlasAsset getAtlas() {
        return atlas;
    }

    public void setAtlas(AtlasAsset atlas) {
        if(!Objects.equals(this.atlas, atlas)) {
            setChanged(true);
        }
        this.atlas = atlas;
    }

    public String getAtlasKey() {
        return atlasKey;
    }

    public void setAtlasKey(String atlasKey) {
        if(!Objects.equals(this.atlasKey, atlasKey)) {
            setChanged(true);
        }
        this.atlasKey = atlasKey;
    }

    public void set(Graphic graphic) {
        this.atlas = graphic.getAtlas();
        this.atlasKey = graphic.getAtlasKey();
        this.color = graphic.getColor();
        this.regionIndex = graphic.getRegionIndex();
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }

    public TextureRegion getRegion() {
        return region;
    }

    public void setRegion(TextureRegion region) {
        this.region = region;
    }

    public Integer getRegionIndex() {
        return regionIndex;
    }

    public void setRegionIndex(Integer regionIndex) {
        if(!Objects.equals(this.regionIndex, regionIndex)) {
            setChanged(true);
        }
        this.regionIndex = regionIndex;
    }
}
