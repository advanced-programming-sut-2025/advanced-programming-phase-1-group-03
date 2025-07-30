package phi.ap.model.items.tools;

import phi.ap.model.Coordinate;
import phi.ap.model.ItemStack;
import phi.ap.model.LevelProcess;
import phi.ap.model.Result;
import phi.ap.model.enums.Tool.BasicToolLevels;

import java.util.ArrayList;
import java.util.List;

public class TrashCan extends Tool {
    public TrashCan(){
        super(new LevelProcess(new ArrayList<>(List.of(Tool.BasicToolLevels.normal, Tool.BasicToolLevels.copper,
                Tool.BasicToolLevels.Iron, Tool.BasicToolLevels.golden, Tool.BasicToolLevels.iridium)), 0),
                new ArrayList<>(List.of(0,0,0,0,0)), null);
        this.setName("Trash Can");
    }


    @Override
    public Result<String> useTool(Coordinate direction) {
        return new Result<>(false, "use inventory trash -i <item’s name> -n <number> to use trashcan");
    }

    public int trash(ItemStack itemStack){
        int money = itemStack.getAmount() * itemStack.getItem().getSellPrice();
        return switch (getLevelProcess().getCurrentTool.BasicToolLevels()){
            case normal -> 0;
            case copper -> money * 15 /100;
            case Iron -> money * 30 /100;
            case golden -> money * 45 / 100;
            case iridium -> money * 60 /100;
            default -> money;
        };
    }
    @Override
    public void doTask() {

    }
}
