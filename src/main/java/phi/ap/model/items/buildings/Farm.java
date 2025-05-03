package phi.ap.model.items.buildings;

import phi.ap.model.Coordinate;
import phi.ap.model.Ground;
import phi.ap.model.Tile;
import phi.ap.model.enums.FarmTypes;
import phi.ap.model.enums.TileType;
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
        cottage = new Cottage(farmType.getCottage().getHeight(), farmType.getCottage().getWidth(),
                farmType.getCottage().getCoordinate());
        cottage.setDoors(new ArrayList<>(List.of(new Tile(TileType.Door.getTile(),
                new Coordinate(cottage.getHeight() - 1, cottage.getWidth() / 2)))));
        greenhouse = new Greenhouse(farmType.getGreenhouse().getHeight(), farmType.getGreenhouse().getWidth(),
                farmType.getGreenhouse().getCoordinate());
        greenhouse.setDoors(new ArrayList<>(List.of(new Tile(TileType.Door.getTile(),
                new Coordinate(greenhouse.getHeight() - 1, greenhouse.getWidth() / 2)))));
        lakes = new ArrayList<>();
        for (BuildingStructure lakeInfos : farmType.getLakes()) {
            Lake lake = new Lake(lakeInfos.getHeight(), lakeInfos.getWidth(), lakeInfos.getCoordinate());
            lakes.add(lake);
        }
        quarries = new ArrayList<>();
        for (BuildingStructure quarryInfos : farmType.getQuarries()) {
            Quarry quarry = new Quarry(quarryInfos.getHeight(), quarryInfos.getWidth(), quarryInfos.getCoordinate());
            quarries.add(quarry);
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
