package phi.ap.model.items.tools;

import phi.ap.model.Coordinate;
import phi.ap.model.LevelProcess;
import phi.ap.model.enums.LevelName;

import java.util.ArrayList;
import java.util.List;

public class FishingPole extends Tool {
    public FishingPole(){
        this.levelProcess = new LevelProcess(new ArrayList<>(List.of(LevelName.training,
                LevelName.bamboo, LevelName.fiberGlass, LevelName.iridium)),
                new ArrayList<>(List.of(8, 8, 6, 4)), 0);
        this.setName("Fishing Pole");
    }

    private LevelName levelName;
    public  FishingPole(LevelName levelName) {
        this.levelName = levelName;
    }
    @Override
    public void useTool(Coordinate direction) {

    }

    @Override
    public void doTask() {

    }
}
