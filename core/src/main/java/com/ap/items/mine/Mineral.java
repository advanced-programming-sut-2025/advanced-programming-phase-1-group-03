package com.ap.items.mine;

import com.ap.items.Item;
import com.ap.model.Minerals;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Mineral extends Item {
    private Minerals type;

    public Mineral(TextureRegion icon, Minerals type) {
        super(type.getName(), 10, icon, type.getSellPrice());
        this.type = type;
    }

    public Minerals getType() {
        return type;
    }

    public void setType(Minerals type) {
        this.type = type;
    }
}
