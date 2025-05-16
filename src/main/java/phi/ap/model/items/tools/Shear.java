package phi.ap.model.items.tools;

import phi.ap.model.Coordinate;
import phi.ap.model.LevelProcess;
import phi.ap.model.Result;
import phi.ap.model.enums.LevelName;

import java.util.ArrayList;
import java.util.List;

public class Shear extends Tool {
    public Shear(){
        super(new LevelProcess(new ArrayList<LevelName>(List.of(LevelName.normal)), 0),
                new ArrayList<>(List.of(4)), null);
        this.setName("Shear");
    }
    @Override
    public Result<String> useTool(Coordinate direction) {
        return new Result<>(false, "Use collect produce -n name command to use this tool");
    }

    @Override
    public void doTask() {

    }
}