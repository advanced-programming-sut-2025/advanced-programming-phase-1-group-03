package phi.ap.model.items.machines.craftingMachines;

import phi.ap.model.Eatable;
import phi.ap.model.Game;
import phi.ap.model.ItemStack;
import phi.ap.model.Result;
import phi.ap.model.enums.*;
import phi.ap.model.items.Seed;
import phi.ap.model.items.machines.Machine;
import phi.ap.model.items.products.*;

public class CraftedProducer extends Machine {
    public CraftedProducer(int height, int width, CraftingTypes craftingType) {
        super(height, width, craftingType);
    }
    public CraftedProducer(int height, int width, String machineName, Integer price) {
        super(height, width, machineName, price);
    }

    public static Boolean checkNear(String name) {
        Boolean ok = false;
        for(int diffx = -1; diffx <= 1; diffx++) {
            for(int diffy = -1; diffy <= 1; diffy++) {
                int x = Game.getInstance().getCurrentPlayer().getLocation().getX() + diffx;
                int y = Game.getInstance().getCurrentPlayer().getLocation().getY() + diffy;
                if(Game.getInstance().getCurrentPlayer().getLocation().getGround().getTopItem(y, x).getName().equals(name))
                    ok = true;
            }
        }
        return ok;
    }
    public Result<String> produceHoney(String foodName) {
        if(!checkNear(CraftingTypes.BeeHouse.getName()))
            return new Result<>(false, "You must be near a " + CraftingTypes.BeeHouse.getName() + " machine.");
        if(!foodName.equals("Honey"))
            return new Result<>(false, "This machine can't produce " + foodName);
        Food artisanFood = new Food(1, 1, FoodTypes.Honey);
        return addArtisan(artisanFood);
    }

    public Result<String> produceCheese(String foodName) {
        if(!checkNear(CraftingTypes.CheesePress.getName()))
            return new Result<>(false, "You must be near a " + CraftingTypes.CheesePress.getName() + " machine.");
        if(!foodName.equals("Cheese") && !foodName.equals("GoatCheese"))
            return new Result<>(false, "This machine can't produce " + foodName);
        Food artisanFood = null;
        if(foodName.equals("Cheese")) {
            if(Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(FoodTypes.Cheese.getRecipe())) {
                artisanFood = new Food(1, 1, FoodTypes.Cheese);
            }
            else {
                if(Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(FoodTypes.CheeseWithLargeMilk.getRecipe())) {
                    artisanFood = new Food(1, 1, FoodTypes.Cheese);
                }
            }
        }
        else if(foodName.equals("GoatCheese")) {
            if(Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(FoodTypes.GoatCheese.getRecipe())) {
                artisanFood = new Food(1, 1, FoodTypes.GoatCheese);
            }
            else {
                if(Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(FoodTypes.GoatCheeseWithLargeMilk.getRecipe())) {
                    artisanFood = new Food(1, 1, FoodTypes.GoatCheese);
                }
            }
        }
        if(artisanFood == null)
            return new Result<>(false, "You don't have enough ingredients.");
        return addArtisan(artisanFood);
    }
    public Result<String> produceKeg(String foodName, String ingredientName) {
        if(!checkNear(CraftingTypes.Keg.getName()))
            return new Result<>(false, "You must be near a " + CraftingTypes.Keg.getName() + " machine.");
        Food artisanFood = null;
        if(foodName.equals("Juice")) {
            CropsTypes cropsType;
            try {
                cropsType = CropsTypes.valueOf(ingredientName);
            } catch (IllegalArgumentException e) {
                return new Result<>(false, "You must enter a crop!");
            }
            if(!Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(new ItemStack(new Crop(1, 1, cropsType), 1)))
                return new Result<>(false, "You don't have enough " + ingredientName);
            artisanFood = new Food(1, 1, FoodTypes.Juice);
            artisanFood.setEatable(new Eatable(2 * cropsType.getEatable().getEnergy()));
            artisanFood.setSellable(true);
            artisanFood.setSellPrice((int)(2.25 * cropsType.getBaseSellPrice()));
        } else if(foodName.equals("Wine")) {
            FruitTypes fruitType;
            try {
                fruitType = FruitTypes.valueOf(ingredientName);
            } catch (IllegalArgumentException e) {
                return new Result<>(false, "You must enter a fruit!");
            }
            if(!Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(new ItemStack(new Fruit(1, 1, fruitType), 1)))
                return new Result<>(false, "You don't have enough " + ingredientName);
            artisanFood = new Food(1, 1, FoodTypes.Wine);
            artisanFood.setEatable(new Eatable((int)(1.75 * fruitType.getEatable().getEnergy())));
            artisanFood.setSellable(true);
            artisanFood.setSellPrice((int)(3 * fruitType.getBaseSellPrice()));
        }
        else if(foodName.equals("Beer") || foodName.equals("Vinegar") || foodName.equals("Coffee") || foodName.equals("Mead") || foodName.equals("PaleAle")){
            if(!Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(FoodTypes.valueOf(foodName).getRecipe()))
                return new Result<>(false, "you don't have enough ingredients");
            artisanFood = new Food(1, 1, FoodTypes.valueOf(foodName));
        }
        else {
            return new Result<>(false, "You can't produce " + foodName + " with this machine");
        }
        if(artisanFood == null)
            return new Result<>(false, "You can't produce " + foodName + " with this machine");
        else
            return addArtisan(artisanFood);
    }
    public Result<String> produceDehydrator(String foodName, String ingredientName) {
        if(!checkNear(CraftingTypes.Dehydrator.getName()))
            return new Result<>(false, "You must be near a " + CraftingTypes.Dehydrator.getName() + " machine.");
        Food artisanFood = null;
        if(foodName.equals("DriedMushrooms")) {
            ForagingCropsTypes foragingCropsType = null;
            if(ingredientName.equals(ForagingCropsTypes.CommonMushroom.getName()))
                foragingCropsType = ForagingCropsTypes.CommonMushroom;
            if(ingredientName.equals(ForagingCropsTypes.RedMushroom.getName()))
                foragingCropsType = ForagingCropsTypes.RedMushroom;
            if(ingredientName.equals(ForagingCropsTypes.PurpleMushroom.getName()))
                foragingCropsType = ForagingCropsTypes.PurpleMushroom;
            if(foragingCropsType == null)
                return new Result<>(false, "You must enter a mushroom");
            if(!Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(new ItemStack(new Crop(1, 1, foragingCropsType), 5)))
                return new Result<>(false, "You don't have enough " + ingredientName);
            artisanFood = new Food(1, 1, FoodTypes.DriedMushrooms);
            artisanFood.setEatable(new Eatable(50));
            artisanFood.setSellable(true);
            artisanFood.setSellPrice((int)(7.5 * foragingCropsType.getBaseSellPrice()) + 25);
        } else if(foodName.equals("DriedFruit")) {
            FruitTypes fruitType;
            try {
                fruitType = FruitTypes.valueOf(ingredientName);
            } catch (IllegalArgumentException e) {
                return new Result<>(false, "You must enter a fruit!");
            }
            if(!Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(new ItemStack(new Fruit(1, 1, fruitType), 5)))
                return new Result<>(false, "You don't have enough " + ingredientName);
            artisanFood = new Food(1, 1, FoodTypes.DriedFruit);
            artisanFood.setEatable(new Eatable(75));
            artisanFood.setSellable(true);
            artisanFood.setSellPrice((int)(7.5 * fruitType.getBaseSellPrice()) + 25);
        } else if(foodName.equals("Raisins")) {
            FoodTypes foodType = FoodTypes.Raisins;
            if(!Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(foodType.getRecipe()))
                return new Result<>(false, "You don't have enough ingredients.");
            artisanFood = new Food(1, 1, foodType);
        }
        if(artisanFood == null)
            return new Result<>(false, "You can't produce " + foodName + " with this machine");
        else
            return addArtisan(artisanFood);
    }
    public Result<String> produceCoal() {
        if(!checkNear(CraftingTypes.CharcoalKiln.getName()))
            return new Result<>(false, "You must be near a " + CraftingTypes.CharcoalKiln.getName() + " machine.");
        Mineral mineral = new Mineral(1, 1, ForagingMineralTypes.Coal);
        mineral.setSellPrice(50);
        if(!Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(new ItemStack(new Wood(1, 1), 10 )))
            return new Result<>(false, "You don't have enough wood.");
        Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(mineral, 1);
        return new Result<>(true, "Coal created successfully.");
    }
    public Result<String> produceLoom() {
        if(!checkNear(CraftingTypes.Loom.getName()))
            return new Result<>(false, "You must be near a " + CraftingTypes.Loom.getName() + " machine.");
        Product product = new Product(1, 1);
        product.setName("Cloth");
        product.setSellPrice(470);
        product.setWaitingTime(4);
        if(!Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.Wool), 1)))
            return new Result<>(false, "You don't have enough wool.");
        addArtisan(product);
        return new Result<>(true, "cloth added successfully.");
    }
    public Result<String> produceMayonnaiseMachine(String foodName, String ingredientName) {
        if(!checkNear(CraftingTypes.MayonnaiseMachine.getName()))
            return new Result<>(false, "You must be near a " + CraftingTypes.MayonnaiseMachine.getName() + " machine.");
        Food food = null;
        if(foodName.equals("Mayonnaise")) {
            if(!ingredientName.equals("LargeChickenEgg") && Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.ChickenEgg), 1))) {
                food = new Food(1, 1, FoodTypes.Mayonnaise);
                food.setSellable(true);
                food.setSellPrice(190);
            }
            else if(!ingredientName.equals("ChickenEgg") && Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(new ItemStack(new AnimalProduct(1, 1, AnimalProductTypes.LargeChickenEgg), 1))) {
                food = new Food(1, 1, FoodTypes.Mayonnaise);
                food.setSellable(true);
                food.setSellPrice(237);
            }
            else
                return new Result<>(false, "You don't have Egg.");
        }
        else if(foodName.equals("DuckMayonnaise")) {
            if(Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(FoodTypes.DuckMayonnaise.getRecipe()))
                food = new Food(1, 1, FoodTypes.DuckMayonnaise);
            else
                return new Result<>(false, "You don't have DuckEgg");
        }
        else if(foodName.equals("Dinosaur Mayonnaise")) {
            if(Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(FoodTypes.DinosaurMayonnaise.getRecipe()))
                food = new Food(1, 1, FoodTypes.DinosaurMayonnaise);
            else
                return new Result<>(false, "You don't have DuckEgg");
        }
        if(food == null)
            return new Result<>(false, "You can't produce this food with this machine.");
        else
            return addArtisan(food);
    }
    public Result<String>  produceOilMaker(String foodName, String ingredientName) {
        if(!checkNear(CraftingTypes.OilMaker.getName()))
            return new Result<>(false, "You must be near a " + CraftingTypes.OilMaker.getName() + " machine.");
        Food food = null;
        if(foodName.equals("Oil")) {
            if(ingredientName.equals("Corn")) {
                if(Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(new ItemStack(new Crop(1, 1, CropsTypes.Corn), 1))) {
                    food = new Food(1, 1, FoodTypes.Oil);
                    food.setWaitingTime(6);
                }
                else
                    return new Result<>(false, "You don't have " + ingredientName);
            }
            else if(ingredientName.equals("SunflowerSeeds")) {
                if(Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(new ItemStack(new Seed(1, 1, SeedTypes.SunflowerSeeds), 1))) {
                    food = new Food(1, 1, FoodTypes.Oil);
                    food.setWaitingTime(2 * 14);
                }
                else
                    return new Result<>(false, "You don't have " + ingredientName);
            }
            else if(ingredientName.equals("Sunflower")) {
                if(Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(new ItemStack(new Crop(1, 1, CropsTypes.Sunflower), 1))) {
                    food = new Food(1, 1, FoodTypes.Oil);
                    food.setWaitingTime(1);
                }
                else
                    return new Result<>(false, "You don't have " + ingredientName);
            }
            else
                return new Result<>(false, ingredientName + " is not valid.");
        }
        else if(foodName.equals("TruffleOil")) {
            if(Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(FoodTypes.TruffleOil.getRecipe())) {
                food = new Food(1, 1, FoodTypes.TruffleOil);
            }
            else
                return new Result<>(false, "You don't have enough truffle.");
        }
        if(food == null)
            return new Result<>(false, "You can't produce this food with this machine.");
        return addArtisan(food);
    }
    public Result<String> producePreservesJar(String foodName, String ingredientName) {
        if(!checkNear(CraftingTypes.PreservesJar.getName()))
            return new Result<>(false, "You must be near a " + CraftingTypes.PreservesJar.getName() + " machine.");
        Food food = null;
        if(foodName.equals("Jelly")) {
            FruitTypes fruitType;
            try {
                fruitType = FruitTypes.valueOf(ingredientName);
            } catch (IllegalArgumentException e) {
                return new Result<>(false, "You must enter a fruit!");
            }
            if(!Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(new ItemStack(new Fruit(1, 1, fruitType), 1)))
                return new Result<>(false, "You don't have enough " + ingredientName);
            food = new Food(1, 1, FoodTypes.Jelly);
            food.setEatable(new Eatable(fruitType.getEatable().getEnergy() * 2));
            food.setWaitingTime(3 * 14);
            food.setSellable(true);
            food.setSellPrice((2 * fruitType.getBaseSellPrice()) + 50);
        } else if(foodName.equals("Pickles")) {
            CropsTypes cropsType;
            try {
                cropsType = CropsTypes.valueOf(ingredientName);
            } catch (IllegalArgumentException e) {
                return new Result<>(false, "You must enter a Crop!");
            }
            if(!Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(new ItemStack(new Crop(1, 1, cropsType), 1)))
                return new Result<>(false, "You don't have enough " + ingredientName);
            food = new Food(1, 1, FoodTypes.Pickles);
            food.setEatable(new Eatable((int)(cropsType.getEatable().getEnergy() * 1.75)));
            food.setWaitingTime(6);
            food.setSellable(true);
            food.setSellPrice((2 * cropsType.getBaseSellPrice()) + 50);
        }
        if(food == null)
            return new Result<>(false, "You can't produce this food with this machine.");
        return addArtisan(food);
    }
    public Result<String> produceFishSmoker(String foodName, String ingredientName) {
        if(!checkNear(CraftingTypes.FishSmoker.getName()))
            return new Result<>(false, "You must be near a " + CraftingTypes.FishSmoker.getName() + " machine.");
        Food food = null;
        if(foodName.equals("Jelly")) {
            FishTypes fishType;
            try {
                fishType = FishTypes.valueOf(ingredientName);
            } catch (IllegalArgumentException e) {
                return new Result<>(false, "You must enter a fish!");
            }
            if(!Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(new ItemStack(new Fish(1, 1, fishType), 1)))
                return new Result<>(false, "You don't have enough " + ingredientName);
            food = new Food(1, 1, FoodTypes.SmokedFish);
            // TODO fish energy
            food.setEatable(new Eatable(70));
            food.setWaitingTime(1);
            food.setSellable(true);
            food.setSellPrice((2 * fishType.getBasePrice()));
        }
        if(food == null)
            return new Result<>(false, "You can't produce this food with this machine.");
        return addArtisan(food);
    }
    public Result<String> produceFurnace(String metalName) {
//        if(!checkNear(CraftingTypes.Furnace.getName()))
//            return new Result<>(false, "You must be near a " + CraftingTypes.Furnace.getName() + " machine.");
        Mineral mineral = null;
        if(metalName.equals("CopperBar")) {
            if(Game.getInstance().getCurrentPlayer().getInventoryManager().CheckExistence(
                    new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Copper), 5))
                && Game.getInstance().getCurrentPlayer().getInventoryManager().CheckExistence(
                        new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Coal), 1))) {
                Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(
                        new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Copper), 5));
                Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(
                        new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Coal), 1));
                mineral = new Mineral(1, 1, ForagingMineralTypes.CopperBar);
                mineral.setSellPrice(ForagingMineralTypes.CopperBar.getSellPrice() * 10);
                mineral.setWaitingTime(4);
            }
            else
                return new Result<>(false, "You don't have enough ingredients.");
        } else if(metalName.equals("GoldBar")) {
            if(Game.getInstance().getCurrentPlayer().getInventoryManager().CheckExistence(
                    new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Gold), 5))
                    && Game.getInstance().getCurrentPlayer().getInventoryManager().CheckExistence(
                    new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Coal), 1))) {
                Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(
                        new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Gold), 5));
                Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(
                        new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Coal), 1));
                mineral = new Mineral(1, 1, ForagingMineralTypes.GoldBar);
                mineral.setSellPrice(ForagingMineralTypes.GoldBar.getSellPrice() * 10);
                mineral.setWaitingTime(4);
            }
            else
                return new Result<>(false, "You don't have enough ingredients.");
        } else if(metalName.equals("IronBar")) {
            if(Game.getInstance().getCurrentPlayer().getInventoryManager().CheckExistence(
                    new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Iron), 5))
                    && Game.getInstance().getCurrentPlayer().getInventoryManager().CheckExistence(
                    new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Coal), 1))) {
                Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(
                        new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Iron), 5));
                Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(
                        new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Coal), 1));
                mineral = new Mineral(1, 1, ForagingMineralTypes.IronBar);
                mineral.setSellPrice(ForagingMineralTypes.IronBar.getSellPrice() * 10);
                mineral.setWaitingTime(4);
            }
            else
                return new Result<>(false, "You don't have enough ingredients.");
        } else if(metalName.equals("IridiumBar")) {
            if(Game.getInstance().getCurrentPlayer().getInventoryManager().CheckExistence(
                    new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Iridium), 5))
                    && Game.getInstance().getCurrentPlayer().getInventoryManager().CheckExistence(
                    new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Coal), 1))) {
                Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(
                        new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Iridium), 5));
                Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(
                        new ItemStack(new Mineral(1, 1, ForagingMineralTypes.Coal), 1));
                mineral = new Mineral(1, 1, ForagingMineralTypes.IridiumBar);
                mineral.setSellPrice(ForagingMineralTypes.IridiumBar.getSellPrice() * 10);
                mineral.setWaitingTime(4);
            }
            else
                return new Result<>(false, "You don't have enough ingredients.");
        }
        if(mineral == null)
            return new Result<>(false, "You can't produce this mineral with this machine.");
        else
            return addArtisan(mineral);
    }
    public Result<String> addArtisan(Product product) {
        Game.getInstance().getCurrentPlayer().getArtisanItems().add(product);
        return new Result<>(true, product.getName() + " produced. you can collect it " + product.getWaitingTime() + " hour later.");
    }
    @Override
    public void doTask() {

    }
}
