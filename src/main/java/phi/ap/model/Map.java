package phi.ap.model;

import phi.ap.model.enums.TileType;
import phi.ap.model.items.Item;
import phi.ap.model.items.buildings.Farm;
import phi.ap.model.items.buildings.NPCVillage;

import java.util.ArrayList;

public class Map extends Item{
    public Map() {
        super(102, 102);
        setName("Map");
        this.setCoordinate(new Coordinate(0, 0));
        fillTile(TileType.Ground.getTile());
    }

    @Override
    public void doTask() {

    }

    public NPCVillage getNPCVillage() {
        for (Item ground : getHoldingItems()) {
            if (ground instanceof NPCVillage) {
                return (NPCVillage) ground;
            }
        }
        return null;
    }

    public void addFarm(Farm farm, Player player) {
        int y, x;
        switch (App.getInstance().getPlayerService().getPlayerIndex(player)) {
            case 0:
                y = 1;
                x = 1;
                break;
            case 1:
                y = 1;
                x = this.getWidth() - 1 - farm.getWidth();
                break;
            case 2:
                y = this.getHeight() - 1 - farm.getHeight();
                x = 1;
                break;
            case 3:
                y = this.getHeight() - 1 - farm.getHeight();
                x = this.getWidth() - 1 - farm.getWidth();
                break;
            default:
                return;
        }
        farm.setCoordinate(new Coordinate(y, x));

        player.setFarm(farm);
        addItem(farm);
    }
    public void addNPCVillage() {
        NPCVillage village = new NPCVillage();
        village.setCoordinate(new Coordinate(31, 31));
        addItem(village);
    }

}
