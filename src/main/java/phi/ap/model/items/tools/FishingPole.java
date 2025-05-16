package phi.ap.model.items.tools;

import phi.ap.model.Coordinate;
import phi.ap.model.LevelProcess;
import phi.ap.model.Result;
import phi.ap.model.enums.AbilityType;
import phi.ap.model.enums.LevelName;

import java.util.ArrayList;
import java.util.List;

public class FishingPole extends Tool {
    public FishingPole(){
        super(new LevelProcess(new ArrayList<>(List.of(LevelName.training,
                LevelName.bamboo, LevelName.fiberGlass, LevelName.iridium)),0),
                new ArrayList<>(List.of(8, 8, 6, 4)), AbilityType.Fishing);
    }

    @Override
    public String getName() {
        return "Fishing Pole " + getLevelProcess().toString();
    }
    @Override
    public Result<String> useTool(Coordinate direction) {
        return new Result<>(true, "If you want to use fishing pole, use fishing -p <fishing pole> command");
    }

    @Override
    public void doTask() {
    }

    public double getCoef(){
        return switch (getLevelProcess().getCurrentLevelName()){
            case training -> 0.1;
            case bamboo -> 0.5;
            case fiberGlass -> 0.9;
            case iridium -> 1.2;
            default -> 0;
        };
    }
}
