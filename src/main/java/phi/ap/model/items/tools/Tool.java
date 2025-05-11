package phi.ap.model.items.tools;

import phi.ap.model.*;
import phi.ap.model.enums.AbilityType;
import phi.ap.model.items.Item;

import java.util.ArrayList;
import java.util.List;

public abstract class Tool extends Item {
    private AbilityType contactedAbility;
    private LevelProcess levelProcess;
    private ArrayList<Integer> energyConsumptionPerLevel;

    public abstract Result<String> useTool(Coordinate direction);

    public Tool(LevelProcess levelProcess, ArrayList<Integer> energyConsumptionPerLevel, AbilityType contactedAbility) {
        this.levelProcess = levelProcess;
        this.contactedAbility = contactedAbility;
        this.energyConsumptionPerLevel = energyConsumptionPerLevel;

        this.setMaxStackSize(1);
    }
    @Override
    public String toString() {
        return getName() + " level : " + levelProcess.toString();
    }

    public void setLevelProcess(LevelProcess levelProcess) {
        this.levelProcess = levelProcess;
    }

    public AbilityType getContactedAbility() {
        return contactedAbility;
    }

    public void setContactedAbility(AbilityType contactedAbility) {
        this.contactedAbility = contactedAbility;
    }
    public LevelProcess getLevelProcess() {
        return levelProcess;
    }
    public int getEnergyNeed() {
        int energy = energyConsumptionPerLevel.get(levelProcess.getCurrentLevel());
        if(contactedAbility != null && Game.getInstance().getCurrentPlayer().isAbilityMax(contactedAbility))
            energy -= 1;
        if(energy < 0) energy = 0;
        return energy;
    }
    public boolean reduceEnergy(){
        int energy = getEnergyNeed();
        if(!Game.getInstance().getCurrentPlayer().getEnergy().hasEnergy(energy))
            return false;
        Game.getInstance().getCurrentPlayer().getEnergy().advanceBaseUnit(-energy);
        return true;
    }
}
