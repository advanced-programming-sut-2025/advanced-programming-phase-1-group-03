package phi.ap.Controller.MenuControllers.MainMenuControllers;

import jdk.jfr.Frequency;
import phi.ap.model.*;
import phi.ap.model.enums.*;
import phi.ap.model.enums.Menus.Menu;
import phi.ap.model.enums.StoreProducts.*;
import phi.ap.model.enums.npcStuff.NPCTypes;
import phi.ap.model.enums.npcStuff.Quests;
import phi.ap.model.items.*;
import phi.ap.model.items.buildings.*;
import phi.ap.model.items.buildings.stores.Store;
import phi.ap.model.items.machines.Machine;
import phi.ap.model.items.machines.Refrigerator;
import phi.ap.model.items.machines.craftingMachines.Bomber;
import phi.ap.model.items.machines.craftingMachines.CraftedProducer;
import phi.ap.model.items.products.*;
import phi.ap.model.items.relations.Gift;
import phi.ap.model.items.tools.*;
import phi.ap.model.items.relations.Friendship;
import phi.ap.model.items.relations.Talk;
import phi.ap.model.items.tools.MilkPail;
import phi.ap.model.items.tools.Shear;
import phi.ap.model.items.tools.Tool;
import phi.ap.model.npcStuff.NPC;
import phi.ap.model.npcStuff.State;
import phi.ap.service.MapService;
import phi.ap.utils.FileManager;
import phi.ap.utils.Misc;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


import static java.util.Collections.frequency;
import static java.util.Collections.swap;

public class GameMenuController {
    public Result<String> test(String input) {
//        for(int i = 0; i < 40; i++) {
//            for(int j = 0; j < 40; j++) {
//                System.out.println(shepherdAnimal(null, Integer.toString(i), Integer.toString(j)));
//            }
//        }
//        return new Result<>(true, Game.getInstance().getCurrentPlayer().getInventoryManager().showStorage());

        //**thunder
//        for (int i = 0; i < Game.getInstance().getMap().getHeight(); i++) {
//            for (int j = 0; j < Game.getInstance().getMap().getWidth(); j++) {
//                cheatThor(String.valueOf(i), String.valueOf(j));
//            }
//        }

        return null;
    }
    public Result<String> test1(String input) {
        Item it = Game.getInstance().getCurrentPlayer().getLocation().getTopItemDiff(1,0);
        if(it != null)
            System.out.println(it.getName());
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


    public Result<String> loadGame() {
        return null; //TODO
    }

    public Result<String> exitGame() {
        if (Game.getInstance().getCurrentPlayer() != Game.getInstance().getPlayers().get(0)) {
            return new Result<>(false, "You are not game loader");
        }

        for(Player player : Game.getInstance().getPlayers()){
            User user = player.getUser();
            user.setMaxGold(Math.max(user.getMaxGold(), player.getGold()));
            user.setGamePlayed(user.getGamePlayed() + 1);
        }
        new FileManager().writeAppData();
        App.getInstance().changeMenu(Menu.MainMenu);
        return new Result<>(true, "game closed successfully");
    }


    public String TalkNotification() {
        StringBuilder stringBuilder = new StringBuilder();
        boolean ok = false;
        stringBuilder.append("Talk notifications: \n");
        ArrayList<Friendship> friendships = Friendship.getFriends(Game.getInstance().getCurrentPlayer());
        for(Friendship friendship : friendships) {
            ArrayList<Talk> talks = friendship.getTalk();
            for(Talk talk : talks) {
                if(!talk.getHaveSeen() && !talk.getSender().equals(Game.getInstance().getCurrentPlayer())) {
                    stringBuilder.append("Message from " + talk.getSender().getUser().getUsername() + ": " + talk.getMessage() + "\n");
                    ok = true;
                    talk.setHaveSeen(true);
                }
            }
        }
        if(!ok)
            return null;
        return stringBuilder.toString();
    }

    public String giftNotification() {
        StringBuilder stringBuilder = new StringBuilder();
        Boolean ok = false;
        stringBuilder.append("Gift notofications: \n");
        ArrayList<Gift> gifts = Gift.getReceivedGifts(Game.getInstance().getCurrentPlayer());
        for(Gift gift : gifts) {
            if(!gift.getHaveSeen()) {
                stringBuilder.append(gift.getNotification() + "\n");
                gift.setHaveSeen(true);
                ok = true;
            }
        }
        if(!ok)
            return null;
        return stringBuilder.toString();
    }

    private String marriageNotification() {
        if(Game.getInstance().getCurrentPlayer().getUser().getGender().equals(Gender.Male))
            return null;
        StringBuilder stringBuilder = new StringBuilder();
        Boolean ok = false;
        stringBuilder.append("Marriage notofications: \n");
        ArrayList<Friendship> friendships = Friendship.getFriends(Game.getInstance().getCurrentPlayer());
        for(Friendship friendship : friendships) {
            if(friendship.getMarriageRequest() != null) {
                stringBuilder.append("You have a marriage request from " + friendship.getMarriageRequest().getApplicant());
                ok = true;
            }
        }
        if(!ok)
            return null;
        return stringBuilder.toString();
    }
    //TODO : election
    public Result<String> nextTurn() {

        Game.getInstance().goNextPlayer();
        String message = "Ok now is the "+Game.getInstance().getCurrentPlayer()+"'s turn\n";

        // age ye door tamoom shod
        if(Game.getInstance().getCurrentPlayer().equals(Game.getInstance().getPlayers().getFirst()))
        {
            advanceHour();
            message += "one hour passed...\n";

        }
        String giftNotification = giftNotification();
        if(giftNotification != null)
            message += "\n" + giftNotification;
        String TalkNotification = TalkNotification();
        if(TalkNotification != null)
            message += "\n" + TalkNotification;
        String marriageNotification = marriageNotification();
        if(marriageNotification != null)
            message += "\n" + marriageNotification;
        if (Game.getInstance().getCurrentPlayer().isPlayerFeinted()) {
            message += "\nplayer " + Game.getInstance().getCurrentPlayer().getUser().getUsername() + " has feinted!\n";
            message += "\n" + nextTurn();
        }
        return new Result<>(true, message);
    }

    private void doArtisanTask() {
        for(Product product : Game.getInstance().getCurrentPlayer().getArtisanItems()) {
            product.reduceWaitingTime(-1);
        }
    }

    private void doHourTask() {
        doArtisanTask();
        //Quests:
        for (Player player : Game.getInstance().getPlayers()) {
            for (Quests quest : Quests.values()) {
                if (player.isQuestActivatedSoFar(quest)) continue;
                if (quest.activeCheck(Game.getInstance().getNPC(
                        NPCTypes.findByName(quest.getOwner())).getState(player))) {
                    player.activateQuest(quest);
                }
            }
        }
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
    private void doFriendShipTask() {
        for(Friendship friendship : Friendship.getFriendships()) {
            friendship.setHaveTalked(false);
            friendship.setFlower(false);
            friendship.setHugged(false);
        }
    }
    private void doEnergyTask() {
        for (Player player : Game.getInstance().getPlayers()) {
            player.getEnergy().reset();
        }
    }
    private void doTransportTasks() {
        for (int i = 0; i < Game.getInstance().getPlayers().size(); i++) {
            if (!Game.getInstance().getCurrentPlayer().getFeintBuff().isActive()) {
                Cottage cot = Game.getInstance().getCurrentPlayer().getFarm().getCottage();
                Coordinate coord = cot.getTileCoordinateBaseMap(1, 1);
                walk(String.valueOf(coord.getY()), String.valueOf(coord.getX()));
            }
            Game.getInstance().goNextPlayer();
        }
    }
    private void doNightTasks() {
        System.out.println("zzz... sleeping");
        //TODO
        doAnimalSystemTasks();
        doStoreTasks();
        doShippingBinTasks();
        doFriendShipTask();
        Game.getInstance().getWeatherManager().setWeathersInMorning();
        App.getInstance().getGameService().generateForaging(1);
        App.getInstance().getGameService().doWeatherTasks();
        App.getInstance().getGameService().turnOnSprinklers();
        App.getInstance().getGameService().doCrowAttack();
        App.getInstance().getGameService().doNPCStuffAtNight();
        doEnergyTask();
        doTransportTasks();
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
            res.append("Zzzz..., you just feinted!");
        }
        return new Result<>(true, res.toString());

    }

    public Tile[][] makeMap() {
        Game game = Game.getInstance();
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
        return tiles;
    }

    public Result<String> showMap() {
        Game game = Game.getInstance();
        if (game == null) {
            return new Result<>(false, "there is no running game");
        }
        Tile[][] tiles = makeMap();
        Map map = Game.getInstance().getMap();
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
    public Result<String> printMap(String startY, String startX, String size) {
        Game game = Game.getInstance();
        if (game == null) {
            return new Result<>(false, "there is no running game");
        }
        int sy, sx, len;
        try {
            sy = Integer.parseInt(startY);
            sx = Integer.parseInt(startX);
            len = Integer.parseInt(size);
        } catch (Exception e) {
            return new Result<>(false, "Invalid coordinate Or length");
        }
        Map map = Game.getInstance().getMap();
        int ey = sy + len - 1;
        int ex = sx + len - 1;
        if (!map.isCoordinateValid(sy, sy) || !map.isCoordinateValid(ey, ey) || len <= 0) {
            return new Result<>(false, "Can't print that, It's wrong!");
        }
        Tile[][] tiles = makeMap();
        StringBuilder res = new StringBuilder();
        for (int i = sy; i <= ey; i++) {
            StringBuilder temp = new StringBuilder();
            for (int j = sx; j <= ex; j++) {
                temp.append(tiles[i][j].toString(true));
            }
            temp.append("\n");
            res.append(temp);
        }
        return new Result<>(true, res.toString());
    }
    public Result<String> printFarm() {
        Game game = Game.getInstance();
        if (game == null) {
            return new Result<>(false, "there is no running game");
        }
        Player player = Game.getInstance().getCurrentPlayer();
        int len = Math.max(player.getFarm().getHeight(), player.getFarm().getWidth());
        int y = player.getFarm().getCoordinateBaseMap().getY();
        int x = player.getFarm().getCoordinateBaseMap().getX();
        return printMap(String.valueOf(y), String.valueOf(x), String.valueOf(len));
    }
    public Result<String> helpReadingMap() {
        StringBuilder res = new StringBuilder();
        res.append(Colors.RED.toString() + "------------------------------------" + Colors.RESET + "\n");

        for (TileType value : TileType.values()) {
            res.append(value.toString() + ": " + value.getTile() + Colors.RESET + "\n");
        }

        res.append(Colors.BLACK.toString() + "------------------------------------" + Colors.RESET + "\n");

        for (ForagingMineralTypes value : ForagingMineralTypes.values()) {
            res.append(value.toString() + ": " + value.getTile() + Colors.RESET + "\n");
        }

        res.append(Colors.BLACK.toString() + "------------------------------------" + Colors.RESET + "\n");

        for (CropsTypes value : CropsTypes.values()) {
            res.append("Crop " + value.toString() + ": " + value.getShapeAtStages() + Colors.RESET + "\n");
        }

        res.append(Colors.BLACK.toString() + "------------------------------------" + Colors.RESET + "\n");

        for (TreeTypes value : TreeTypes.values()) {
            res.append("Tree " + value.toString() + ": " + value.getShapeAtStages() + Colors.RESET + "\n");
        }

        res.append(Colors.BLACK.toString() + "------------------------------------" + Colors.RESET + "\n");

        for (StoneTypes value : StoneTypes.values()) {
            res.append(value.toString() + ": " + value.getTile() + Colors.RESET + "\n");
        }

        res.append(Colors.BLACK.toString() + "------------------------------------" + Colors.RESET + "\n");

        for (ProductNames value : ProductNames.values()) {
            res.append(value.toString() + ": " + value.getTile() + Colors.RESET + "\n");
        }

        res.append(Colors.RED.toString() + "------------------------------------" + Colors.RESET + "\n");
        return new Result<>(true, res.toString());
    }

    public Result<String> showEnergy() {
        return new Result<>(true, "Energy: " + Game.getInstance().getCurrentPlayer().getEnergy().getAmountBaseUnit());
    }

    public Result<String> cheatSetEnergy(String energyString) {
        int energy = Integer.parseInt(energyString);
        energy *= EnergyManager.unit;
        int dif = energy - Game.getInstance().getCurrentPlayer().getEnergy().getAmount();
        Game.getInstance().getCurrentPlayer().getEnergy().advanceBaseInt(dif);
        return new Result<>(true, "energy has been set to " + Game.getInstance().getCurrentPlayer().getEnergy().getAmountBaseUnit());
    }
    public Result<String> cheatSetEnergy(int energy) {
        int dif = energy - Game.getInstance().getCurrentPlayer().getEnergy().getAmount();
        Game.getInstance().getCurrentPlayer().getEnergy().advanceBaseInt(dif);
        return new Result<>(true, "energy has been set to " + Game.getInstance().getCurrentPlayer().getEnergy().getAmountBaseUnit());
    }

    public Result<String> cheatSetEnergyUnlimited() {
        Game.getInstance().getCurrentPlayer().getEnergy().setMaxAmount(Integer.MAX_VALUE / 2);
        if (Game.getInstance().getCurrentPlayer().getEnergy().getAmount() == 0) Game.getInstance().getCurrentPlayer().feint();
        return cheatSetEnergy(Integer.MAX_VALUE / 2);
    }

    public Result<String> showInventory() {
        return new Result<>(true, Game.getInstance().getCurrentPlayer().getInventoryManager().showStorage());
    }

    public Result<String> inventoryTrash(String itemName, String amountSt) {
        ItemStack itemStack = Game.getInstance().getCurrentPlayer().getInventoryManager().getItemByName(itemName);
        if(itemStack == null) {
            return new Result<>(false, "Item doesn't exists!!");
        }
        int amount;
        if(amountSt == null)
            amount = itemStack.getAmount();
        else
            amount = Integer.parseInt(amountSt);
        if(amount > itemStack.getAmount())
            return new Result<>(false, "We don't have this amount");
        TrashCan trashCan = Game.getInstance().getCurrentPlayer().getToolManager().getTrashCan();
        int money = trashCan.trash(new ItemStack(itemStack.getItem(), amount));
        Game.getInstance().getCurrentPlayer().setGold(money + Game.getInstance().getCurrentPlayer().getGold());
        Game.getInstance().getCurrentPlayer().getInventoryManager().removeItem(itemStack.getItem(), amount);
        return new Result<>(true, "Ok ok trashcan produces " + money + "$ for you");
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
        if(!((Store)ground).getStoreTypes().equals(StoreTypes.Blacksmith))
            return new Result<>(false, "You are not in BlackSmith Shop");
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
        if (plant instanceof Crop crop) return new Result<>(true, plant.showPlant() + crop.showCrop());
        else if (plant instanceof Tree tree) return new Result<>(true, plant.showPlant() + tree.showTree());
        else return new Result<>(true, plant.showPlant());
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
//        if(!(Game.getInstance().getCurrentPlayer().getLocation().getGround() instanceof Cottage))
//            return new Result<>(false, "You should be in Cottage for crafting..");
        if(recipe == null)
            return new Result<>(false, "You don't have it's recipe.");
        if(!Game.getInstance().getCurrentPlayer().getInventoryManager().canAdd())
            return new Result<>(false, "Inventory is full.");
        if(!Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(recipe))
            return new Result<>(false, "You don't have enough ingredients.");

        if(!Game.getInstance().getCurrentPlayer().getEnergy().hasEnergy(2)) {
            Game.getInstance().getCurrentPlayer().getFeintBuff().activate();
            return new Result<>(false, "You don't have enough energy");
        }

        Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(recipe.getResult().getItem(), 1);
        Game.getInstance().getCurrentPlayer().getEnergy().advanceBaseInt(-2 * EnergyManager.unit);
        return new Result<>(true, "item crafted successfully.");
    }
    public Result<String> placeItem(String itemName, String directionString) {
        int direction = Integer.parseInt(directionString);
        if(direction < 0 || direction > 7)
            return new Result<>(false, "invalid direction");
        ItemStack itemStack = Game.getInstance().getCurrentPlayer().getInventoryManager().getItemByName(itemName);
        if(itemStack == null)
            return new Result<>(false, "There is not item with the given name in inventory");
        if(!itemStack.getItem().isRemovableByPickaxe())
            return new Result<>(false, "You cannot put this item on the ground");
        if (itemStack.getItem() instanceof Animal) {
            return new Result<>(false, "You cannot put animal item on the ground");
        }

        Coordinate cord = Misc.getDiffFromDirection(direction);
        Item topItem = Game.getInstance().getCurrentPlayer().getLocation().getTopItemDiff(cord.getY(), cord.getX());
        if(topItem == null || topItem instanceof Dirt){
            Coordinate coordinateB = new Coordinate(cord.getY() + Game.getInstance().getCurrentPlayer().getLocation().getY(),
                    cord.getX() + Game.getInstance().getCurrentPlayer().getLocation().getX());
            Item item = itemStack.getItem();
            item.setCoordinate(coordinateB);
                Game.getInstance().getCurrentPlayer().getLocation().getGround().addItem(item);
            Game.getInstance().getCurrentPlayer().getLocation().getGround().addItem(item);
            if (!(item instanceof Machine)) Game.getInstance().getCurrentPlayer().getInventoryManager().removeItem(itemStack.getItem(), 1);
            StringBuilder resss = new StringBuilder();
            resss.append(itemStack.getItem().getName() + " placed successfully.");
            if (item instanceof Bomber bomber) {
                resss.append("\n...Booooom!");
                bomber.active();
            }
            return new Result<>(true, resss.toString());

        }else
            return new Result<>(false, "You can't place item in this place");
    }

    public Result<String> cheatAddItem(String itemName, String amountString) {
        int amount = Integer.parseInt(amountString);
        Item item = App.getInstance().getGameService().getItem(itemName);
        if(item == null)
            return new Result<>(false, "There is no item with this name.");
        if(!Game.getInstance().getCurrentPlayer().getInventoryManager().canAdd())
            return new Result<>(false, "Inventory is full.");
        int added = Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(item, amount);
        return new Result<>(true, item.getName() + " with amount " + added + " has been added to inventoryManger.");
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
        if(cropsType != null) {
            isFood = true;
            pickAmount = Refrigerator.getInstance().pickItem(new ItemStack(new Crop(1, 1, cropsType), amount));
        }
        if(pickAmount == 0)
            return new Result<>(false, "inventory is full");
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
        if(!Game.getInstance().getCurrentPlayer().getInventoryManager().canAdd())
            return new Result<>(false, "Inventory is full.");
        if(!Game.getInstance().getCurrentPlayer().getInventoryManager().CheckCanBuild(recipe))
                return new Result<>(false, "You don't have enough ingredients in your inventory. you can add it from refrigerator");
        if(!Game.getInstance().getCurrentPlayer().getEnergy().hasEnergy(3)) {
            Game.getInstance().getCurrentPlayer().getFeintBuff().activate();
            return new Result<>(false, "You don't have enough energy.");
        }
        Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(recipe.getResult().getItem(), recipe.getResult().getAmount());
        Game.getInstance().getCurrentPlayer().getEnergy().advanceBaseInt(-3 * EnergyManager.unit);
        return new Result<>(true, recipe.getResult().getItem().getName() + " created and added to Inventory.");
    }
    public Result<String> eatFood(String foodName) {
        FoodTypes foodType = null;
        if(FoodTypes.getType(foodName) != null)
            foodType = FoodTypes.getType(foodName);
        Food food = Game.getInstance().getCurrentPlayer().getInventoryManager().getFood(foodType);
        if(food == null)
            return new Result<>(false, "There is not any food with this name.");
        Game.getInstance().getCurrentPlayer().getInventoryManager().removeItem(food, 1);
        Game.getInstance().getCurrentPlayer().getEnergy().advanceBaseInt(food.getEatable().getEnergy() * EnergyManager.unit);
        Game.getInstance().getCurrentPlayer().setFoodBuff(food.getFoodType().getFoddBuff());
        Game.getInstance().getCurrentPlayer().getLastFoodBuff().activate();
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

        Pair<StoreItemProducer> toBuy = null;
        for(Object p : store.getAvailableProducts()){
            Pair<StoreItemProducer> product = (Pair<StoreItemProducer>) p;
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

    public Result<String> CheatAddFriendship(String player1String, String player2String, String amountString) {
        Player player1 = Game.getInstance().getPlayerByUserName(player1String);
        Player player2 = Game.getInstance().getPlayerByUserName(player2String);
        if(player1 == null || player2 == null)
            return new Result<>(false, "username invalid");
        int amount = Integer.parseInt(amountString);
        Friendship friendship = Friendship.getFriendShip(player1, player2);
        Friendship.AddXp(player1, player2, amount);
        return new Result<>(true, "friendship set to Level: " + friendship.getLevel() + " Xp: " + friendship.getXp());
    }
    public Result<String> shepherdAnimal(String animalName, String xString, String yString) {
        Animal animal = Game.getInstance().getCurrentPlayer().getAnimalByName(animalName);
        if(animal == null)
            return new Result<>(false, "animal name invalid.");
        if(!Game.getInstance().getWeatherManager().getCurrentWeather().equals(Weather.Sunny))
            return new Result<>(false, "Weather must be Sunny.");
        Integer x;
        Integer y;
        try {
            x = Integer.parseInt(xString);
        } catch (Exception e) {
            return new Result<>(false, "x invalid");
        }
        try {
            y = Integer.parseInt(yString);
        } catch (Exception e) {
            return new Result<>(false, "y invalid");
        }
        Location location = App.getInstance().getMapService().getLocationOnMap(x, y);
        Item item = location.getGround().getTopItem(location.getY(), location.getX());
        if(item == null)
            return new Result<>(false, "location invalid. doesn't exist.");
        if(location.getGround() instanceof AnimalHouse) {
            animal.getFather().removeItem(animal);
            return new Result<>(true, "animal sent to its house.");
        }
        if(!item.getName().equals("Grass"))
            return new Result<>(false, "this location doesn't have Grass to feed.");
        animal.setFeeded(true);
        animal.setInHome(false);
        if(item.getFather() instanceof Dirt) {
           Dirt dirt =  (Dirt)item.getFather();
           dirt.removeItem(item);
           animal.setCoordinate(new Coordinate(0, 0));
           dirt.addItem(animal);
        }
        return new Result<>(true, "Animal shpherd successfully");
    }
    public Result<String> cheatSetFriendshipAnimal(String userName, String amountString) {
        Integer amount;
        try  {
            amount = Integer.parseInt(amountString);
        } catch (Exception e) {
            return new Result<>(false, "amount is invalid.");
        }
        Animal animal = Game.getInstance().getCurrentPlayer().getAnimalByName(userName);
        if(animal  == null)
            return new Result<>(false, "There is no animal with this name.");
        animal.setFriendShipAmount(amount);
        return new Result<>(true, animal.getName() + " friendship set to " + amount);
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
        animal.addFriendShip(20);
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
    public Result<String> collectProduce(String name) {
        Tool tool = Game.getInstance().getCurrentPlayer().getToolManager().getCurrentTool();
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
            Game.getInstance().getCurrentPlayer().getAbility(AbilityType.Farming).advanceXP(5);
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
        ItemStack it = Game.getInstance().getCurrentPlayer().getInventoryManager().getItemByName(fishingPole);
        if(it == null)
            return new Result<>(false, "You don't have this fishing pole");
        FishingPole pole = (FishingPole) it.getItem();
        boolean seaIsNear = false;
        for(int d = 0; d < 8; d++){
            Coordinate coord = Misc.getDiffFromDirection(d);
            Item nearItem = Game.getInstance().getCurrentPlayer().getLocation().getTopItemDiff(coord.getY(), coord.getX());
            if((nearItem instanceof Water) && ((Water)nearItem).isHasFish()){
                seaIsNear = true;
            }
        }
        if(!seaIsNear)
            return new Result<>(false, "You are not close to the lake");
        if(!Game.getInstance().getCurrentPlayer().getEnergy().hasEnergy(pole.getEnergyNeed()))
            return new Result<>(false, "You don't have enough energy");

        Game.getInstance().getCurrentPlayer().getEnergy().advanceBaseUnit(-pole.getEnergyNeed());

        double R = (double)App.getInstance().getRandomNumber(1, 100) / 100.0;
        double M = switch (Game.getInstance().getWeatherManager().getCurrentWeather()){
            case Sunny -> 1.5;
            case Rain -> 1.2;
            case Storm -> 0.5;
            default -> 1;
        };
        double skill = Game.getInstance().getCurrentPlayer().getAbilityLevel(AbilityType.Fishing);
        double poleCoef = pole.getCoef();

        int fishCount = Math.min((int) Math.ceil(R * M * (skill + 2)), 6);


        ArrayList<FishTypes> possibleFishTypes = new ArrayList<>();
        for(FishTypes fishType : FishTypes.values()){
            if(!Game.getInstance().getDate().getSeason().equals(fishType.getSeason()))
                continue;
            if(fishType.isFishingAbilityMustBeMax() && !Game.getInstance().getCurrentPlayer().isAbilityMax(AbilityType.Fishing))
                continue;
            possibleFishTypes.add(fishType);
        }

        StringBuilder response = new StringBuilder();

        Game.getInstance().getCurrentPlayer().getAbility(AbilityType.Fishing).advanceXP(5);
        response.append("5 fishing xp gained\n");
        for(int i = 0; i < fishCount; i++){
            Fish fish = new Fish(1, 1, possibleFishTypes.get(new Random().nextInt(possibleFishTypes.size())) );
            R = (double)App.getInstance().getRandomNumber(1, 100) / 100.0;
            double qualityCoef = (R * (skill + 2) * poleCoef) / (7.0 - M);
            if(qualityCoef < 0.5)
                fish.getLevels().setCurrentLevel(0);
            else if(qualityCoef < 0.7)
                fish.getLevels().setCurrentLevel(1);
            else if(qualityCoef < 0.9)
                fish.getLevels().setCurrentLevel(2);
            else
                fish.getLevels().setCurrentLevel(3);
            Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(fish, 1);
            response.append(fish.getName() + " caught! " + fish.getLevels().getCurrentLevelName() + "\n");
        }

        return new Result<>(true, response.toString());
    }
    public Result<String> useArtisan(String artisanName, String itemName, String ingredient) {
        if(!CraftedProducer.checkNear(artisanName))
            return new Result<>(false, "You are not near a " + artisanName + " machine.");
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
        return null; //TODO : proper error
    }
    public Result<String> getArtisan(String artisanName, String machineName) {
        if(CraftingTypes.getType(machineName) == null)
            return new Result<>(false, "There is no machine with this name.");
        if(!CraftedProducer.checkNear(machineName))
            return new Result<>(false, "You are not near a " + machineName + " machine.");
        if(Game.getInstance().getCurrentPlayer().getArtisanItems().isEmpty())
            return new Result<>(false, "There is no item to collect.");
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
            Pair<StoreItemProducer> product = (Pair<StoreItemProducer>) p;

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
            Pair<StoreItemProducer> product = (Pair<StoreItemProducer>) p;
            if(product.getKey().getItem() == null)
                continue;
            if(product.getValue() == 0)
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
                builder.append(item.getItem().getName());
                if(item.getItem() instanceof Backpack)
                    builder.append("With the size of " + ((Backpack)item.getItem()).getSize());
                builder.append("\n");
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
            Pair<StoreItemProducer> product = (Pair<StoreItemProducer>) p;
            if(product.getKey().getNameInStore().equalsIgnoreCase(productName)) {
                productExists = true;
            }
        }

        if(!productExists)
            return new Result<>(false, "Product does not exist!");


        Pair<StoreItemProducer> toBuy = null;
        for(Object p : store.getAvailableProducts()){
            Pair<StoreItemProducer> product = (Pair<StoreItemProducer>) p;
            if(product.getKey().getNameInStore().equalsIgnoreCase(productName)) {
                toBuy = product;
            }
        }

        if(toBuy.getValue() < amount || amount == 0)
            return new Result<>(false, "This item is not available in the Store with this amount!!");
        int totalMoney = amount * toBuy.getKey().getItem().getSellPrice();
        if(totalMoney > Game.getInstance().getCurrentPlayer().getGold())
            return new Result<>(false, "You don't have enough fucking money.");

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
                if(toBuy.getKey().getItem() instanceof Backpack){
                    int expected = (((Backpack)toBuy.getKey().getItem()).getLevelProcess().getCurrentLevel());
                    if(expected == Game.getInstance().getCurrentPlayer().getToolManager().getBackpack().getLevelProcess().getCurrentLevel() + 1)
                        Game.getInstance().getCurrentPlayer().getToolManager().getBackpack().upgrade(-1);
                    else
                        return new Result<>(false, "You can't buy this backpack!");
                }else
                    Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(new ItemStack(toBuy.getKey().getItem(), amount));
            } case JojaMart -> {
                Game.getInstance().getCurrentPlayer().getInventoryManager().addItem(new ItemStack(toBuy.getKey().getItem(), amount));
            }
        }


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
            stringBuilder.append(" Level Of friendShip: " + friendship.getLevel() + " Xp: " + friendship.getXp() + "\n");
        }
        return new Result<>(true, stringBuilder.toString());
    }
    public Result<String> talk(String userName, String message) {
        Friendship friendship = Friendship.getFriendShip(Game.getInstance().getCurrentPlayer(), Game.getInstance().getPlayerByUserName(userName));
        if(friendship == null)
            return new Result<>(false, "username invalid.");
        friendship.addTalk(Game.getInstance().getCurrentPlayer(), friendship, message);
        if(friendship.getMarried())
            friendship.friendShipAddXp(50);
        return new Result<>(true, "Your message have been sent.");
    }
    public Result<String> showTalkHistory(String userName) {
        Friendship friendship = Friendship.getFriendShip(Game.getInstance().getCurrentPlayer(),
                Game.getInstance().getPlayerByUserName(userName));
        if(friendship == null)
            return new Result<>(false, "There is not a player with this userName.");
        ArrayList<Talk> talks = friendship.getTalk();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Talk History: \n");
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
                Game.getInstance().getPlayerByUserName(username));
        if(friendship == null)
            return new Result<>(false, "There is no Player with this userName.");
        if(friendship.getLevel() < 1)
            return new Result<>(false, "You should reach at leat friendship level 1 with " + username);
        Item item = App.getInstance().getGameService().getItem(itemName);
        if(item == null)
            return new Result<>(false, "There is no item with this name.");
        if(!Game.getInstance().isNear(Game.getInstance().getCurrentPlayer(), Game.getInstance().getPlayerByUserName(username)))
            return new Result<>(false, "You must be near " + username);
        if(Game.getInstance().getCurrentPlayer().getInventoryManager().getItem(item).getAmount() < amount)
            return new Result<>(false, "You don't have enough amount of " + itemName);
        friendship.GiftPlayer(Game.getInstance().getCurrentPlayer(), Game.getInstance().getPlayerByUserName(username), new ItemStack(item, amount));
        if(friendship.getMarried())
            friendship.friendShipAddXp(50);
        if(itemName.equals("Bouquet")) {

        }
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
            return new Result<>(false, "There is no gift with this id.");
        if(gift.getRate() != null)
            return new Result<>(false, "You have rate this gift before.");
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
        if(friendship.getPlayer2().equals(Game.getInstance().getCurrentPlayer())) {
            receivedGifts = friendship.getPlayer2Gift();
            sentGifts = friendship.getPlayer1Gift();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Received Gifts: \n");
        for(Gift gift : receivedGifts) {
            stringBuilder.append(gift.printGift() + "\n");
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
        Friendship friendship = Friendship.getFriendShip(Game.getInstance().getCurrentPlayer(),
                Game.getInstance().getPlayerByUserName(username));
        if(friendship.getLevel() < 2)
            return new Result<>(false, "You should reach at least friendship level 2 with " + username);
        if(!Game.getInstance().isNear(Game.getInstance().getCurrentPlayer(),
                Game.getInstance().getPlayerByUserName(username)))
            return new Result<>(false, "You must be near " + username);
        friendship.hug();
        if(friendship.getMarried())
            friendship.friendShipAddXp(50);
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
        if(friendship.getLevel() < 2)
            return new Result<>(false, "You should reach at least friendship level 2 with " + username);
        if(friendship.getXp() < (friendship.getLevel() + 1) * 100)
            return new Result<>(false, "xp must be " + (friendship.getLevel() + 1) * 100);
        ItemStack itemStack = Game.getInstance().getCurrentPlayer().getInventoryManager().getItem(new Product(ProductNames.Bouquet));
        if(itemStack.getAmount() == 0)
            return new Result<>(false, "You don't have Bouquet in your inventoryManger.");
        Game.getInstance().getCurrentPlayer().getInventoryManager().removeItem(new Product(ProductNames.Bouquet), 1);
        Game.getInstance().getPlayerByUserName(username).getInventoryManager().addItem(new Product(ProductNames.Bouquet), 1);
        friendship.giveFlower();
        if(friendship.getMarried())
            friendship.friendShipAddXp(50);
        return new Result<>(true, "flower sent.");
    }
    public Result<String> askMarriage(String username, String ringName) {
        if(Game.getInstance().getCurrentPlayer().getUser().getGender().equals(Gender.Female))
            return new Result<>(false, "only male can ask marriage.");
        Player partner = Game.getInstance().getPlayerByUserName(username);
        if(partner == null)
            return new Result<>(false, "There is no player with this userName.");
        if(partner.getUser().getGender().equals(Game.getInstance().getCurrentPlayer().getUser().getGender()))
            return new Result<>(false, "The player Gender is the Same! Shame on you!");
       ItemStack itemStack = Game.getInstance().getCurrentPlayer().getInventoryManager().getItem(new Product(ProductNames.WeddingRing));
        Friendship friendship = Friendship.getFriendShip(Game.getInstance().getCurrentPlayer(), partner);
        if(friendship.getLevel() < 3)
            return new Result<>(false, "You must have at least friendShip with level 3.");
        if(friendship.getXp() < (friendship.getLevel() + 1) * 100)
            return new Result<>(false, "xp must be " + (friendship.getLevel() + 1) * 100);
        if(itemStack.getAmount() == 0)
            return new Result<>(false, "You don't have WeddingRing.");
        friendship.askMarriage(Game.getInstance().getCurrentPlayer(), partner, new Product(ProductNames.WeddingRing));
        return new Result<>(true, "Your request have been sent.");
    }
    public Result<String> respondToMarriageRequest(String username, String answer) {
        Friendship friendship = Friendship.getFriendShip(Game.getInstance().getCurrentPlayer(), Game.getInstance().getPlayerByUserName(username));
        if(friendship == null)
            return new Result<>(false, "userName is not valid.");
        if(friendship.getMarriageRequest() == null)
            return new Result<>(false, "You don't have any marriage request from " + username);
        if(!answer.equals("accept") && !answer.equals("reject"))
            return new Result<>(false, "answer invalid");
        if(answer.equals("accept"))
            friendship.respondMarriage(Game.getInstance().getPlayerByUserName(username), Game.getInstance().getCurrentPlayer(), true);
        else
            friendship.respondMarriage(Game.getInstance().getPlayerByUserName(username), Game.getInstance().getCurrentPlayer(), false);
        System.out.println(Game.getInstance().getCurrentPlayer().getPartner().getUser().getUsername());
        return new Result<>(true, "Your answer have been sent.");
    }
    public Result<String> startTrade() {
        return null;
    }

    //NPC:
    public Result<String> showNPCList() {
        StringBuilder res = new StringBuilder();
        for (NPCTypes value : NPCTypes.values()) {
            res.append("Name: " + value.getNpcName() + "\n");
            res.append("Job: " + value.getJob() + "\n");
            res.append("WorkPlace: " + value.getWorkPlace() + "\n");
//            else res.append("Home: " + value.getHouse() + "\n");
            res.append("Favorites: " + value.getFavorites() + "\n");
            res.append("Possible gifts: " + value.getPossibleGiftsToSend() + "\n");
            res.append("All Quests: " + value.getQuests() + "\n");
            res.append(Colors.RED + "-----------------------------" + Colors.RESET + "\n");
        }
        return new Result<>(true, res.toString());
    }
    public Result<String> meetNPC(String npcName) {
        NPCTypes type = NPCTypes.findByName(npcName);
        if (type == null) {
            return new Result<>(false, "There is no NPC with this name.");
        }
        NPC npc = Game.getInstance().getNPC(type);
        Coordinate npcCoord = npc.getCoordinateBaseMap();
        Location npcLoc = App.getInstance().getMapService().getLocationOnMap(npcCoord.getY(), npcCoord.getX());
        Location pLoc = Game.getInstance().getCurrentPlayer().getLocation();
        Coordinate pCoord = pLoc.getGround().getTileCoordinateBaseMap(pLoc.getY(), pLoc.getX());
        if (!(Math.abs(npcCoord.getY() - pCoord.getY()) <= 1 &&  Math.abs(npcCoord.getX() - pCoord.getX()) <= 1)) {
            return new Result<>(false, "You are so far.");
        }
        return npc.getType().getDialogueTreeNode().runDialogue(npc.getState(Game.getInstance().getCurrentPlayer()));
    }
    public Result<String> giftNPC(String npcName, String ItemName) {
        NPC npc = Game.getInstance().getNPC(NPCTypes.findByName(npcName));
        Item item = App.getInstance().getGameService().getItem(ItemName.replaceAll("\\s+", ""));
        if (npc == null) {
            return new Result<>(false, "There is no NPC with this name.");
        }
        if (item == null || item instanceof Tool) {
            return new Result<>(false, "Item not found.");
        }
        State state = npc.getState(Game.getInstance().getCurrentPlayer());
        boolean notSameDay = true;
        if (state.getLastGiftReceived() != null) {
            notSameDay = (state.getCurrentDate().getRawDay() - state.getLastGiftReceived().getRawDay()) > 0;
        }
        boolean isFav = false;
        for (Item favorite : npc.getType().getFavorites()) {
            if (favorite.canStackWith(item)) {
                isFav = true;
                break;
            }
        }
        int xpGained = 0;
        if (notSameDay) {
            if (isFav) state.advanceFriendshipXp(xpGained = 200);
            else state.advanceFriendshipXp(xpGained = 50);
        }
        state.setLastGiftReceived(Game.getInstance().getDate());
        state.getGiftReceived().addItem(item, 1);
        state.getPlayer().getInventoryManager().removeItem(item, 1);
        StringBuilder res = new StringBuilder();
        res.append(npc.getType().getNpcName() + "recieved your gift.\n");
        if(isFav) res.append("It was his favorite!\n");
        res.append("You gained " + xpGained + " friendship xp with " + npc.getType().getNpcName() + "\n");
        return new Result<>(true, res.toString());
    }
    public Result<String> showFriendshipNPCList() {
        StringBuilder res = new StringBuilder();
        for (NPC npc : Game.getInstance().getNpcs()) {
            State state = npc.getState(Game.getInstance().getCurrentPlayer());
            res.append(npc.getType().getNpcName() + ": level = " + state.getFriendshipLevel() + " xp = " + state.getFriendshipXp() + "\n");
            res.append(Colors.RED.toString() + "--------------------------------------------" + Colors.RESET + "\n");
        }
        return new Result<>(true, res.toString());
    }
    public Result<String> showQuestList () {
        StringBuilder res = new StringBuilder();
        res.append(Colors.YELLOW + "Active quests:" + Colors.RESET + "\n");
        ArrayList<Quests> activeQuests = Game.getInstance().getCurrentPlayer().getActiveQuests();
        for (int i = 0; i < activeQuests.size(); i++) {
            Quests quest = activeQuests.get(i);
            res.append("Index: " + i + "\n");
            res.append(quest.details());
            res.append(Colors.RED + "------------------------------------------------" + Colors.RESET + "\n");
        }
        res.append((Colors.GREEN + "Completed quests:" + Colors.RESET + "\n"));
        for (Quests quest : Game.getInstance().getCurrentPlayer().getDoneQuests()) {
            res.append(quest.details());
            res.append(Colors.RED + "------------------------------------------------" + Colors.RESET + "\n");
        }
        return new Result<>(true, res.toString());
    }
    public Result<String> showAllQuests() {
        StringBuilder res = new StringBuilder();
        for (Quests value : Quests.values()) {
            res.append(value.details());
            res.append(Colors.RED + "------------------------------------------------" + Colors.RESET + "\n");
        }
        return new Result<>(true, res.toString());
    }
    public Result<String> finishQuest(String questIndex) {
        int ind;
        try {
            ind = Integer.parseInt(questIndex);
        } catch (Exception e) {
            return new Result<>(false, "Invalid quest index.");
        }
        Player player = Game.getInstance().getCurrentPlayer();
        if (ind < 0 || ind >= player.getActiveQuests().size()) {
            return new Result<>(false, "Quest not found.");
        }
        Quests quest = player.getActiveQuests().get(ind);
        NPC npc = Game.getInstance().getNPC(NPCTypes.findByName(quest.getOwner()));
        if (!quest.reqCheck(npc.getState(player))) {
            return new Result<>(false, "You don't have required items.");
        }
        quest.doQuest(npc.getState(player));
        quest.addRewards(npc.getState(player));
        return new Result<>(true, "Quest completed, your rewards have been added.");
    }
    public Result<String> cheatAddNPCFriendshipXP(String npcName, String xpAmount) {
        NPC npc = Game.getInstance().getNPC(NPCTypes.findByName(npcName));
        if (npc == null) {
            return new Result<>(false, "NPC not found.");
        }
        int xp;
        try {
            xp = Integer.parseInt(xpAmount);
        } catch (Exception e) {
            return new Result<>(false, "Invalid xp amount.");
        }
        npc.getState(Game.getInstance().getCurrentPlayer()).advanceFriendshipXp(xp);
        return new Result<>(true, "NPC friendship xp has been added.");
    }

    public Result<String> showAbility(String name) {
        Ability ability = Game.getInstance().getCurrentPlayer().getAbility(AbilityType.valueOf(name));
        StringBuilder builder = new StringBuilder();
        builder.append(ability.getAbilityType().toString() + " level " + ability.getLevel() + "\n");
        builder.append(ability.getXp() + "\n");

        return new Result<>(true, builder.toString());
    }

    public Result<String> cheatAbility(String name){
        Game.getInstance().getCurrentPlayer().getAbility(AbilityType.valueOf(name)).advanceLevel();
        return showAbility(name);
    }
}
