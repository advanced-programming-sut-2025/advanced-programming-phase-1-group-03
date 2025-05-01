package phi.ap.view.menus.MainMenus;

import phi.ap.Controller.MenuControllers.LoginMenuController;
import phi.ap.model.App;
import phi.ap.model.Result;
import phi.ap.model.enums.Menus.Menu;
import phi.ap.model.enums.commands.CommonCommands;
import phi.ap.model.enums.commands.MainMenuCommands;
import phi.ap.view.AppMenu;

import java.util.regex.Matcher;

public class MainMenu extends AppMenu {
    LoginMenuController loginController = new LoginMenuController();
    @Override
    public void check(String input) {
        Matcher matcher = null;
        if((matcher = MainMenuCommands.Logout.getMatcher(input)) != null){
            Result<String> result = loginController.logout();
            System.out.println(result);
            if(result.success)
                App.getInstance().changeMenu(Menu.LoginMenu);
        } else if ((matcher = CommonCommands.MenuExit.getMatcher(input)) != null){
            App.getInstance().changeMenu(Menu.ExitMenu);
        } else if((matcher = CommonCommands.MenuEnter.getMatcher(input)) != null){
            String menu = matcher.group("menu").trim();
            if (Menu.fromString(menu) != null) {
                App.getInstance().changeMenu(Menu.fromString(menu));
            } else {
                System.out.println("invalid menu");
            }
        } else{
            super.check(input);
        }
    }

    @Override
    public void start() {
        System.out.println("Redirecting to main menu....");
    }
}
