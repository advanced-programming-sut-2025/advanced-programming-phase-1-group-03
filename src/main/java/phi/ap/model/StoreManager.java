package phi.ap.model;

import phi.ap.model.enums.FaceWay;
import phi.ap.model.enums.StoreProducts.*;
import phi.ap.model.enums.StoreTypes;
import phi.ap.model.enums.TileType;
import phi.ap.model.items.Portal;
import phi.ap.model.items.buildings.stores.*;
import phi.ap.model.npcStuff.NPC;

import java.util.AbstractMap;
import java.util.ArrayList;

public class StoreManager {
    private Store blacksmith;
    private Store marnieRanch;
    private Store fish;
    private Store starDrop;
    private Store jojaMart;
    private Store pierre;
    private Store carpenter;

    public StoreManager() {

        blacksmith = new Store<BlackSmithsProducts>(5, 14, StoreTypes.Blacksmith);
        blacksmith.setCoordinate(new Coordinate(2, 2));
        Text text = new Text("BlackSmith", new Coordinate(2, 2));
        blacksmith.addItem(text);

        marnieRanch = new Store<MarnieRanchProducts>(7, 15, StoreTypes.MarnieRanch);
        marnieRanch.setCoordinate(new Coordinate(3, 20));
        text = new Text("MarnieRanch", new Coordinate(3, 2));
        marnieRanch.addItem(text);

        fish = new Store<FishShopProducts>(3, 14, StoreTypes.FishShop);
        fish.setCoordinate(new Coordinate(9, 2));
        text = new Text("FishShop", new Coordinate(1, 3));
        fish.addItem(text);

        starDrop = new Store<StarDropSaloonProducts>(5, 14, StoreTypes.TheStarDropSaloon);
        starDrop.setCoordinate(new Coordinate(33, 2));
        text = new Text("StarDrop", new Coordinate(2, 3));
        starDrop.addItem(text);

        jojaMart = new Store<JojaMarketProducts>(7, 14, StoreTypes.JojaMart);
        jojaMart.setCoordinate(new Coordinate(31, 22));
        text = new Text("JojaMart", new Coordinate(3, 3));
        jojaMart.addItem(text);

        pierre = new Store<PierreGeneralStoreProduct>(5, 20, StoreTypes.PierreGeneralStore);
        pierre.setCoordinate(new Coordinate(25, 2));
        text = new Text("PierreStore", new Coordinate(2, 5));
        pierre.addItem(text);

        carpenter = new Store<CarpenterShopProducts>(3, 14, StoreTypes.CarpenterShop);
        carpenter.setCoordinate(new Coordinate(26, 24));
        text = new Text("Carpenter", new Coordinate(1, 2));
        carpenter.addItem(text);

        Game.getInstance().getMap().getNPCVillage().addItem(blacksmith);
        Game.getInstance().getMap().getNPCVillage().addItem(fish);
        Game.getInstance().getMap().getNPCVillage().addItem(marnieRanch);
        Game.getInstance().getMap().getNPCVillage().addItem(pierre);
        Game.getInstance().getMap().getNPCVillage().addItem(starDrop);
        Game.getInstance().getMap().getNPCVillage().addItem(jojaMart);
        Game.getInstance().getMap().getNPCVillage().addItem(carpenter);

        NPC npc;
        npc = new NPC(blacksmith.getStoreTypes().getStoreManagerName());
        npc.setCoordinate(new Coordinate(blacksmith.getHeight() / 2 - 1, blacksmith.getWidth() / 2));
        blacksmith.addItem(npc);
        Game.getInstance().getNpcs().add(npc);

        npc = new NPC(fish.getStoreTypes().getStoreManagerName());
        npc.setCoordinate(new Coordinate(1, 1));
        fish.addItem(npc);
        Game.getInstance().getNpcs().add(npc);

        npc = new NPC(marnieRanch.getStoreTypes().getStoreManagerName());
        npc.setCoordinate(new Coordinate(marnieRanch.getHeight() / 2 - 1, marnieRanch.getWidth() / 2));
        marnieRanch.addItem(npc);
        Game.getInstance().getNpcs().add(npc);

        npc = new NPC(pierre.getStoreTypes().getStoreManagerName());
        npc.setCoordinate(new Coordinate(pierre.getHeight() / 2 + 1, pierre.getWidth() / 2));
        pierre.addItem(npc);
        Game.getInstance().getNpcs().add(npc);

        npc = new NPC(starDrop.getStoreTypes().getStoreManagerName());
        npc.setCoordinate(new Coordinate(starDrop.getHeight() / 2 + 1, starDrop.getWidth() / 2));
        starDrop.addItem(npc);
        Game.getInstance().getNpcs().add(npc);

        npc = new NPC(jojaMart.getStoreTypes().getStoreManagerName());
        npc.setCoordinate(new Coordinate(jojaMart.getHeight() / 2 + 1, jojaMart.getWidth() / 2));
        jojaMart.addItem(npc);
        Game.getInstance().getNpcs().add(npc);

        npc = new NPC(carpenter.getStoreTypes().getStoreManagerName());
        npc.setCoordinate(new Coordinate(1, 1));
        carpenter.addItem(npc);
        Game.getInstance().getNpcs().add(npc);

        Portal.makeMiddleDoor(carpenter, Game.getInstance().getMap().getNPCVillage(), TileType.Door.getTile(), FaceWay.Up);
        Portal.makeMiddleDoor(pierre, Game.getInstance().getMap().getNPCVillage(), TileType.Door.getTile(), FaceWay.Up);
        Portal.makeMiddleDoor(jojaMart, Game.getInstance().getMap().getNPCVillage(), TileType.Door.getTile(), FaceWay.Up);
        Portal.makeMiddleDoor(starDrop, Game.getInstance().getMap().getNPCVillage(), TileType.Door.getTile(), FaceWay.Up);
        Portal.makeMiddleDoor(fish, Game.getInstance().getMap().getNPCVillage(), TileType.Door.getTile(), FaceWay.Down);
        Portal.makeMiddleDoor(marnieRanch, Game.getInstance().getMap().getNPCVillage(), TileType.Door.getTile(), FaceWay.Down);
        Portal.makeMiddleDoor(blacksmith, Game.getInstance().getMap().getNPCVillage(), TileType.Door.getTile(), FaceWay.Down);

        refreshItems();
    }

    private void refreshFishShop(){
        fish.clearProducts();
        ArrayList<Pair<FishShopProducts>> allProducts = new ArrayList<>();
        for(FishShopProducts product : FishShopProducts.values()){
            allProducts.add(new Pair<FishShopProducts>(product, product.getDailyLimit()));
            fish.addProduct(new Pair<FishShopProducts>(product, product.getDailyLimit()));
        }
        fish.setAllProducts(allProducts);
    }
    private void refreshJojaMart(){
        jojaMart.clearProducts();
        ArrayList<Pair<JojaMarketProducts>> allProducts = new ArrayList<>();
        for(JojaMarketProducts product : JojaMarketProducts.values()){
            allProducts.add(new Pair<JojaMarketProducts>(product, product.getDailyLimit()));
            jojaMart.addProduct(new Pair<JojaMarketProducts>(product, product.getDailyLimit()));
        }
        jojaMart.setAllProducts(allProducts);
    }
    private void refreshBlackSmith(){
        blacksmith.clearProducts();
        ArrayList<Pair<BlackSmithsProducts>> allProducts = new ArrayList<>();
        for(BlackSmithsProducts product : BlackSmithsProducts.values()){
            allProducts.add(new Pair<BlackSmithsProducts>(product, product.getDailyLimit()));
            blacksmith.addProduct(new Pair<BlackSmithsProducts>(product, product.getDailyLimit()));
        }
        blacksmith.setAllProducts(allProducts);
    }
    private void refreshCarpenter(){
        carpenter.clearProducts();
        ArrayList<Pair<CarpenterShopProducts>> allProducts = new ArrayList<>();
        for(CarpenterShopProducts product : CarpenterShopProducts.values()){
            allProducts.add(new Pair<CarpenterShopProducts>(product, product.getDailyLimit()));
            carpenter.addProduct(new Pair<CarpenterShopProducts>(product, product.getDailyLimit()));
        }
        carpenter.setAllProducts(allProducts);
    }
    private void refreshMarnieRench(){
        marnieRanch.clearProducts();
        ArrayList<Pair<MarnieRanchProducts>> allProducts = new ArrayList<>();
        for(MarnieRanchProducts product : MarnieRanchProducts.values()){
            allProducts.add(new Pair<MarnieRanchProducts>(product, product.getDailyLimit()));
            marnieRanch.addProduct(new Pair<MarnieRanchProducts>(product, product.getDailyLimit()));
        }
        marnieRanch.setAllProducts(allProducts);
    }
    private void refreshStarDrop(){
        starDrop.clearProducts();
        ArrayList<Pair<StarDropSaloonProducts>> allProducts = new ArrayList<>();
        for(StarDropSaloonProducts product : StarDropSaloonProducts.values()){
            allProducts.add(new Pair<StarDropSaloonProducts>(product, product.getDailyLimit()));
            starDrop.addProduct(new Pair<StarDropSaloonProducts>(product, product.getDailyLimit()));
        }
        starDrop.setAllProducts(allProducts);
    }
    private void refreshPierreStore(){
        pierre.clearProducts();
        ArrayList<Pair<PierreGeneralStoreProduct>> allProducts = new ArrayList<>();
        for(PierreGeneralStoreProduct product : PierreGeneralStoreProduct.values()){
            allProducts.add(new Pair<PierreGeneralStoreProduct>(product, product.getDailyLimit()));
            pierre.addProduct(new Pair<PierreGeneralStoreProduct>(product, product.getDailyLimit()));
        }
        pierre.setAllProducts(allProducts);
    }
    public void refreshItems(){
        refreshFishShop();
        refreshCarpenter();
        refreshJojaMart();
        refreshBlackSmith();
        refreshStarDrop();
        refreshPierreStore();
        refreshMarnieRench();
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
