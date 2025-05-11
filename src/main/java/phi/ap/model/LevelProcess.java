package phi.ap.model;

import phi.ap.model.enums.LevelName;

import java.util.ArrayList;
import java.util.List;

public class LevelProcess {
    private ArrayList<LevelName> levelNames;
    private int currentLevel;

    public LevelProcess() {
        this.levelNames = new ArrayList<>(List.of(LevelName.normal));
        this.currentLevel = 0;
    }

    public LevelProcess(ArrayList<LevelName> levelNames, int currentLevel) {
        this.levelNames = levelNames;
        this.currentLevel = currentLevel;
    }

    public LevelProcess(LevelProcess levelProcess) {
        this.levelNames = new ArrayList<>(levelProcess.getLevelNames());
        this.currentLevel = levelProcess.getCurrentLevel();
    }

    public boolean levelNamesEqual(ArrayList<LevelName> levelNames) {
        if (levelNames == null) return false;
        if (levelNames.size() != this.levelNames.size()) return false;
        for (int i = 0; i < levelNames.size(); i++) {
            if (!levelNames.get(i).equals(this.levelNames.get(i))) return false;
        }
        return true;
    }

    public boolean isEqual(LevelProcess levelProcess) {
        if (levelProcess == null) return false;
        if (!levelNamesEqual(levelProcess.getLevelNames())) return false;
        if (levelProcess.getCurrentLevel() != this.currentLevel) return false;
        return true;
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
    public void setCurrentLevel(LevelName currentLevel) {
        for(int i = 0; i < levelNames.size(); i++)
            if(levelNames.get(i) == currentLevel)
                this.currentLevel = i;
    }
    public int getLevelOfThisType(LevelName levelName){
        for(int i = 0; i < levelNames.size(); i++)
            if(levelNames.get(i) == levelName)
                return i;
        return -1;
    }
    @Override
    public String toString() {
        return levelNames.get(currentLevel).toString();
    }
}
