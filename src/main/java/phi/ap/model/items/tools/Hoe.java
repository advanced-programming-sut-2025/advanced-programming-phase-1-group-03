package phi.ap.model.items.tools;

import phi.ap.model.*;
import phi.ap.model.enums.AbilityType;
import phi.ap.model.enums.Tool.BasicToolLevels;
import phi.ap.model.items.Dirt;
import phi.ap.model.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Hoe extends Tool {
    public Hoe(){
        super(new LevelProcess(new ArrayList<>(List.of(Tool.BasicToolLevels.normal, Tool.BasicToolLevels.copper,
                Tool.BasicToolLevels.Iron, Tool.BasicToolLevels.golden, Tool.BasicToolLevels.iridium)),0),
                new ArrayList<>(List.of(5,4,3,2,1)), AbilityType.Farming);
        this.setName("Hoe");
    }
    @Override
    public Result<String> useTool(Coordinate direction) {
        Item item = Game.getInstance().getCurrentPlayer().getLocation().getTopItemDiff(direction.getY(), direction.getX());
        int energy = getEnergyNeed();
        if(!Game.getInstance().getCurrentPlayer().getEnergy().hasEnergy(energy))
            return new Result<>(false, "You don't have enough energy");


        Game.getInstance().getCurrentPlayer().getEnergy().advanceBaseUnit(-energy);

        if(!(item instanceof Dirt dirt)) //Item is not dirt
            return new Result<>(false, "This item is not dirt!");
        dirt.plow();
        return new Result<>(true, "Dirt plowed");
    }

    @Override
    public void doTask() {
    }
}
