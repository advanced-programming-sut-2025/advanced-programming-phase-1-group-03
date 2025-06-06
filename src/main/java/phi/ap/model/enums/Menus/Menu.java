package phi.ap.model.enums.Menus;

import phi.ap.view.AppMenu;
import phi.ap.view.menus.LoginMenu;
import phi.ap.view.menus.MainMenus.AvatarMenu;
import phi.ap.view.menus.MainMenus.GameMenu;
import phi.ap.view.menus.MainMenus.MainMenu;
import phi.ap.view.menus.MainMenus.ProfileMenu;
import phi.ap.view.menus.MainMenus.ExitMenu;
import phi.ap.view.menus.MainMenus.sub_menus.ChoosingMapMenu;
import phi.ap.view.menus.MainMenus.sub_menus.DialogueAnswerMenu;
import phi.ap.view.menus.MainMenus.sub_menus.TradeMenu;

public enum Menu {
    LoginMenu(new LoginMenu()),
    MainMenu(new MainMenu()),
    ProfileMenu(new ProfileMenu()),
    AvatarMenu(new AvatarMenu()),
    GameMenu(new GameMenu()),
    TradeMenu(new TradeMenu()),
    ExitMenu(new ExitMenu()),
    ChoosingMapMenu(new ChoosingMapMenu()),
    DialogueAnwerMenu(new DialogueAnswerMenu()),
    ;

    private AppMenu menu;

    Menu(AppMenu menu) {
        this.menu = menu;
    }

    public void start(){
        menu.start();
    }
    public void checkCommand(String input) {
        menu.check(input);
    }

    public static Menu fromString(String input) {
        for(Menu menu : Menu.values()) {
            if(menu.toString().equals(input)) {
                return menu;
            }
        }
        return null;
    }
}
