package com.ap.client.items.plant;

import com.ap.client.items.Item;

import java.util.List;

public class Tree extends Plant{
    public Tree(String name) {
        super(name, 0, null, 0);
    }


    @Override
    public List<Item> produceItems() {
        return List.of();
    }
}
