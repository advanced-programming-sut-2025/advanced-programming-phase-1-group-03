package phi.ap.service;

import phi.ap.model.*;
import phi.ap.model.enums.*;
import phi.ap.model.enums.Time.Seasons;
import phi.ap.model.items.Item;
import phi.ap.model.items.buildings.Farm;
import phi.ap.model.items.buildings.Quarry;
import phi.ap.model.items.producers.Seed;
import phi.ap.model.items.producers.Tree;
import phi.ap.model.items.products.Crop;
import phi.ap.model.items.products.Mineral;
import phi.ap.model.items.products.Stone;

public class GameService {
    private Game game;

    public GameService(Game game) {
        this.game = game;
    }

    public boolean checkNeighboursClassSame(Ground ground, Coordinate coordinate, Class <?> cls) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        Item neighbour = null;
        if (x > 0) neighbour = ground.getItem(y, x - 1);
        if (cls.isInstance(neighbour)) return true;
        neighbour = null;

        if (x < ground.getWidth() - 1) neighbour = ground.getItem(y, x + 1);
        if (cls.isInstance(neighbour)) return true;
        neighbour = null;

        if (y > 0) neighbour = ground.getItem(y - 1, x);
        if (cls.isInstance(neighbour)) return true;
        neighbour = null;

        if (y < ground.getWidth() - 1) neighbour = ground.getItem(y + 1, x);
        if (cls.isInstance(neighbour)) return true;
        neighbour = null;

        return false;
    }
    public void initializeGame() {
        game.setMap(new Map());
        //adding farms
        for (Player player : game.getPlayers()) {
            game.getMap().addFarm(new Farm(player.getFarmType()), player);
        }
        game.getMap().addNPCVillage();
        //adding trees with probability 10%, stones 10% foraging 3% minerals 5%
        for (Item ground : game.getMap().getHoldingItems()) {
            if (ground instanceof Farm) {
                for (int i = 1; i < ground.getHeight() - 1; i++) {
                    for (int j = 1; j < ground.getWidth() - 1; j++) {
                        //check if place is free, check not two neighbor trees
                        if (ground.getItem(i, j) != null) continue;
                        if (checkNeighboursClassSame(ground, new Coordinate(i, j), Tree.class)) continue;
                        if (App.getInstance().eventRandom(10)) {
                            int ind = App.getInstance().getRandomNumber(0, TreeTypes.values().length - 1);
                            TreeTypes type = TreeTypes.values()[ind];
                            Tree tree = new Tree(type, 1, 1);
                            tree.setCoordinate(new Coordinate(i, j));
                            ground.addItem(tree);
                        }
                    }
                }
                for (int i = 1; i < ground.getHeight() - 1; i++) {
                    for (int j = 1; j < ground.getWidth() - 1; j++) {
                        if (ground.getItem(i, j) != null) continue;
                        if (App.getInstance().eventRandom(3)) {
                            Item item = switch (App.getInstance().getRandomNumber(0, 1)) {
                                case 0 -> new Seed(1, 1, SeedTypes.getRandomFromSeason(Seasons.Spring));
                                case 1 -> new Crop(1, 1,
                                        ForagingCropsTypes.getRandomFromSeason(Seasons.Spring));
                                default -> null;
                            };
                            if (item == null) continue;
                            item.setCoordinate(new Coordinate(i, j));
                            ground.addItem(item);
                        }
                    }
                }
                for (int i = 1; i < ground.getHeight() - 1; i++) {
                    for (int j = 1; j < ground.getWidth() - 1; j++) {
                        if (ground.getItem(i, j) != null) continue;
                        if (checkNeighboursClassSame(ground, new Coordinate(i, j), Stone.class)) continue;
                        if (App.getInstance().eventRandom(10)) {
                            int ind = App.getInstance().getRandomNumber(0, StoneTypes.values().length - 1);
                            StoneTypes type = StoneTypes.values()[ind];
                            Stone stone = new Stone(1, 1, StoneTypes.RegularStone);
                            stone.setCoordinate(new Coordinate(i, j));
                            ground.addItem(stone);
                        }
                    }
                }
                for (Item quarry : ground.getHoldingItems()) {
                    if (!(quarry instanceof Quarry)) continue;
                    for (int i = 1; i < quarry.getHeight() - 1; i++) {
                        for (int j = 1; j < quarry.getWidth() - 1; j++) {
                            if (quarry.getItem(i, j) != null) continue;
                            if (App.getInstance().eventRandom(5)) {
                                Mineral mineral = new Mineral(1, 1, ForagingMineralTypes.getRandom());
                                mineral.setCoordinate(new Coordinate(i, j));
                                quarry.addItem(mineral);
                            }
                        }
                    }
                }
            }
        }
    }
}
