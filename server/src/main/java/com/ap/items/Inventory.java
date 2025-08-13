package com.ap.items;

import com.ap.model.*;
import com.ap.notifiers.InventoryNotifier;

import java.util.ArrayList;

public class Inventory {
    public final static int maxStorage = 36;
    public final static int maxTrashCanLevel = 4;
    private int storage = 24;
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
        return addItem(itemStack.getItem(), itemStack.getAmount(), false);
    }
    public int addItem(Item item, int amount, boolean isRefrigerator) {
        int added = add(item, amount);
        if(added > 0) {
            sendInventory(isRefrigerator);
        }
        return added;
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

    public ArrayList<NetworkItemStack> getNetworkItems() {
        ArrayList<NetworkItemStack> networkItems = new ArrayList<>();
        for(ItemStack itemStack : items) {
            networkItems.add(new NetworkItemStack(
                    itemStack.getItem().name,
                    itemStack.getAmount(),
                    itemStack.getItem().atlasAsset,
                    itemStack.getItem().atlasKey));
        }
        return networkItems;
    }

    public void sendInventory(boolean isRefrigerator) {
        ArrayList<NetworkItemStack> networkItems = getNetworkItems();
        var notifier = new InventoryNotifier(networkItems.toArray(new NetworkItemStack[0]), storage, isRefrigerator);
        player.connection.sendTCP(notifier);
    }

    public void sendInventory() {
        ArrayList<NetworkItemStack> networkItems = getNetworkItems();
        var notifier = new InventoryNotifier(networkItems.toArray(new NetworkItemStack[0]), storage, false);
        player.connection.sendTCP(notifier);
    }


    /**
     * remove amount items from inventory, note that if we don't have enough items, it doesn't remove amount
     */
    public int removeItem(Item item, int amount, boolean isRefrigerator) {
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
        sendInventory(isRefrigerator);
        return removed;
    }

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

    public int removeItem(String itemName, int amount) {
        int removed = 0;
        for (ItemStack stack : items) {
            if (!stack.getItem().getName().equals(itemName)) {
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

    public boolean have(String itemName, int amount) {
        for(ItemStack stack : items) {
            if(stack.getItem().getName().equals(itemName)) {
                amount -= stack.getAmount();
            }
        }
        return amount <= 0;
    }

    public boolean haveCookingRecipe(String recipeName) {
        for(FoodRecipes foodRecipe : foodRecipes) {
            if(foodRecipe.getFood().getName().equals(recipeName))
                return true;
        }
        return false;
    }

    public boolean isEatable(String name) {
        if(Foods.getFoodByName(name) != null || Cookings.getFoodByName(name) != null ||
                CropsType.getFoodByName(name) != null)
            return true;
        return false;
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
        removeItem(itemStack.getItem(), itemStack.getAmount(), false);
    }

    public ArrayList<FoodRecipes> getFoodRecipes() {
        return foodRecipes;
    }
}
