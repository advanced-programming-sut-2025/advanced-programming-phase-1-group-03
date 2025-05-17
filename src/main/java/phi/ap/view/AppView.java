package phi.ap.view;

import phi.ap.model.App;
import phi.ap.model.enums.Menus.Menu;

import java.util.Scanner;

public class AppView {
    public static Scanner scanner = new Scanner(System.in);
    public static void runProgram() {
        while(App.getInstance().getMenu() != Menu.ExitMenu) {
            String input = scanner.nextLine();
            try {
                App.getInstance().getMenu().checkCommand(input);
            }catch (Exception e) {
                System.out.println("Error:\n"+e.getMessage());
            }
        }
    }
}
