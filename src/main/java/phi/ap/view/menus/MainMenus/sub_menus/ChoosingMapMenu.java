package phi.ap.view.menus.MainMenus.sub_menus;

import phi.ap.Controller.MenuControllers.MainMenuControllers.subMenus.ChoosingMapController;
import phi.ap.model.App;
import phi.ap.model.Game;
import phi.ap.model.Player;
import phi.ap.model.Result;
import phi.ap.model.enums.FarmTypes;
import phi.ap.model.enums.Menus.Menu;
import phi.ap.model.enums.commands.ChoosingMapMenuCommands;
import phi.ap.view.AppMenu;

import java.util.regex.Matcher;

public class ChoosingMapMenu extends AppMenu {
    ChoosingMapController controller = new ChoosingMapController();
    @Override
    public void check(String input) {
        Matcher matcher;
        if ((matcher = ChoosingMapMenuCommands.ChooseGameMap.getMatcher(input)) != null) {
            Result<String> result = controller.chooseMap(matcher.group("mapNumber"));
            System.out.println(result);
            if (result.success) App.getInstance().changeMenu(Menu.GameMenu);
        } else {
            super.check(input);
        }
    }

    @Override
    public void start() {
        StringBuilder message = new StringBuilder();
        message.append("Choose Farm types for users ");
        Game game = Game.getInstance();
        for (Player player : Game.getInstance().getPlayers()) {
            message.append(player.getUser().getUsername());
            message.append(" ");
        }
        message.append(".\n");
        message.append("available farm types: ");
        for (FarmTypes farmType : FarmTypes.values()) {
            message.append(farmType.toString());
            message.append(" ");
        }
        message.append(".");
        System.out.println(message.toString());
    }
}
