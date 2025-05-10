package phi.ap.model.items.tools;

import phi.ap.model.Ability;
import phi.ap.model.Coordinate;
import phi.ap.model.LevelProcess;
import phi.ap.model.Result;
import phi.ap.model.items.Item;

import java.util.ArrayList;
import java.util.List;

public abstract class Tool extends Item {
    protected Ability contactedAbility;
    protected LevelProcess levelProcess;
    public abstract Result<String> useTool(Coordinate direction);

    public Tool(){
        this.setMaxStackSize(1);
    }
    @Override
    public String toString() {
        return getName() + " level : " + levelProcess.toString();
    }
}
