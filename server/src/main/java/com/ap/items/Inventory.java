package com.ap.items;

import com.ap.model.FoodRecipes;
import com.ap.model.NetworkItemStack;
import com.ap.model.ServerPlayer;
import com.ap.notifiers.InventoryNotifier;

import java.util.ArrayList;

public class Inventory {
    public final static int maxStorage = 36;
    public final static int maxTrashCanLevel = 4;
    private int storage = 12;
    private final ArrayList<ItemStack> items = new ArrayList<>();
    private final ArrayList<FoodRecipes> foodRecipes = new ArrayList<>();

    private int trashCanLevel = 0;

    private ServerPlayer player;

    public Inventory(ServerPlayer player) {
        this.player = player;
    }

    public boolean canAdd() {
        return !(items.size() == storage);
    }

    public int addItem(ItemStack itemStack) {
        return addItem(itemStack.getItem(), itemStack.getAmount());
    }
    public int addItem(Item item, int amount) {
        int added = add(item, amount);
        if(added > 0) {
            sendInventory();
        }
        return added;
    }
    public int add(Item item, int amount) {
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
     * Sending current status of inventory to the client
     */
    private void sendInventory() {
        ArrayList<NetworkItemStack> networkItems = new ArrayList<>();
        for(ItemStack itemStack : items) {
            networkItems.add(new NetworkItemStack(
                    itemStack.getItem().name,
                    itemStack.getAmount(),
                    itemStack.getItem().atlasAsset,
                    itemStack.getItem().atlasKey));
        }
        var notifier = new InventoryNotifier(networkItems.toArray(new NetworkItemStack[0]), storage);
        player.connection.sendTCP(notifier);
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
        sendInventory();
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

    public void setMaxSize(int storage) {
        this.storage = storage;
    }

    public int getTrashCanLevel() {
        return trashCanLevel;
    }

    public void setTrashCanLevel(int trashCanLevel) {
        this.trashCanLevel = trashCanLevel;
    }

    public void removeItemViaTrashCan(ItemStack itemStack) {
        //TODO gaining money logic
        removeItem(itemStack.getItem(), itemStack.getAmount());
    }

    public ArrayList<FoodRecipes> getFoodRecipes() {
        return foodRecipes;
    }
}
