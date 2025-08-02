package com.ap.managers;

import com.ap.model.AbilityType;

import java.util.HashMap;
import java.util.Map;

public class AbilityManager {

    private final Map<AbilityType, Ability> abilities;

    public AbilityManager() {
        abilities = new HashMap<>();
        for (AbilityType type : AbilityType.values()) {
            abilities.put(type, new Ability(type));
        }
    }

    public Ability getAbility(AbilityType type) {
        return abilities.get(type);
    }

    public class Ability {
        private final AbilityType abilityType;
        private int level = 0;
        private int xp = 0;

        public Ability(AbilityType abilityType) {
            this.abilityType = abilityType;
        }

        public int getLevel() {
            return level;
        }

        public int getXp() {
            return xp;
        }

        public AbilityType getAbilityType() {
            return abilityType;
        }

        public int xpNeedToNextLevel() {
            return (level + 1) * 100 + 50;
        }

        public void advanceLevel(){
            level = Math.min(abilityType.maxLevel, level + 1);
        }

        public void advanceXP(int gained){
            while(gained > 0){
                int need = Math.min(gained, xpNeedToNextLevel());
                gained -= need;
                xp += need;
                if(xp == xpNeedToNextLevel()){
                    advanceLevel();
                    xp = 0;
                }
            }
        }
    }
}
