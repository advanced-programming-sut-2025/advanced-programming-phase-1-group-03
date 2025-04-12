package phi.ap.model;

import phi.ap.model.enums.LevelName;

import java.util.ArrayList;

public class LevelProcess {
    private ArrayList<LevelName> levelNames;
    private int currentLevel;

    public LevelProcess(ArrayList<LevelName> levelNames, int currentLevel) {
        this.levelNames = levelNames;
        this.currentLevel = currentLevel;
    }

    public ArrayList<LevelName> getLevelNames() {
        return levelNames;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
}
