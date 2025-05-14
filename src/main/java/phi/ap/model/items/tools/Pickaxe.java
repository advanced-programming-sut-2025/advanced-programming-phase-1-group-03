package phi.ap.model.items.tools;

import phi.ap.model.Coordinate;
import phi.ap.model.Game;
import phi.ap.model.LevelProcess;
import phi.ap.model.Result;
import phi.ap.model.enums.AbilityType;
import phi.ap.model.enums.LevelName;
import phi.ap.model.items.Dirt;
import phi.ap.model.items.Item;
import phi.ap.model.items.products.Mineral;
import phi.ap.model.items.products.Plant;
import phi.ap.model.items.products.Stone;

import java.util.ArrayList;
import java.util.List;

public class Pickaxe extends Tool{
    public Pickaxe(){
        super(new LevelProcess(new ArrayList<>(List.of(LevelName.normal, LevelName.copper,
                LevelName.iron, LevelName.golden, LevelName.iridium)),0),
                new ArrayList<>(List.of(5,4,3,2,1)), AbilityType.Extraction);
        this.setName("Pickaxe");
    }

    @Override
    public Result<String> useTool(Coordinate direction) {
        Item item = Game.getInstance().getCurrentPlayer().getLocation().getTopItemDiff(direction.getY(), direction.getX());

        switch (item) {
            case Dirt dirt -> {
                if(dirt.isPlowed()){
                    if(!reduceEnergy()) return new Result<>(false, "You don't have enough energy!");
                    dirt.unPlow();
                    return new Result<>(true, "Ok ok, now the dirt is not plowed");
                }else{
                    if(!reduceFailedEnergy()) return new Result<>(false, "You don't have enough energy!");
                    return new Result<>(true, "This dirt is not plowed!");
                }
            }
            case Stone stone -> {
                if(!reduceEnergy()) return new Result<>(false, "You don't have enough energy!");
                Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(stone, 1);
                stone.getFather().removeItem(stone);
                return new Result<>(true, "You gained one stone!");
            }
            case Mineral mineral -> {
                if(!reduceEnergy()) return new Result<>(false, "You don't have enough energy!");
                if(!canIMineThisLevel(mineral.getForagingType().getLevelNeedToMine()))
                    return new Result<>(false,
                            "Your pickaxe must be at least " + mineral.getForagingType().getLevelNeedToMine().toString());
                Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(mineral, 1);
                mineral.getFather().removeItem(mineral);
                return new Result<>(true, "You gained one " + mineral.getName() +"!");
            }
            case null, default -> {
                if(item != null && item.isRemovableByPickaxe()){
                    if(!reduceEnergy()) return new Result<>(false, "You don't have enough energy!");
                    if (item instanceof Plant) ((Plant) item).delete();
                    else item.getFather().removeItem(item);
                    return new Result<>(true, "Item removed");
                }
                if(!reduceFailedEnergy()) return new Result<>(false, "You don't have enough energy!");
                return new Result<>(false, "you can't do anything with this item!\n" +
                        "you only wasted your energy -_-");
            }
        }
    }

    private boolean canIMineThisLevel(LevelName level){
        return getLevelProcess().getLevelOfThisType(level) <= getLevelProcess().getCurrentLevel();
    }
    private boolean reduceFailedEnergy(){
        int energy = getEnergyNeed() - 1;
        if(energy < 0) energy = 0;
        if(!Game.getInstance().getCurrentPlayer().getEnergy().hasEnergy(energy))
            return false;
        Game.getInstance().getCurrentPlayer().getEnergy().advanceBaseUnit(-energy);
        return true;
    }
    @Override
    public void doTask() {

    }
}
