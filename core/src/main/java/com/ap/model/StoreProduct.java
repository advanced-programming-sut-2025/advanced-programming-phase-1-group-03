package com.ap.model;


import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StoreProduct {
    public TextureRegion texture;
    public String name;
    public String enumName;
    public String description;
    public int sellPrice;
    public int row;

    public StoreProduct(TextureRegion texture, String name, String enumName, String description, int sellPrice, int row) {
        this.texture = texture;
        this.name = name;
        this.enumName = enumName;
        this.sellPrice = sellPrice;
        this.row = row;
        this.description = description;
    }
}