package phi.ap;

import phi.ap.model.App;
import phi.ap.model.Game;
import phi.ap.utils.FileManager;
import phi.ap.view.AppView;

import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start...");
        App.getInstance().load();
        AppView.runProgram();
    }
}
