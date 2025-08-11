package com.ap.notifiers;

import com.ap.component.Graphic;
import com.ap.component.Transform;
import com.badlogic.ashley.core.Component;

public class ItemNotifier {
    public int engineId;
    public int itemId;
    public Component[] components;

    public ItemNotifier(int engineId, int itemId, Component... components) {
        this.engineId = engineId;
        this.itemId = itemId;
        this.components = components;
    }

    public ItemNotifier() {
    }
}
