package phi.ap.model.items.tools;

import phi.ap.model.*;
import phi.ap.model.enums.AbilityType;
import phi.ap.model.enums.Tool.BasicToolLevels;
import phi.ap.model.enums.ProductNames;
import phi.ap.model.items.Dirt;
import phi.ap.model.items.Item;
import phi.ap.model.items.products.*;

import java.util.ArrayList;
import java.util.List;

public class Scythe extends Tool{
    public Scythe(){
        super(new LevelProcess(new ArrayList<Tool.BasicToolLevels>(List.of(Tool.BasicToolLevels.normal)),0),
                new ArrayList<>(List.of(2)),null);
        this.setName("Scythe");
    }
    @Override
    public Result<String> useTool(Coordinate direction) {
        Item item = Game.getInstance().getCurrentPlayer().getLocation().getTopItemDiff(direction.getY(), direction.getX ());
        if(!reduceEnergy()) return new Result<>(false, "You don't have enough energy!");

        StringBuilder response = new StringBuilder();
        switch (item) {
            case Plant plant -> {
                if((plant instanceof Crop) && ((Crop)plant).getForagingType() != null)
                    Game.getInstance().getCurrentPlayer().getAbility(AbilityType.Foraging).advanceXP(5);
                if((plant instanceof Tree) && ((Tree)plant).isForaging())
                    Game.getInstance().getCurrentPlayer().getAbility(AbilityType.Foraging).advanceXP(5);

                for(ItemStack itemStack : plant.getProducts()){
                    int cnt = Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(itemStack);
                    Game.getInstance().getCurrentPlayer().getAbility(AbilityType.Farming).advanceXP(5);
                    response.append(cnt).append(" of ").append(itemStack.getItem().getName()).append(" gained!\n");
                }
                if(plant instanceof Tree && ((Tree)plant).isThundered()){
                    plant.delete();
                }
                return new Result<>(true, response.toString());
            }case Product grass -> {
                if(grass.canStackWith(ProductNames.Grass.getInstance())){
                    grass.getFather().removeItem(grass);
                    for (ItemStack itemStack : grass.getDrops()) {
                        Game.getInstance().getCurrentPlayer().getAbility(AbilityType.Farming).advanceXP(5);
                        int cnt = Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(itemStack);
                        response.append(cnt).append(" of ").append(itemStack.getItem().getName()).append(" gained!\n");
                    }
                    return new Result<>(true, response.toString() + "Grass destroyed!");
                }
            }
            case null, default -> {}
        }
        return new Result<>(false, "you can't do anything with this item!\n" +
                "you only wasted your energy -_-");
    }

    @Override
    public void doTask() {

    }
}
