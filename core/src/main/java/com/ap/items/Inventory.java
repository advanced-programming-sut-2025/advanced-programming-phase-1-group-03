package com.ap.items;

import java.util.ArrayList;

public class Inventory {
    private int storage = 12;
    private final ArrayList<ItemStack> items = new ArrayList<>();

    public boolean canAdd() {
        return !(items.size() == storage);
    }

    public int addItem(ItemStack itemStack) {
        return addItem(itemStack.getItem(), itemStack.getAmount());
    }
    public int addItem(Item item, int amount) {
        int added = 0;
        for (ItemStack stack : items) {
            if (!stack.getItem().canStackWith(item)) {
                continue;
            }
            int toAdd = Math.min(item.getMaxStackSize() - stack.getAmount(), amount);
            amount -= toAdd;
            stack.increaseAmount(toAdd);
            added += toAdd;
        }
        if (amount == 0) {
            return added;
        }
        while (this.canAdd()) {
            int toAdd = Math.min(item.getMaxStackSize(), amount);
            items.add(new ItemStack(item, toAdd));
            added += toAdd;
            amount -= toAdd;
            if (amount == 0) {
                return added;
            }
        }
        return added;
    }


    public ArrayList<ItemStack> getItems() {
        return items;
    }

    public int getMaxSize() {
        return storage;
    }
    public int getSize() {
        return items.size();
    }
}
