package phi.ap.model.items.tools;

import phi.ap.model.*;
import phi.ap.model.enums.LevelName;
import phi.ap.model.items.Dirt;
import phi.ap.model.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Hoe extends Tool {
    public Hoe(){
        this.levelProcess = new LevelProcess(new ArrayList<>(List.of(LevelName.normal, LevelName.copper,
                LevelName.iron, LevelName.golden, LevelName.iridium)),
                new ArrayList<>(List.of(5,4,3,2,1)), 0);
        this.setName("Hoe");
    }
    @Override
    public Result<String> useTool(Coordinate direction) {
        Item item = Game.getInstance().getCurrentPlayer().getLocation().getTopItemDiff(direction.getX(), direction.getY());
        int energy = this.levelProcess.getEnergyNeed();

        if(!(item instanceof Dirt)) //Item is not dirt
            return new Result<>(false, "This item is not dirt!");
        Dirt dirt = (Dirt)item;
        dirt.plow();
    }

    @Override
    public void doTask() {
    }
}
