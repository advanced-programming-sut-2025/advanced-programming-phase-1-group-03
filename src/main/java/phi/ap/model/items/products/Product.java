package phi.ap.model.items.products;

import phi.ap.model.LevelProcess;
import phi.ap.model.enums.LevelName;
import phi.ap.model.items.Item;

import java.util.ArrayList;
import java.util.List;

public abstract class Product extends Item {
    private int sellPrice;

    public Product(int height, int width) {
        super(height, width);
    }

    private final static  LevelProcess levelProcess = new LevelProcess(new ArrayList<>(
            List.of(LevelName.normal, LevelName.silver, LevelName.golden, LevelName.iridium)), 0);
}
