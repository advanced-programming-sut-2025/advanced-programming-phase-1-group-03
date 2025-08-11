package com.ap.items;

public class ItemStack {
    private Item item;
    private int amount;
    public ItemStack(Item item, int stack) {
        this.item = item;
        this.amount = stack;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setAmount(int stack) {
        this.amount = stack;
    }
    public void increaseAmount(int size) {
        amount += size;
    }
}
