package phi.ap.model.items.tools;

import phi.ap.model.Coordinate;
import phi.ap.model.LevelProcess;
import phi.ap.model.Result;
import phi.ap.model.enums.AbilityType;
import phi.ap.model.enums.LevelName;

import java.util.ArrayList;
import java.util.List;

public class Axe extends Tool{
    public Axe(){
        super(new LevelProcess(new ArrayList<>(List.of(LevelName.normal, LevelName.copper,
                LevelName.iron, LevelName.golden, LevelName.iridium)), 0),
                new ArrayList<>(List.of(5,4,3,2,1)),AbilityType.Foraging);
        this.setName("Axe");
    }
    @Override
    public Result<String> useTool(Coordinate direction) {
        return null;
    }

    @Override
    public void doTask() {

    }
}
