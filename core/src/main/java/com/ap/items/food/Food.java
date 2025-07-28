package com.ap.items.food;

import com.ap.items.Item;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Food extends Item {
    public Food(String name, int maxStackSize, TextureRegion icon, int sellPrice) {
        super(name, maxStackSize, icon, sellPrice);
    }
}
