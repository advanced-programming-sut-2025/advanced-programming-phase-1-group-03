package phi.ap.model.items.products;

import phi.ap.model.Eatable;
import phi.ap.model.ItemStack;
import phi.ap.model.LevelProcess;
import phi.ap.model.enums.LevelName;
import phi.ap.model.enums.ProductNames;
import phi.ap.model.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Product extends Item {
    private LevelProcess levels;
    private ArrayList<ItemStack> drops = new ArrayList<>();
    private ArrayList<ItemStack> products = new ArrayList<>();

    public ArrayList<ItemStack> getProducts(){
        return products;
    }; // with scythe(dus);
    public ArrayList<ItemStack> getDrops(){
        return drops;
    }; // with axe;

    public Product(int height, int width) {
        super(height, width);
    }

    public Product(Product product) {
        super(product.getHeight(), product.getWidth());
        this.levels = new LevelProcess(product.getLevels());
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
        Product product = type.getInstance();
        this.levels = new LevelProcess(product.getLevels());
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

    private final static  LevelProcess levelProcess = new LevelProcess(new ArrayList<>(
            List.of(LevelName.normal, LevelName.silver, LevelName.golden, LevelName.iridium)), 0);

    @Override
    public void doTask() {

    }

    @Override
    public boolean canStackWith(Item otherItem) {
        if (!super.canStackWith(otherItem)) return false;
        if (!(otherItem instanceof Product otherProduct)) return false;
        return otherProduct.getName().equals(getName());
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
}
