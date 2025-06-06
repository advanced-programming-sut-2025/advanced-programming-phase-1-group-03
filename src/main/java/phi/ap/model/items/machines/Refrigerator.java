package phi.ap.model.items.machines;

import phi.ap.model.Game;
import phi.ap.model.ItemStack;
import phi.ap.model.Result;
import phi.ap.model.items.Item;

import java.util.ArrayList;

public class Refrigerator {
    private ArrayList<ItemStack> storage = new ArrayList<>();
    private static Refrigerator refrigerator = null;

    public static Refrigerator getInstance() {
        if(refrigerator == null)
            refrigerator = new Refrigerator();
        return refrigerator;
    }

    public Result<String> putItem(ItemStack itemStack) {
        int added = 0;
        Item item = itemStack.getItem();
        int amount = itemStack.getAmount();
        ItemStack invertorytStack = Game.getInstance().getCurrentPlayer().getInventoryManager().getItem(item);
        if(invertorytStack.getAmount() == 0)
            return new Result<>(false, "You don't have " + invertorytStack.getItem().getName() + " in your inventory.");
        amount = Math.min(invertorytStack.getAmount(), amount);
        for (ItemStack stack : storage) {
            if (!stack.getItem().canStackWith(item)) {
                continue;
            }
            int toAdd = Math.min(item.getMaxStackSize() - stack.getAmount(), amount);
            amount -= toAdd;
            stack.advanceAmount(toAdd);
            Game.getInstance().getCurrentPlayer().getInventoryManager().removeItem(item, toAdd);
            added += toAdd;
        }
        if (amount == 0) {
            return new Result<>(true, "Item added successfully.");
        }
        while (true) {
            int toAdd = Math.min(item.getMaxStackSize(), amount);
            storage.add(new ItemStack(item, toAdd));
            Game.getInstance().getCurrentPlayer().getInventoryManager().removeItem(item, toAdd);
            added += toAdd;
            amount -= toAdd;
            if (amount == 0) {
                return new Result<>(true, "Item added successfully.");
            }
        }
    }

    public int pickItem(ItemStack stack) {
        Item item = stack.getItem();
        int amount = stack.getAmount();
        ItemStack newItemStack = new ItemStack(item, 0);
        for (ItemStack stack1 : storage) {
            if (stack1.canStackWith(item)) {
                int value = Math.min(amount, stack1.getAmount());
                newItemStack.advanceAmount(value);
                stack1.reduceAmount(value);
                amount -= value;
            }
        }
        int loop = storage.size();
        for(int j = 0; j < loop; j++) {
            for(int i = 0; i < storage.size(); i++) {
                ItemStack itemStack = storage.get(i);
                if(itemStack.getAmount() == 0) {
                    storage.remove(i);
                    break;
                }
            }
        }
        int val = Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(newItemStack.getItem(), newItemStack.getAmount());
        putItem(new ItemStack(newItemStack.getItem(), newItemStack.getAmount() - val));
        return val;
    }

    public String showStorage() {
        StringBuilder stringBuilder = new StringBuilder();
        for(ItemStack itemStack : storage) {
            stringBuilder.append("Name: " + itemStack.getItem().getName() + " Amount: " + itemStack.getAmount() + "\n");
        }
        return stringBuilder.toString();
    }
}
