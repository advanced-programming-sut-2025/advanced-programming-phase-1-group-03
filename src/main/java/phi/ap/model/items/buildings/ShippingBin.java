package phi.ap.model.items.buildings;

import phi.ap.model.ItemStack;
import phi.ap.model.enums.TileType;
import phi.ap.model.items.Item;
import phi.ap.model.items.products.Product;

import java.util.ArrayList;

public class ShippingBin extends Building {
    private ArrayList<ItemStack> itemStacks = new ArrayList<>();

    public ShippingBin(int height, int width) {
        super(height, width);
        setName("ShippingBin");
        fillTile(TileType.ShippingBin.getTile());
    }
    @Override
    public void doTask() {

    }

    public void addItem(ItemStack item) {
        itemStacks.add(item);
    }
    /*
    this functions returns total interest from selling products
     */
    public int sellAll(){
        int totalMoney = 0;
        for(ItemStack itemSt : itemStacks){
            Item item = itemSt.getItem();
            if(item instanceof Product){
                totalMoney += ((Product)item).getSellPrice() * itemSt.getAmount();
            }else{
                totalMoney += item.getSellPrice() * itemSt.getAmount();
            }
        }
        return totalMoney;
    }
}
