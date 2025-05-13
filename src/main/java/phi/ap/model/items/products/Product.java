package phi.ap.model.items.products;

import phi.ap.model.App;
import phi.ap.model.Eatable;
import phi.ap.model.ItemStack;
import phi.ap.model.LevelProcess;
import phi.ap.model.enums.LevelName;
import phi.ap.model.enums.ProductNames;
import phi.ap.model.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Product extends Item {
    private LevelProcess levels = null;
    private ArrayList<ItemStack> drops = new ArrayList<>();
    private ArrayList<ItemStack> products = new ArrayList<>();
    private Integer waitingTime = null;

    public void setWaitingTime(Integer waitingTime) {
        this.waitingTime = waitingTime;
    }


    public Integer getWaitingTime() {
        return waitingTime;
    }

    public void reduceWaitingTime(int diff) {
        this.waitingTime -= diff;
    }
    public ArrayList<ItemStack> getProducts(){
        return products;
    }; // with scythe(dus);
    public ArrayList<ItemStack> getDrops(){
        return drops;
    }; // with axe;

    public Product(int height, int width) {
        super(height, width);
        levels = new LevelProcess(new ArrayList<>(List.of(LevelName.normal)), 0);
    }

    public Product(Product product) {
        super(product.getHeight(), product.getWidth());
        levels = new LevelProcess(new ArrayList<>(List.of(LevelName.normal)), 0);
        if (product.getLevels() != null) this.levels = new LevelProcess(product.getLevels());
        if (product.getEatable() != null) setEatable(new Eatable(product.getEatable()));
        else setEatable(null);
        setName(product.getName());
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                setTile(i, j, product.getTile(i, j));
            }
        }
        setSellable(product.isSellable());
        setSellPrice(product.getSellPrice());
        drops = new ArrayList<>(product.getDrops());
        products = new ArrayList<>(product.getProducts());
    }

    public Product(ProductNames type) {
        super(type.getInstance().getHeight(), type.getInstance().getWidth());
        levels = new LevelProcess(new ArrayList<>(List.of(LevelName.normal)), 0);
        Product product = type.getInstance();
        if (product.getLevels() != null) this.levels = new LevelProcess(product.getLevels());
        if (product.getEatable() != null) setEatable(new Eatable(product.getEatable()));
        else setEatable(null);
        setName(product.getName());
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                setTile(i, j, product.getTile(i, j));
            }
        }
        setSellable(product.isSellable());
        setSellPrice(product.getSellPrice());
        drops = new ArrayList<>(product.getDrops());
        products = new ArrayList<>(product.getProducts());
    }

    public final static  LevelProcess levelProcessSample = new LevelProcess(new ArrayList<>(
            List.of(LevelName.normal, LevelName.silver, LevelName.golden, LevelName.iridium)), 0);
    public static LevelProcess getRandomLevelProcessSample() {
        LevelProcess levelsSample = new LevelProcess(new LevelProcess(Product.levelProcessSample));
        int rand = App.getInstance().getRandomNumber(1, 100);
        if (rand <= 50) levelsSample.setCurrentLevel(0);
        else if (rand <= 70) levelsSample.setCurrentLevel(1);
        else if (rand <= 90) levelsSample.setCurrentLevel(2);
        else levelsSample.setCurrentLevel(3);
        return levelsSample;
    }

    @Override
    public void doTask() {

    }

    @Override
    public boolean canStackWith(Item otherItem) {
        if (!super.canStackWith(otherItem)) return false;
        if (!(otherItem instanceof Product otherProduct)) return false;
        if(!otherProduct.getName().equals(getName())) return false;
        if (otherProduct.getLevels() != null) {
            if (levels == null) return false;
            if (!levels.isEqual(otherProduct.getLevels())) return false;
        }
        return true;
    }

    public LevelProcess getLevels() {
        return levels;
    }

    public void setLevels(LevelProcess levels) {
        this.levels = levels;
    }

    public void setProducts(ArrayList<ItemStack> products) {
        this.products = products;
    }

    public void setDrops(ArrayList<ItemStack> drops) {
        this.drops = drops;
    }

    public void copy(Product product) {
        if (product.getLevels() != null) this.levels = new LevelProcess(product.getLevels());
        else this.levels = null;
        if (product.getEatable() != null) setEatable(new Eatable(product.getEatable()));
        else setEatable(null);
        setName(product.getName());
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (product.getTile(i, j) != null)setTile(i, j, product.getTile(i, j));
            }
        }
        setSellable(product.isSellable());
        setSellPrice(product.getSellPrice());
        drops = new ArrayList<>(product.getDrops());
        products = new ArrayList<>(product.getProducts());
    }

    public int getSellPrice() {
        if (levels == null) return super.getSellPrice();
        return (int) (super.getSellPrice() * levels.getCurrentLevelName().getSellPrinceCoefficient());
    }
}
