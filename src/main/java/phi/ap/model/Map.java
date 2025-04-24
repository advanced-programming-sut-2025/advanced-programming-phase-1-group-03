package phi.ap.model;

import phi.ap.model.enums.TileType;
import phi.ap.model.items.Item;
import phi.ap.model.items.buildings.Farm;
import phi.ap.model.items.buildings.NPCVillage;

import java.util.ArrayList;

public class Map extends Ground{
    public Map() {
        super(100, 100);
        this.setCoordinate(new Coordinate(0, 0));
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                setTile(i, j, TileType.Ground);
            }
        }
    }
    private ArrayList<Farm> farms;
    private ArrayList<NPCVillage> npcVillages;

    public ArrayList<Farm> getFarms() {
        return farms;
    }

    public ArrayList<NPCVillage> getNpcVillages() {
        return npcVillages;
    }

}
