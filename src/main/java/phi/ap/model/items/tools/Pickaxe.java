package phi.ap.model.items.tools;

import phi.ap.model.Coordinate;
import phi.ap.model.Game;
import phi.ap.model.LevelProcess;
import phi.ap.model.Result;
import phi.ap.model.enums.AbilityType;
import phi.ap.model.enums.Tool.BasicToolLevels;
import phi.ap.model.items.Dirt;
import phi.ap.model.items.Item;
import phi.ap.model.items.products.Mineral;
import phi.ap.model.items.products.Plant;
import phi.ap.model.items.products.Stone;

import java.util.ArrayList;
import java.util.List;

public class Pickaxe extends Tool{
    public Pickaxe(){
        super(new LevelProcess(new ArrayList<>(List.of(Tool.BasicToolLevels.normal, Tool.BasicToolLevels.copper,
                Tool.BasicToolLevels.Iron, Tool.BasicToolLevels.golden, Tool.BasicToolLevels.iridium)),0),
                new ArrayList<>(List.of(5,4,3,2,1)), AbilityType.Extraction);
        this.setName("Pickaxe");
    }

    @Override
    public Result<String> useTool(Coordinate direction) {
        Item item = Game.getInstance().getCurrentPlayer().getLocation().getTopItemDiff(direction.getY(), direction.getX());
        int getCount = 1;
        if(Game.getInstance().getCurrentPlayer().getAbilityLevel(AbilityType.Extraction) >= 2)
            getCount = 2;

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
                Game.getInstance().getCurrentPlayer().getAbility(AbilityType.Extraction).advanceXP(10);
                Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(stone, getCount);
                stone.getFather().removeItem(stone);
                return new Result<>(true, "You gained "+getCount+" stone!");
            }
            case Mineral mineral -> {
                if(!reduceEnergy()) return new Result<>(false, "You don't have enough energy!");
                if(!canIMineThisLevel(mineral.getForagingType().getLevelNeedToMine()))
                    return new Result<>(false,
                            "Your pickaxe must be at least " + mineral.getForagingType().getLevelNeedToMine().toString());

                if(mineral.getForagingType() != null)
                    Game.getInstance().getCurrentPlayer().getAbility(AbilityType.Foraging).advanceXP(5);

                Game.getInstance().getCurrentPlayer().getAbility(AbilityType.Extraction).advanceXP(10);

                Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(mineral, getCount);
                mineral.getFather().removeItem(mineral);
                return new Result<>(true, "You gained "+getCount+" " + mineral.getName() +"!");
            }
            case null, default -> {
                if(item != null && item.isRemovableByPickaxe()){
                    if(!reduceEnergy()) return new Result<>(false, "You don't have enough energy!");
                    if (item instanceof Plant) ((Plant) item).delete();
                    else {
                        item.getFather().removeItem(item);
                        if(item.isRemovableByPickaxe()) {
                            int amount = Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(item, 1);
                            if(amount > 0)
                                return new Result<>(true, item.getName() + " Picked up");
                        }
                    }
                    return new Result<>(true, "Item removed");
                }
                if(!reduceFailedEnergy()) return new Result<>(false, "You don't have enough energy!");
                return new Result<>(false, "you can't do anything with this item!\n" +
                        "you only wasted your energy -_-");
            }
        }
    }

    private boolean canIMineThisLevel(Tool.BasicToolLevels level){
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
