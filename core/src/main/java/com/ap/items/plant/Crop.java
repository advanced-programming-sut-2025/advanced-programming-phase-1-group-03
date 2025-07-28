package com.ap.items.plant;

import com.ap.model.CropsType;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Crop extends Plant {
    private CropsType type;
    public Crop(TextureRegion icon, CropsType type) {
        super(type.getName(), 0, icon, 0);
    }
}
