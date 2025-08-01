package com.ap.component;

import com.ap.asset.AtlasAsset;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

/**
 * If an entity has SeasonalGraphic it must have Graphic component too
 */
public class SeasonalGraphic implements Component {
    public static ComponentMapper<SeasonalGraphic> mapper = ComponentMapper.getFor(SeasonalGraphic.class);

    private AtlasAsset atlas;
    private String atlasKey;
    private Integer index = null;

    public SeasonalGraphic(AtlasAsset atlas, String atlasKey) {
        this.atlas = atlas;
        this.atlasKey = atlasKey;
    }
    public SeasonalGraphic(AtlasAsset atlas, String atlasKey, int index) {
        this.atlas = atlas;
        this.atlasKey = atlasKey;
        this.index = index;
    }
    public AtlasAsset getAtlas() {
        return atlas;
    }

    public String getAtlasKey() {
        return atlasKey;
    }

    public void setAtlasKey(String atlasKey) {
        this.atlasKey = atlasKey;
    }

    public Integer getIndex() {
        return index;
    }
}
