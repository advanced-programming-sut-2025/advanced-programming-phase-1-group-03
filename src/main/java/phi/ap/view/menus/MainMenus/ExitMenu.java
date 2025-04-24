package phi.ap.view.menus.MainMenus;

import phi.ap.utils.FileManager;
import phi.ap.view.AppMenu;

public class ExitMenu extends AppMenu {
    @Override
    public void start() {
        new FileManager().writeAppData();
        System.out.println("exiting...");
    }
}
