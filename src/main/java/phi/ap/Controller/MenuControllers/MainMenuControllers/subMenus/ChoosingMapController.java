package phi.ap.Controller.MenuControllers.MainMenuControllers.subMenus;

import phi.ap.model.App;
import phi.ap.model.Game;
import phi.ap.model.Result;
import phi.ap.model.enums.FarmTypes;
import phi.ap.model.enums.Menus.Menu;

public class ChoosingMapController {
    public Result<String> chooseMap(String mapNumber) {
        int mapN;
        try {
            mapN = Integer.parseInt(mapNumber);
        } catch (Exception e) {
            return new Result<>(false, "invalid map number");
        }
        if (mapN >= FarmTypes.values().length) {
            return new Result<>(false, "invalid map number");
        }
        Game.getInstance().getCurrentPlayer().setFarmType(FarmTypes.values()[mapN]);
        Game.getInstance().goNextPlayer();
        if (Game.getInstance().getCurrentPlayer().getUser().getUsername().
                equals(Game.getInstance().getPlayers().get(0).getUser().getUsername())) {
            App.getInstance().getGameService().initializeGame();
            return new Result<>(true, "All players have chosen their map.");
        }
        return new Result<>(false, "map chosen successfully. Next player to choose :" +
                Game.getInstance().getCurrentPlayer().getUser().getUsername());
    }
}
