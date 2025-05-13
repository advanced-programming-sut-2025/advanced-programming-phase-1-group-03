package phi.ap.model.enums;

import phi.ap.model.Eatable;
import phi.ap.model.ItemStack;
import phi.ap.model.LevelProcess;
import phi.ap.model.Tile;
import phi.ap.model.items.products.Product;

import java.util.ArrayList;
import java.util.List;

public enum ProductNames {
    Grass("Grass", new LevelProcess(), new Tile("#", Colors.fg(28), "", false)),
    Fiber("Fiber", new LevelProcess(), new Tile("#", Colors.fg(28), "", false)),
    ;
    private String name;
    private LevelProcess levels;
    private Product instance;
    private Tile tile;
    private int sellPrice;
    private boolean sellable;
    private Eatable eatable;
    private ArrayList<ItemStack> drops;
    private ArrayList<ItemStack> products;

    ProductNames(String name, LevelProcess levels, Tile tile, boolean sellable,int sellPrice, Eatable eatable, ArrayList<ItemStack> drops, ArrayList<ItemStack> products) {
        this.name = name;
        this.levels = levels;
        this.instance = new Product(1, 1);
        this.tile = new Tile(tile);
        this.sellable = sellable;
        this.sellPrice = sellPrice;
        this.eatable = eatable;
        this.drops = drops;
        this.products = products;
        instance.setName(name);
        instance.setLevels(levels);
        if (eatable != null) instance.setEatable(new Eatable(eatable));
        else instance.setEatable(null);
        instance.setSellPrice(sellPrice);
        instance.setSellable(sellable);
        instance.fillTile(tile);
        instance.setDrops(new ArrayList<>(drops));
        instance.setProducts(new ArrayList<>(products));
    }

    ProductNames(String name, LevelProcess levels, Tile tile, boolean sellable,int sellPrice, Eatable eatable) {
        this.name = name;
        this.levels = levels;
        this.instance = new Product(1, 1);
        this.tile = new Tile(tile);
        this.sellable = sellable;
        this.sellPrice = sellPrice;
        this.eatable = eatable;
        this.drops = new ArrayList<>();
        this.products = new ArrayList<>();
        instance.setName(name);
        instance.setLevels(levels);
        if (eatable != null) instance.setEatable(new Eatable(eatable));
        else instance.setEatable(null);
        instance.setSellPrice(sellPrice);
        instance.setSellable(sellable);
        instance.fillTile(tile);
        instance.setDrops(new ArrayList<>(drops));
        instance.setProducts(new ArrayList<>(products));
    }

    ProductNames(String name, LevelProcess levels, Tile tile) {
        this.name = name;
        this.levels = levels;
        this.instance = new Product(1, 1);
        this.tile = new Tile(tile);
        this.sellable = false;
        this.sellPrice = 0;
        this.eatable = null;
        this.drops = new ArrayList<>();
        this.products = new ArrayList<>();
        instance.setName(name);
        instance.setLevels(levels);
        if (eatable != null) instance.setEatable(new Eatable(eatable));
        else instance.setEatable(null);
        instance.setSellPrice(sellPrice);
        instance.setSellable(sellable);
        instance.fillTile(tile);
        instance.setDrops(new ArrayList<>(drops));
        instance.setProducts(new ArrayList<>(products));
    }

    public String getName() {
        return name;
    }

    public LevelProcess getLevels() {
        return levels;
    }

    public Product getInstance() {
        return instance;
    }

    public Tile getTile() {
        return tile;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public Eatable getEatable() {
        return eatable;
    }

    public boolean isSellable() {
        return sellable;
    }

    public ArrayList<ItemStack> getDrops() {
        return drops;
    }

    public ArrayList<ItemStack> getProducts() {
        return products;
    }
}
