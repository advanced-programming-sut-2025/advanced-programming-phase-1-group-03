package phi.ap.model.items.tools;

import phi.ap.model.*;
import phi.ap.model.enums.AbilityType;
import phi.ap.model.enums.LevelName;
import phi.ap.model.items.*;
import phi.ap.model.items.products.Crop;
import phi.ap.model.items.products.Fruit;
import phi.ap.model.items.products.Tree;
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
                response.append("1 amount of wood gained!");
                Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(wood, 1);
                wood.getFather().removeItem(wood);
            }
            case Tree tree -> {
                for (ItemStack drop : tree.getDrops()) {
                    int cnt = Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(drop);
                    response.append(cnt + "amount of " + drop.getItem().getName() + " gained!\n");
                }
                tree.getFather().removeItem(tree);
            } case Crop crop -> {
                for (ItemStack drop : crop.getDrops()) {
                    int cnt = Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(drop);
                    response.append(cnt + "amount of " + drop.getItem().getName() + " gained!\n");
                }
                crop.getFather().removeItem(crop);
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
