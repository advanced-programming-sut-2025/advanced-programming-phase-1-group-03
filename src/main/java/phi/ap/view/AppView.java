package phi.ap.view;

import phi.ap.model.App;
import phi.ap.model.enums.Menus.Menu;

import java.util.Scanner;

public class AppView {
    public static Scanner scanner = new Scanner(System.in);
    public static void runProgram() {
        while(App.getInstance().getMenu() != Menu.ExitMenu) {
            String input = scanner.nextLine();
            App.getInstance().getMenu().checkCommand(input);
        }
    }
}
