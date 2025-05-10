package phi.ap.model.items;

import phi.ap.model.Player;
import phi.ap.model.enums.FaceWay;
import phi.ap.model.enums.TileType;

public class PlayerIcon extends Item{

    private final Player player;
    private FaceWay faceWay;

    public PlayerIcon(Player player, FaceWay faceWay) {
        super(1, 1);
        this.player = player;
        this.faceWay = faceWay;
        fillTile(TileType.Player.getTileWithFaceWay(faceWay));
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void doTask() {

    }
}
