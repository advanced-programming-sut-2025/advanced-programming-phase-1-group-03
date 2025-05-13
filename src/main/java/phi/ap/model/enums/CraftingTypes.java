package phi.ap.model.enums;

import phi.ap.model.ItemStack;
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
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Copper), 4),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Coal), 1))),
            AbilityType.Extraction, 1, 50, new Bomber(1, 1, "CherryBomb", 50)),

    Bomb("Bomb", new ArrayList<>(Arrays.asList(
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Iron), 4),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Coal), 1))),
    AbilityType.Extraction, 2, 50, new Bomber(1, 1, "Bomb", 50)),

    MegaBomb("MegaBomb", new ArrayList<>(Arrays.asList(
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Gold), 4),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Coal), 1))),
    AbilityType.Extraction, 3, 50, new Bomber(1, 1, "MegaBomb", 50)),

    Sprinkler("Sprinkler", new ArrayList<>(Arrays.asList(
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Copper), 1),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.IronBar), 1))),
    AbilityType.Farming, 1, null, new Sprinkler(1, 1, "Sprinkler", null)),

    QualitySprinkler("QualitySprinkler", new ArrayList<>(Arrays.asList(
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.IronBar), 1),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.GoldBar), 1))),
    AbilityType.Farming, 2, null, new Sprinkler(1, 1, "QualitySprinkler", null)),

    IridiumSprinkler("IridiumSprinkler", new ArrayList<>(Arrays.asList(
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.GoldBar), 1),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Iridium), 1))),
            AbilityType.Farming, 3, null, new Sprinkler(1, 1, "IridiumSprinkler", null)),


    Furnace("Furnace", new ArrayList<>(Arrays.asList(
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Copper), 20),
            new ItemStack(new Wood(1, 1), 25))),
            null, null, null, new CraftedProducer(1 ,1, "Furnace", null)),

    CharcoalKiln("CharcoalKiln", new ArrayList<>(Arrays.asList(
            new ItemStack(new Wood(1, 1), 10))),
            AbilityType.Foraging, 1, null, new CraftedProducer(1 ,1, "CharcoalKiln", null)),

    Scarecrow("Scarecrow", new ArrayList<>(Arrays.asList(
            new ItemStack(new Wood(1, 1), 50),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Coal), 1))),
            null, null, null, new Scarecrow(1, 1, "Scarecrow", null)),

    DeluxeScarecrow("DeluxeScarecrow", new ArrayList<>(Arrays.asList(
            new ItemStack(new Wood(1, 1), 50),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Coal), 1),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Iridium), 1))),
            AbilityType.Farming, 2, null, new Scarecrow(1, 1, "DeluxeScarecrow", null)),

    BeeHouse("BeeHouse", new ArrayList<>(Arrays.asList(
            new ItemStack(new Wood(1, 1), 40),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Coal), 8),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.IronBar), 1))),
            AbilityType.Farming, 1, null, new CraftedProducer(1, 1, "BeeHouse", null)),

    CheesePress("CheesePress", new ArrayList<>(Arrays.asList(
            new ItemStack(new Wood(1, 1), 45),
            new ItemStack(new Stone(1, 1, StoneTypes.RegularStone), 45),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.CopperBar), 1))),
            AbilityType.Farming, 2, null, new CraftedProducer(1, 1, "CheesePress", null)),

    Keg("Keg", new ArrayList<>(Arrays.asList(
            new ItemStack(new Wood(1, 1), 30),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.CopperBar), 1),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.IronBar), 1))),
            AbilityType.Farming, 3, null, new CraftedProducer(1, 1, "Keg", null)),

    Loom("Loom", new ArrayList<>(Arrays.asList(
            new ItemStack(new Wood(1, 1), 60))),
            AbilityType.Farming, 3, null, new CraftedProducer(1, 1, "Loom", null)),

    MayonnaiseMachine("MayonnaiseMachine", new ArrayList<>(Arrays.asList(
            new ItemStack(new Wood(1, 1), 15),
            new ItemStack(new Stone(1, 1, StoneTypes.RegularStone), 15),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.CopperBar), 1))),
            null, null, null, new CraftedProducer(1, 1, "MayonnaiseMachine", null)),

    OilMaker("OilMaker", new ArrayList<>(Arrays.asList(
            new ItemStack(new Wood(1, 1), 100),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.GoldBar), 1),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.IronBar), 1))),
            AbilityType.Farming, 3, null, new CraftedProducer(1, 1, "OilMaker", null)),

    PreservesJar("PreservesJar", new ArrayList<>(Arrays.asList(
            new ItemStack(new Wood(1, 1), 50),
            new ItemStack(new Stone(1, 1, StoneTypes.RegularStone), 40),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Coal), 8))),
            AbilityType.Farming, 2, null, new CraftedProducer(1, 1, "PreservesJar", null)),

    Dehydrator("Dehydrator", new ArrayList<>(Arrays.asList(
            new ItemStack(new Wood(1, 1), 30),
            new ItemStack(new Stone(1, 1, StoneTypes.RegularStone), 20))),
            null, null, null, new CraftedProducer(1, 1, "Dehydrator", null)),
    GrassStarter("GrassStarter", new ArrayList<>(Arrays.asList(
            new ItemStack(new Wood(1, 1), 1))),
            null, null, null, new CraftedProducer(1, 1, "GrassStarter", null)),

    FishSmoker("FishSmoker", new ArrayList<>(Arrays.asList(
            new ItemStack(new Wood(1, 1), 50),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.IronBar), 3),
            new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Coal), 10))),
            null, null, null, new CraftedProducer(1, 1, "FishSmoker", null));

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
        this.recipe = new Recipe(arrayList, new ItemStack(machine, 1), RecipeTypes.valueOf(name));
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

    public static CraftingTypes getType(String name) {
        CraftingTypes craftingType;
        try {
            craftingType = CraftingTypes.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
        return craftingType;
    }
}
