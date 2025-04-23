package phi.ap.view.menus;

import phi.ap.model.App;
import phi.ap.model.enums.Menus.Menu;
import phi.ap.model.enums.commands.CommonCommands;
import phi.ap.view.AppMenu;

import java.util.regex.Matcher;

public class LoginMenu extends AppMenu {
    @Override
    public void check(String input) {
        Matcher matcher = null;
        if((matcher = CommonCommands.MenuExit.getMatcher(input)) != null){
            App.getInstance().changeMenu(Menu.ExitMenu);
        }else{
            super.check(input);
        }
    }

    @Override
    public void start() {

    }
}
