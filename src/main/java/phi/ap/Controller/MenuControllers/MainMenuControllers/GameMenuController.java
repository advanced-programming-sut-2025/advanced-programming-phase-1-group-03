package phi.ap.Controller.MenuControllers.MainMenuControllers;

import jdk.jfr.Frequency;
import phi.ap.model.*;
import phi.ap.model.enums.*;
import phi.ap.model.enums.StoreProducts.*;
import phi.ap.model.items.*;
import phi.ap.model.items.buildings.AnimalHouse;
import phi.ap.model.items.buildings.Building;
import phi.ap.model.items.buildings.Farm;
import phi.ap.model.items.buildings.Greenhouse;
import phi.ap.model.items.buildings.ShippingBin;
import phi.ap.model.items.buildings.stores.Store;
import phi.ap.model.items.machines.Machine;
import phi.ap.model.items.machines.Refrigerator;
import phi.ap.model.items.machines.craftingMachines.CraftedProducer;
import phi.ap.model.items.products.*;
import phi.ap.model.items.relations.Gift;
import phi.ap.model.items.tools.*;
import phi.ap.model.items.relations.Friendship;
import phi.ap.model.items.relations.Talk;
import phi.ap.model.items.tools.MilkPail;
import phi.ap.model.items.tools.Shear;
import phi.ap.model.items.tools.Tool;
import phi.ap.utils.Misc;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;

public class GameMenuController {
    public Result<String> test(String input) {
        System.out.println(Game.getInstance().getCurrentPlayer().getGold());

        Item item = Game.getInstance().getCurrentPlayer().getLocation().getTopItemDiff(0,0);
        if(item instanceof Store){
            System.out.println("kooni");
        }
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
        doStoreTasks();
        doShippingBinTasks();
        Game.getInstance().getWeatherManager().setWeathersInMorning();
        App.getInstance().getGameService().generateForaging(1);
        App.getInstance().getGameService().doWeatherTasks();
        App.getInstance().getGameService().turnOnSprinklers();
        App.getInstance().getGameService().doCrowAttack();
        //anjam kar haiee ke bayad too shab anjam beshan
    }

    private void doShippingBinTasks(){
        for(ShippingBin shippingBin : Game.getInstance().getShippingBins()){
            Game.getInstance().getCurrentPlayer().setGold(
                    shippingBin.sellAll() + Game.getInstance().getCurrentPlayer().getGold());
        }
    }
    private void doStoreTasks() {
        Game.getInstance().getStoreManager().refreshItems();
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

    public Result<String> walkWASD(String dir) {
        String ydiff;
        String xdiff = switch (dir) {
            case "w" -> {
                ydiff = "-1";
                yield "0";
            }
            case "a" -> {
                ydiff = "0";
                yield "-1";
            }
            case "s" -> {
                ydiff = "1";
                yield "0";
            }
            case "d" -> {
                ydiff = "0";
                yield "1";
            }
            default -> {
                ydiff = "@";
                yield "@";
            }
        };
        return walkOne(ydiff, xdiff);
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
        for (Coordinate thunderedTile : game.getMap().getThunderedTiles()) {
            int y = thunderedTile.getY();
            int x = thunderedTile.getX();
            tiles[y][x].setBgColor(tiles[y][x].getBgColor() + Colors.bg(236));
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
        ItemStack stack = Game.getInstance().getCurrentPlayer().getInventoryManager()
                .getItemByName(toolName);
        if (stack == null) {
            return new Result<>(false, "there is no tool with that name");
        }
        Tool tool = (Tool)stack.getItem();

        Ground ground = Game.getInstance().getCurrentPlayer().getLocation().getGround();
        if(!(ground instanceof Store store)){
            return new Result<>(false, "You are not in a store!!");
        }
        int currentHour = Game.getInstance().getDate().getCurrentHour();
        if (currentHour < store.getStoreTypes().getOpeningTime() || currentHour > store.getStoreTypes().getClosingTime())
            return new Result<>(false, "Store is closed in this time!!");

        if(tool.getLevelProcess().isMax())
            return new Result<>(false, "Your tool is at maximum level!! you can't upgrade it anymore");

        LevelName level = tool.getLevelProcess().getCurrentLevelName();
        BlackSmithsProducts productNextLevel;
        if(tool instanceof TrashCan)
            productNextLevel = BlackSmithsProducts.getNextLevelTrashcan(level);
        else
            productNextLevel = BlackSmithsProducts.getNextLevelTool(level);
        if(productNextLevel.getPrice() > Game.getInstance().getCurrentPlayer().getGold())
            return new Result<>(false, "You don't have enough money!!");

        if(!Game.getInstance().getCurrentPlayer().getInventoryManager().CheckExistence(productNextLevel.getIngredient()))
            return new Result<>(false, "You don't have enough "+productNextLevel.getIngredient().getItem().getName());

        if(tool.getUpgradeDay() == Game.getInstance().getDate().getRawDay())
            return new Result<>(false, "You already updated your tool this day!!");

        tool.upgrade(Game.getInstance().getDate().getRawDay());
        Game.getInstance().getCurrentPlayer().getInventoryManager().removeItem(
                productNextLevel.getIngredient().getItem(), productNextLevel.getIngredient().getAmount());
        Game.getInstance().getCurrentPlayer().setGold(
                Game.getInstance().getCurrentPlayer().getGold() - productNextLevel.getPrice());
        return new Result<>(true, "tool upgraded successfully, current Level is : " + tool.getLevelProcess().getCurrentLevelName().toString());
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
            else res.append("Seasons: " + foragingCropsType.getSeasonsList() + "\n");
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

    public boolean giantChecker(int y, int x) {
        int sy, sx;
        int[][] dir = {{y - 1, x - 1}, {y - 1, x}, {y, x - 1}, {y, x}};
        int[][] dir2 = {{0, 0}, {1, 0}, {0, 1}, {1, 1}};
        for (int[] d1 : dir) {
            sy = d1[0];
            sx = d1[1];
            //greenhouse checker:
            boolean inGreenhouse = false;
            for (int[] d2 : dir2) {
                y = sy + d2[0];
                x = sx + d2[1];
                Location loc = App.getInstance().getMapService().getLocationOnMap(y, x);
                if (loc.getGround() instanceof Greenhouse) inGreenhouse = true;
            }
            if (inGreenhouse) continue;
            Item item = Game.getInstance().getMap().getTopItem(sy, sx);
            if (!(item instanceof Plant)) continue;
            Plant plant = (Plant) item;
            boolean same = true;
            if (plant.getGiant() != null) same = false;
            if (!plant.isCanBecomeGiant()) same = false;
            ArrayList<Plant> members = new ArrayList<>();
            for (int[] d2 : dir2) {
                y = sy + d2[0];
                x = sx + d2[1];
                Item item2 = Game.getInstance().getMap().getTopItem(y, x);
                if (!(item2 instanceof Plant)) same = false;
                else {
                    Plant plant2 = (Plant) item2;
                    if (!plant.canStackWith(plant2)) same = false;
                    else if (plant2.getGiant() != null) same = false;
                    else if (!plant2.isCanBecomeGiant()) same = false;
                    else {
                        members.add(plant2);
                    }
                }
            }
            if (!same) continue;
            Giant giant = new Giant(members);
            for (int[] d2 : dir2) {
                y = sy + d2[0];
                x = sx + d2[1];
                Item item2 = Game.getInstance().getMap().getTopItem(y, x);
                Plant plant2 = (Plant) item2;
                plant2.setGiant(giant);
            }
            return true;
        }
        return false;
    }

    public Result<String> plant(String sourceName, String directionSt) {
        int direction;
        try {
            direction = Integer.parseInt(directionSt);
        } catch (Exception e) {
            return new Result<>(false, "invalid direction");
        }
        if (direction < 0 || direction > 7) return new Result<>(false, "direction must be between 0 and 7");
        Coordinate d = new Coordinate(Misc.getDiffFromDirection(direction));
        Player player = Game.getInstance().getCurrentPlayer();
        Location loc = player.getLocation();
        Item item;
        boolean inGreenhouse = loc.getGround() instanceof Greenhouse;
        int y = loc.getY() + d.getY() + loc.getGround().getCoordinateBaseMap().getY();
        int x = loc.getX() + d.getX() + loc.getGround().getCoordinateBaseMap().getX();
        if (!((item = loc.getGround().getTopItem(loc.getY() + d.getY(), loc.getX() + d.getX())) instanceof Dirt)) {
            return new Result<>(false, "You cant plant something there!");
        }
        Dirt dirt = (Dirt) item;
        if (!dirt.isPlowed()) {
            return new Result<>(false, "dirt is not plowed");
        }
        sourceName = sourceName.replaceAll("\\s+", "").toLowerCase();
        SeedTypes seedType = SeedTypes.find(sourceName);
        SaplingTypes saplingType = SaplingTypes.find(sourceName);

        if (seedType != null) {
            Seed seed = new Seed(1, 1, seedType);
            if (player.getInventoryManager().getItem(seed).getAmount() == 0) {
                return new Result<>(false, "You don't have enough seeds");
            }
            CropsTypes cropType = seedType.findCropType();
            TreeTypes treeTypes = TreeTypes.findBySeed(seedType);
            if (cropType != null) {
                Crop crop = new Crop(1, 1, cropType);
                crop.setCoordinate(new Coordinate(0, 0));
                dirt.unPlow();
                dirt.addItem(crop);
                player.getInventoryManager().removeItem(seed, 1);
                if (giantChecker(y, x)) return new Result<>(true, cropType + " planted successfully and you just made giant plant!" );
                else return new Result<>(true, cropType + " planted successfully");
            } else if (treeTypes != null) {
                Tree tree = new Tree(1, 1, treeTypes, false);
                tree.setCoordinate(new Coordinate(0, 0));
                dirt.unPlow();
                dirt.addItem(tree);
                player.getInventoryManager().removeItem(seed, 1);
                if (giantChecker(y, x)) return new Result<>(true, treeTypes + " planted successfully and you just made giant plant!" );
                else return new Result<>(true, treeTypes + " planted successfully");
            } else {
                return new Result<>(false, "wierd happened!");
            }
        } else if (saplingType != null) {
            Sapling sapling = new Sapling(1, 1, saplingType);
            if (player.getInventoryManager().getItem(sapling).getAmount() == 0) {
                return new Result<>(false, "You don't have enough saplings");
            }
            TreeTypes treeTypes = TreeTypes.findBySapling(saplingType);
            if (treeTypes != null) {
                Tree tree = new Tree(1, 1, treeTypes, false);
                tree.setCoordinate(new Coordinate(0, 0));
                dirt.unPlow();
                dirt.addItem(tree);
                player.getInventoryManager().removeItem(sapling, 1);
                if (giantChecker(y, x)) return new Result<>(true, treeTypes + " planted successfully and you just made giant plant!" );
                else return new Result<>(true, treeTypes + " planted successfully");
            } else {
                return new Result<>(false, "wierd happened!");
            }
        } else {
            return new Result<>(false, "Nothing found");
        }


    }

    public Result<String> showPlant(String ySt, String xSt) {
        int y, x;
        try {
            y = Integer.parseInt(ySt);
            x = Integer.parseInt(xSt);
        } catch (Exception e) {
            return new Result<>(false, "invalid coordinate");
        }
        Item item = Game.getInstance().getMap().getTopItem(y, x);
        if (!(item instanceof Plant)) {
            return new Result<>(false, "there is no plant there");
        }
        Plant plant = (Plant) item;
        return new Result<>(true, plant.showPlant());
    }

    public Result<String> showPlantDiff(String ySt, String xSt) {
        int y, x;
        try {
            y = Integer.parseInt(ySt);
            x = Integer.parseInt(xSt);
        } catch (Exception e) {
            return new Result<>(false, "invalid coordinate");
        }
        Location loc = Game.getInstance().getCurrentPlayer().getLocation();
        y += loc.getGround().getTileCoordinateBaseMap(loc.getY(), loc.getX()).getY();
        x += loc.getGround().getTileCoordinateBaseMap(loc.getY(), loc.getX()).getX();
        return showPlant(String.valueOf(y), String.valueOf(x));
    }

    public Result<String> fertilizePlant(String fertilizerSt, String directionSt) {
        int direction;
        try {
            direction = Integer.parseInt(directionSt);
        } catch (Exception e) {
            return new Result<>(false, "invalid direction");
        }
        if (direction < 0 || direction > 7) return new Result<>(false, "direction must be between 0 and 7");
        Coordinate d = new Coordinate(Misc.getDiffFromDirection(direction));
        Player player = Game.getInstance().getCurrentPlayer();
        Location loc = player.getLocation();
        Item item;
        if (!((item = loc.getGround().getTopItem(loc.getY() + d.getY(), loc.getX() + d.getX())) instanceof Plant)) {
            return new Result<>(false, "there is no plant there!");
        }
        SoilTypes soilType = SoilTypes.find(fertilizerSt.replaceAll("\\s+", "").toLowerCase());
        if (soilType == null) {
            return new Result<>(false, "Soil not found!");
        }
        Plant plant = (Plant) item;
        return switch (soilType) {
            case BasicRetaining, QualityRetaining, GrassStarter -> new Result<>(false, "Unknown soil type");
            default -> {
                plant.fertilize(soilType);
                yield new Result<>(true, "Plant fertilized");
            }
        };
    }

    public Result<String> howMuchWater() {
        //TODO : parsa
        return null;
    }

    public Result<String> greenHouseBuild() {
        Player player = Game.getInstance().getCurrentPlayer();
        Greenhouse greenhouse = player.getFarm().getGreenhouse();
        if (greenhouse.isBuilt()) {
            return new Result<>(false, "greenhouse has been built already!");
        }
        if (player.getInventoryManager().getItem(new Wood(1, 1)).getAmount() < Greenhouse.woodRequired
            || player.getGold() < Greenhouse.goldRequired) {
            return new Result<>(false, "You need " +
                    Greenhouse.goldRequired + "gold and " +
                    Greenhouse.woodRequired + "wood " +
                    "to build greenhouse!");
        }
        player.setGold(player.getGold() - Greenhouse.goldRequired);
        player.getInventoryManager().removeItem(new Wood(1, 1), Greenhouse.woodRequired);

        greenhouse.build();

        return new Result<>(true, "greenhouse built successfully!");

    }

    public Result<String> cheatThor(String ySt, String xSt) {
        int y, x;
        try {
            y = Integer.parseInt(ySt);
            x = Integer.parseInt(xSt);
        } catch (Exception e) {
            return new Result<>(false, "invalid coordinate");
        }
        return App.getInstance().getGameService().thunderCoordinate(y, x);
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
        if(CropsTypes.find(name) != null)
            return new Crop(1, 1 , CropsTypes.find(name));
        if(CraftingTypes.getType(name) != null)
            return CraftingTypes.getType(name).getRecipe().getResult().getItem();
        if(FishTypes.getType(name) != null)
            return new Fish(1, 1, FishTypes.getType(name));
        if(FoodTypes.getType(name) != null)
            return new Food(1, 1, FoodTypes.getType(name));
        if(ForagingCropsTypes.find(name) != null)
            return new Crop(1, 1, ForagingCropsTypes.find(name));
        if(ForagingMineralTypes.getType(name) != null)
            return new Mineral(1, 1, ForagingMineralTypes.getType(name));
        if(TreeTypes.find(name) != null)
            return new Tree(1, 1, TreeTypes.find(name), false);
        if(SeedTypes.find(name) != null)
            return new Seed(1, 1, SeedTypes.find(name));
        if(SaplingTypes.find(name) != null)
            return new Sapling(1, 1, SaplingTypes.find(name));
        if(StoneTypes.getType(name) != null)
            return new Stone(1, 1, StoneTypes.getType(name));
        if(SoilTypes.find(name) != null)
            return new Soil(1, 1, SoilTypes.find(name));
        if(ForagingMineralTypes.find(name) != null)
            return ForagingMineralTypes.find(name).getItem();
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
    public Result<String> buildBuilding(String buildingName, String sx, String sy) {
        int x = Integer.parseInt(sx);
        int y = Integer.parseInt(sy);

        Ground ground = Game.getInstance().getCurrentPlayer().getLocation().getGround();

        if(!(ground instanceof Store store) || !((Store)ground).getStoreTypes().equals(StoreTypes.CarpenterShop)){
            return new Result<>(false, "You are not in carpenter shop.");
        }
        int currentHour = Game.getInstance().getDate().getCurrentHour();
        if (currentHour < store.getStoreTypes().getOpeningTime() || currentHour > store.getStoreTypes().getClosingTime())
            return new Result<>(false, "Store is closed in this time!!");

        AbstractMap.SimpleEntry<StoreItemProducer, Integer> toBuy = null;
        for(Object p : store.getAvailableProducts()){
            AbstractMap.SimpleEntry<StoreItemProducer, Integer> product = (AbstractMap.SimpleEntry<StoreItemProducer, Integer>) p;
            if(product.getKey().getNameInStore().equalsIgnoreCase(buildingName)) {
                toBuy = product;
            }
        }
        if(toBuy == null)
            return new Result<>(false, "Building name is invalid");
        if(toBuy.getValue() == 0)
            return new Result<>(false, "Your daily limit reached");

        int totalMoney = toBuy.getKey().getItem().getSellPrice();
        if(totalMoney > Game.getInstance().getCurrentPlayer().getGold())
            return new Result<>(false, "You don't have enough fucking money.");

        Building house = (Building)toBuy.getKey().getItem();

        if(!Game.getInstance().getCurrentPlayer().getFarm().isAreaEmpty(y, x, house.getHeight(), house.getWidth()))
            return new Result<>(false, "Chosen area must be empty!!");

        ItemStack itemStack = Game.getInstance().getCurrentPlayer().getInventoryManager()
                .getItem(CarpenterShopProducts.Wood.getItem());

        int neededWood = CarpenterShopProducts.fromString(buildingName).getNeededWood();
        if(itemStack.getAmount() < neededWood)
            return new Result<>(false, "You don't have enough wood");

        itemStack = Game.getInstance().getCurrentPlayer().getInventoryManager()
                .getItem(CarpenterShopProducts.Stone.getItem());

        int neededStone = CarpenterShopProducts.fromString(buildingName).getNeededStone();
        if(itemStack.getAmount() < neededStone)
            return new Result<>(false, "You don't have enough stone");

        Game.getInstance().getCurrentPlayer().getInventoryManager().removeItem(CarpenterShopProducts.Wood.getItem(), neededWood);
        Game.getInstance().getCurrentPlayer().getInventoryManager().removeItem(CarpenterShopProducts.Stone.getItem(), neededStone);
        store.buy(toBuy.getKey().getItem());
        Game.getInstance().getCurrentPlayer().setGold(
                Game.getInstance().getCurrentPlayer().getGold() - totalMoney);

        house.setCoordinate(new Coordinate(y,x));

        Game.getInstance().getCurrentPlayer().getFarm().addItem(house);

        if(house instanceof AnimalHouse)
            Game.getInstance().getCurrentPlayer().getOwnedAnimalHouse().add((AnimalHouse) house);
        else if(house instanceof ShippingBin)
            Game.getInstance().addShippingBin((ShippingBin) house);
        return new Result<>(true, "Building added successfully.");
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
        Ground ground = Game.getInstance().getCurrentPlayer().getLocation().getGround();
        if(!(ground instanceof Store store)){
            return new Result<>(false, "You are not in a store!!");
        }

        int currentHour = Game.getInstance().getDate().getCurrentHour();
        if (currentHour < store.getStoreTypes().getOpeningTime() || currentHour > store.getStoreTypes().getClosingTime())
            return new Result<>(false, "Store is closed in this time!!");

        StringBuilder response = new StringBuilder();
        for(Object p : store.getAllProducts()){
            AbstractMap.SimpleEntry<StoreItemProducer, Integer> product = (AbstractMap.SimpleEntry<StoreItemProducer, Integer>) p;

            if(product.getKey().getItem() == null)
                continue;
            String amount = String.valueOf(product.getValue());
            if(product.getValue() == Integer.MAX_VALUE) amount = " unlimited";
            response.append(product.getKey().getNameInStore()).append(" ").append(product.getKey().getItem().getSellPrice()).append(" coin")
                    .append("   amount : ").append(amount).append("\n");
        }
        return new Result<>(true, response.toString());
    }
    public Result<String> showAvailableProducts() {
        Ground ground = Game.getInstance().getCurrentPlayer().getLocation().getGround();
        if(!(ground instanceof Store store)){
            return new Result<>(false, "You are not in a store!!");
        }
        int currentHour = Game.getInstance().getDate().getCurrentHour();
        if (currentHour < store.getStoreTypes().getOpeningTime() || currentHour > store.getStoreTypes().getClosingTime())
            return new Result<>(false, "Store is closed in this time!!");

        StringBuilder response = new StringBuilder();
        for(Object p : store.getAvailableProducts()){
            AbstractMap.SimpleEntry<StoreItemProducer, Integer> product = (AbstractMap.SimpleEntry<StoreItemProducer, Integer>) p;
            if(product.getKey().getItem() == null)
                continue;
            String amount = String.valueOf(product.getValue());
            if(product.getValue() == Integer.MAX_VALUE) amount = " unlimited";
            response.append(product.getKey().getNameInStore()).append(" ").append(product.getKey().getItem().getSellPrice()).append(" coin")
                    .append("   amount : ").append(amount).append("\n");
        }
        return new Result<>(true, response.toString());
    }
    public Result<String> showAllAvailableTools() {
        StringBuilder builder = new StringBuilder();
        for(ItemStack item : Game.getInstance().getCurrentPlayer().getInventoryManager().getAllItems()){
            if(item.getItem() instanceof Tool){
                builder.append(item.getItem().getName()+"\n");
            }
        }
        return new Result<>(true, builder.toString());
    }
    public Result<String> purchase(String productName, String amountString) {
        int amount;
        if(amountString == null || amountString.isEmpty())
            amount = 1;
        else
            amount = Integer.parseInt(amountString);

        Ground ground = Game.getInstance().getCurrentPlayer().getLocation().getGround();

        if(!(ground instanceof Store store)){
            return new Result<>(false, "You are not in a store!!");
        }
        int currentHour = Game.getInstance().getDate().getCurrentHour();
        if (currentHour < store.getStoreTypes().getOpeningTime() || currentHour > store.getStoreTypes().getClosingTime())
            return new Result<>(false, "Store is closed in this time!!");

        boolean productExists = false;
        for(Object p : store.getAllProducts()){
            AbstractMap.SimpleEntry<StoreItemProducer, Integer> product = (AbstractMap.SimpleEntry<StoreItemProducer, Integer>) p;
            if(product.getKey().getNameInStore().equalsIgnoreCase(productName)) {
                productExists = true;
            }
        }

        if(!productExists)
            return new Result<>(false, "Product does not exist!");


        AbstractMap.SimpleEntry<StoreItemProducer, Integer> toBuy = null;
        for(Object p : store.getAvailableProducts()){
            AbstractMap.SimpleEntry<StoreItemProducer, Integer> product = (AbstractMap.SimpleEntry<StoreItemProducer, Integer>) p;
            if(product.getKey().getNameInStore().equalsIgnoreCase(productName)) {
                toBuy = product;
            }
        }

        if(toBuy.getValue() < amount || amount == 0)
            return new Result<>(false, "This item is not available in the Store with this amount!!");

        switch (store.getStoreTypes()){
            case StoreTypes.FishShop -> {
                FishShopProducts product = FishShopProducts.fromString(productName);
                if(Game.getInstance().getCurrentPlayer().getAbilityLevel(AbilityType.Fishing) < product.getFishingSkill())
                    return new Result<>(false, "For buying this item your fishing skill must be at least "
                            + product.getFishingSkill());

                if(toBuy.getKey().getItem() instanceof Recipe)
                    Game.getInstance().getCurrentPlayer().addCraftingRecipe((Recipe) toBuy.getKey().getItem());
                else
                    Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(new ItemStack(toBuy.getKey().getItem(), amount));
            }
            case StoreTypes.CarpenterShop -> {
                if(toBuy.getKey().getItem() instanceof Building)
                    return new Result<>(false, "If you want to build an animal house, use build command");


                Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(new ItemStack(toBuy.getKey().getItem(), amount));
            }
            case TheStarDropSaloon -> {
                if(toBuy.getKey().getItem() instanceof Recipe)
                    Game.getInstance().getCurrentPlayer().addCookingRecipe((Recipe) toBuy.getKey().getItem());
                else
                    Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(new ItemStack(toBuy.getKey().getItem(), amount));
            }
            case Blacksmith -> {
                if(toBuy.getKey().getItem() == null)
                    return new Result<>(false, "use upgrade tool to upgrade your tools");
                else{
                    Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(new ItemStack(toBuy.getKey().getItem(), amount));
                }
            } case MarnieRanch -> {
                Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(new ItemStack(toBuy.getKey().getItem(), amount));
            } case PierreGeneralStore -> {
                Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(new ItemStack(toBuy.getKey().getItem(), amount));
            } case JojaMart -> {
                Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(new ItemStack(toBuy.getKey().getItem(), amount));
            }
        }

        int totalMoney = amount * toBuy.getKey().getItem().getSellPrice();
        if(totalMoney > Game.getInstance().getCurrentPlayer().getGold())
            return new Result<>(false, "You don't have enough fucking money.");

        Game.getInstance().getCurrentPlayer().setGold(
                Game.getInstance().getCurrentPlayer().getGold() - totalMoney);

        store.buy(toBuy.getKey().getItem());

        return new Result<>(true, amount + " " + productName + " bought!");
    }
    public Result<String> cheatAddDollar(String amountSt) {
        int amount;
        try {
            amount = Integer.parseInt(amountSt);
        } catch (Exception e) {
            return new Result<>(false, "Invalid amount!");
        }
        Game.getInstance().getCurrentPlayer().setGold(Game.getInstance().getCurrentPlayer().getGold() + amount);
        return new Result<>(true, amount + "dollars added, your currency: " +
                Game.getInstance().getCurrentPlayer().getGold() + "$");
    }
    public Result<String> sellProduct(String productName, String amountString) {
        if(amountString == null) amountString = "1";
        int amount = Integer.parseInt(amountString);
        ShippingBin ship = null;
        for(int d = 0; d < 8; d++){
            Coordinate cord = Misc.getDiffFromDirection(d);
            Item itemOnTop = Game.getInstance().getCurrentPlayer().getLocation().getTopItemDiff(cord.getY(), cord.getX());
            if(itemOnTop instanceof ShippingBin)
                ship = (ShippingBin) itemOnTop;
        }
        if(ship == null)
            return new Result<>(false, "You are not close to a shipping bin!");
        ItemStack itemStack = Game.getInstance().getCurrentPlayer().getInventoryManager().getItemByName(productName);
        if(itemStack == null)
            return new Result<>(false, "We don't have this item");
        if(itemStack.getAmount() < amount)
            return new Result<>(false, "We don't have " + amount + " of this item");
        if(!itemStack.getItem().isSellable())
            return new Result<>(false, "This Item is not sellable");
        Game.getInstance().getCurrentPlayer().getInventoryManager().removeItem(itemStack.getItem(), amount);
        ship.addItem(new ItemStack(itemStack.getItem(), amount));
        return new Result<>(true, "Items put in shipping bin, interest from selling them will be paid tomorrow");
    }
    public Result<String> showFriendShip() {
        ArrayList<Friendship> friendships = Friendship.getFriends(Game.getInstance().getCurrentPlayer());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Game.getInstance().getCurrentPlayer().getUser().getUsername() + " Friends: \n");
        for(Friendship friendship : friendships) {
            stringBuilder.append("UserName: ");
            if(!friendship.getPlayer1().equals(Game.getInstance().getCurrentPlayer()))
                stringBuilder.append(friendship.getPlayer1().getUser().getUsername());
            if(!friendship.getPlayer2().equals(Game.getInstance().getCurrentPlayer()))
                stringBuilder.append(friendship.getPlayer2().getUser().getUsername());
            stringBuilder.append("Level Of friendShip: " + friendship.getLevel() + " Xp: " + friendship.getXp());
        }
        return new Result<>(true, stringBuilder.toString());
    }
    public Result<String> talk(String userName, String message) {
        Friendship friendship = Friendship.getFriendShip(Game.getInstance().getCurrentPlayer(), Game.getInstance().getPlayerByUserName(userName));
        friendship.addTalk(Game.getInstance().getCurrentPlayer(), friendship, message);
        return new Result<>(true, "Your message have been sent.");
    }
    public Result<String> showTalkHistory(String userName) {
        Friendship friendship = Friendship.getFriendShip(Game.getInstance().getCurrentPlayer(),
                Game.getInstance().getPlayerByUserName(userName));
        if(friendship == null)
            return new Result<>(false, "There is not a player with this userName.");
        ArrayList<Talk> talks = friendship.getTalk();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Talk History: ");
        for(Talk talk : talks) {
            stringBuilder.append("Sender: " + talk.getSender().getUser().getUsername() + " Message: " +
                    talk.getMessage());
        }
        return new Result<>(true, stringBuilder.toString());
    }
    public Result<String> showFriendShip(String userName) {
        return null;
    }
    public Result<String> sendGift(String username, String itemName, String amountString) {
        int amount = Integer.parseInt(amountString);
        Friendship friendship = Friendship.getFriendShip(Game.getInstance().getCurrentPlayer(),
                Game.getInstance().getPlayerByUserName(itemName));
        if(friendship == null)
            return new Result<>(false, "There is no Player with this userName.");
        Item item = getItem(itemName);
        if(item == null)
            return new Result<>(false, "There is no item with this name.");
        if(!Game.getInstance().isNear(Game.getInstance().getCurrentPlayer(), Game.getInstance().getPlayerByUserName(username)))
            return new Result<>(false, "You must be near " + username);
        friendship.GiftPlayer(Game.getInstance().getCurrentPlayer(), Game.getInstance().getPlayerByUserName(username), new ItemStack(item, amount));
        return new Result<>(true, "Your Gift have been sent.");
    }
    public Result<String> showListOfGifts() {
        ArrayList<Gift> gifts = Gift.getReceivedGifts(Game.getInstance().getCurrentPlayer());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Received Gifts: \n");
        for(Gift gift : gifts) {
            stringBuilder.append(gift.printGift() + "\n");
        }
        return new Result<>(true, stringBuilder.toString());
    }
    public Result<String> rateGift(String giftString, String rate) {
        int giftNumber = Integer.parseInt(giftString);
        int rateNumber  = Integer.parseInt(rate);
        Gift gift = Gift.getGiftById(giftNumber);
        if(gift == null)
            return new Result<>(false, "There is no gift with this name.");
        if(rateNumber < 1 || rateNumber > 5)
            return new Result<>(false, "Rate must be between 1 to 5");
        gift.setRate(rateNumber);
        return new Result<>(true, "Your rate submitted.");
    }
    public Result<String> giftHistory(String userName) {
        Friendship friendship = Friendship.getFriendShip(Game.getInstance().getCurrentPlayer(), Game.getInstance().getPlayerByUserName(userName));
        if(friendship == null)
            return new Result<>(false, "There is no player with this name.");
        ArrayList<Gift> receivedGifts = friendship.getPlayer1Gift();
        ArrayList<Gift> sentGifts = friendship.getPlayer2Gift();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Received Gifts: \n");
        for(Gift gift : receivedGifts) {
            stringBuilder.append(gift.printGift());
        }
        stringBuilder.append("Sent Gifts: \n");
        for(Gift gift : sentGifts) {
            stringBuilder.append(gift.printGift() + "\n");
        }
        return new Result<>(true, stringBuilder.toString());
    }
    public Result<String> hug(String username) {
        if(Game.getInstance().getPlayerByUserName(username) == null)
            return new Result<>(false, "invalid userName.");
        if(!Game.getInstance().isNear(Game.getInstance().getCurrentPlayer(),
                Game.getInstance().getPlayerByUserName(username)))
            return new Result<>(false, "You must be near " + username);
        Friendship friendship = Friendship.getFriendShip(Game.getInstance().getCurrentPlayer(),
                Game.getInstance().getPlayerByUserName(username));
        friendship.hug();
        return new Result<>(true, "players hugged each other.");
    }
    public Result<String> flower(String username) {
        if(Game.getInstance().getPlayerByUserName(username) == null)
            return new Result<>(false, "invalid userName.");
        if(!Game.getInstance().isNear(Game.getInstance().getCurrentPlayer(),
                Game.getInstance().getPlayerByUserName(username)))
            return new Result<>(false, "You must be near " + username);
        Friendship friendship = Friendship.getFriendShip(Game.getInstance().getCurrentPlayer(),
                Game.getInstance().getPlayerByUserName(username));
        ItemStack itemStack = Game.getInstance().getCurrentPlayer().getInventoryManager().getItem(new Product(ProductNames.Bouquet));
        if(itemStack.getAmount() == 0)
            return new Result<>(false, "You don't have Bouquet in your inventoryManger.");
        Game.getInstance().getCurrentPlayer().getInventoryManager().removeItem(new Product(ProductNames.Bouquet), 1);
        Game.getInstance().getPlayerByUserName(username).getInventoryManager().addItem(new Product(ProductNames.Bouquet), 1);
        friendship.giveFlower();
    }
    public Result<String> askMarriage(String username, String ringName) {
        Player partner = Game.getInstance().getPlayerByUserName(username);
        if(partner == null)
            return new Result<>(false, "There is no player with this userName.");
        if(partner.getUser().getGender().equals(Game.getInstance().getCurrentPlayer().getUser().getGender()))
            return new Result<>(false, "The player Gender is the Same! Shame on you!");
        ItemStack itemStack = Game.getInstance().getCurrentPlayer().getInventoryManager().getItem(new Product(ProductNames.WeddingRing));
        Friendship friendship = Friendship.getFriendShip(Game.getInstance().getCurrentPlayer(), partner);
        if(friendship.getLevel() <= 2)
            return new Result<>(false, "You must have at least friendShip with level 3.");
        if(itemStack.getAmount() == 0)
            return new Result<>(false, "You don't have WeddingRing.");

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
