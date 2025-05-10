package phi.ap.model.items.tools;

import phi.ap.model.Coordinate;
import phi.ap.model.LevelProcess;
import phi.ap.model.Result;
import phi.ap.model.enums.LevelName;

import java.util.ArrayList;
import java.util.List;

public class MilkPail extends Tool {
    public MilkPail(){
        super(new LevelProcess(new ArrayList<LevelName>(List.of(LevelName.normal)), 0),
                new ArrayList<>(List.of(4)), null);
        this.setName("Milk Pail");
    }
    @Override
    public Result<String> useTool(Coordinate direction) {
        return null;
    }

    @Override
    public void doTask() {

    }
}
