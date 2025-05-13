package phi.ap;

import phi.ap.model.App;
import phi.ap.model.Game;
import phi.ap.model.enums.Colors;
import phi.ap.utils.FileManager;
import phi.ap.view.AppView;

import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
//        System.out.println("┌──────────┐");
//        System.out.println("│  Hello!  │");
//        System.out.println("└──────────┘");
        System.out.println("Start...");
        App.getInstance().load();
        AppView.runProgram();
    }
}
