package phi.ap.model.items.buildings.stores;

import phi.ap.model.ProductInStore;
import phi.ap.model.enums.StoreTypes;
import phi.ap.model.items.Item;
import phi.ap.model.items.buildings.Building;

import java.util.AbstractMap;
import java.util.ArrayList;

public class Store extends Building {
    private StoreTypes storeTypes;
    private ArrayList<AbstractMap.SimpleEntry<String, Item>> availableProducts = new ArrayList<>();
    private ArrayList<AbstractMap.SimpleEntry<String, Item>> allProducts;

    public Store(int height, int width, StoreTypes storeType) {
        super(height, width);
        setName(storeType.getName());
        fillTile(storeType.getTileType().getTile());
        setWalls();
        this.storeTypes = storeType;
    }


    public void addProduct(AbstractMap.SimpleEntry<String, Item> item){
        availableProducts.add(item);
    }
    public void clearProducts(){
        availableProducts.clear();
    }
    @Override
    public void doTask() {

    }

    public StoreTypes getStoreTypes() {
        return storeTypes;
    }

    public ArrayList<AbstractMap.SimpleEntry<String, Item>> getAvailableProducts(){
        return availableProducts;
    }
    public ArrayList<AbstractMap.SimpleEntry<String, Item>> getAllProducts(){
        return allProducts;
    }

    public void setAllProducts(ArrayList<AbstractMap.SimpleEntry<String, Item>> allProducts) {
        this.allProducts = allProducts;
    }
    public void buy(Item it){
        for(AbstractMap.SimpleEntry<String, Item> item : availableProducts){
            if(item.getValue().equals(it)){
                availableProducts.remove(item);
                return;
            }
        }
    }
}
