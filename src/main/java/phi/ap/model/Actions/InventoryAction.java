package phi.ap.model.Actions;

import phi.ap.model.Coordinate;
import phi.ap.model.enums.Weather;

public class InventoryAction{
    private static InventoryAction instance = null;

    private InventoryAction() {
    }

    public static InventoryAction getInstance() {
        if (instance == null) {
            instance = new InventoryAction();
        }
        return instance;
    }

    public void deleteFromInventory(Coordinate direction) {}
}
