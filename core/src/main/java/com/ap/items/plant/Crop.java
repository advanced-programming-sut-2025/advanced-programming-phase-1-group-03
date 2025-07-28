package com.ap.items.plant;

import com.ap.items.Item;
import com.ap.items.ItemFactory;
import com.ap.model.CropsType;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public class Crop extends Plant {
    private CropsType type;
    private boolean isRegrowing = false;

    public Crop(TextureRegion icon, CropsType type) {
        super(type.getName(), 0, icon, 0);
        this.type = type;
    }

    @Override
    public List<Item> produceItems() {
        return List.of(ItemFactory.instance.CreateProductOfCrop(type));
    }

    public CropsType getType() {
        return type;
    }

    public void setupRegrowth() {
        isRegrowing = true;
    }

    public boolean isRegrowing() {
        return isRegrowing;
    }

    public void setRegrowing(boolean regrowing) {
        isRegrowing = regrowing;
    }
}
