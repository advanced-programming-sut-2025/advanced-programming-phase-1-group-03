package com.ap.component;

import com.ap.items.Item;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class ItemHolder implements Component {
    public static final ComponentMapper<ItemHolder> mapper = ComponentMapper.getFor(ItemHolder.class);
    private Item item;
    public ItemHolder(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
