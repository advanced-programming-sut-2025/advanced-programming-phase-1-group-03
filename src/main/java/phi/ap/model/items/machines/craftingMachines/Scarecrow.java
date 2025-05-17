package phi.ap.model.items.machines.craftingMachines;

import phi.ap.model.Tile;
import phi.ap.model.enums.Colors;
import phi.ap.model.enums.CraftingTypes;
import phi.ap.model.items.machines.Machine;

public class Scarecrow extends Machine {
    public static int maxProtectingEdge = 0;
    int protectingEdge = 0;
    public Scarecrow(int height, int width, CraftingTypes craftingType) {
        super(height, width, craftingType);
        switch (craftingType) {
            case CraftingTypes.Scarecrow:
                protectingEdge = 8;
                maxProtectingEdge = Math.max(maxProtectingEdge, protectingEdge);
                break;
            case CraftingTypes.DeluxeScarecrow:
                protectingEdge = 12;
                maxProtectingEdge = Math.max(maxProtectingEdge, protectingEdge);
                break;
        }
    }
    public Scarecrow(int height, int width, String machineName, Integer price) {
        super(height, width, machineName, price);
        makeRemovableByPickaxe();
        switch (machineName) {
            case "Scarecrow":
                protectingEdge = 8;
                maxProtectingEdge = Math.max(maxProtectingEdge, protectingEdge);
                fillTile(new Tile("☥", Colors.fg(88), ""));
                break;
            case "DeluxeScarecrow":
                protectingEdge = 12;
                maxProtectingEdge = Math.max(maxProtectingEdge, protectingEdge);
                fillTile(new Tile("☥", Colors.fg(232), ""));
                break;
        }
    }

    public int getProtectingEdge() {
        return protectingEdge;
    }

    @Override
    public void doTask() {

    }
}
