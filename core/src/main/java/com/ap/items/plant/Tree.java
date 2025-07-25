package com.ap.items.plant;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tree extends Plant{
    private boolean hasLeaf;
    public Tree(String name, boolean hasLeaf) {
        super(name, 0, null, 0);
        this.hasLeaf = hasLeaf;
    }

    public boolean hasLeaf() {
        return hasLeaf;
    }

    public void setHasLeaf(boolean hasLeaf) {
        this.hasLeaf = hasLeaf;
    }
}
