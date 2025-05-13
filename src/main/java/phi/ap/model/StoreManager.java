package phi.ap.model;

import phi.ap.model.enums.FaceWay;
import phi.ap.model.enums.StoreTypes;
import phi.ap.model.enums.TileType;
import phi.ap.model.items.Portal;
import phi.ap.model.items.buildings.Store;

import java.awt.*;

public class StoreManager {
    private Store blacksmith;
    private Store marnieRanch;
    private Store fish;
    private Store starDrop;
    private Store jojaMart;
    private Store pierre;
    private Store carpenter;
    public StoreManager() {
        blacksmith = new Store(5, 14, StoreTypes.Blacksmith);
        blacksmith.setCoordinate(new Coordinate(2, 2));
        Portal.makeMiddleDoor(blacksmith, Game.getInstance().getMap().getNPCVillage(), TileType.Door.getTile(), FaceWay.Down);
        Text text = new Text("BlackSmith", new Coordinate(2, 2));
        blacksmith.addItem(text);

        marnieRanch = new Store(7, 15, StoreTypes.MarnieRanch);
        marnieRanch.setCoordinate(new Coordinate(3, 20));
        Portal.makeMiddleDoor(marnieRanch, Game.getInstance().getMap().getNPCVillage(), TileType.Door.getTile(), FaceWay.Down);
        text = new Text("MarnieRanch", new Coordinate(3, 2));
        marnieRanch.addItem(text);

        fish = new Store(3, 14, StoreTypes.FishShop);
        fish.setCoordinate(new Coordinate(9, 2));
        Portal.makeMiddleDoor(fish, Game.getInstance().getMap().getNPCVillage(), TileType.Door.getTile(), FaceWay.Down);
        text = new Text("FishShop", new Coordinate(1, 3));
        fish.addItem(text);

        starDrop = new Store(5, 14, StoreTypes.TheStarDropSaloon);
        starDrop.setCoordinate(new Coordinate(33, 2));
        Portal.makeMiddleDoor(starDrop, Game.getInstance().getMap().getNPCVillage(), TileType.Door.getTile(), FaceWay.Up);
        text = new Text("StarDrop", new Coordinate(2, 3));
        starDrop.addItem(text);

        jojaMart = new Store(7, 14, StoreTypes.JojaMart);
        jojaMart.setCoordinate(new Coordinate(31, 22));
        Portal.makeMiddleDoor(jojaMart, Game.getInstance().getMap().getNPCVillage(), TileType.Door.getTile(), FaceWay.Up);
        text = new Text("JojaMart", new Coordinate(3, 3));
        jojaMart.addItem(text);

        pierre = new Store(5, 20, StoreTypes.PierreGeneralStore);
        pierre.setCoordinate(new Coordinate(25, 2));
        Portal.makeMiddleDoor(pierre, Game.getInstance().getMap().getNPCVillage(), TileType.Door.getTile(), FaceWay.Up);
        text = new Text("PierreStore", new Coordinate(2, 5));
        pierre.addItem(text);

        carpenter = new Store(3, 14, StoreTypes.CarpenterShop);
        carpenter.setCoordinate(new Coordinate(26, 24));
        Portal.makeMiddleDoor(carpenter, Game.getInstance().getMap().getNPCVillage(), TileType.Door.getTile(), FaceWay.Up);
        text = new Text("Carpenter", new Coordinate(1, 2));
        carpenter.addItem(text);

        Game.getInstance().getMap().getNPCVillage().addItem(blacksmith);
        Game.getInstance().getMap().getNPCVillage().addItem(fish);
        Game.getInstance().getMap().getNPCVillage().addItem(marnieRanch);
        Game.getInstance().getMap().getNPCVillage().addItem(pierre);
        Game.getInstance().getMap().getNPCVillage().addItem(starDrop);
        Game.getInstance().getMap().getNPCVillage().addItem(jojaMart);
        Game.getInstance().getMap().getNPCVillage().addItem(carpenter);
    }

    private void refreshFishShop(){

    }
}
