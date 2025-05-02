package phi.ap.model.items.buildings;

import phi.ap.model.Coordinate;
import phi.ap.model.Ground;
import phi.ap.model.enums.FarmTypes;
import phi.ap.model.enums.TileType;
import phi.ap.model.items.Item;

import java.util.ArrayList;

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
        greenhouse = new Greenhouse(farmType.getGreenhouse().getHeight(), farmType.getGreenhouse().getWidth(),
                farmType.getGreenhouse().getCoordinate());
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
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                setTile(i, j, TileType.Farm);
            }
        }
        addItem(cottage);
        addItem(greenhouse);
        for (Lake lake : lakes) {
            addItem(lake);
        }
        for (Quarry quarry : quarries) {
            addItem(quarry);
        }
    }


    @Override
    public void doTask() {

    }
}
