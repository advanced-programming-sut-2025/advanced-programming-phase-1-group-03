package phi.ap.model.items.tools;

import phi.ap.model.Coordinate;
import phi.ap.model.Game;
import phi.ap.model.LevelProcess;
import phi.ap.model.Result;
import phi.ap.model.enums.AbilityType;
import phi.ap.model.enums.Tool.BasicToolLevels;
import phi.ap.model.items.Item;
import phi.ap.model.items.products.Plant;
import phi.ap.model.items.Water;

import java.util.ArrayList;
import java.util.List;

public class WateringCan extends Tool{
    private ArrayList<Integer> capacity;
    private int occupiedCapacity = 0;

    public WateringCan(){
        super(new LevelProcess(new ArrayList<>(List.of(Tool.BasicToolLevels.normal, Tool.BasicToolLevels.copper,
                Tool.BasicToolLevels.Iron, Tool.BasicToolLevels.golden, Tool.BasicToolLevels.iridium)), 0),
                new ArrayList<>(List.of(5,4,3,2,1)), AbilityType.Farming);
        this.setName("Watering Can");
        capacity = new ArrayList<>(List.of(40, 55, 70, 85, 100));
    }

    @Override
    public Result<String> useTool(Coordinate direction) {
        Item item = Game.getInstance().getCurrentPlayer().getLocation().getTopItemDiff(direction.getY(), direction.getX());
        if(!reduceEnergy()) return new Result<>(false, "You don't have enough energy!");
        switch(item){
            case Water water:
                occupiedCapacity += Math.min(getCapacity() - occupiedCapacity, water.getCapacity());
                water.reduceCapacity(occupiedCapacity);
                return new Result<>(true, "now your watering can has " + occupiedCapacity + " unit of water");
            case Plant plant:
                int needWater = 1;
                if(plant.getGiant() != null) needWater = 4;
                if(occupiedCapacity < needWater)
                    return new Result<>(false, "Watering can doesn't have enough water");
                occupiedCapacity -= needWater;
                plant.watering();
                return new Result<>(true, "plant watered... now your watering can has "
                        + occupiedCapacity + " unit of water");
             case null, default:
                 if(occupiedCapacity > 0) occupiedCapacity -= 1;
                 return new Result<>(false, "This item is not plant! you just wasted one unit of water! -_-");
        }
    }

    @Override
    public void doTask() {

    }
    private int getCapacity(){
        return capacity.get(getLevelProcess().getCurrentLevel());
    }
}
