package phi.ap.view.menus.MainMenus;

import phi.ap.Controller.MenuControllers.MainMenuControllers.GameMenuController;
import phi.ap.model.App;
import phi.ap.model.Game;
import phi.ap.model.Result;
import phi.ap.model.enums.Menus.Menu;
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
        } else {
            if ((matcher = GameMenuCommands.PrintMapComplete.getMatcher(input)) != null) {
                System.out.println(controller.showMap());
            } else {
                super.check(input);
            }
        }
    }

    @Override
    public void start() {
        System.out.println("Redirecting to game menu...");
    }
}
