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
        } else if((matcher = GameMenuCommands.Walk.getMatcher(input)) != null) {
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
        } else if((matcher = GameMenuCommands.Build.getMatcher(input)) != null) {
            System.out.println(controller.buildBuilding(matcher.group("name"), matcher.group("x"), matcher.group("y")));
        } else if((matcher = GameMenuCommands.BuyAnimal.getMatcher(input)) != null) {
            System.out.println(controller.buyAnimal(matcher.group("animalName"), matcher.group("name")));
        }else if((matcher = GameMenuCommands.ShowCurrentTool.getMatcher(input)) != null) {
            System.out.println(controller.showCurrentTool());
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
            System.out.println(controller.collectProduce(matcher.group("name"), Game.getInstance().getCurrentPlayer().getToolManager().getCurrentTool()));
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
        } else {
            super.check(input);
        }
    }

    @Override
    public void start() {
        System.out.println("Redirecting to game menu...");
    }
}
