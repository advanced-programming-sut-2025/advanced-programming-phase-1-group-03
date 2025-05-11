package phi.ap.model.items;

import phi.ap.model.Date;
import phi.ap.model.Game;
import phi.ap.model.ItemStack;
import phi.ap.model.enums.FruitTypes;
import phi.ap.model.enums.SaplingTypes;
import phi.ap.model.enums.SeedTypes;
import phi.ap.model.enums.TreeTypes;
import phi.ap.model.items.products.Fruit;

import java.util.ArrayList;

public class Tree extends Plant {
    private final TreeTypes type;
    private SeedTypes seedType;
    private SaplingTypes saplingType;
    private FruitTypes fruit;
    private int remainingHarvestCycles;
    private int HarvestRegrowthTime = 1; // default : last stage
    private Date lastHarvestDate;

    public Tree(int height, int width, TreeTypes type, boolean isForaging) {

        super(height, width, null, type.getStages(), false, Game.getInstance().getDate());
        if (isForaging) {
            setPlantingDate(new Date(Game.getInstance().getDate().getDay() - totalHarvestTime()));
        }
        String sourceName = null;
        seedType = type.getSeedType();
        saplingType = type.getSaplingType();
        if (seedType != null) sourceName = seedType.toString();
        else if (saplingType != null) sourceName = saplingType.toString();
        setSourceName(sourceName);
        this.type = type;
        this.seedType = seedType;
        this.saplingType = saplingType;
        this.fruit = type.getFruit();
        setName(type.toString());
        this.remainingHarvestCycles = fruit.getFruitHarvestCycle();
        if (!getStages().isEmpty()) {
            HarvestRegrowthTime = getStages().getLast();
        }
        fillTile(type.getTile());//TODO : look manage;
    }


    public TreeTypes getType() {
        return type;
    }

    @Override
    public ArrayList<ItemStack> getProducts() {
        ArrayList<ItemStack> products = new ArrayList<>();
        if (!isAlive()) return products;
        if (!isStagesDone()) return products;
        if (remainingHarvestCycles == 0) return products;
        if (lastHarvestDate != null) {
            if (Game.getInstance().getDate().getDay() - lastHarvestDate.getDay() > HarvestRegrowthTime) {
                products.add(new ItemStack(new Fruit(1, 1, fruit), fruit.getStackSize()));
                --remainingHarvestCycles;
            }
        } else {
            products.add(new ItemStack(new Fruit(1, 1, fruit), fruit.getStackSize()));
            --remainingHarvestCycles;
        }
        return products;
    }

    @Override
    public ArrayList<ItemStack> getDrops() {
        return null;
    }

    @Override
    public void doTask() {

    }


}
