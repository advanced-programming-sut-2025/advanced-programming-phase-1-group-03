package phi.ap.model;

import phi.ap.model.items.Item;

public class ItemStack {
    private Item item;
    private int amount;

    public ItemStack(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public void reduceAmount(int amount) {
        this.amount -= amount;
    }

    public boolean isFull() {
        if (amount == item.getMaxStackSize()) {
            return true;
        }
        return false;
    }

    public boolean canStackWith(Item otherItem) {
        //TODO : canStackWith function for each item if necessary
        return item.canStackWith(otherItem);
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getAmount() {
        return amount;
    }

    public Item getItem() {
        return item;
    }

    public void advanceAmount(int amount) {
        this.amount += amount;
    }

    public String toString() {
        return "(" + item.getName() + ", " + amount + ")";
    }
}
