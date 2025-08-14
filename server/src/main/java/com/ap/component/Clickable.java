package com.ap.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class Clickable implements Component {
    public static final ComponentMapper<Clickable> mapper = ComponentMapper.getFor(Clickable.class);

    boolean isClicked = false;
    private int buttonClicked = 0;
    private String itemName = "";
    private int itemAmount = 0;

    public Clickable() {
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public int getButtonClicked() {
        return buttonClicked;
    }

    public void setButtonClicked(int buttonClicked) {
        this.buttonClicked = buttonClicked;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemAmount() {
        return itemAmount;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }
}
