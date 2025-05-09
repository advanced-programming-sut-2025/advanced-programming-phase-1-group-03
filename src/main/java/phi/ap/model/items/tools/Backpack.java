package phi.ap.model.items.tools;

import phi.ap.model.Coordinate;
import phi.ap.model.ItemStack;

import java.util.ArrayList;

public class Backpack extends Tool{

    private int size = 100;
    private ArrayList<ItemStack> stacks = new ArrayList<>();
    @Override
    public void useTool(Coordinate direction) {

    }

    @Override
    public void doTask() {

    }

    public ArrayList<ItemStack> getStacks() {
        return stacks;
    }

    public void setBackpackAsInventory() {

    }

    public boolean isFull() {
        return false;
        //TODO
    }

    public int getSize() {
        return size;
    }

    public void addStack(ItemStack itemStack) {
        stacks.add(itemStack);
    }
}
