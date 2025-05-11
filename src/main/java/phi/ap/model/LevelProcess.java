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
