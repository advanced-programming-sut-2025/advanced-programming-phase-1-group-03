package phi.ap.view;

import phi.ap.model.App;

import java.util.Scanner;

public class AppView {
    public static Scanner scanner = new Scanner(System.in);
    public static void runProgram() {
        String input = scanner.nextLine();
        App.getInstance().getMenu().checkCommand(input);
    }
}
