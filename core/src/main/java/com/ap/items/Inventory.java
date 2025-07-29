package com.ap.items;

import java.util.ArrayList;

public class Inventory {
    public final static int maxStorage = 36;
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

    /**
     * remove amount items from inventory, note that if we don't have enough items, it doesn't remove amount
     */
    public int removeItem(Item item, int amount) {
        int removed = 0;
        for (ItemStack stack : items) {
            if (!stack.getItem().canStackWith(item)) {
                continue;
            }
            int toRemove = Math.min(stack.getAmount(), amount);
            amount -= toRemove;
            stack.increaseAmount(-toRemove);
            removed += toRemove;
        }
        items.removeIf(stack -> stack.getAmount() == 0);
        return removed;
    }

    public boolean have(Item item, int amount) {
        for(ItemStack stack : items) {
            if(stack.getItem().canStackWith(item)) {
                amount -= stack.getAmount();
            }
        }
        return amount <= 0;
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
