package phi.ap.model.items.buildings;

import phi.ap.model.*;
import phi.ap.model.enums.FarmTypes;
import phi.ap.model.enums.TileType;
import phi.ap.model.items.Dirt;
import phi.ap.model.items.Item;
import phi.ap.model.items.Portal;
import phi.ap.model.items.Water;

import java.util.ArrayList;

public class Farm extends Building {
    private Cottage cottage;
    private Greenhouse greenhouse;
    private ArrayList<Lake>lakes;
    private ArrayList<Quarry>quarries;
    private FarmTypes farmType;
    private ArrayList<Player> availablePlayers = new ArrayList<>();

    public Farm(FarmTypes farmType) {
        super(30, 30);
        setName("Farm");
        this.farmType = farmType;
        for (int i = 1; i < getHeight() - 1; i++) {
            for (int j = 1; j < getWidth() - 1; j++) {
                Dirt dirt = new Dirt();
                dirt.setCoordinate(new Coordinate(i, j));
                addItem(dirt);
            }
        }
        fillTile(TileType.Farm.getTile());
        setWalls();

        cottage = new Cottage(farmType.getCottage().getHeight(), farmType.getCottage().getWidth(),
                farmType.getCottage().getCoordinate());
        //cottage doors:
        addItem(cottage);
        Portal.makeMiddleDoor(cottage, this, TileType.Door.getTile());


        greenhouse = new Greenhouse(farmType.getGreenhouse().getHeight(), farmType.getGreenhouse().getWidth(),
                farmType.getGreenhouse().getCoordinate());
        //greenhouse doors
        addItem(greenhouse);
//        Portal.makeMiddleDoor(greenhouse, this, TileType.Door.getTile());

        lakes = new ArrayList<>();
        for (BuildingStructure lakeInfos : farmType.getLakes()) {
            Lake lake = new Lake(lakeInfos.getHeight(), lakeInfos.getWidth(), lakeInfos.getCoordinate());
            lakes.add(lake);
        }
        for (Lake lake : lakes) {
            addItem(lake);
        }

        for (BuildingStructure quarryInfos : farmType.getQuarries()) {
            Quarry quarry = new Quarry(quarryInfos.getHeight(), quarryInfos.getWidth(), quarryInfos.getCoordinate());
            addItem(quarry);
            Coordinate tempCo;
//            tempCo = new Coordinate(0, 0);
//            Portal.makePortalTwoWay(this, getCoordinateOfCoordinateInChild(quarry,tempCo),
//                    quarry, new Coordinate(1, 1), null);
//            tempCo = new Coordinate(0, quarry.getWidth() - 1);
//            Portal.makePortalTwoWay(this, getCoordinateOfCoordinateInChild(quarry,tempCo),
//                    quarry, new Coordinate(1, quarry.getHeight() - 2), null);
//            tempCo = new Coordinate(quarry.getHeight() - 1, 0);
//            Portal.makePortalTwoWay(this, getCoordinateOfCoordinateInChild(quarry,tempCo),
//                    quarry, new Coordinate(quarry.getHeight() - 2, 1), null);
//            tempCo = new Coordinate(quarry.getHeight() - 1, quarry.getWidth() - 1);
//            Portal.makePortalTwoWay(this, getCoordinateOfCoordinateInChild(quarry,tempCo),
//                    quarry, new Coordinate(quarry.getHeight() - 2, quarry.getWidth() - 2), null);
            for (int i = 1; i < quarry.getHeight() - 1; i++) {
                tempCo = new Coordinate(i, 0);
                Portal.makePortalTwoWay(this, getCoordinateOfCoordinateInChild(quarry,tempCo),
                        quarry, tempCo, null);
                tempCo = new Coordinate(i, quarry.getWidth() - 1);
                Portal.makePortalTwoWay(this, getCoordinateOfCoordinateInChild(quarry,tempCo),
                        quarry, tempCo, null);
            }
            for (int j = 1; j < quarry.getWidth() - 1; j++) {
                tempCo = new Coordinate(0, j);
                Portal.makePortalTwoWay(this, getCoordinateOfCoordinateInChild(quarry,tempCo),
                        quarry, tempCo, null);
                tempCo = new Coordinate(quarry.getHeight() - 1, j);
                Portal.makePortalTwoWay(this, getCoordinateOfCoordinateInChild(quarry,tempCo),
                        quarry, tempCo, null);
            }

        }
    }

    public void removeItem(Item item) { //base on ground;
        super.removeItem(item);
        int y = item.getCoordinate().getY();
        int x = item.getCoordinate().getX();
        for (int i = y; i < y + item.getHeight(); i++) {
            for (int j = x; j < x + item.getWidth(); j++) {
                if (getItem(i, j) == null) {
                    Dirt dirt = new Dirt();
                    dirt.setCoordinate(new Coordinate(i, j));
                    addItem(dirt);
                }
            }
        }
    }

    public ArrayList<Player> getAvailablePlayers() {
        return availablePlayers;
    }

    public boolean isPlayerAvailable(Player player) {
        return availablePlayers.contains(player);
//        return true;
    }

    public Cottage getCottage() {
        return cottage;
    }

    public Greenhouse getGreenhouse() {
        return greenhouse;
    }

    public ArrayList<Lake> getLakes() {
        return lakes;
    }

    public ArrayList<Quarry> getQuarries() {
        return quarries;
    }

    public FarmTypes getFarmType() {
        return farmType;
    }

    public boolean isAreaEmpty(int y, int x, int height, int width){
        for(int i = y; i < y + height; i++) {
            for(int j = x; j < x + width; j++) {
                if(!(getTopItem(i, j) instanceof Dirt)) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public void doTask() {

    }
}
