package phi.ap.view;

import phi.ap.model.App;
import phi.ap.model.enums.commands.CommonCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public abstract class AppMenu {
    public void check(String input){
        Matcher matcher = null;
        if((matcher = CommonCommands.ShowCurrentMenu.getMatcher(input)) != null){
            System.out.println(App.getInstance().getMenu().toString());
        }else{
            System.out.println("invalid command");
        }
    }
    public abstract void start();
}
