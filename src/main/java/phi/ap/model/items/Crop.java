package phi.ap.model.items;

import phi.ap.model.Date;
import phi.ap.model.Game;
import phi.ap.model.ItemStack;
import phi.ap.model.enums.CropsTypes;
import phi.ap.model.enums.ForagingCropsTypes;
import phi.ap.model.enums.SeedTypes;

import java.util.ArrayList;
import java.util.List;

public class Crop extends Plant {

    private SeedTypes seedType;
    private CropsTypes type = null;
    private ForagingCropsTypes foragingType = null;

    public Crop(int height, int width, CropsTypes type) {
        super(height, width, type.getSeed().toString(), type.getStage(), type.getCanBecomeGiant(),
                Game.getInstance().getDate());
        this.seedType = type.getSeed();
        this.type = type;
    }

    public Crop(int height, int width, ForagingCropsTypes type) {
        super(height, width, "foragingSystem", new ArrayList<>(List.of(0)), false,
                Game.getInstance().getDate());
        this.foragingType = type;
        this.seedType = null;
        fillTile(type.getTile());
        setName(type.getName());
    }

    @Override
    public ArrayList<ItemStack> getProducts() {
        return null;
    }

    @Override
    public ArrayList<ItemStack> getDrops() {
        return null;
    }

    @Override
    public void doTask() {

    }
}
