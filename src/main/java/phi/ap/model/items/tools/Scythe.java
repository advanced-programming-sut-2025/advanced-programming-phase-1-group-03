package phi.ap.model.items.tools;

import phi.ap.model.*;
import phi.ap.model.enums.LevelName;
import phi.ap.model.enums.ProductNames;
import phi.ap.model.items.Dirt;
import phi.ap.model.items.Item;
import phi.ap.model.items.products.*;

import java.util.ArrayList;
import java.util.List;

public class Scythe extends Tool{
    public Scythe(){
        super(new LevelProcess(new ArrayList<LevelName>(List.of(LevelName.normal)),0),
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
                for(ItemStack itemStack : plant.getProducts()){
                    int cnt = Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(itemStack);
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
