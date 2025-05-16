package phi.ap.view;

import phi.ap.Controller.MenuControllers.MainMenuControllers.GameMenuController;
import phi.ap.model.App;
import phi.ap.model.enums.Menus.Menu;
import phi.ap.model.enums.commands.Command;
import phi.ap.model.enums.commands.CommonCommands;
import phi.ap.model.enums.commands.GameMenuCommands;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public abstract class AppMenu {
    public void check(String input){
        GameMenuController controller = new GameMenuController();
        Matcher matcher = null;
        if((matcher = CommonCommands.ShowCurrentMenu.getMatcher(input)) != null){
            System.out.println(App.getInstance().getMenu().toString());
        } else if((matcher = CommonCommands.MenuExit.getMatcher(input)) != null){
            App.getInstance().changeMenu(Menu.LoginMenu);
        } else if((matcher = CommonCommands.MenuEnter.getMatcher(input)) != null) {
            App.getInstance().changeMenu(Menu.TradeMenu);
        }
        else{
            System.out.println("invalid command");
        }
    }
    public abstract void start();
}
