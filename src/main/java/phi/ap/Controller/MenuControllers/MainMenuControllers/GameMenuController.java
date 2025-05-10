package phi.ap.Controller.MenuControllers.MainMenuControllers;

import phi.ap.model.*;
import phi.ap.model.enums.*;
import phi.ap.model.enums.Menus.Menu;
import phi.ap.model.enums.StoreProducts.*;
import phi.ap.model.items.Item;
import phi.ap.model.items.PlayerIcon;
import phi.ap.model.items.buildings.Farm;
import phi.ap.model.items.producers.Animal;
import phi.ap.model.items.products.AnimalProduct;
import phi.ap.model.items.products.Food;
import phi.ap.model.items.tools.MilkPail;
import phi.ap.model.items.tools.Shear;
import phi.ap.model.items.tools.Tool;
import phi.ap.service.MapService;
import phi.ap.utils.Misc;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GameMenuController {
    public Result<String> test(String input) {
        String[] params = Arrays.stream(input.split("\\s+")).filter(s -> !s.isEmpty()).toArray(String[]::new);

        return null;
    }
    public Result<String> test1(String input) {
        //get info in coordinate;
        Farm farm = Game.getInstance().getCurrentPlayer().getFarm();
        Map map = Game.getInstance().getMap();
        String[] params = Arrays.stream(input.split("\\s+")).filter(s -> !s.isEmpty()).toArray(String[]::new);
        int y = Integer.parseInt(params[0].replaceAll("\\s+", ""));
        int x = Integer.parseInt(params[1].replaceAll("\\s+", ""));
        System.out.println(map.getTopItem(y, x) + map.getTopTile(y, x).toString(true));
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

    private void advanceHour(){
        if(Game.getInstance().getDate().advanceHour()) {
            Game.getInstance().getDate().goToNextDay();
            doNightTasks();
        }
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
            animal.reduceRemainingDayToProduce();
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
        //anjam kar haiee ke bayad too shab anjam beshan
    }

    public Result<String> showTime() {
        return new Result<>(true, "It's " + Game.getInstance().getDate().getHour()+" o'clock");
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

        for(int i = 0; i < hour; i++)
            advanceHour();
        return new Result<>(true, "zaman " + amount + " saat raft jeloo");
    }

    public Result<String> cheatAdvanceDate(String amount) {
        int day;
        try {
            day = Integer.parseInt(amount);
        }catch (Exception e){
            return new Result<>(false, "dorost bede dige, in chie akhe?");
        }
        for(int i = 0; i < day; i++) {
            Game.getInstance().getDate().goToNextDay();
            doNightTasks();
        }
        return new Result<>(true, "zaman " + day + " rooz raft jeloo");
    }

    public Result<String> showSeason() {
        return new Result<>(true, "It is " + Game.getInstance().getDate().getSeason().name);
    }

    public Result<String> showWeather() {
        return null;
    }

    public Result<String> showWeatherForecast() {
        return null;
    }

    public Result<String> cheatSetTomorrowWeather(String weather) {
        return null;
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
                    "(y:" + location.getY() + ", x:" + location.getY() + ")");
        } else {
            return new Result<>(false, "Walk -one wasn't successful");
        }


    }

//    public Result<String> walk(String yDest, String xDest) {
//        int y, x;
//        try {
//            y = Integer.parseInt(yDest);
//            x = Integer.parseInt(xDest);
//        } catch (Exception e) {
//            return new Result<>(false, "Invalid coordinate");
//        }
//        MapService mapService = App.getInstance().getMapService();
//        if (mapService.getMap() == null) return new Result<>(false, "There is no map");
//        Location target = mapService.getLocationOnMap(y, x);
//        Player player = Game.getInstance().getCurrentPlayer();
//        Location source = player.getLocation();
//        if (target == null) return new Result<>(false, "you can't go there cause it doesn't exist!");
//        Path path = mapService.getPath(source, target);
//        if (path == null) return new Result<>(false, "you can't go there");
//        int energyCost = (path.getCost() + 19) / 20;
//        if (player.getEnergy() < energyCost) {
//            return new Result<>(false, "you don't have enough energy");
//        }
//        player.setEnergy(player.getEnergy() - energyCost);
//        //TODO : manage energy and turn;
//        //TODO : faint check(ghash kardan);
//        //TODO : in case of opening menu or market manage it;
//        for (Coordinate step : path.getSteps()) {
//            source.walkOne(step.getY(), step.getX());
//        }
//        return new Result<>(true, "walk successful");
//    }

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
        return null;
    }

    public Result<String> cheatSetEnergy(String energy) {
        return null;
    }

    public Result<String> cheatSetEnergyUnlimited() {
        return null;
    }

    public Result<String> showInventory() {
        return null;
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
        if(d < 1 || d > 8)
            return new Result<>(false, "direction must be between 1 and 8");
        d -= 1; // make direction zero base
        Tool currentTool = Game.getInstance().getCurrentPlayer().getToolManager().getCurrentTool();
        if(currentTool == null)
            return new Result<>(false, "you don't have tool, first equip some of them");
        currentTool.useTool(Misc.getDiffFromDirection(d));
        return new Result<>(true, "Tool used");
    }
    public Result<String> showCraftingRecipes() {
        return null;
    }
    public Result<String> craftItem(String itemName) {
        return null;
    }
    public Result<String> placeItem(String itemName, String direction) {
        return null;
    }
    public Result<String> cheatAddItem(String itemName, String amount) {
        return null;
    }
    public Result<String> openRefrigerator(String pickPut, String Item) {
        return null;
    }
    public Result<String> showCookingRecipe() {
        return null;
    }
    public Result<String> prepareCooking(String recipeName){
        return null;
    }
    public Result<String> eatFood(String foodName) {
        return null;
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
        for(Animal animal : animals) {
            if(animal.getAnimalProducts().size() > 0) {
                stringBuilder.append("Animal name: " + animal.getName() + " " + "Type: " + animal.getType().toString());
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
    public Result<String> useArtisan(String artisanName, String itemName) {
        return null;
    }
    public Result<String> getArtisan(String artisanName) {
        return null;
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
    public Result<String> showAllAvailableProducts() {
        String response = "";
        response += Game.getInstance().getCurrentPlayer().getToolManager().getAxe().toString()+"\n";
        response += Game.getInstance().getCurrentPlayer().getToolManager().getBackpack().toString()+"\n";
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
        return null;
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
