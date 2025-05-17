package phi.ap.model.items.machines.craftingMachines;

import phi.ap.model.enums.CraftingTypes;
import phi.ap.model.items.machines.Machine;

public class Bomber extends Machine {
    public Bomber(int height, int width, CraftingTypes craftingType) {
        super(height, width, craftingType);
        makeRemovableByPickaxe();
    }
    public Bomber(int height, int width, String machineName, Integer price) {
        super(height, width, machineName, price);
    }
    @Override
    public void doTask() {

    }
}
