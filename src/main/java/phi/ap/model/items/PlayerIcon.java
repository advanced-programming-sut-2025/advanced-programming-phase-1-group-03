package phi.ap.model.items;

import phi.ap.model.Player;
import phi.ap.model.enums.TileType;

public class PlayerIcon extends Item{

    private final Player player;

    public PlayerIcon(Player player) {
        super(1, 1);
        this.player = player;
        fillTile(TileType.Player.getTile());
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void doTask() {

    }
}
