package phi.ap.model;

import phi.ap.model.enums.Menus.Menu;

public class App {
    private static App instance = null;
    private User stayLoggedInUser = null;

    private Menu menu = Menu.LoginMenu;

    public static App getInstance() {
        if (instance == null)
            instance = new App();
        return instance;
    }

    public Menu getMenu() {
        return menu;
    }
    public void changeMenu(Menu newMenu) {
        menu = newMenu;
        menu.start();
    }
}
