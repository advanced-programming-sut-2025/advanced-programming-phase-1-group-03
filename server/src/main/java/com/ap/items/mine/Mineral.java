package com.ap.items.mine;

import com.ap.asset.AtlasAsset;
import com.ap.items.Item;
import com.ap.model.Minerals;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Mineral extends Item {
    private Minerals type;

    public Mineral(AtlasAsset atlasAsset, String atlasKey, Minerals type) {
        super(type.getName(), 10, atlasAsset, atlasKey, type.getSellPrice());
        this.type = type;
    }

    public Minerals getType() {
        return type;
    }

    public void setType(Minerals type) {
        this.type = type;
    }
}
