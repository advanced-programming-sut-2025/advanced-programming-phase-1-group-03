package phi.ap.service;

import phi.ap.model.Game;
import phi.ap.model.Map;
import phi.ap.model.Player;
import phi.ap.model.items.buildings.Farm;

public class GameService {
    private Game game;

    public GameService(Game game) {
        this.game = game;
    }
    public void initializeGame() {
        game.setMap(new Map());
        for (Player player : game.getPlayers()) {
            game.getMap().addFarm(new Farm(player.getFarmType()), player);
        }
    }
}
