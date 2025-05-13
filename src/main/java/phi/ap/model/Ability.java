package phi.ap.model;

import phi.ap.model.enums.AbilityType;
import phi.ap.model.enums.CraftingTypes;
import phi.ap.model.enums.FoodTypes;
import phi.ap.model.items.Item;
import phi.ap.model.items.machines.Machine;
import phi.ap.model.items.machines.craftingMachines.Sprinkler;
import phi.ap.model.items.products.Recipe;

import java.util.ArrayList;

public class Ability {
    private AbilityType abilityType;
    private int level = 0;
    private int xp = 0;

    public Ability(AbilityType abilityType) {
        this.abilityType = abilityType;
    }

    private int xpNeedToNextLevel() {
        return (level + 1) * 100 + 50;
    }

    // TODO food recipes
    public void checkingCookingRecipes() {
        ArrayList<Recipe> recipe = Game.getInstance().getCurrentPlayer().getCookingRecipes();
        for(FoodTypes foodTypes : FoodTypes.values()) {
            if(foodTypes.getAbilityType() != null && foodTypes.getLevel() != null)
                if(foodTypes.getAbilityType().toString().equals(abilityType.toString()) && foodTypes.getLevel().equals(level)) {
                    recipe.add(foodTypes.getRecipe());
                }
        }
    }

    public void checkingCraftingRecipes() {
        ArrayList<Recipe> recipe = Game.getInstance().getCurrentPlayer().getCraftingRecipes();
        for(CraftingTypes craftingTypes : CraftingTypes.values()) {
            //if(level == 3)
                //System.out.println(craftingTypes.getAbilityType() + " " + craftingTypes.getLevel() + " " + abilityType + " " + level);
            if(craftingTypes.getAbilityType() != null && craftingTypes.getLevel() != null)
                if(craftingTypes.getAbilityType().toString().equals(abilityType.toString()) && craftingTypes.getLevel().equals(level)) {
                    recipe.add(craftingTypes.getRecipe());
                    //System.out.println("!!!");
                }
        }
    }

    public void advanceLevel(){
        level = Math.min(AbilityType.MAX_VALUE, level + 1);
        checkingCraftingRecipes();
        checkingCookingRecipes();
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

