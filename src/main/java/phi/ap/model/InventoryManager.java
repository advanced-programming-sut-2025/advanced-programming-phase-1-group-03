package phi.ap.model;

import phi.ap.model.enums.FoodTypes;
import phi.ap.model.items.Item;
import phi.ap.model.items.products.Food;
import phi.ap.model.items.products.Recipe;
import phi.ap.model.items.products.Stone;
import phi.ap.model.items.tools.Backpack;
import phi.ap.model.items.tools.TrashCan;

import java.util.ArrayList;

public class InventoryManager {
    private final Backpack storage;
    private final TrashCan trashCan;
    public InventoryManager(Backpack storage, TrashCan trashCan) {
        this.trashCan = trashCan;
        this.storage = storage;
    }
    public boolean canAdd() {
        if(!storage.isFull())
            return true;
        return false;
    }
    public int addItem(ItemStack itemStack) {
        return addItem(itemStack.getItem(), itemStack.getAmount());
    }
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
        }
        if (amount == 0) {
            return added;
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

        int removed = 0;
        for (ItemStack stack : storage.getStacks()) {
            if (!stack.canStackWith(item)) {
                continue;
            }
            int toRemove = Math.min(stack.getAmount(), amount);
            amount -= toRemove;
            stack.advanceAmount(-toRemove);
            removed += toRemove;
        }
        //TODO : gain from trashCan
        return removed;
    }

    public boolean CheckCanBuild(Recipe recipe) {
        ArrayList<ItemStack> itemStack = recipe.getIngredients();
        for(ItemStack itemStack1 : itemStack) {
            if(!Game.getInstance().getCurrentPlayer().getInventoryManager().CheckExistence(itemStack1))
                return false;
        }
        for(ItemStack itemStack1 : itemStack) {
            Game.getInstance().getCurrentPlayer().getInventoryManager().removeItem(itemStack1.getItem(), itemStack1.getAmount());
        }
        return true;
    }
    public boolean CheckCanBuild(ItemStack itemStack1) {
        if(!Game.getInstance().getCurrentPlayer().getInventoryManager().CheckExistence(itemStack1))
            return false;
        Game.getInstance().getCurrentPlayer().getInventoryManager().removeItem(itemStack1.getItem(), itemStack1.getAmount());
        return true;
    }
    public boolean CheckExistence(ItemStack itemStack) {
        Item item = itemStack.getItem();
        int amount = itemStack.getAmount();
        int deleted;
        if((deleted = removeItem(item, amount)) == amount) {
            addItem(item, amount);
            return true;
        }
        else {
            addItem(item, deleted);
            return false;
        }
    }
    public ItemStack getItem(Item item) {
        //get new ItemStack with sum of amounts in ItemStack
        ItemStack stack = new ItemStack(item, 0);
        for (ItemStack stack1 : storage.getStacks()) {
            if (stack1.canStackWith(item)) {
                stack.advanceAmount(stack1.getAmount());
            }
        }
        return stack;
    }

    //return item must be unique
    //if there are two items with the given name this function won't work
    public ItemStack getItemByName(String name) {
        for (ItemStack stack : storage.getStacks()) {
            if(stack.getItem().getName().equalsIgnoreCase(name))
                return stack;
        }
        return null;
    }
    public Food getFood(FoodTypes foodType) {
        Food food = null;
        for (ItemStack stack1 : storage.getStacks()) {
            if (stack1.getItem() instanceof Food) {
                if(((Food)stack1.getItem()).getFoodType().equals(foodType)) {
                    stack1.advanceAmount(-1);
                    food = new Food(1, 1, foodType);
                    break;
                }
            }
        }
        return food;
    }
    public String showStorage() {
        StringBuilder stringBuilder = new StringBuilder();
        for(ItemStack itemStack : storage.getStacks()) {
            stringBuilder.append("Name: " + itemStack.getItem().getName() + " Amount: " + itemStack.getAmount() + "\n");
        }
        return stringBuilder.toString();
    }
}

