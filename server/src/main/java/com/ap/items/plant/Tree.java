package com.ap.items.plant;

import com.ap.items.Item;

import java.util.List;

public class Tree extends Plant{
    public Tree(String name) {
        super(name, 0, null, null, 0);
    }


    @Override
    public List<Item> produceItems() {
        return List.of();
    }
}
