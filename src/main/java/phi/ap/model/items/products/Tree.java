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
    private TreeTypes type;
    private SeedTypes seedType;
    private SaplingTypes saplingType;
    private FruitTypes fruit;
    private int remainingHarvestCycles;
    private int HarvestRegrowthTime = 1; // default : last stage
    private Date lastHarvestDate;
    private boolean isThundered = false;
    private boolean isForaging;

    public Tree(int height, int width, TreeTypes type, boolean isForaging) {

        super(height, width, null, type.getStages(), false, Game.getInstance().getDate());
        if (isForaging) {
            setPlantingDate(new Date(Game.getInstance().getDate().getDay() - totalHarvestTime()));
        }
        this.isThundered = isForaging;
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
        setSellable(false);
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
            setLevelsForArrayList(products);
            return products;
        }
        if (!isAlive()) {
            setLevelsForArrayList(products);
            return products;
        }
        if (!isStagesDone()) {
            setLevelsForArrayList(products);
            return products;
        }
        if (remainingHarvestCycles == 0) {
            setLevelsForArrayList(products);
            return products;
        }
        if (!type.getSeason().equals(Game.getInstance().getDate().getSeason())) {
            setLevelsForArrayList(products);
            return products;
        }
        if (lastHarvestDate != null) {
            if (Game.getInstance().getDate().getDay() - lastHarvestDate.getDay() > HarvestRegrowthTime) {
                products.add(new ItemStack(new Fruit(1, 1, fruit), fruit.getStackSize()));
                --remainingHarvestCycles;
            }
        } else {
            products.add(new ItemStack(new Fruit(1, 1, fruit), fruit.getStackSize()));
            --remainingHarvestCycles;
        }
        setLevelsForArrayList(products);
        return products;
    }

    @Override
    public ArrayList<ItemStack> getDrops() {
        ArrayList<ItemStack> drops = new ArrayList<>();
        if (!isAlive() || isThundered) {
            drops.add(new ItemStack(new Wood(1, 1), App.getInstance().getRandomNumber(2, 3)));
        } else {
            drops.add(new ItemStack(new Wood(1, 1), App.getInstance().getRandomNumber(2, 3)));
            if (fruit.isSyrup()) {
                drops.add(new ItemStack(new Fruit(1, 1, fruit), fruit.getStackSize()));
            }
            if (seedType != null) {
                drops.add(new ItemStack(new Seed(1, 1, seedType),
                        App.getInstance().getRandomNumber(0, 2)));
            } else if (saplingType != null) {
                drops.add(new ItemStack(new Sapling(1, 1, saplingType), App.getInstance().getRandomNumber(0, 2)));
            }
        }
        setLevelsForArrayList(drops);
        return drops;
    }


    @Override
    public void doTask() {

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

    public SaplingTypes getSaplingType() {
        return saplingType;
    }

    public int getRemainingHarvestCycles() {
        return remainingHarvestCycles;
    }

    public int getHarvestRegrowthTime() {
        return HarvestRegrowthTime;
    }

    public Date getLastHarvestDate() {
        return lastHarvestDate;
    }

    public void copy(Tree otherTree) {
        super.copy(otherTree);
        setSourceName(otherTree.getSourceName());
        this.seedType = otherTree.getSeedType();
        this.saplingType = otherTree.getSaplingType();
        this.type = otherTree.getType();
        this.fruit = otherTree.getFruit();
        this.remainingHarvestCycles = otherTree.getRemainingHarvestCycles();
        this.HarvestRegrowthTime = otherTree.getHarvestRegrowthTime();
        this.isThundered = otherTree.isThundered();
        if (otherTree.lastHarvestDate != null)this.lastHarvestDate = new Date(otherTree.getLastHarvestDate().getHour());
        else this.lastHarvestDate = null;
    }

    public boolean isForaging() {
        return isForaging;
    }
}
