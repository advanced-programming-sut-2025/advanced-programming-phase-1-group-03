package phi.ap.model.items.machines.craftingMachines;

import phi.ap.model.App;
import phi.ap.model.Location;
import phi.ap.model.Tile;
import phi.ap.model.enums.Colors;
import phi.ap.model.enums.CraftingTypes;
import phi.ap.model.items.Dirt;
import phi.ap.model.items.Item;
import phi.ap.model.items.Portal;
import phi.ap.model.items.buildings.Building;
import phi.ap.model.items.machines.Machine;
import phi.ap.model.npcStuff.NPC;

import java.util.Locale;

public class Bomber extends Machine {
    public Bomber(int height, int width, CraftingTypes craftingType) {
        super(height, width, craftingType);
    }
    private int edge;
    public Bomber(int height, int width, String machineName, Integer price) {
        super(height, width, machineName, price);
        switch (machineName) {
            case "CherryBomb":
                edge = 3;
                fillTile(new Tile("●", Colors.fg(17), ""));
                break;
            case "Bomb":
                edge = 5;
                fillTile(new Tile("●", Colors.fg(0), ""));
                break;
            case "MegaBomb":
                edge = 7;
                fillTile(new Tile("●", Colors.fg(88), ""));
                break;
        }
    }

    public int getEdge() {
        return edge;
    }
    public void active() {
        Location loc = App.getInstance().getMapService().getLocationOnMap(getCoordinateBaseMap().getY(), getCoordinateBaseMap().getX());
        for (int dy = -edge; dy <= edge; dy++) {
            for (int dx = -edge; dx <= edge; dx++) {
                if (dx == 0 && dy == 0) continue;
                Item item = loc.getGround().getTopItem(loc.getY() + dy, loc.getX() + dx);
                if (item == null) continue;
                if (item instanceof Building ||
                    item instanceof NPC ||
                    item instanceof Dirt ||
                    item instanceof Portal) continue;
                item.getFather().removeItem(item);
            }
        }
        getFather().removeItem(this);
    }

    @Override
    public void doTask() {

    }
}
