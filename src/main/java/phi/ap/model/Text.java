package phi.ap.model;

import phi.ap.model.enums.TileType;
import phi.ap.model.items.Item;

public class Text extends Item {
    public Text(String text, Coordinate coordinate) {
        super(1, text.length());
        setName("Text");
        setCoordinate(coordinate);
        for(int i = 0; i < text.length(); i++) {
            setTile(0, i, TileType.valueOf( String.valueOf(text.charAt(i))).getTile());
        }
    }
    @Override
    public void doTask() {

    }
}
