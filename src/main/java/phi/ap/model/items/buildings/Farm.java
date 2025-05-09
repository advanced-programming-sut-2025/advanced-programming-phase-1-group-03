package phi.ap.model.items.buildings;

import phi.ap.model.*;
import phi.ap.model.enums.FarmTypes;
import phi.ap.model.enums.TileType;
import phi.ap.model.items.Dirt;
import phi.ap.model.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Farm extends Building {
    private Cottage cottage;
    private Greenhouse greenhouse;
    private ArrayList<Lake>lakes;
    private ArrayList<Quarry>quarries;
    private FarmTypes farmType;
    public Farm(FarmTypes farmType) {
        super(30, 30);
        this.farmType = farmType;
        for (int i = 1; i < getHeight() - 1; i++) {
            for (int j = 1; j < getWidth() - 1; j++) {
                Dirt dirt = new Dirt();
                dirt.setTile(0, 0, TileType.Dirt.getTile());
                dirt.setCoordinate(new Coordinate(i, j));
                addItem(dirt);
            }
        }
        cottage = new Cottage(farmType.getCottage().getHeight(), farmType.getCottage().getWidth(),
                farmType.getCottage().getCoordinate());
        //cottage doors:
        Portal.makeMiddleDoor(cottage, this, TileType.Door.getTile());
        greenhouse = new Greenhouse(farmType.getGreenhouse().getHeight(), farmType.getGreenhouse().getWidth(),
                farmType.getGreenhouse().getCoordinate());
        //greenhouse doors
        Portal.makeMiddleDoor(greenhouse, this, TileType.Door.getTile());
        //TODO : doors
        lakes = new ArrayList<>();
        for (BuildingStructure lakeInfos : farmType.getLakes()) {
            Lake lake = new Lake(lakeInfos.getHeight(), lakeInfos.getWidth(), lakeInfos.getCoordinate());
            lakes.add(lake);
        }
        quarries = new ArrayList<>();
        for (BuildingStructure quarryInfos : farmType.getQuarries()) {
            Quarry quarry = new Quarry(quarryInfos.getHeight(), quarryInfos.getWidth(), quarryInfos.getCoordinate());
            quarries.add(quarry);
            Coordinate tempCo;
            tempCo = new Coordinate(0, 0);
            Portal.makePortalTwoWay(this, getCoordinateOfCoordinateInChild(quarry,tempCo),
                    quarry, new Coordinate(1, 1), null);
            tempCo = new Coordinate(0, quarry.getWidth() - 1);
            Portal.makePortalTwoWay(this, getCoordinateOfCoordinateInChild(quarry,tempCo),
                    quarry, new Coordinate(1, quarry.getHeight() - 2), null);
            tempCo = new Coordinate(quarry.getHeight() - 1, 0);
            Portal.makePortalTwoWay(this, getCoordinateOfCoordinateInChild(quarry,tempCo),
                    quarry, new Coordinate(quarry.getHeight() - 2, 1), null);
            tempCo = new Coordinate(quarry.getHeight() - 1, quarry.getWidth() - 1);
            Portal.makePortalTwoWay(this, getCoordinateOfCoordinateInChild(quarry,tempCo),
                    quarry, new Coordinate(quarry.getHeight() - 2, quarry.getWidth() - 2), null);
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
        fillTile(TileType.Farm.getTile());
        addItem(cottage);
        addItem(greenhouse);
        for (Lake lake : lakes) {
            addItem(lake);
        }
        for (Quarry quarry : quarries) {
            addItem(quarry);
        }
        setWalls();
    }


    @Override
    public void doTask() {

    }
}
