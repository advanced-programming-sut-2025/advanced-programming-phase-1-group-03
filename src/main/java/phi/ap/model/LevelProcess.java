package phi.ap.model;

import phi.ap.model.enums.LevelName;

import java.util.ArrayList;

public class LevelProcess {
    private ArrayList<LevelName> levelNames;
    private ArrayList<Integer> energyConsumptionPerLevel;
    private int currentLevel;

    public LevelProcess(ArrayList<LevelName> levelNames, ArrayList<Integer> energyConsumptionPerLevel, int currentLevel) {
        this.levelNames = levelNames;
        this.energyConsumptionPerLevel = energyConsumptionPerLevel;
        this.currentLevel = currentLevel;
    }

    public ArrayList<LevelName> getLevelNames() {
        return levelNames;
    }

    public int getEnergyNeed(){
        return energyConsumptionPerLevel.get(currentLevel);
    }
    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    @Override
    public String toString() {
        return levelNames.get(currentLevel).toString();
    }
}
