package phi.ap.model;

import phi.ap.model.items.Item;
import phi.ap.model.items.tools.Backpack;
import phi.ap.model.items.tools.TrashCan;

public class InventoryManager {
    private Backpack storage;
    private TrashCan trashCan;

    public int addItem(Item item, int amount) {
        int added = 0;
        for (ItemStack stack : storage.getStacks()) {
            if (!stack.canStackWith(item)) {
                continue;
            }
            int toAdd = Math.min(item.getMaxStackSize() - stack.getAmount(), amount);
            amount -= toAdd;
            stack.advanceAmount(toAdd);
            added += toAdd;
            if (amount == 0) {
                return added;
            }
        }

        while (!storage.isFull()) {
            int toAdd = Math.min(item.getMaxStackSize(), amount);
            storage.addStack(new ItemStack(item, toAdd));
            added += toAdd;
            amount -= toAdd;
            if (amount == 0) {
                return added;
            }
        }
        return added;
    }
    public int removeItem(Item item, int amount) {
        //TODO : like add, + gain from trashCan
        return 0;
    }
    public ItemStack getItem(Item item) {
        //get new ItemStack with sum of amounts in ItemStack
        return null;
    }
}
