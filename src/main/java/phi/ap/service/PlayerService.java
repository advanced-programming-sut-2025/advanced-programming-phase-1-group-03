package phi.ap.service;

import phi.ap.model.Game;
import phi.ap.model.Player;

public class PlayerService {
    private Game game;
    public PlayerService(Game game) {
        this.game = game;
    }
    public Integer getPlayerIndex(Player player) {
        for (int i = 0; i < game.getPlayers().size(); i++) {
            Player gamePlayer = game.getPlayers().get(i);
            if (gamePlayer.getUser().getUsername().equals(player.getUser().getUsername())) {
                return i;
            }
        }
        return null;
    }
}
