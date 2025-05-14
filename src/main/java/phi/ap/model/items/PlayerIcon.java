package phi.ap.model.items;

import phi.ap.model.Player;
import phi.ap.model.Tile;
import phi.ap.model.enums.FaceWay;
import phi.ap.model.enums.TileType;

public class PlayerIcon extends Item{

    private final Player player;
    private FaceWay faceWay;

    public PlayerIcon(Player player, FaceWay faceWay) {
        super(1, 1);
        setName("PlayerIcon");
        this.player = player;
        this.faceWay = faceWay;
        Tile icon = TileType.Player.getTileWithFaceWay(faceWay);
        icon.setFgColor(icon.getFgColor() + player.getColor());
        fillTile(icon);
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void doTask() {

    }
}
