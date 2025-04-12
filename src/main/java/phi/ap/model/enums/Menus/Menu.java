package phi.ap.model.enums.Menus;

import phi.ap.view.AppMenu;
import phi.ap.view.LoginMenu;
import phi.ap.view.MainMenus.AvatarMenu;
import phi.ap.view.MainMenus.GameMenu;
import phi.ap.view.MainMenus.MainMenu;
import phi.ap.view.MainMenus.ProfileMenu;
import phi.ap.view.RegisterMenu;

import java.util.Scanner;

public enum Menu {
    LoginMenu(new LoginMenu()),
    RegisterMenu(new RegisterMenu()),
    MainMenu(new MainMenu()),
    ProfileMenu(new ProfileMenu()),
    AvatarMenu(new AvatarMenu()),
    GameMenu(new GameMenu())
    ;

    private AppMenu menu;

    Menu(AppMenu menu) {
        this.menu = menu;
    }

    public void checkCommand(Scanner scanner) {
        menu.check(scanner);
    }

}
