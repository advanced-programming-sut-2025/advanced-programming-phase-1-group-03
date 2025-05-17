package phi.ap.view.menus.MainMenus;

import phi.ap.Controller.MenuControllers.MainMenuControllers.GameMenuController;
import phi.ap.model.App;
import phi.ap.model.Game;
import phi.ap.model.Result;
import phi.ap.model.Tile;
import phi.ap.model.enums.Menus.Menu;
import phi.ap.model.enums.StoreTypes;
import phi.ap.model.enums.TileType;
import phi.ap.model.enums.commands.GameMenuCommands;
import phi.ap.view.AppMenu;
import phi.ap.view.AppView;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class GameMenu extends AppMenu {
    GameMenuController controller = new GameMenuController();
    @Override
    public void check(String input) {
        Matcher matcher;
        if (Game.getInstance() == null) {
            if ((matcher = GameMenuCommands.NewGame.getMatcher(input)) != null) {
                String users = matcher.group("usernames");
                ArrayList<String> usernames = new ArrayList<>();
                String[] words = users.split("\\s+");
                for (String word : words) {
                    if (word.isEmpty() || word.matches("\\s+")) continue;
                    usernames.add(word);
                }
                Result<String> result = controller.newGame(usernames);
                System.out.println(result);
                if (result.success) App.getInstance().changeMenu(Menu.ChoosingMapMenu);
            } else {
                super.check(input);
            }
            return;
        }
        if ((matcher = GameMenuCommands.PrintMapComplete.getMatcher(input)) != null) {
            System.out.println(controller.showMap());
        } else if ((matcher = GameMenuCommands.PrintMap.getMatcher(input)) != null) {
            System.out.println(controller.printMap(matcher.group("y"), matcher.group("x"), matcher.group("size")));
        } else if ((matcher = GameMenuCommands.PrintFarm.getMatcher(input)) != null) {
            System.out.println(controller.printFarm());
        }else if((matcher = GameMenuCommands.Walk.getMatcher(input)) != null) {
            System.out.println(controller.walk(matcher.group("y"), matcher.group("x")));
        } else if((matcher = GameMenuCommands.WalkDiff.getMatcher(input)) != null) {
            System.out.println(controller.walkDiff(matcher.group("y"), matcher.group("x")));
        } else if((matcher = GameMenuCommands.NextTurn.getMatcher(input)) != null){
            System.out.println(controller.nextTurn());
        } else if((matcher = GameMenuCommands.Time.getMatcher(input)) != null){
            System.out.println(controller.showTime());
        } else if((matcher = GameMenuCommands.Date.getMatcher(input)) != null){
            System.out.println(controller.showDate());
        } else if((matcher = GameMenuCommands.Season.getMatcher(input)) != null){
            System.out.println(controller.showSeason());
        } else if((matcher = GameMenuCommands.DateTime.getMatcher(input)) != null){
            System.out.println(controller.showDateTime());
        } else if((matcher = GameMenuCommands.DayOfWeek.getMatcher(input)) != null){
            System.out.println(controller.showWeekDay());
        } else if((matcher = GameMenuCommands.CheatAdvanceTime.getMatcher(input)) != null){
            String hour = matcher.group("hour");
            System.out.println(controller.cheatAdvanceTime(hour));
        } else if((matcher = GameMenuCommands.CheatAdvanceDate.getMatcher(input)) != null){
            String day = matcher.group("day");
            System.out.println(controller.cheatAdvanceDate(day));
        } else if((matcher = GameMenuCommands.showAllProducts.getMatcher(input)) != null){
            System.out.println(controller.showAllProducts());
        } else if((matcher = GameMenuCommands.showAvailableProducts.getMatcher(input)) != null) {
            System.out.println(controller.showAvailableProducts());
        } else if((matcher = GameMenuCommands.purchase.getMatcher(input)) != null) {
            System.out.println(controller.purchase(matcher.group("productName").trim(), matcher.group("amount")));
        } else if((matcher = GameMenuCommands.BuildAnimalBarn.getMatcher(input)) != null) {
            System.out.println(controller.buildBuilding(matcher.group("name"), matcher.group("x"), matcher.group("y")));
        } else if((matcher = GameMenuCommands.BuyAnimal.getMatcher(input)) != null) {
            System.out.println(controller.buyAnimal(matcher.group("animalName"), matcher.group("name")));
        }else if((matcher = GameMenuCommands.ShowCurrentTool.getMatcher(input)) != null) {
            System.out.println(controller.showCurrentTool());
        }else if((matcher = GameMenuCommands.UpgradeTool.getMatcher(input)) != null) {
            System.out.println(controller.upgradeTool(matcher.group("toolName")));
        }else if((matcher = GameMenuCommands.EquipTool.getMatcher(input)) != null) {
            System.out.println(controller.toolEquip(matcher.group("toolName")));
        }else if((matcher = GameMenuCommands.ShowAvailableTools.getMatcher(input)) != null) {
            System.out.println(controller.showAllAvailableTools());
        } else if((matcher = GameMenuCommands.UseTool.getMatcher(input)) != null) {
            System.out.println(controller.useTool(matcher.group("direction")));
        } else if((matcher = GameMenuCommands.test.getMatcher(input)) != null) {
            System.out.println(controller.test(matcher.group(1)));
        } else if((matcher = GameMenuCommands.test1.getMatcher(input)) != null) {
            System.out.println(controller.test1(matcher.group(1)));
        } else if ((matcher = GameMenuCommands.WalkOne.getMatcher(input)) != null) {
            System.out.println(controller.walkOne(matcher.group("y"), matcher.group("x")));
        } else if ((matcher = GameMenuCommands.Weather.getMatcher(input)) != null) {
            System.out.println(controller.showWeather());
        } else if ((matcher = GameMenuCommands.WeatherForecast.getMatcher(input)) != null) {
            System.out.println(controller.showWeatherForecast());
        } else if ((matcher = GameMenuCommands.CheatWeatherSet.getMatcher(input)) != null) {
            System.out.println(controller.cheatSetTomorrowWeather(matcher.group("weatherName")));
        } else if ((matcher = GameMenuCommands.PutRefrigerator.getMatcher(input)) != null) {
            System.out.println(controller.putItemToRefrigerator(matcher.group("name"), (matcher.group("amount"))));
        } else if ((matcher = GameMenuCommands.PickRefrigerator.getMatcher(input)) != null) {
            System.out.println(controller.pickItemFromRefrigerator(matcher.group("name"), (matcher.group("amount"))));
        } else if ((matcher = GameMenuCommands.ShowCookingRecipes.getMatcher(input)) != null) {
            System.out.println(controller.showCookingRecipe());
        } else if ((matcher = GameMenuCommands.PrepareCooking.getMatcher(input)) != null) {
            System.out.println(controller.prepareCooking(matcher.group("name")));
        } else if ((matcher = GameMenuCommands.EatFood.getMatcher(input)) != null) {
            System.out.println(controller.eatFood(matcher.group("name")));
        } else if ((matcher = GameMenuCommands.ShowCraftingRecipes.getMatcher(input)) != null) {
            System.out.println(controller.showCraftingRecipes());
        } else if ((matcher = GameMenuCommands.Craft.getMatcher(input)) != null) {
            System.out.println(controller.craftItem(matcher.group("name")));
        } else if ((matcher = GameMenuCommands.CraftInfo.getMatcher(input)) != null) {
            System.out.println(controller.craftInfo(matcher.group("name")));
        } else if ((matcher = GameMenuCommands.UseArtisan.getMatcher(input)) != null) {
            System.out.println(controller.useArtisan(matcher.group("name"),
                    matcher.group("itemName"), matcher.group("ingredientName")));
        } else if ((matcher = GameMenuCommands.GetArtisan.getMatcher(input)) != null) {
            System.out.println(controller.getArtisan(matcher.group("name")));
        } else if ((matcher = GameMenuCommands.CheatAddItem.getMatcher(input)) != null) {
            System.out.println(controller.cheatAddItem(matcher.group("name"), matcher.group("amount")));
        } else if ((matcher = GameMenuCommands.Pet.getMatcher(input)) != null) {
            System.out.println(controller.petAnimal(matcher.group("name")));
        }  else if ((matcher = GameMenuCommands.Animals.getMatcher(input)) != null) {
            System.out.println(controller.showAnimalsInfo());
        }  else if ((matcher = GameMenuCommands.FeedHay.getMatcher(input)) != null) {
            System.out.println(controller.feedAnimal(matcher.group("name")));
        }  else if ((matcher = GameMenuCommands.Produces.getMatcher(input)) != null) {
            System.out.println(controller.produces());
        }  else if ((matcher = GameMenuCommands.CollectProduce.getMatcher(input)) != null) {
            System.out.println(controller.collectProduce(matcher.group("name")));
        }  else if ((matcher = GameMenuCommands.SellAnimal.getMatcher(input)) != null) {
            System.out.println(controller.sellAnimal(matcher.group("name")));
        } else if ((matcher = GameMenuCommands.Plant.getMatcher(input)) != null) {
            System.out.println(controller.plant(matcher.group("source"), matcher.group("direction")));
        } else if ((matcher = GameMenuCommands.ShowPlant.getMatcher(input)) != null) {
            System.out.println(controller.showPlant(matcher.group("y"), matcher.group("x")));
        } else if ((matcher = GameMenuCommands.ShowPlantDiff.getMatcher(input)) != null) {
            System.out.println(controller.showPlantDiff(matcher.group("y"), matcher.group("x")));
        } else if ((matcher = GameMenuCommands.Fertilize.getMatcher(input)) != null) {
            System.out.println(controller.fertilizePlant(matcher.group("name"), matcher.group("direction")));
        } else if((matcher = GameMenuCommands.CheatAddDollar.getMatcher(input)) != null) {
            System.out.println(controller.cheatAddDollar(matcher.group("count")));
        } else if ((matcher = GameMenuCommands.GreenHouseBuild.getMatcher(input)) != null) {
            System.out.println(controller.greenHouseBuild());
        } else if ((matcher = GameMenuCommands.CheatThor.getMatcher(input)) != null) {
            System.out.println(controller.cheatThor(matcher.group("y"), matcher.group("x")));
        } else if ((matcher = GameMenuCommands.WalkWASD.getMatcher(input)) != null) {
            System.out.println(controller.walkWASD(matcher.group("direction")));
        } else if((matcher = GameMenuCommands.SellProduct.getMatcher(input)) != null) {
            System.out.println(controller.sellProduct(matcher.group("productName").trim(), matcher.group("amount")));
        }else if((matcher = GameMenuCommands.TrashItem.getMatcher(input)) != null) {
            System.out.println(controller.inventoryTrash(matcher.group("itemName").trim(), matcher.group("amount")));
        } else if ((matcher = GameMenuCommands.TalkHistory.getMatcher(input)) != null) {
            System.out.println(controller.showTalkHistory(matcher.group("username")));
        } else if ((matcher = GameMenuCommands.Friendships.getMatcher(input)) != null) {
            System.out.println(controller.showFriendShip());
        } else if ((matcher = GameMenuCommands.GiftRate.getMatcher(input)) != null) {
            System.out.println(controller.rateGift((matcher.group("giftNumber")), (matcher.group("rate"))));
        } else if ((matcher = GameMenuCommands.GiftHistory.getMatcher(input)) != null) {
            System.out.println(controller.giftHistory(matcher.group("username")));
        } else if ((matcher = GameMenuCommands.Flower.getMatcher(input)) != null) {
            System.out.println(controller.flower(matcher.group("username")));
        } else if ((matcher = GameMenuCommands.Hug.getMatcher(input)) != null) {
            System.out.println(controller.hug(matcher.group("username")));
        } else if ((matcher = GameMenuCommands.Talk.getMatcher(input)) != null) {
            System.out.println(controller.talk(matcher.group("username"), matcher.group("message")));
        } else if ((matcher = GameMenuCommands.Gift.getMatcher(input)) != null) {
            System.out.println(controller.sendGift(matcher.group("username"), matcher.group("item"), (matcher.group("amount"))));
        } else if ((matcher = GameMenuCommands.AskMarriage.getMatcher(input)) != null) {
            System.out.println(controller.askMarriage(matcher.group("username"), matcher.group("ring")));
        } else if ((matcher = GameMenuCommands.Respond.getMatcher(input)) != null) {
            System.out.println(controller.respondToMarriageRequest(matcher.group("username"), matcher.group("condition")));
        }  else if ((matcher = GameMenuCommands.GiftList.getMatcher(input)) != null) {
            System.out.println(controller.showListOfGifts());
        } else if((matcher = GameMenuCommands.MeetNPC.getMatcher(input)) != null) {
            System.out.println(controller.meetNPC(matcher.group("name")));
        } else if ((matcher = GameMenuCommands.ShowNPCList.getMatcher(input)) != null) {
            System.out.println(controller.showNPCList());
        } else if ((matcher = GameMenuCommands.GiftNPC.getMatcher(input)) != null) {
            System.out.println(controller.giftNPC(matcher.group("npcName"), matcher.group("item")));
        } else if ((matcher = GameMenuCommands.NPCFriendshipList.getMatcher(input)) != null) {
            System.out.println(controller.showFriendshipNPCList());
        } else if ((matcher = GameMenuCommands.QuestsList.getMatcher(input)) != null) {
            System.out.println(controller.showQuestList());
        } else if ((matcher = GameMenuCommands.QuestFinish.getMatcher(input)) != null) {
            System.out.println(controller.finishQuest(matcher.group("index")));
        } else if ((matcher = GameMenuCommands.AllQuestsList.getMatcher(input)) != null) {
            System.out.println(controller.showAllQuests());
        } else if ((matcher = GameMenuCommands.CheatAddNPCFriendshipXP.getMatcher(input)) != null) {
            System.out.println(controller.cheatAddNPCFriendshipXP(matcher.group("name"), matcher.group("count")));
        }else if((matcher = GameMenuCommands.Fishing.getMatcher(input)) != null) {
            System.out.println(controller.fishing(matcher.group("fishingPole").trim()));
        }else if((matcher = GameMenuCommands.PlaceItem.getMatcher(input)) != null) {
            System.out.println(controller.placeItem(
                    matcher.group("itemName").trim(), matcher.group("direction")));
        } else if((matcher = GameMenuCommands.CheatSetAnimalFriendShip.getMatcher(input)) != null) {
            System.out.println(controller.cheatSetFriendshipAnimal(matcher.group("name"), matcher.group("amount")));
        }
        else {
            super.check(input);
        }
    }

    @Override
    public void start() {
        System.out.println("Redirecting to game menu...");
    }
}
