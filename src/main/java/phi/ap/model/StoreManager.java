package phi.ap.model;

import phi.ap.model.enums.FaceWay;
import phi.ap.model.enums.StoreProducts.FishShopProducts;
import phi.ap.model.enums.StoreTypes;
import phi.ap.model.enums.TileType;
import phi.ap.model.items.Item;
import phi.ap.model.items.Portal;
import phi.ap.model.items.buildings.stores.*;

import java.util.AbstractMap;
import java.util.ArrayList;

public class StoreManager {
    private BlackSmithStore blacksmith;
    private MarnieRenchStore marnieRanch;
    private FishShop fish;
    private StarDropSaloon starDrop;
    private JojaMart jojaMart;
    private PierreStore pierre;
    private CarpenterStore carpenter;

    public StoreManager() {

        blacksmith = new BlackSmithStore(5, 14);
        marnieRanch = new MarnieRenchStore(7, 15);
        fish = new FishShop(3, 14);
        starDrop = new StarDropSaloon(5, 14);
        jojaMart = new JojaMart(7, 14);
        pierre = new PierreStore(5, 20);
        carpenter = new CarpenterStore(3, 14);

        Game.getInstance().getMap().getNPCVillage().addItem(blacksmith);
        Game.getInstance().getMap().getNPCVillage().addItem(fish);
        Game.getInstance().getMap().getNPCVillage().addItem(marnieRanch);
        Game.getInstance().getMap().getNPCVillage().addItem(pierre);
        Game.getInstance().getMap().getNPCVillage().addItem(starDrop);
        Game.getInstance().getMap().getNPCVillage().addItem(jojaMart);
        Game.getInstance().getMap().getNPCVillage().addItem(carpenter);

        Portal.makeMiddleDoor(carpenter, Game.getInstance().getMap().getNPCVillage(), TileType.Door.getTile(), FaceWay.Up);
        Portal.makeMiddleDoor(pierre, Game.getInstance().getMap().getNPCVillage(), TileType.Door.getTile(), FaceWay.Up);
        Portal.makeMiddleDoor(jojaMart, Game.getInstance().getMap().getNPCVillage(), TileType.Door.getTile(), FaceWay.Up);
        Portal.makeMiddleDoor(starDrop, Game.getInstance().getMap().getNPCVillage(), TileType.Door.getTile(), FaceWay.Up);
        Portal.makeMiddleDoor(fish, Game.getInstance().getMap().getNPCVillage(), TileType.Door.getTile(), FaceWay.Down);
        Portal.makeMiddleDoor(marnieRanch, Game.getInstance().getMap().getNPCVillage(), TileType.Door.getTile(), FaceWay.Down);
        Portal.makeMiddleDoor(blacksmith, Game.getInstance().getMap().getNPCVillage(), TileType.Door.getTile(), FaceWay.Down);

        refreshFishShop();
    }

    private void refreshFishShop(){
        fish.clearProducts();
        ArrayList<AbstractMap.SimpleEntry<String, Item>> allProducts = new ArrayList<>();
        for(FishShopProducts product : FishShopProducts.values()){
            for(int i = 0; i < product.getDailyLimit(); i++)
                fish.addProduct(new AbstractMap.SimpleEntry<>(product.getName(), product.getItem()));
            allProducts.add(new AbstractMap.SimpleEntry<>(product.getName(), product.getItem()));
        }
        fish.setAllProducts(allProducts);
    }

    public void refreshItems(){
        refreshFishShop();
    }

    public Store getStore(StoreTypes storeType){
        if(storeType == StoreTypes.PierreGeneralStore)
            return pierre;
        else if(storeType == StoreTypes.Blacksmith)
            return blacksmith;
        else if(storeType == StoreTypes.CarpenterShop)
            return carpenter;
        else if(storeType == StoreTypes.JojaMart)
            return jojaMart;
        else if(storeType == StoreTypes.TheStarDropSaloon)
            return starDrop;
        else if(storeType == StoreTypes.MarnieRanch)
            return marnieRanch;
        else if(storeType == StoreTypes.FishShop)
            return fish;
        return null; //this never happen
    }
}
