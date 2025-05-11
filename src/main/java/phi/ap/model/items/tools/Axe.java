package phi.ap.model.items.tools;

import phi.ap.model.*;
import phi.ap.model.enums.AbilityType;
import phi.ap.model.enums.LevelName;
import phi.ap.model.items.*;
import phi.ap.model.items.products.Fruit;
import phi.ap.model.items.products.Wood;

import java.util.ArrayList;
import java.util.List;

public class Axe extends Tool{
    public Axe(){
        super(new LevelProcess(new ArrayList<>(List.of(LevelName.normal, LevelName.copper,
                LevelName.iron, LevelName.golden, LevelName.iridium)), 0),
                new ArrayList<>(List.of(5,4,3,2,1)),AbilityType.Foraging);
        this.setName("Axe");
    }
    @Override
    public Result<String> useTool(Coordinate direction) {
        Item item = Game.getInstance().getCurrentPlayer().getLocation().getTopItemDiff(direction.getX(), direction.getY());
        if(!reduceEnergy()) return new Result<>(false, "You don't have enough energy!");
        StringBuilder response = new StringBuilder();
        switch(item){
            case Wood wood -> {
                response.append("1 one amount of wood gained!");
                Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(wood, 1);
                wood.getFather().removeItem(wood);
            }
            case Tree tree -> {
                //TODO get drops
//                Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(new Wood(1,1), tree.getWoodAmount());
//                response.append("2 one amount of wood gained!\n");
//                int seedAmount = 1;
//                if(App.getInstance().eventRandom(50))
//                    seedAmount = 2;
//                if(tree.getSeedType() != null) {
//                    Seed seed = new Seed(1,1,tree.getSeedType());
//                    response.append(seedAmount).append(" of ").append(tree.getSeedType().toString()).append(" gained!\n");
//                    Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(seed, seedAmount);
//                }
//                if(tree.getFruit().isSyrup()){
//                    for(ItemStack itemStack : tree.getProducts()){
//                        if(!(itemStack.getItem() instanceof Fruit))
//                            continue;
//                        int added = Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(itemStack);
//                        response.append(added).append(" amount of ").append(tree.getFruit().toString()).append(" gained!\n");
//                    }
//                }
                tree.getFather().removeItem(tree);
            }
            case null, default -> {
                return new Result<>(false, "This item is not tree or wood! you just wasted your energy! -_-");
            }
        }
        return new Result<>(true, response.toString());
    }

    @Override
    public void doTask() {

    }
}
