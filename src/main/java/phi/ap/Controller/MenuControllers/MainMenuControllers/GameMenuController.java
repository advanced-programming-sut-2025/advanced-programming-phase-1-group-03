package phi.ap.Controller.MenuControllers.MainMenuControllers;

import phi.ap.model.*;
import phi.ap.model.enums.*;
import phi.ap.model.enums.StoreProducts.*;
import phi.ap.model.items.*;
import phi.ap.model.items.buildings.Farm;
import phi.ap.model.items.buildings.Store;
import phi.ap.model.items.machines.Machine;
import phi.ap.model.items.machines.Refrigerator;
import phi.ap.model.items.machines.craftingMachines.Bomber;
import phi.ap.model.items.machines.craftingMachines.CraftedProducer;
import phi.ap.model.items.machines.craftingMachines.Sprinkler;
import phi.ap.model.items.products.*;
import phi.ap.model.items.tools.MilkPail;
import phi.ap.model.items.tools.Shear;
import phi.ap.model.items.tools.Tool;
import phi.ap.utils.Misc;

import java.util.ArrayList;
import java.util.Arrays;

public class GameMenuController {
    public Result<String> test(String input) {
        System.out.println(Game.getInstance().getCurrentPlayer().getGold());
        Ability ability = new Ability(AbilityType.Farming);
        System.out.println(Game.getInstance().getCurrentPlayer().getCookingRecipes());
        ability.advanceLevel();
        System.out.println(Game.getInstance().getCurrentPlayer().getCookingRecipes());
        ability.advanceLevel();
        System.out.println(Game.getInstance().getCurrentPlayer().getCookingRecipes());
        ability.advanceLevel();
        System.out.println(Game.getInstance().getCurrentPlayer().getCookingRecipes());
        return new Result<>(true, Game.getInstance().getCurrentPlayer().getInventoryManager().showStorage());
    }
    public Result<String> test1(String input) {
        //get info in coordinate;
        Farm farm = Game.getInstance().getCurrentPlayer().getFarm();
        Map map = Game.getInstance().getMap();
        String[] params = Arrays.stream(input.split("\\s+")).filter(s -> !s.isEmpty()).toArray(String[]::new);
        try {
            int y = Integer.parseInt(params[1].replaceAll("\\s+", ""));
            int x = Integer.parseInt(params[0].replaceAll("\\s+", ""));
            System.out.println(map.getTopItem(y, x) + map.getTopTile(y, x).toString(true));
        } catch (Exception e) {
            return new Result<>(true, e.getMessage());
        }
        return null;
    }
    public Result<String> newGame(ArrayList<String> usernames) {
//        User temp = App.getInstance().getLoggedInUser();
        if (App.getInstance().getLoggedInUser() == null) {
            return new Result<>(false, "You are not logged in");
        }
        if (App.getInstance().getLoggedInUser().getGameJoinedId() != null) {
            return new Result<>(false, "You are member of another game");
        }
        if (usernames.isEmpty()) {
            return new Result<>(false, "You must enter at least one username");
        }
        if (usernames.size() > 3) {
            return new Result<>(false, "You must enter at most three usernames");
        }
        ArrayList<User> users = new ArrayList<>();
        users.add(App.getInstance().getLoggedInUser());
        for (String username : usernames) {
            User user = App.getInstance().getUserService().getUserByUsername(username);
            if (user == null) {
                return new Result<>(false, "User " +
                        username +
                        " not found");
            }
            users.add(user);
        }
        for (User user : users) {
            int c = 0;
            for (User user1 : users) {
                if (user1.getUsername().equals(user.getUsername())) {
                    ++c;
                }
            }
            if (c >= 2) {
                return new Result<>(false, "You can't add a user more than once");
            }
        }
        for (User user : users) {
            if (user.getGameJoinedId() != null) {
                return new Result<>(false, "user " +
                        user.getUsername() +
                        " is already member of another game");
            }
        }
        Game.setInstance(new Game(Game.getNewGameID(), users));
        for (User user : users) {
            user.setGameJoinedId(Game.getInstance().getGameID());
        }
        return new Result<>(true, "");
    }

    public Result<String> chooseMap(String mapNumber) {
        return null;
    }

    public Result<String> loadGame() {
        return null;
    }

    public Result<String> exitGame() {
        return null;
    }

    //TODO : election
    public Result<String> nextTurn() {
        Game.getInstance().goNextPlayer();

        String message = "Ok now is the "+Game.getInstance().getCurrentPlayer()+"'s turn";

        // age ye door tamoom shod
        if(Game.getInstance().getCurrentPlayer().equals(Game.getInstance().getPlayers().getFirst()))
        {
            advanceHour();
            message = "one hour passed...";
        }

        return new Result<>(true, message);
    }

    private void doArtisanTask() {
        for(Product product : Game.getInstance().getCurrentPlayer().getArtisanItems()) {
            product.reduceWaitingTime(-1);
            System.out.println(product.getWaitingTime());
        }
    }

    private void doHourTask() {
        doArtisanTask();
    }

    private boolean advanceHour(){
        doHourTask();
        int nightHours = Game.getInstance().getDate().advanceHour();
        for(int i = 0; i < nightHours; i++){
            Game.getInstance().getDate().advanceHour();
        }
        if(nightHours != 0) {
            doNightTasks();
            return true;
        }
        return false;
    }
    private void doAnimalSystemTasks() {
        ArrayList<Animal> animals = Game.getInstance().getCurrentPlayer().getAnimals();
        for(Animal animal : animals) {
            if(!animal.getIsFeeded())
                animal.addFriendShip(-20);
            if(!animal.getIsBeenPet())
                animal.addFriendShip(-10);
            if(!animal.getIsInHome())
                animal.addFriendShip(-20);
            System.out.println("!! " + animal.getRemainingDayToProduce());
            animal.reduceRemainingDayToProduce();
            System.out.println("!! " + animal.getRemainingDayToProduce());
            if(animal.getIsFeeded() && animal.getRemainingDayToProduce() == 0) {
                AnimalProduct animalProduct = animal.produceProduct();
                if(animalProduct != null) {
                    ArrayList<AnimalProduct> animalProduct1 = animal.getAnimalProducts();
                    animalProduct1.add(animalProduct);
                }
                animal.setRemainingDayToProduce();
            }
            animal.setFeeded(false);
            animal.setBeenPet(false);
        }
    }
    private void doNightTasks() {
        System.out.println("zzz... sleeping");

        //TODO
        doAnimalSystemTasks();
        Game.getInstance().getWeatherManager().setWeathersInMorning();
        App.getInstance().getGameService().generateForaging(1);
        //anjam kar haiee ke bayad too shab anjam beshan
    }

    public Result<String> showTime() {
        return new Result<>(true, "It's " + Game.getInstance().getDate().getHour()%24+" o'clock");
    }

    public Result<String> showDate() {
        Date curDate = Game.getInstance().getDate();
        String message = "it's day " + curDate.getDay() + " of " + curDate.getSeason().name;
        return new Result<>(true, message);
    }

    public Result<String> showDateTime() {
        String message = showTime().data + "\n" + showDate().data;
        return new Result<>(true, message);
    }

    public Result<String> showWeekDay() {
        return new Result<>(true, "It is " +Game.getInstance().getDate().getWeekDay().toString());
    }

    public Result<String> cheatAdvanceTime(String amount) {
        int hour;
        try {
             hour = Integer.parseInt(amount);
        }catch (Exception e){
            return new Result<>(false, "dorost bede dige, in chie akhe?");
        }
        if(hour < 0)
            return new Result<>(false, "can time be negative???!");

        for(int i = 0; i < hour; i++) {
            if(advanceHour())
                hour = hour - Date.SLEEP_TIME;
        }
        return new Result<>(true, "zaman " + amount + " saat raft jeloo");
    }

    public Result<String> cheatAdvanceDate(String amount) {
        int day;
        try {
            day = Integer.parseInt(amount);
        }catch (Exception e){
            return new Result<>(false, "dorost bede dige, in chie akhe?");
        }
        cheatAdvanceTime(String.valueOf(day*24));
        return new Result<>(true, "zaman " + day + " rooz raft jeloo");
    }

    public Result<String> showSeason() {
        return new Result<>(true, "It is " + Game.getInstance().getDate().getSeason().name);
    }

    public Result<String> showWeather() {
        return new Result<>(true, Game.getInstance().getWeatherManager().getCurrentWeather().toString());
    }

    public Result<String> showWeatherForecast() {
        return new Result<>(true, Game.getInstance().getWeatherManager().getTomorrowWeather().toString());
    }

    public Result<String> cheatSetTomorrowWeather(String weatherName) {
        Weather weather = Weather.getWeatherByName(weatherName);
        if (weather == null) return new Result<>(false, weatherName + "doesn't exist");
        Game.getInstance().getWeatherManager().setTomorrowWeather(weather);
        return new Result<>(true, "Tomorrow weather is " + weather + " now");
    }

    public Result<String> buildGreenhouse() {
        return null;
    }

    public Result<String> walkOne(String yDiffSt, String xDiffSt) {
        if (Game.getInstance() == null) return new Result<>(false, "You are not in a game!");
        int yDiff, xDiff;
        try {
            yDiff = Integer.parseInt(yDiffSt);
            xDiff = Integer.parseInt(xDiffSt);
        } catch (Exception e){
            return new Result<>(false, "Invalid distance");
        }
        Player player = Game.getInstance().getCurrentPlayer();
        Location location = player.getLocation();
        if (!location.isWalkable(yDiff, xDiff)) {
            return new Result<>(false, "You can't go there");
        }
        if (!App.getInstance().getMapService().isNeighbor(yDiff, xDiff)) {
            return new Result<>(false, "is it one block distance?!");
        }
        int cost = location.getCostOfWalkOne(yDiff, xDiff);
        if (player.getEnergy().getAmount() < cost) {
            return new Result<>(false, "Aww, you're so tired for that!");
        }
        if (location.walkOne(yDiff, xDiff)) {
            player.getEnergy().advanceBaseInt(-cost);
            return new Result<>(true, "walk successful, energy consumed = " +
                    cost +
                    "\n" +
                    "current location : " +
                    location.getGround() +
                    ", " +
                    "(y:" + location.getY() + ", x:" + location.getX() + ")");
        } else {
            return new Result<>(false, "Walk -one wasn't successful");
        }


    }

    public Result<String> walkDiff(String yDiffSt, String xDiffSt) {
        int yDiff, xDiff;
        try {
            yDiff = Integer.parseInt(yDiffSt);
            xDiff = Integer.parseInt(xDiffSt);
        } catch (Exception e){
            return new Result<>(false, "Invalid coordinate");
        }
        Coordinate coord = Game.getInstance().getCurrentPlayer().getLocation().getGround().getTileCoordinateBaseMap(
                Game.getInstance().getCurrentPlayer().getLocation().getY(),
                Game.getInstance().getCurrentPlayer().getLocation().getX());
        int y = yDiff + coord.getY();
        int x = xDiff + coord.getX();
        return walk(String.valueOf(y), String.valueOf(x));
    }

    public Result<String> walk(String yDestSt, String xDestSt) {

        int yDest, xDest;
        try {
            yDest = Integer.parseInt(yDestSt);
            xDest = Integer.parseInt(xDestSt);
        } catch (Exception e){
            return new Result<>(false, "Invalid coordinate");
        }

        Player player = Game.getInstance().getCurrentPlayer();
        Location location = player.getLocation();
        Location target = App.getInstance().getMapService().getLocationOnMap(yDest, xDest);
        if (target == null) return new Result<>(false, "That place doesn't exist!");
        target.setFaceWay(null);
        Path path = App.getInstance().getMapService().getPath(location, target);
        if (path == null) return new Result<>(false, "You can't go there");
        int cost = 0;
        for (Coordinate step : path.getSteps()) {
            int costPer = location.getCostOfWalkOne(step.getY(), step.getX());
            player.getEnergy().advanceBaseInt(-costPer);
            if (player.getEnergy().getAmount() == 0) {
                break;
            }
            cost += costPer;
            if (!location.walkOne(step.getY(), step.getX())) return new Result<>(false,
                    "something happened, you can't go there.");
        }

        StringBuilder  res = new StringBuilder();
        res.append("energy consumed = " +
            cost +
            "\n" +
            "current location : " +
            location.getGround() +
            ", " +
            "(y:" + location.getY() + ", x:" + location.getX() + ")");


        if (player.getEnergy().getAmount() == 0) {
            res.append("Zzzz...");
        }
        return new Result<>(true, res.toString());
        //TODO : in case of opening menu or market manage it;

    }

    public Result<String> showMap() {
        Game game = Game.getInstance();
        if (game == null) {
            return new Result<>(false, "there is no running game");
        }
        Map map = game.getMap();
        Tile[][] tiles = new Tile[map.getHeight()][map.getWidth()];

        map.show(0, 0, tiles);
        //add players on the map;
        for (Player player : Game.getInstance().getPlayers()) {
            PlayerIcon icon = new PlayerIcon(player, player.getLocation().getFaceWay());
            icon.setCoordinate(player.getLocation().getCoordinate());
            Coordinate coord = player.getLocation().getGround().getTileCoordinateBaseMap(
                    icon.getCoordinate().getY(), icon.getCoordinate().getX());
            for (int i = 0; i < icon.getHeight(); i++) {
                for (int j = 0; j < icon.getWidth(); j++) {
                    int y = coord.getY() + i;
                    int x = coord.getX() + j;
                    tiles[y][x] = new Tile(icon.getTile(i, j).getSymbol(), tiles[y][x].getFgColor() + icon.getTile(i, j).getFgColor(),
                            tiles[y][x].getBgColor() + icon.getTile(i, j).getBgColor());
                }
            }
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < map.getHeight(); i++) {
            StringBuilder temp = new StringBuilder();
            for (int j = 0; j < map.getWidth(); j++) {
                temp.append(tiles[i][j].toString(true));
            }
            temp.append("\n");
            res.append(temp);
        }
        return new Result<>(true, res.toString());
    }
    public Result<String> printMap(String startX, String startY, String size) {
        return null;
    }
    public Result<String> helpReadingMap() {
        return null;
    }

    public Result<String> showEnergy() {
        return new Result<>(true, "Energy: " + Game.getInstance().getCurrentPlayer().getEnergy().getAmount());
    }

    public Result<String> cheatSetEnergy(String energyString) {
        int energy = Integer.parseInt(energyString);
        int dif = energy - Game.getInstance().getCurrentPlayer().getEnergy().getAmount();
        Game.getInstance().getCurrentPlayer().getEnergy().advanceBaseInt(dif);
        return new Result<>(true, "energ has been set to " + energy);
    }
    public Result<String> cheatSetEnergy(int energy) {
        int dif = energy - Game.getInstance().getCurrentPlayer().getEnergy().getAmount();
        Game.getInstance().getCurrentPlayer().getEnergy().advanceBaseInt(dif);
        return new Result<>(true, "energ has been set to " + energy);
    }

    public Result<String> cheatSetEnergyUnlimited() {
        Game.getInstance().getCurrentPlayer().getEnergy().setMaxAmount(Integer.MAX_VALUE);
        return cheatSetEnergy(Integer.MAX_VALUE);
    }

    public Result<String> showInventory() {
        return new Result<>(true, Game.getInstance().getCurrentPlayer().getInventoryManager().showStorage());
    }

    public Result<String> inventoryTrash(String itemName, String amount) {
        return null;
    }

    public Result<String> toolEquip(String toolName) {
        ItemStack stack = Game.getInstance().getCurrentPlayer().getInventoryManager()
                .getItemByName(toolName);
        if (stack == null) {
            return new Result<>(false, "there is no tool with that name");
        }
        Tool tool = (Tool)stack.getItem();
        Game.getInstance().getCurrentPlayer().getToolManager().setCurrentTool(tool);
        return new Result<>(true, "Tool equipped");
    }

    public Result<String> showCurrentTool() {
        if(Game.getInstance().getCurrentPlayer().getToolManager().getCurrentTool() == null)
            return new Result<>(false, "You don't have a current tool..");
        return new Result<>(true, Game.getInstance().getCurrentPlayer().getToolManager().getCurrentTool().getName());
    }

    public Result<String> showAvailableTools() {
        return null;
    }

    public Result<String> upgradeTool(String toolName) {
        return null;
    }

    public Result<String> useTool(String direction) {
        int d;
        try{
            d = Integer.parseInt(direction);
        }catch (Exception e) {
            return new Result<>(false,"your direction is not valid!");
        }
        if(d < 0 || d > 7)
            return new Result<>(false, "direction must be between 0 and 7");
        Tool currentTool = Game.getInstance().getCurrentPlayer().getToolManager().getCurrentTool();
        if(currentTool == null)
            return new Result<>(false, "you don't have tool, first equip some of them");
        return currentTool.useTool(Misc.getDiffFromDirection(d));
    }

    public Result<String> craftInfo(String nameSt) {

        String name = nameSt.replaceAll("\\s+", "").toLowerCase();

        CropsTypes cropsType = CropsTypes.find(name);
        ForagingCropsTypes foragingCropsType = ForagingCropsTypes.find(name);
        TreeTypes treeType = TreeTypes.find(name);
        ForagingTreeTypes foragingTreeType = ForagingTreeTypes.find(name);
        FruitTypes fruitType = FruitTypes.find(name);
        SeedTypes seedType = SeedTypes.find(name);
        SaplingTypes saplingType = SaplingTypes.find(name);
        MixedSeedsTypes mixedSeedsType = MixedSeedsTypes.find(name);

        StringBuilder res = new StringBuilder();
        if (cropsType != null || foragingCropsType != null) {
            Crop v;
            if (cropsType != null) v = new Crop(1, 1, cropsType);
            else v = new Crop(1, 1, foragingCropsType);
            res.append("Name: " + v.getName() + "\n");
            res.append("Type: ");
            if (cropsType != null) res.append("crop\n");
            else res.append("foraging crop\n");
            res.append("Source: " + v.getSourceName() + "\n");
            res.append("Total harvest time: " + v.totalHarvestTime() + "\n");
            res.append("One time: " + v.isOneTime() + "\n");
            if (!v.isOneTime()) res.append("Regrowth time: " + v.getRegrowthTime() + "\n");
            res.append("Growing stages: " + v.getStages().toString() + "\n");
            res.append("Base sell price: " + v.getSellPrice() + "\n");
            res.append("Eatable: " + (v.getEatable() != null) + "\n");
            if (v.getEatable() != null) {
                res.append("Energy: " + v.getEatable().getEnergy() + "\n");
            }
            if (cropsType != null) res.append("Seasons: " + cropsType.getSeasonsList().toString() + "\n");
            else res.append("Seasons: " + foragingCropsType.getSeasonList() + "\n");
            res.append("Giantable: " + v.isCanBecomeGiant() + "\n");
        } else if (foragingTreeType != null || treeType != null) {
            Tree v;
            TreeTypes foragingType = null;
            if (foragingTreeType != null) {
                foragingType = foragingTreeType.getTreeType();
            }
            if (foragingTreeType != null) v = new Tree(1, 1, foragingType, true);
            else v = new Tree(1, 1, treeType, false);

            res.append("Name: " + v.getName() + "\n");
            res.append("Type: ");
            if (foragingTreeType != null) res.append("foraging tree\n");
            else res.append("tree\n");
            res.append("Fruit name: " + v.getFruit().toString() + "\n");
            res.append("Source: " + v.getSourceName() + "\n");
            res.append("Total harvest time: " + v.totalHarvestTime() + "\n");
            res.append("Fruit harvest cycles: " + v.getRemainingHarvestCycles() + "\n");
            if (v.getRemainingHarvestCycles() > 1) res.append("Fruit regrowth time: " + v.getHarvestRegrowthTime() + "\n");
            res.append("Growing stages: " + v.getStages().toString() + "\n");
            res.append("Fruit base sell price: " + v.getFruit().getBaseSellPrice() + "\n");
            if (treeType != null) res.append("Seasons: " + treeType.getSeasonList() + "\n");
            else res.append("Seasons: " + foragingType.getSeasonList() + "\n");
            res.append("Giantable: " + v.isCanBecomeGiant() + "\n");
        } else if (fruitType != null) {
            Fruit v = new Fruit(1, 1, fruitType);

            res.append("Name: " + v.getName() + "\n");
            res.append("Type: fruit\n");
            res.append("Source: " + fruitType.getTreeType() + "\n");
            res.append("Eatable: " + (fruitType.getEatable() != null) + "\n");
            if (fruitType.getEatable() != null) res.append("Energy: " + fruitType.getEatable().getEnergy() + "\n");
            res.append("Harvest cycles: " + fruitType.getFruitHarvestCycle() + "\n");
            res.append("Fruit regrowth time: " + fruitType.getTreeType().getStages().getLast() + "\n");
            res.append("Base sell price: " + v.getSellPrice() + "\n");
            res.append("Seasons: " + fruitType.getSeasonList() + "\n");
        } else if(mixedSeedsType != null) {
            res.append("Name: " + mixedSeedsType + "\n");
            res.append("Type: mixed seed\n");
            res.append("Possible seeds: " + mixedSeedsType.getMixedSeeds() + "\n");
            res.append(("Season: " + mixedSeedsType.getSeasonList() + "\n"));
        } else if (seedType != null) {
            res.append("Name: " + seedType + "\n");
            res.append("Type: seed\n");
            res.append("Product: ");
            res.append(seedType.findCropType() + "\n");
            res.append("Seasons: " + seedType.getSeasonList() + "\n");
        } else if (saplingType != null) {
            res.append("Name: " + saplingType + "\n");
            res.append("Type: sapling\n");
            res.append("Product: ");
            res.append(saplingType.getTreeType() + "\n");
            res.append("Seasons: " + saplingType.getTreeType().getSeasonList() + "\n");
        } else {
            return new Result<>(false, "Nothing found");
        }
        return new Result<>(true, res.toString());

    }











    public Result<String> showCraftingRecipes() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("showCraftingRecipes:\n");
        for(Recipe recipe : Game.getInstance().getCurrentPlayer().getCraftingRecipes())
            stringBuilder.append(recipe.getRecipeType().getName() + "\n");
        return new Result<>(true, stringBuilder.toString());
    }
    public Result<String> craftItem(String itemName) {
        if(CraftingTypes.getType(itemName) == null)
            return new Result<>(false, "there is not any machine with this name.");
        System.out.println(itemName);
        Recipe recipe = null;
        for(Recipe recipe1 : Game.getInstance().getCurrentPlayer().getCraftingRecipes()) {
            if(recipe1.getRecipeType().getName().equals(itemName))
                recipe = recipe1;
        }
        if(recipe == null)
            return new Result<>(false, "You don't have it's recipe.");
        if(!Game.getInstance().getCurrentPlayer().getInventoryManager().canAdd())
            return new Result<>(false, "Inventory is full.");
        if(!Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(recipe))
            return new Result<>(false, "You don't have enough ingredients.");

        if(!Game.getInstance().getCurrentPlayer().getEnergy().hasEnergy(2))
            return new Result<>(false, "You don't have enough energy");

        Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(recipe.getResult().getItem(), 1);
        Game.getInstance().getCurrentPlayer().getEnergy().advanceBaseInt(-2);
        return new Result<>(true, "item crafted successfully.");
    }
    public Result<String> placeItem(String itemName, String direction) {
        return null;
    }
    public Item getItem(String name) {
        if(name.equals("Wood"))
            return new Wood(1, 1);
        if(name.equals("Stone"))
            return new Stone(1, 1, StoneTypes.RegularStone);
        if(AnimalProductTypes.getType(name) != null)
            return new AnimalProduct(1, 1, AnimalProductTypes.getType(name));
        if(AnimalTypes.getType(name) != null)
            return new Animal(AnimalTypes.getType(name), 1, 1);
        if(CropsTypes.getType(name) != null)
            return new Crop(1, 1 , CropsTypes.getType(name));
        if(CraftingTypes.getType(name) != null)
            return CraftingTypes.getType(name).getRecipe().getResult().getItem();
        if(FishTypes.getType(name) != null)
            return new Fish(1, 1, FishTypes.getType(name));
        if(FoodTypes.getType(name) != null)
            return new Food(1, 1, FoodTypes.getType(name));
        if(ForagingCropsTypes.getType(name) != null)
            return new Crop(1, 1, ForagingCropsTypes.getType(name));
        if(ForagingMineralTypes.getType(name) != null)
            return new Mineral(1, 1, ForagingMineralTypes.getType(name));
        if(TreeTypes.getType(name) != null)
            return new Tree(1, 1, TreeTypes.getType(name), false);
        if(SeedTypes.getType(name) != null)
            return new Seed(1, 1, SeedTypes.getType(name));
        if(SaplingTypes.getType(name) != null)
            return new Sapling(1, 1, SaplingTypes.getType(name));
        if(StoneTypes.getType(name) != null)
            return new Stone(1, 1, StoneTypes.getType(name));
        return null;
    }
    public Result<String> cheatAddItem(String itemName, String amountString) {
        int amount = Integer.parseInt(amountString);
        Item item = getItem(itemName);
        if(item == null)
            return new Result<>(false, "There is no item with this name.");
        Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(item, amount);
        return new Result<>(true, item.getName() + " with amount " + amount + " has been added to inventoryManger.");
    }
    public Result<String> putItemToRefrigerator(String name, String amountString) {
        Item item;
        int amount = Integer.parseInt(amountString);
        Boolean isFood = false;
        FoodTypes foodType = FoodTypes.getType(name);
        if(foodType != null) {
            isFood = true;
            return Refrigerator.getInstance().putItem(new ItemStack(new Food(1, 1, foodType), amount));
        }
        AnimalProductTypes animalProductType = AnimalProductTypes.getType(name);
        if(animalProductType != null && !animalProductType.equals(AnimalProductTypes.Wool) && !animalProductType.equals(AnimalProductTypes.RabbitFoot)) {
            isFood = true;
            return Refrigerator.getInstance().putItem(new ItemStack(new AnimalProduct(1, 1, animalProductType, null, 0, null), amount));
        }
        FishTypes fishType = FishTypes.getType(name);
        if(fishType != null) {
            isFood = true;
            return Refrigerator.getInstance().putItem(new ItemStack(new Fish(1, 1, fishType), amount));
        }
        FruitTypes fruitType = FruitTypes.getType(name);
        if(fruitType != null) {
            isFood = true;
            return Refrigerator.getInstance().putItem(new ItemStack(new Fruit(1, 1, fruitType), amount));
        }
        CropsTypes cropsType = CropsTypes.getType(name);
        if(cropsType != null) {
            isFood = true;
            return Refrigerator.getInstance().putItem(new ItemStack(new Crop(1, 1, cropsType), amount));
        }
        if(isFood)
            return new Result<>(true, "Item added to refrigerator successfully.");
        else
            return new Result<>(false, "This item is not food.");
    }
    public Result<String> pickItemFromRefrigerator(String name, String amountString) {
        int amount = Integer.parseInt(amountString);
        int pickAmount = 0;
        Item item;
        Boolean isFood = false;
        FoodTypes foodType = FoodTypes.getType(name);
        if(foodType != null) {
            isFood = true;
            pickAmount = Refrigerator.getInstance().pickItem(new ItemStack(new Food(1, 1, foodType), amount));
        }
        AnimalProductTypes animalProductType = AnimalProductTypes.getType(name);
        if(animalProductType != null && !animalProductType.equals(AnimalProductTypes.Wool) && !animalProductType.equals(AnimalProductTypes.RabbitFoot)) {
            isFood = true;
            pickAmount = Refrigerator.getInstance().pickItem(new ItemStack(new AnimalProduct(1, 1, animalProductType, null, 0, null), amount));
        }
        FishTypes fishType = FishTypes.getType(name);
        if(fishType != null) {
            isFood = true;
            pickAmount = Refrigerator.getInstance().pickItem(new ItemStack(new Fish(1, 1, fishType), amount));
        }
        FruitTypes fruitType = FruitTypes.getType(name);
        if(fruitType != null) {
            isFood = true;
            pickAmount = Refrigerator.getInstance().pickItem(new ItemStack(new Fruit(1, 1, fruitType), amount));
        }
        CropsTypes cropsType = CropsTypes.getType(name);
        if(fruitType != null) {
            isFood = true;
            pickAmount = Refrigerator.getInstance().pickItem(new ItemStack(new Crop(1, 1, cropsType), amount));
        }
        if(isFood)
            return new Result<>(true, pickAmount + " Items picked from refrigerator successfully.");
        else
            return new Result<>(false, "This item is not food.");
    }
    public Result<String> showCookingRecipe() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Learned Recipes: \n");
        for(Recipe recipe : Game.getInstance().getCurrentPlayer().getCookingRecipes()) {
            stringBuilder.append(recipe.getRecipeType().toString() + "\n");
        }
        return new Result<>(true, stringBuilder.toString());
    }
    public Result<String> prepareCooking(String recipeName) {
        Recipe recipe = Game.getInstance().getCurrentPlayer().getRecipe(recipeName);
        if(recipe == null)
            return new Result<>(false, "You don't have the recipe.");
        if(!Game.getInstance().getCurrentPlayer().getEnergy().hasEnergy(3))
            return new Result<>(false, "You don't have enough energy.");
        if(!Game.getInstance().getCurrentPlayer().getInventoryManager().canAdd())
            return new Result<>(false, "Inventory is full.");
        if(!Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(recipe))
                return new Result<>(false, "You don't have enough ingredients in your inventory. you can add it from refrigerator");
        Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(recipe.getResult().getItem(), recipe.getResult().getAmount());
        Game.getInstance().getCurrentPlayer().getEnergy().advanceBaseInt(-3);
        return new Result<>(true, recipe.getResult().getItem().getName() + " created and added to Inventory.");
    }
    public Result<String> eatFood(String foodName) {
        Food food = Game.getInstance().getCurrentPlayer().getInventoryManager().getFood(FoodTypes.valueOf(foodName));
        if(food == null)
            return new Result<>(false, "There is not any food with this name.");
        Game.getInstance().getCurrentPlayer().getInventoryManager().removeItem(food, 1);
        Game.getInstance().getCurrentPlayer().getEnergy().advanceBaseInt(food.getEatable().getEnergy());
        return new Result<>(true, food.getName() + " eated. " + "You earned " + food.getFoodType().getEatable().getEnergy() + " amount of energy.");
    }
    public Result<String> buildBuilding(String buildingName, String x, String y) {
        return CarpenterShopProducts.Build(buildingName, Integer.parseInt(x), Integer.parseInt(y));
    }
    public Result<String> buyAnimal(String animalType, String animalName) {
        return MarnieRanchProducts.buyAnimal(animalType, animalName);
    }
    public Result<String> petAnimal(String name) {
        Animal animal = Game.getInstance().getCurrentPlayer().getAnimalByName(name);
        if(animal == null)
            return new Result<>(false, "There is no animal with this name.");
        animal.addFriendShip(15);
        animal.setBeenPet(true);
        return new Result<>(true, "yo have pet the animal, 15 FriendShip added");
    }
    public Result<String> cheatSetFriendship(String name, String amount) {
        Animal animal = Game.getInstance().getCurrentPlayer().getAnimalByName(name);
        if(animal == null)
            return new Result<>(false, "There is no animal with this name.");
        animal.addFriendShip(15);
        animal.setBeenPet(true);
        return new Result<>(true, "yo have pet the animal, 15 FriendShip added");
    }
    public Result<String> showAnimalsInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<Animal> animals = Game.getInstance().getCurrentPlayer().getAnimals();
        for(Animal animal : animals) {
            stringBuilder.append("Animal name: " + animal.getName() + " Type: " + animal.getType().toString() +
                    " friendShipAmount: " + animal.getFriendShipAmount() + " isFed: " + animal.getIsFeeded() +
                    " isPet: " + animal.getIsBeenPet());
        }
        return new Result<>(true, stringBuilder.toString());
    }
    public Result<String> shepherdAnimal(String animalName, String x, String y) {
        return null;
    }
    public Result<String> feedAnimal(String animalName) {
        Animal animal = Game.getInstance().getCurrentPlayer().getAnimalByName(animalName);
        if(animal == null) {
            return new Result<>(false, "There is not any Animal with this name.");
        }
        Food food = Game.getInstance().getCurrentPlayer().getInventoryManager().getFood(FoodTypes.Hay);
        if(food == null)
            return new Result<>(false, "You don't have any Hay to feed " + animalName);
        animal.setFeeded(true);
        return new Result<>(true, "Animal fed successfully.");
    }
    public Result<String> produces() {
        ArrayList<Animal> animals = Game.getInstance().getCurrentPlayer().getAnimals();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("collecting produces: \n");
        for(Animal animal : animals) {
            if(animal.getAnimalProducts().size() > 0) {
                stringBuilder.append("Animal name: " + animal.getName() + " " + "Type: " + animal.getType().toString() + "\n");
                for(AnimalProduct animalProduct : animal.getAnimalProducts()) {
                    stringBuilder.append(animalProduct.getAnimalProductType().toString());
                }
            }
        }
        return new Result<>(true, stringBuilder.toString());
    }
    public Result<String> collectProduce(String name, Tool tool) {
        Animal animal = Game.getInstance().getCurrentPlayer().getAnimalByName(name);
        if(animal == null)
            return new Result<>(false, "There is not any animal with this name.");
        ArrayList<AnimalProduct> animalProducts = animal.getAnimalProducts();
        if(animalProducts.isEmpty())
            return new Result<>(false, "this animal doesn't have any product.");
        if(animal.getType().equals(AnimalTypes.Cow) || animal.getType().equals(AnimalTypes.Goat))
            if(!(tool instanceof MilkPail))
                return new Result<>(false, "You don't have milkPail.");
        if(animal.getType().equals(AnimalTypes.Sheep))
            if(!(tool instanceof Shear))
                return new Result<>(false, "You don't have Shear.");
        for(AnimalProduct animalProduct : animalProducts) {
            // TODO set currect amount
            Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(animalProduct, 1);
        }
        animalProducts.clear();
        return new Result<>(true, "products collected successfully");
    }
    public Result<String> sellAnimal(String name) {
        Animal animal = Game.getInstance().getCurrentPlayer().getAnimalByName(name);
        if(animal == null)
            return new Result<>(false, "There is no Animal with this name.");
        ArrayList<Animal> animals = Game.getInstance().getCurrentPlayer().getAnimals();
        int ind = 0;
        for(int i = 0; i < animals.size(); i++) {
            Animal animal1 = animals.get(i);
            if(animal1.getName().equals(name)) {
                ind = i;
            }
        }
        Game.getInstance().getCurrentPlayer().setGold(Game.getInstance().getCurrentPlayer().getGold() +
                (int)animal.getSellPrice());
        animals.remove(ind);
        return new Result<>(true, "animal selled successfully");
    }
    public Result<String> fishing(String fishingPole) {
        return null;
    }
    public Result<String> useArtisan(String artisanName, String itemName, String ingredient) {
        CraftingTypes craftingType;
        try {
            craftingType = CraftingTypes.valueOf(artisanName);
        } catch (IllegalArgumentException e) {
            return new Result<>(false, "There is no machine with this name.");
        }
        if(!Game.getInstance().getCurrentPlayer().getInventoryManager().CheckExistence(new ItemStack(craftingType.getRecipe().getResult().getItem(), 1)))
            return new Result<>(false, "You don't have this machine.");
        Machine machine = (Machine)craftingType.getRecipe().getResult().getItem();
        switch (artisanName) {
            case "Furnace" : return ((CraftedProducer)machine).produceFurnace(itemName);
            case "CharcoalKiln" : return ((CraftedProducer)machine).produceCoal();
            case "BeeHouse" : return ((CraftedProducer)machine).produceHoney(itemName);
            case "CheesePress" : return ((CraftedProducer)machine).produceCheese(itemName);
            case "Keg" : return ((CraftedProducer)machine).produceKeg(itemName, ingredient);
            case "Loom" : return ((CraftedProducer)machine).produceLoom();
            case "MayonnaiseMachine" : return ((CraftedProducer)machine).produceMayonnaiseMachine(itemName, ingredient);
            case "OilMaker" : return ((CraftedProducer)machine).produceOilMaker(itemName, ingredient);
            case "PreservesJar" : return ((CraftedProducer)machine).producePreservesJar(itemName, ingredient);
            case "Dehydrator" : return ((CraftedProducer)machine).produceDehydrator(itemName, ingredient);
            case "FishSmoker" : return ((CraftedProducer)machine).produceFishSmoker(itemName, ingredient);
        }
        return null;
    }
    public Result<String> getArtisan(String artisanName) {
        ArrayList<Product> removeProducts = new ArrayList<>();
        for(Product product : Game.getInstance().getCurrentPlayer().getArtisanItems()) {
            if(product.getName().equals(artisanName) && product.getWaitingTime() == 0) {
                Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(product, 1);
                removeProducts.add(product);
            }
        }
        if(removeProducts.isEmpty())
            return new Result<>(false, "There is no collecting products with this name");
        for(Product product : removeProducts) {
            Game.getInstance().getCurrentPlayer().getArtisanItems().remove(product);
        }
        return new Result<>(true, artisanName + " collected successfully.");
    }
    public Result<String> showAllProducts() {
        // TODO give a true value to storeType based on the store we are there.
        StoreTypes storeType = StoreTypes.MarnieRanch;
        switch (storeType) {
            case StoreTypes.Blacksmith -> {return BlackSmithsProducts.showAllProducts();}
            case StoreTypes.MarnieRanch -> {return MarnieRanchProducts.showAllProducts();}
            case StoreTypes.CarpenterShop -> {return CarpenterShopProducts.showAllProducts();}
            case StoreTypes.FishShop -> {return FishShopProducts.showAllProducts();}
            case StoreTypes.PierreGeneralStore -> {return FishShopProducts.showAllProducts();}
            case StoreTypes.JojaMart -> {return JojaMarketProducts.showAllProducts();}
            case StoreTypes.TheStarDropSaloon -> {return StarDropSaloonProducts.showAllProducts();}
            default -> {
                return null;
            }
        }
    }
    public Result<String> showAvailableProducts() {
        // TODO give a true value to storeType based on the store we are there.
        StoreTypes storeType = StoreTypes.MarnieRanch;
        switch (storeType) {
            case StoreTypes.Blacksmith -> {return BlackSmithsProducts.showAvailableProducts();}
            case StoreTypes.MarnieRanch -> {return MarnieRanchProducts.showAvailableProducts();}
            case StoreTypes.CarpenterShop -> {return CarpenterShopProducts.showAvailableProducts();}
            case StoreTypes.FishShop -> {return FishShopProducts.showAvailableProducts();}
            case StoreTypes.PierreGeneralStore -> {return FishShopProducts.showAvailableProducts();}
            case StoreTypes.JojaMart -> {return JojaMarketProducts.showAvailableProducts();}
            case StoreTypes.TheStarDropSaloon -> {return StarDropSaloonProducts.showAvailableProducts();}
            default -> {
                return null;
            }
        }
    }
    public Result<String> showAllAvailableTools() {
        String response = "";
        response += Game.getInstance().getCurrentPlayer().getToolManager().getAxe().toString()+"\n";
        response += Game.getInstance().getCurrentPlayer().getToolManager().getBackpack().toString()+"\n";
        if(Game.getInstance().getCurrentPlayer().getToolManager().getFishingPole()!=null)
            response += Game.getInstance().getCurrentPlayer().getToolManager().getFishingPole().toString()+"\n";
        response += Game.getInstance().getCurrentPlayer().getToolManager().getHoe().toString()+"\n";
        response += Game.getInstance().getCurrentPlayer().getToolManager().getMilkPail().toString()+"\n";
        response += Game.getInstance().getCurrentPlayer().getToolManager().getPickaxe().toString()+"\n";
        response += Game.getInstance().getCurrentPlayer().getToolManager().getScythe().toString()+"\n";
        response += Game.getInstance().getCurrentPlayer().getToolManager().getShear().toString()+"\n";
        response += Game.getInstance().getCurrentPlayer().getToolManager().getTrashCan().toString()+"\n";
        response += Game.getInstance().getCurrentPlayer().getToolManager().getWateringCan().toString()+"\n";
        return new Result<>(true, response);
    }
    public Result<String> purchase(String productName, String amountString) {
        // TODO give a true value to storeType based on the store we are there.
        StoreTypes storeType = StoreTypes.Blacksmith;
        switch (storeType) {
            case StoreTypes.Blacksmith -> {return BlackSmithsProducts.purchase(productName, amountString);}
            case StoreTypes.CarpenterShop -> {return CarpenterShopProducts.purchase(productName, amountString);}
            case StoreTypes.MarnieRanch -> {return MarnieRanchProducts.purchase(productName, amountString);}
            case StoreTypes.FishShop -> {return FishShopProducts.purchase(productName, amountString);}
            case StoreTypes.PierreGeneralStore -> {return FishShopProducts.purchase(productName, amountString);}
            case StoreTypes.JojaMart -> {return JojaMarketProducts.purchase(productName, amountString);}
            case StoreTypes.TheStarDropSaloon -> {return StarDropSaloonProducts.purchase(productName, amountString);}
            default -> {
                return null;
            }
        }
    }
    public Result<String> cheatAddGold(String amount) {
        Game.getInstance().getCurrentPlayer().setGold(Integer.parseInt(amount));
        return new Result<>(true, Integer.parseInt(amount) + " Gold added.");
    }
    public Result<String> sellProduct(String productName, String amount) {
        return null;
    }
    public Result<String> showFriendShip() {
        return null;
    }
    public Result<String> talk(String userName, String message) {
        return null;
    }
    public Result<String> showTalkHistory(String userName) {
        return null;
    }
    public Result<String> showFriendShip(String userName) {
        return null;
    }
    public Result<String> sendGift(String username, String itemName, String amount) {
        return null;
    }
    public Result<String> showListOfGifts() {
        return null;
    }
    public Result<String> rateGift(String giftNumber, String rate) {
        return null;
    }
    public Result<String> hugGift(String username) {
        return null;
    }
    public Result<String> flower(String username) {
        return null;
    }
    public Result<String> askMarriage(String username, String ringName) {
        return null;
    }
    public Result<String> respondToMarriageRequest(String answer, String username) {
        return null;
    }
    public Result<String> startTrade() {
        return null;
    }
    public Result<String> meetNPC(String npcName) {
        return null;
    }
    public Result<String> giftNPC(String npcName, String ItemName) {
        return null;
    }
    public Result<String> showFriendshipNPCList() {
        return null;
    }
    public Result<String> showQuestList () {
        return null;
    }
    public Result<String> finishQuest(String questIndex) {
        return null;
    }

}
