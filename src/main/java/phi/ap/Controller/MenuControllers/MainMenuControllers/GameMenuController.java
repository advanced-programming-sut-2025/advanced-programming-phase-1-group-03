package phi.ap.Controller.MenuControllers.MainMenuControllers;

import phi.ap.model.*;
import phi.ap.model.enums.FarmTypes;
import phi.ap.model.enums.Menus.Menu;
import phi.ap.model.enums.TileType;
import phi.ap.model.items.Item;
import phi.ap.model.items.buildings.Farm;

import java.util.ArrayList;

public class GameMenuController {
    public Result<String> newGame(ArrayList<String> usernames) {
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
        return null;
    }

    public Result<String> showTime() {
        return null;
    }

    public Result<String> showDate() {
        return null;
    }

    public Result<String> showDateTime() {
        return null;
    }

    public Result<String> showWeekDay() {
        return null;
    }

    public Result<String> cheatAdvanceTime(String amount) {
        return null;
    }

    public Result<String> cheatAdvanceDate(String amount) {
        return null;
    }

    public Result<String> showSeason() {
        return null;
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

    public Result<String> walk(String xDest, String yDest) {
        return null;
    }

    public Result<String> showMap() {
        Game game = Game.getInstance();
        if (game == null) {
            return new Result<>(false, "there is no running game");
        }
        Map map = game.getMap();
        Tile[][] tiles = new Tile[map.getHeight()][map.getWidth()];
        map.show(0, 0, tiles);
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < map.getHeight(); i++) {
            StringBuilder temp = new StringBuilder();
            for (int j = 0; j < map.getWidth(); j++) {
                temp.append(tiles[i][j].toString(true));
//                System.out.print(tiles[i][j].toString(true));
            }
            temp.append("\n");
//            System.out.print("\n");
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
        return null;
    }

    public Result<String> showCurrentTool() {
        return null;
    }

    public Result<String> showAvailableTools() {
        return null;
    }

    public Result<String> upgradeTool(String toolName) {
        return null;
    }

    public Result<String> useTool(String direction) {
        return null;
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
        return null;
    }
    public Result<String> buyAnimal(String animalType, String animalName) {
        return null;
    }
    public Result<String> petAnimal(String name) {
        return null;
    }
    public Result<String> cheatSetFreindship(String animalName, String amount) {
        return null;
    }
    public Result<String> showAnimalsInfo() {
        return null;
    }
    public Result<String> shepherdAnimal(String animalName, String x, String y) {
        return null;
    }
    public Result<String> feedAnimal(String animalName) {
        return null;
    }
    public Result<String> produces() {
        return null;
    }
    public Result<String> collectProduce(String name) {
        return null;
    }
    public Result<String> sellAnimal(String name) {
        return null;
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
        return null;
    }
    public Result<String> showAllAvailableProducts() {
        return null;
    }
    public Result<String> purchase(String productName, String amount) {
        return null;
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
