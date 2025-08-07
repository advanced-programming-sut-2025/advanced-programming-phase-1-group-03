package com.ap.client.items.Animals;

import com.ap.client.items.Item;
import com.ap.client.model.FishTypes;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Fish extends Item {
    private FishTypes fishTypes;

    public Fish(FishTypes fishTypes, TextureRegion icon) {
        super(fishTypes.getName(), 5, icon, fishTypes.getBasePrice());
        this.fishTypes = fishTypes;
    }
}
