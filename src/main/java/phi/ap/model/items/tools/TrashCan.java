package phi.ap.model.items.tools;

import phi.ap.model.Coordinate;
import phi.ap.model.LevelProcess;
import phi.ap.model.Result;
import phi.ap.model.enums.LevelName;

import java.util.ArrayList;
import java.util.List;

public class TrashCan extends Tool {
    public TrashCan(){
        super(new LevelProcess(new ArrayList<>(List.of(LevelName.normal, LevelName.copper,
                LevelName.iron, LevelName.golden, LevelName.iridium)), 0),
                new ArrayList<>(List.of(0,0,0,0,0)), null);
        this.setName("Trash Can");
    }
    @Override
    public Result<String> useTool(Coordinate direction) {
        return null;
    }

    @Override
    public void doTask() {

    }
}
