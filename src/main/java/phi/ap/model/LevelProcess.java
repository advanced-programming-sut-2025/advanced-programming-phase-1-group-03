package phi.ap.model;

import phi.ap.model.enums.Tool.BasicToolLevels;

import java.util.ArrayList;
import java.util.List;

public class LevelProcess {
    private ArrayList<Tool.BasicToolLevels> Tool.BasicToolLevelss;
    private int currentLevel;

    public LevelProcess() {
        this.Tool.BasicToolLevelss = new ArrayList<>(List.of(Tool.BasicToolLevels.normal));
        this.currentLevel = 0;
    }

    public LevelProcess(ArrayList<Tool.BasicToolLevels> Tool.BasicToolLevelss, int currentLevel) {
        this.Tool.BasicToolLevelss = Tool.BasicToolLevelss;
        this.currentLevel = currentLevel;
    }

    public LevelProcess(LevelProcess levelProcess) {
        this.Tool.BasicToolLevelss = new ArrayList<>(levelProcess.getTool.BasicToolLevelss());
        this.currentLevel = levelProcess.getCurrentLevel();
    }

    public void upgrade(){
        currentLevel = Math.min((currentLevel + 1),Tool.BasicToolLevelss.size() - 1);
    }
    public boolean Tool.BasicToolLevelssEqual(ArrayList<Tool.BasicToolLevels> Tool.BasicToolLevelss) {
        if (Tool.BasicToolLevelss == null) return false;
        if (Tool.BasicToolLevelss.size() != this.Tool.BasicToolLevelss.size()) return false;
        for (int i = 0; i < Tool.BasicToolLevelss.size(); i++) {
            if (!Tool.BasicToolLevelss.get(i).equals(this.Tool.BasicToolLevelss.get(i))) return false;
        }
        return true;
    }

    public boolean isEqual(LevelProcess levelProcess) {
        if (levelProcess == null) return false;
        if (!Tool.BasicToolLevelssEqual(levelProcess.getTool.BasicToolLevelss())) return false;
        if (levelProcess.getCurrentLevel() != this.currentLevel) return false;
        return true;
    }

    public ArrayList<Tool.BasicToolLevels> getTool.BasicToolLevelss() {
        return Tool.BasicToolLevelss;
    }

    public int  getCurrentLevel() {
        return currentLevel;
    }

    public Tool.BasicToolLevels getCurrentTool.BasicToolLevels() {
        if (Tool.BasicToolLevelss == null) return null;
        return Tool.BasicToolLevelss.get(currentLevel);
    }

    public boolean isMax(){
        return currentLevel == Tool.BasicToolLevelss.size() - 1;
    }
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
    public void setCurrentLevel(Tool.BasicToolLevels currentLevel) {
        for(int i = 0; i < Tool.BasicToolLevelss.size(); i++)
            if(Tool.BasicToolLevelss.get(i) == currentLevel)
                this.currentLevel = i;
    }
    public int getLevelOfThisType(Tool.BasicToolLevels Tool.BasicToolLevels){
        for(int i = 0; i < Tool.BasicToolLevelss.size(); i++)
            if(Tool.BasicToolLevelss.get(i) == Tool.BasicToolLevels)
                return i;
        return -1;
    }
    @Override
    public String toString() {
        return Tool.BasicToolLevelss.get(currentLevel).toString();
    }
}
