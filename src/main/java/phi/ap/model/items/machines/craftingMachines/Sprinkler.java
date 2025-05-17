package phi.ap.model.items.machines.craftingMachines;

import phi.ap.model.Coordinate;
import phi.ap.model.Game;
import phi.ap.model.Tile;
import phi.ap.model.enums.Colors;
import phi.ap.model.enums.CraftingTypes;
import phi.ap.model.items.Item;
import phi.ap.model.items.machines.Machine;
import phi.ap.model.items.products.Plant;

import java.util.ArrayList;
import java.util.List;

public class Sprinkler extends Machine {
    private ArrayList<Coordinate> protects = new ArrayList<>();
    public Sprinkler(int height, int width, CraftingTypes craftingType) {
        super(height, width, craftingType);
        fillTile(new Tile("*", Colors.fg(18), Colors.bg(21)));
        switch (craftingType) {
            case CraftingTypes.Sprinkler:
                this.protects = new ArrayList<>(List.of(
                        new Coordinate(-1, 0),
                        new Coordinate(1, 0),
                        new Coordinate(0, 1),
                        new Coordinate(0, -1)));
                fillTile(new Tile("*", Colors.fg(18), Colors.bg(21)));
                break;
            case CraftingTypes.QualitySprinkler:
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0) continue;
                        protects.add(new Coordinate(i, j));
                    }
                }
                fillTile(new Tile("*", Colors.fg(118), Colors.bg(21)));
                break;
            case CraftingTypes.IridiumSprinkler:
                for (int i = -2; i <= 2; i++) {
                    for (int j = -2; j <= 2; j++) {
                        if (i == 0 && j == 0) continue;
                        protects.add(new Coordinate(i, j));
                    }
                }
                fillTile(new Tile("*", Colors.fg(232), Colors.bg(21)));
                break;
        }
    }
    public Sprinkler(int height, int width, String machineName, Integer price) {
        super(height, width, machineName, price);
        fillTile(new Tile("*", Colors.fg(18), Colors.bg(21)));
        switch (machineName) {
            case "Sprinkler":
                this.protects = new ArrayList<>(List.of(
                        new Coordinate(-1, 0),
                        new Coordinate(1, 0),
                        new Coordinate(0, 1),
                        new Coordinate(0, -1)));
                fillTile(new Tile("*", Colors.fg(18), Colors.bg(21)));
                break;
            case "QualitySprinkler":
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0) continue;
                        protects.add(new Coordinate(i, j));
                    }
                }
                fillTile(new Tile("*", Colors.fg(118), Colors.bg(21)));
                break;
            case "IridiumSprinkler":
                for (int i = -2; i <= 2; i++) {
                    for (int j = -2; j <= 2; j++) {
                        if (i == 0 && j == 0) continue;
                        protects.add(new Coordinate(i, j));
                    }
                }
                fillTile(new Tile("*", Colors.fg(232), Colors.bg(21)));
                break;
        }
    }

    public void turnOn() {
        if (getCoordinate() == null) return;
        int y = getCoordinate().getY();
        int x = getCoordinate().getX();
        for (Coordinate diff : protects) {
            int yy = y + diff.getY();
            int xx = x + diff.getX();
            Item item = Game.getInstance().getMap().getTopItem(yy, xx);
            if (item instanceof Plant plant) {
                plant.watering();
            }
        }
    }

    @Override
    public void doTask() {

    }
}
