package phi.ap.model.items.products;

import phi.ap.model.App;
import phi.ap.model.Date;
import phi.ap.model.Game;
import phi.ap.model.ItemStack;
import phi.ap.model.enums.*;
import phi.ap.model.items.Sapling;
import phi.ap.model.items.Seed;

import java.util.ArrayList;

public class Tree extends Plant {
    private final TreeTypes type;
    private SeedTypes seedType;
    private SaplingTypes saplingType;
    private FruitTypes fruit;
    private int remainingHarvestCycles;
    private int HarvestRegrowthTime = 1; // default : last stage
    private int woodAmount = 2;
    private Date lastHarvestDate;
    private boolean isThundered = false;


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
        if (isThundered) {
            products.add(new ItemStack(new Mineral(1, 1, ForagingMineralTypes.COAL),
                    App.getInstance().getRandomNumber(1, 2)));
            return products;
        }
        if (!isAlive()) return products;
        if (!isStagesDone()) return products;
        if (remainingHarvestCycles == 0) return products;
        if (!type.getSeason().equals(Game.getInstance().getDate().getSeason())) return products;
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
        ArrayList<ItemStack> drops = new ArrayList<>();
        if (!isAlive() || isThundered) {
            drops.add(new ItemStack(new Wood(1, 1), App.getInstance().getRandomNumber(2, 3)));
        } else {
            drops.add(new ItemStack(new Wood(1, 1), App.getInstance().getRandomNumber(2, 3)));
            if (fruit == FruitTypes.MapleSyrup || fruit == FruitTypes.MysticSyrup) {
                drops.add(new ItemStack(new Fruit(1, 1, fruit), fruit.getStackSize()));
            }
            if (seedType != null) {
                drops.add(new ItemStack(new Seed(1, 1, seedType),
                        App.getInstance().getRandomNumber(0, 2)));
            } else if (saplingType != null) {
                drops.add(new ItemStack(new Sapling(1, 1, saplingType), App.getInstance().getRandomNumber(0, 1)));
            }
        }
        return drops;
    }

    @Override
    public void doTask() {

    }


    public int getWoodAmount() {
        return woodAmount;
    }

    public SeedTypes getSeedType() {
        return seedType;
    }

    public FruitTypes getFruit() {
        return fruit;
    }

    public void setThundered(boolean thundered) {
        isThundered = thundered;
    }

    public boolean isThundered() {
        return isThundered;
    }
}
