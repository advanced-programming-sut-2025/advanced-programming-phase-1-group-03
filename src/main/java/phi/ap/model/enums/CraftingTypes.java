package phi.ap.model.enums;

import phi.ap.model.ItemStack;
import phi.ap.model.items.Item;
import phi.ap.model.items.machines.Machine;
import phi.ap.model.items.machines.craftingMachines.*;
import phi.ap.model.items.products.Mineral;
import phi.ap.model.items.products.Recipe;
import phi.ap.model.items.products.Stone;
import phi.ap.model.items.products.Wood;

import java.util.ArrayList;
import java.util.Arrays;

public enum CraftingTypes {
    CherryBomb("CherryBomb", new ArrayList<>(Arrays.asList(
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.COPPER), 4),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.COAL), 1))),
            AbilityType.Extraction, 1, 50, new Bomber(1, 1)),

    Bomb("Bomb", new ArrayList<>(Arrays.asList(
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.IRON), 4),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.COAL), 1))),
    AbilityType.Extraction, 2, 50, new Bomber(1, 1)),

    MegaBomb("MegaBomb", new ArrayList<>(Arrays.asList(
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.GOLD), 4),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.COAL), 1))),
    AbilityType.Extraction, 3, 50, new Bomber(1, 1)),

    Sprinkler("Sprinkler", new ArrayList<>(Arrays.asList(
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.COPPER), 1),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.IRON_BAR), 1))),
    AbilityType.Farming, 1, null, new Sprinkler(1, 1)),

    QualitySprinkler("QualitySprinkler", new ArrayList<>(Arrays.asList(
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.IRON_BAR), 1),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.GOLD_BAR), 1))),
    AbilityType.Farming, 2, null, new Sprinkler(1, 1)),

    IridiumSprinkler("IridiumSprinkler", new ArrayList<>(Arrays.asList(
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.GOLD_BAR), 1),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.IRIDIUM), 1))),
            AbilityType.Farming, 3, null, new Sprinkler(1, 1)),


    Furnace("Furnace", new ArrayList<>(Arrays.asList(
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.COPPER), 20),
            new ItemStack(new Wood(1, 1), 25))),
            null, null, null, new Converter(1 ,1)),

    CharcoalKiln("CharcoalKiln", new ArrayList<>(Arrays.asList(
            new ItemStack(new Wood(1, 1), 10))),
            AbilityType.Foraging, 1, null, new Converter(1 ,1)),

    Scarecrow("Scarecrow", new ArrayList<>(Arrays.asList(
            new ItemStack(new Wood(1, 1), 50),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.COAL), 1))),
            null, null, null, new Scarecrow(1, 1)),

    DeluxeScarecrow("DeluxeScarecrow", new ArrayList<>(Arrays.asList(
            new ItemStack(new Wood(1, 1), 50),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.COAL), 1),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.IRIDIUM), 1))),
            AbilityType.Farming, 2, null, new Scarecrow(1, 1)),

    BeeHouse("BeeHouse", new ArrayList<>(Arrays.asList(
            new ItemStack(new Wood(1, 1), 40),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.COAL), 8),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.IRON_BAR), 1))),
            AbilityType.Farming, 1, null, new CraftedProducer(1, 1)),

    CheesePress("CheesePress", new ArrayList<>(Arrays.asList(
            new ItemStack(new Wood(1, 1), 45),
            new ItemStack(new Stone(1, 1, StoneTypes.RegularStone), 45),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.COPPER_BAR), 1))),
            AbilityType.Farming, 2, null, new Converter(1, 1)),

    Keg("Keg", new ArrayList<>(Arrays.asList(
            new ItemStack(new Wood(1, 1), 30),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.COPPER_BAR), 1),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.IRON_BAR), 1))),
            AbilityType.Farming, 3, null, new Converter(1, 1)),

    Loom("Loom", new ArrayList<>(Arrays.asList(
            new ItemStack(new Wood(1, 1), 60))),
            AbilityType.Farming, 3, null, new Converter(1, 1)),

    MayonnaiseMachine("MayonnaiseMachine", new ArrayList<>(Arrays.asList(
            new ItemStack(new Wood(1, 1), 15),
            new ItemStack(new Stone(1, 1, StoneTypes.RegularStone), 15),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.COPPER_BAR), 1))),
            null, null, null, new Converter(1, 1)),

    OilMaker("OilMaker", new ArrayList<>(Arrays.asList(
            new ItemStack(new Wood(1, 1), 100),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.GOLD_BAR), 1),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.IRON_BAR), 1))),
            AbilityType.Farming, 3, null, new Converter(1, 1)),

    PreservesJar("PreservesJar", new ArrayList<>(Arrays.asList(
            new ItemStack(new Wood(1, 1), 50),
            new ItemStack(new Stone(1, 1, StoneTypes.RegularStone), 40),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.COAL), 8))),
            AbilityType.Farming, 2, null, new Converter(1, 1)),

    Dehydrator("Dehydrator", new ArrayList<>(Arrays.asList(
            new ItemStack(new Wood(1, 1), 30),
            new ItemStack(new Stone(1, 1, StoneTypes.RegularStone), 20))),
            null, null, null, new CraftingMachine(1, 1)),
    GrassStarter("GrassStarter", new ArrayList<>(Arrays.asList(
            new ItemStack(new Wood(1, 1), 1))),
            null, null, null, new CraftingMachine(1, 1)),

    FishSmoker("FishSmoker", new ArrayList<>(Arrays.asList(
            new ItemStack(new Wood(1, 1), 50),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.IRON_BAR), 3),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.COAL), 10))),
            null, null, null, new Converter(1, 1));

    // TODO complete this. we don't have foraging trees.
//    MysticTreeSeed("MysticTreeSeed", new ArrayList<>(Arrays.asList(
//            new ItemStack(new Crop(1, 1, ), 5),
//            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.MAPLE_SEED), 5),
//            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.PINE_CONE), 5),
//            new ItemStack(new Mineral(1, 1, ForagingCropsTypes.M), 5))),
//            AbilityType.Farming, 4, 100);
    private String name;
    private Recipe recipe;
    private AbilityType abilityType;
    private Integer level;
    private Integer sellPrice;
    CraftingTypes(String name, ArrayList<ItemStack> arrayList, AbilityType abilityType, Integer level, Integer sellPrice, Machine machine) {
        this.name = name;
        this.abilityType = abilityType;
        this.level = level;
        this.sellPrice = sellPrice;
        this.recipe = new Recipe(1 , 1, arrayList, new ItemStack(machine, 1), RecipeTypes.valueOf(name));
        //((Machine)this.recipe.getResult().getItem()).setCraftingType(CraftingTypes.valueOf(name));
    }

    public String getName() {
        return name;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public AbilityType getAbilityType() {
        return abilityType;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getSellPrice() {
        return sellPrice;
    }
}
