package phi.ap.model.items.tools;

import phi.ap.model.Coordinate;
import phi.ap.model.enums.LevelName;

public class FishingPole extends Tool {
    private LevelName levelName;
    private int height;
    private int width;
    public  FishingPole(int height, int width, LevelName levelName) {
        this.height = height;
        this.width = width;
        this.levelName = levelName;
    }
    @Override
    public void useTool(Coordinate direction) {

    }

    @Override
    public void doTask() {

    }
}
