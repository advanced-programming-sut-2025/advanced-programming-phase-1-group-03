package phi.ap.model.items.products;

import phi.ap.model.Date;
import phi.ap.model.Eatable;
import phi.ap.model.Game;
import phi.ap.model.ItemStack;
import phi.ap.model.enums.CropsTypes;
import phi.ap.model.enums.ForagingCropsTypes;
import phi.ap.model.enums.SeedTypes;

import java.util.ArrayList;
import java.util.List;

public class Crop extends Plant {
    private SeedTypes seedType;
    private CropsTypes type = null;
    private ForagingCropsTypes foragingType = null;
    private boolean oneTime = true;
    private int regrowthTime = 100000000;
    private Date lastHarvestDate = null;

    public Crop(int height, int width, CropsTypes type) {
        super(height, width, type.getSeed().toString(), type.getStage(), type.getCanBecomeGiant(),
                Game.getInstance() != null ? Game.getInstance().getDate() : null);
        this.seedType = type.getSeed();
        this.type = type;
        oneTime = type.getOneTime();
        regrowthTime = type.getRegrowthTime();
        setSellable(true);
        setSellPrice(type.getBaseSellPrice());
        setName(type.getName());
        if (type.getEatable() != null) {
            setEatable(new Eatable(type.getEatable()));
        }
        setShapeAtStage(type.getShapeAtStages());
        setShapeForCurrentStage();
    }

    public Crop(int height, int width, ForagingCropsTypes type) {
        super(height, width, "foragingSystem", new ArrayList<>(List.of(0)), false,
                Game.getInstance() != null ? Game.getInstance().getDate() : null);
        this.foragingType = type;
        this.seedType = null;
        setName(type.getName());
        setSellable(true);
        setSellPrice(foragingType.getBaseSellPrice());
        setEatable(new Eatable(foragingType.getEnergy()));
        setShapeAtStage(new ArrayList<>(List.of(type.getTile(), type.getTile())));
        setShapeForCurrentStage();
    }

    public String showCrop() {
        String res = "";
        if (!oneTime) res += "Regrowth time: " + String.valueOf(regrowthTime) + "\n";
        res += "Last harvest date: ";
        if (lastHarvestDate != null) res += (Game.getInstance().getDate().getRawDay() - lastHarvestDate.getRawDay()) + " day ago\n";
        else res += "none\n";
        res += "OneTime: " + oneTime + "\n";
        return res;
    }

    @Override
    public ArrayList<ItemStack> getProducts() {
        ArrayList<ItemStack> products = new ArrayList<>();
        if (!isAlive()) {
            setLevelsForArrayList(products);
            return products;
        }
        if (!isStagesDone()) {
            setLevelsForArrayList(products);
            return products;
        }
        if ((type != null ? !type.getSeasonsList().contains(Game.getInstance().getDate().getSeason()) : !foragingType.getSeasonsList().contains(Game.getInstance().getDate().getSeason())) && !isInGreenHouse()) {
            setLevelsForArrayList(products);
            return products;
        }

        if (lastHarvestDate == null || Game.getInstance().getDate().getRawDay() - lastHarvestDate.getRawDay() > regrowthTime) {
            Crop instance;
            int amount = 1;
            if (type != null) {
                instance = new Crop(getHeight(), getWidth(), type);
                amount = type.getStackSize();
            }
            else {
                instance = new Crop(getHeight(), getWidth(), foragingType);
                if (foragingType != null) amount = foragingType.getStackSize();
            }
            products.add(new ItemStack(instance, amount));
            lastHarvestDate = new Date(Game.getInstance().getDate().getHour());
            if (oneTime) {
                delete();
            }
        }
        setLevelsForArrayList(products);
        if (getGiant() != null) {
            for (ItemStack product : products) {
                product.advanceAmount(9 * product.getAmount());
            }
        }
        return products;
    }

    @Override
    public ArrayList<ItemStack> getDrops() {
        return new ArrayList<>();
    }

    public void copy(Crop otherCrop) {
        super.copy(otherCrop);
        this.seedType = otherCrop.seedType;
        this.type = otherCrop.type;
        this.foragingType = otherCrop.foragingType;
        this.oneTime = otherCrop.oneTime;
        this.regrowthTime = otherCrop.regrowthTime;
        if (otherCrop.lastHarvestDate != null) this.lastHarvestDate = new Date(otherCrop.lastHarvestDate.getHour());
        else lastHarvestDate = null;
    }

    public SeedTypes getSeedType() {
        return seedType;
    }

    public CropsTypes getType() {
        return type;
    }

    public ForagingCropsTypes getForagingType() {
        return foragingType;
    }

    public boolean isOneTime() {
        return oneTime;
    }

    public int getRegrowthTime() {
        return regrowthTime;
    }

    public Date getLastHarvestDate() {
        return lastHarvestDate;
    }

    public void setLastHarvestDate(Date lastHarvestDate) {
        this.lastHarvestDate = new Date(lastHarvestDate);
    }

    @Override
    public void doTask() {

    }
}
