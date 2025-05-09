package phi.ap.view.menus.MainMenus;

import phi.ap.Controller.MenuControllers.MainMenuControllers.GameMenuController;
import phi.ap.model.App;
import phi.ap.model.Game;
import phi.ap.model.Result;
import phi.ap.model.enums.Menus.Menu;
import phi.ap.model.enums.StoreTypes;
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

        //TODO
        //This section will be removed
        if(input.split("\\s")[0].equals("w")){
            int x = Integer.parseInt(input.split("\\s")[1]);
            int y = Integer.parseInt(input.split("\\s")[2]);
//            System.out.println(Game.getInstance().getCurrentPlayer().getLocation().getTopTileDiff(y, x).getTileType());
        }
        //end of removal

        if ((matcher = GameMenuCommands.PrintMapComplete.getMatcher(input)) != null) {
            System.out.println(controller.showMap());
        } else if((matcher = GameMenuCommands.Walk.getMatcher(input)) != null) {
            //System.out.println(controller.walk(matcher.group("y"), matcher.group("x")));
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
            System.out.println(controller.purchase(matcher.group("productName"), matcher.group("amount")));
        } else if((matcher = GameMenuCommands.test.getMatcher(input)) != null) {
            System.out.println(controller.test());
        } else if((matcher = GameMenuCommands.Build.getMatcher(input)) != null) {
            System.out.println(controller.buildBuilding(matcher.group("name"), matcher.group("x"), matcher.group("y")));
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
