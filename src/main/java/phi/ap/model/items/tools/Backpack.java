package phi.ap.model.items.tools;

import phi.ap.model.Coordinate;
import phi.ap.model.ItemStack;
import phi.ap.model.LevelProcess;
import phi.ap.model.Result;
import phi.ap.model.enums.Tool.BasicToolLevels;

import java.util.ArrayList;
import java.util.List;

public class Backpack extends Tool{
    public Backpack(){
        super(new LevelProcess(new ArrayList<>(List.of(Tool.BasicToolLevels.normal, Tool.BasicToolLevels.big,
                Tool.BasicToolLevels.deluxe)), 0),new ArrayList<>(List.of(0,0,0)), null);
        this.setName("Backpack");
    }
    public Backpack(int currentLevel){
        super(new LevelProcess(new ArrayList<>(List.of(Tool.BasicToolLevels.normal, Tool.BasicToolLevels.big,
                Tool.BasicToolLevels.deluxe)), currentLevel),new ArrayList<>(List.of(0,0,0)), null);
        this.setName("Backpack");
    }
    private int size;
    private ArrayList<ItemStack> stacks = new ArrayList<>();
    @Override
    public Result<String> useTool(Coordinate direction) {return null;}

    @Override
    public void doTask() {}

    public ArrayList<ItemStack> getStacks() {
        return stacks;
    }

    public boolean isFull() {
        return stacks.size() == getSize();
    }

    public int getSize() {
        return switch (getLevelProcess().getCurrentLevel()) {
            // TODO set it to 12 again
            case 0 -> 12;
            case 1 -> 24;
            case 2 -> Integer.MAX_VALUE;
            default -> -1;
        };
    }

    public void addStack(ItemStack itemStack) {
        stacks.add(itemStack);
    }

    @Override
    public String toString() {
        return super.toString() + " capacity: " + getSize();
    }
}
