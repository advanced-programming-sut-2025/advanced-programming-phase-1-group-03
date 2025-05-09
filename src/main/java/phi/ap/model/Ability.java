package phi.ap.model;

import phi.ap.model.enums.AbilityType;
import phi.ap.model.items.Item;

import java.util.ArrayList;

public class Ability {
    private AbilityType abilityType;
    private int level = 0;
    private int xp = 0;

    public Ability(AbilityType abilityType) {
        this.abilityType = abilityType;
    }
    private int xpNeedToNextLevel(){
        return (level + 1) * 100 + 50;
    }

    private void advanceLevel(){
        level = Math.min(4, level + 1);
    }
    public void advanceXP(int gained){
        while(gained > 0){
            int need = Math.min(gained, xpNeedToNextLevel());
            gained -= need;
            xp += need;
            if(xp == xpNeedToNextLevel()){ // we reach to the next level!!!
                advanceLevel();
                xp = 0;
            }
        }
    }

    public int getLevel(){
        return this.level;
    }
    public AbilityType getAbilityType() {
        return abilityType;
    }
}

