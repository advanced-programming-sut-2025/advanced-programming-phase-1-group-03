package phi.ap.model.items.buildings.stores;

import phi.ap.model.ProductInStore;
import phi.ap.model.enums.StoreProducts.StoreItemProducer;
import phi.ap.model.enums.StoreTypes;
import phi.ap.model.items.Item;
import phi.ap.model.items.buildings.Building;

import java.util.AbstractMap;
import java.util.ArrayList;

public class Store<T extends StoreItemProducer> extends Building {
    private StoreTypes storeTypes;
    private ArrayList<AbstractMap.SimpleEntry<T, Integer>> availableProducts = new ArrayList<>();
    private ArrayList<AbstractMap.SimpleEntry<T, Integer>> allProducts;

    public Store(int height, int width, StoreTypes storeType) {
        super(height, width);
        setName(storeType.getName());
        fillTile(storeType.getTileType().getTile());
        setWalls();
        this.storeTypes = storeType;
    }


    public void addProduct(AbstractMap.SimpleEntry<T, Integer> item){
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

    public ArrayList<AbstractMap.SimpleEntry<T, Integer>> getAvailableProducts(){
        return availableProducts;
    }
    public ArrayList<AbstractMap.SimpleEntry<T, Integer>> getAllProducts(){
        return allProducts;
    }

    public void setAllProducts(ArrayList<AbstractMap.SimpleEntry<T, Integer>> allProducts) {
        this.allProducts = allProducts;
    }
    public void buy(Item it){
        for(AbstractMap.SimpleEntry<T, Integer> item : availableProducts){
            if(item.getKey().getItem().canStackWith(it)){
                item.setValue(item.getValue() - 1);
                System.out.println(item.getKey().getNameInStore());
                return;
            }
        }
    }
}
