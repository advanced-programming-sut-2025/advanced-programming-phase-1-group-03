package phi.ap.model.items.tools;

import phi.ap.model.Coordinate;
import phi.ap.model.LevelProcess;
import phi.ap.model.enums.LevelName;

import java.util.ArrayList;
import java.util.List;

public class Pickaxe extends Tool{
    public Pickaxe(){
        this.levelProcess = new LevelProcess(new ArrayList<>(List.of(LevelName.normal, LevelName.copper,
                LevelName.iron, LevelName.golden, LevelName.iridium)),
                new ArrayList<>(List.of(5,4,3,2,1)),  0);
        this.setName("Pickaxe");
    }

    @Override
    public void useTool(Coordinate direction) {

    }

    @Override
    public void doTask() {

    }
}
