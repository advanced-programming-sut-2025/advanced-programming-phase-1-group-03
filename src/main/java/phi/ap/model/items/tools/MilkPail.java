package phi.ap.model.items.tools;

import phi.ap.model.Coordinate;
import phi.ap.model.LevelProcess;
import phi.ap.model.enums.LevelName;

import java.util.ArrayList;
import java.util.List;

public class MilkPail extends Tool {
    public MilkPail(){
        this.levelProcess = new LevelProcess(new ArrayList<LevelName>(List.of(LevelName.normal)),
                new ArrayList<>(List.of(4)), 0);
        this.setName("Milk Pail");
    }
    @Override
    public void useTool(Coordinate direction) {

    }

    @Override
    public void doTask() {

    }
}
