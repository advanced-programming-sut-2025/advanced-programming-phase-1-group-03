package phi.ap.model.items.tools;

import phi.ap.model.Coordinate;
import phi.ap.model.LevelProcess;
import phi.ap.model.Result;
import phi.ap.model.enums.Tool.BasicToolLevels;

import java.util.ArrayList;
import java.util.List;

public class MilkPail extends Tool {
    public MilkPail(){
        super(new LevelProcess(new ArrayList<Tool.BasicToolLevels>(List.of(Tool.BasicToolLevels.normal)), 0),
                new ArrayList<>(List.of(4)), null);
        this.setName("Milk Pail");
    }
    @Override
    public Result<String> useTool(Coordinate direction) {
        return new Result<>(false, "Use collect produce -n name command to use this tool");
    }

    @Override
    public void doTask() {

    }
}
