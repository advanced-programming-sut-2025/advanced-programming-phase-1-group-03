package phi.ap.model.items.tools;

import phi.ap.model.Ability;
import phi.ap.model.Coordinate;
import phi.ap.model.LevelProcess;
import phi.ap.model.items.Item;

public abstract class Tool extends Item {
    private int energyConsumption;
    private Ability contactedAbility;
    private LevelProcess levelProcess;
    public abstract void useTool(Coordinate direction);
}
