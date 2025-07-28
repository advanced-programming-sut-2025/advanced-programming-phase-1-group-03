package com.ap.items.plant;

import com.ap.items.Item;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public abstract class Plant extends Item {
    public Plant(String name, int maxStackSize, TextureRegion icon, int sellPrice) {
        super(name, maxStackSize, icon, sellPrice);
    }
    public abstract List<Item> produceItems();
}
