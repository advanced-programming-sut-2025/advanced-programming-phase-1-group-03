package phi.ap.service;

import phi.ap.model.*;
import phi.ap.model.enums.*;
import phi.ap.model.enums.Time.Seasons;
import phi.ap.model.items.Dirt;
import phi.ap.model.items.Item;
import phi.ap.model.items.Portal;
import phi.ap.model.items.buildings.Cottage;
import phi.ap.model.items.buildings.Farm;
import phi.ap.model.items.buildings.NPCVillage;
import phi.ap.model.items.buildings.Quarry;
import phi.ap.model.items.Seed;
import phi.ap.model.items.products.*;

public class GameService {
    private Game game;

    public GameService(Game game) {
        this.game = game;
    }

    public void setForaging(int y, int x, boolean force, int probBoost) {
        for (Player player : game.getPlayers()) {
            if (player.getLocation().getGround().getTileCoordinateBaseMap(
                    player.getLocation().getY(), player.getLocation().getX())
                    .equals(new Coordinate(y, x))) return;
        }
        Location loc = App.getInstance().getMapService().getLocationOnMap(y, x);
        if (loc == null) return; // TODO : || get.ground() == null
        Item topItem = loc.getGround().getTopItem(loc.getY(), loc.getX());
        if (!loc.getGround().getTopTile(loc.getY(), loc.getX()).isWalkable()) return;
        if (loc.getGround().getClass().getSimpleName().equals(Farm.class.getSimpleName())) {
            if (!force && App.getInstance().getRandomNumber(1, 100) > 1 * probBoost) return;
            if (!(topItem instanceof Dirt dirt)) return;
            if (dirt.isPlowed()) {
                return;
                //TODO : add foraging seed random after planting system completed;
            }

            int rand = App.getInstance().getRandomNumber(1, 100);
            int[] probs = {20, 20, 10, 15, 15, 20};
            int ind = 0; int sum = 0;
            for (int i = 0; i < probs.length; i++) {
                sum += probs[i];
                if (rand <= sum) {
                    ind = i;
                    break;
                }
            }
            Item item = null;
            switch (ind) {
                case 1:
                    Product product = new Product(ProductNames.Grass);
                    if (App.getInstance().getRandomNumber(1, 3) == 1) {
                        product.getDrops().add(new ItemStack(new Product(ProductNames.Fiber), 1));
                    }
                    item = product;
                    break;
                case 2:
                    item = new Wood(1, 1);
                    break;
                case 3:
                    ForagingCropsTypes fCropType = ForagingCropsTypes.getRandomFromSeason(Game.getInstance().getDate().getSeason());
                    //TODO: Set null checker;
                    Crop crop = new Crop(1, 1, fCropType);
                    item = crop;
                    break;
                case 4:
                    SeedTypes seedType = SeedTypes.getRandomFromSeason(Game.getInstance().getDate().getSeason());
                    Seed seed = new Seed(1, 1, seedType);
                    seed.makeRemovableByPickaxe();
                    item = seed;
                    break;
                case 5:
                    ForagingTreeTypes fTreeType= ForagingTreeTypes.getRandom(Game.getInstance().getDate().getSeason());
                    Tree tree = new Tree(1, 1, fTreeType.getTreeType(), true);
                    item = tree;
                    break;
                default:
                    item = new Stone(1, 1, StoneTypes.RegularStone);
            }
            item.setCoordinate(new Coordinate(0, 0));
            dirt.addItem(item);
        }
        else if(loc.getGround().getClass().getSimpleName().equals(Quarry.class.getSimpleName())) {
            if (!force && App.getInstance().getRandomNumber(1, 100) > 1 * probBoost) return;
            if (topItem != null) return;
            Item item;
            if (App.getInstance().getRandomNumber(1, 10) == 1) {
                item = new Stone(1, 1, StoneTypes.RegularStone);
            } else {
                item = new Mineral(1, 1, ForagingMineralTypes.getRandom());
            }
            item.setCoordinate(new Coordinate(loc.getY(), loc.getX()));
            loc.getGround().addItem(item);
        }
    }

    public void generateForaging(int probBoost) {
        if (probBoost < 1) probBoost = 1;
        for (int i = 0; i < game.getMap().getHeight(); i++) {
            for (int j = 0; j < game.getMap().getWidth(); j++) {
                setForaging(i, j, false, probBoost);
            }
        }
    }

    public void initializeGame() {
        game.setMap(new Map());
        game.getWeatherManager().setWeathersInMorning();
        //adding farms
        for (Player player : game.getPlayers()) {
            game.getMap().addFarm(new Farm(player.getFarmType()), player);
            Farm farm = player.getFarm();
            farm.getAvailablePlayers().add(player);
            //set Player location on door of cottage
            int y = 0, x = 0;
            for (Item item : farm.getHoldingItems()) {
                if (item instanceof Cottage) {
                    y = item.getPortalList().get(0).getCoordinateOnDest().getY();
                    x = item.getPortalList().get(0).getCoordinateOnDest().getX();
                    ++y;
                    //coordinate of exit of cottage;
                }
            }
            player.setLocation(new Location(new Coordinate(y, x), farm, FaceWay.Down));
            player.getLocation().setPlayer(player);
        }
        game.getMap().addNPCVillage();
        //makeDoors between village and farms;
        for (Player player : game.getPlayers()) {
            Farm farm = player.getFarm();

            NPCVillage village = game.getMap().getNPCVillage();
            Coordinate fp1 = null;
            Coordinate fp2 = null;
            Coordinate vp1 = null;
            Coordinate vp2 = null;
            switch (App.getInstance().getPlayerService().getPlayerIndex(player)) {
                case 0:
                    fp1 = new Coordinate(farm.getHeight() - 1, farm.getWidth() / 2);
                    fp2 = new Coordinate(farm.getHeight() / 2, farm.getWidth() - 1);
                    vp1 = new Coordinate(village.getHeight() / 3, 0);
                    vp2 = new Coordinate(0, village.getWidth() / 3);
                    break;
                case 1:
                    fp1 = new Coordinate(farm.getHeight() - 1, farm.getWidth() / 2);
                    fp2 = new Coordinate(farm.getHeight() / 2, 0);
                    vp1 = new Coordinate(village.getHeight() / 3, village.getWidth() - 1);
                    vp2 = new Coordinate(0, 2 * village.getWidth() / 3);
                    break;
                case 2:
                    fp1 = new Coordinate(0, farm.getWidth() / 2);
                    fp2 = new Coordinate(farm.getHeight() / 2, farm.getWidth() - 1);
                    vp1 = new Coordinate(2 * village.getHeight() / 3, 0);
                    vp2 = new Coordinate(village.getHeight() - 1, village.getWidth() / 3);
                    break;
                case 3:
                    fp1 = new Coordinate(0, farm.getWidth() / 2);
                    fp2 = new Coordinate(farm.getHeight() / 2, 0);
                    vp1 = new Coordinate(2 * village.getHeight() / 3, village.getWidth() - 1);
                    vp2 = new Coordinate(village.getHeight() - 1, 2 * village.getWidth() / 3);
                    break;
            }
            Portal.makePortalTwoWay(farm, fp1, village, vp1, TileType.Door.getTile());
            Portal.makePortalTwoWay(farm, fp2, village, vp2, TileType.Door.getTile());
        }

        //adding foraging;
        /*for (Item ground : game.getMap().getHoldingItems()) {
            if (ground instanceof Farm) {
                //tree
                for (int i = 1; i < ground.getHeight() - 1; i++) {
                    for (int j = 1; j < ground.getWidth() - 1; j++) {

                        //check if place is free, check not two neighbor trees
                        if (!(ground.getItem(i, j) instanceof Dirt)) continue;
                        if (ground.getItem(i, j).getItem(0, 0) != null) continue;
                        if (App.getInstance().getMapService().checkIsPlayerHere(ground, new Coordinate(i, j), null)) continue;
                        if (App.getInstance().eventRandom(10)) {
                            int ind = App.getInstance().getRandomNumber(0, TreeTypes.values().length - 1);
                            TreeTypes type = TreeTypes.values()[ind];
                            Tree tree = new Tree(1, 1, type, true);
                            tree.setCoordinate(new Coordinate(0, 0));
                            ground.getItem(i, j).addItem(tree);
                        }
                    }
                }
                //f crops
                for (int i = 1; i < ground.getHeight() - 1; i++) {
                    for (int j = 1; j < ground.getWidth() - 1; j++) {
                        if (!(ground.getItem(i, j) instanceof Dirt)) continue;
                        if (ground.getItem(i, j).getItem(0, 0) != null) continue;
                        if (App.getInstance().getMapService().checkIsPlayerHere(ground, new Coordinate(i, j), null)) continue;
                        if (App.getInstance().eventRandom(3)) {
                            Item item = switch (App.getInstance().getRandomNumber(0, 1)) {
                                case 0 -> new Seed(1, 1, SeedTypes.getRandomFromSeason(Seasons.Spring));
                                case 1 -> new Crop(1, 1,
                                        ForagingCropsTypes.getRandomFromSeason(Seasons.Spring));
                                default -> null;
                            };
                            if (item == null) continue;
                            item.setCoordinate(new Coordinate(0, 0));
                            ground.getItem(i, j).addItem(item);
                        }
                    }
                }
                //stones
                for (int i = 1; i < ground.getHeight() - 1; i++) {
                    for (int j = 1; j < ground.getWidth() - 1; j++) {
                        if (!(ground.getItem(i, j) instanceof Dirt)) continue;
                        if (ground.getItem(i, j).getItem(0, 0) != null) continue;
                        if (App.getInstance().getMapService().checkIsPlayerHere(ground, new Coordinate(i, j), null)) continue;
                        if (App.getInstance().eventRandom(5)) {
                            int ind = App.getInstance().getRandomNumber(0, StoneTypes.values().length - 1);
                            StoneTypes type = StoneTypes.values()[ind];
                            Stone stone = new Stone(1, 1, StoneTypes.RegularStone);
                            stone.setCoordinate(new Coordinate(0, 0));
                            ground.getItem(i, j).addItem(stone);
                        }
                    }
                }
                for (Item quarry : ground.getHoldingItems()) {
                    if (!(quarry instanceof Quarry)) continue;
                    for (int i = 2; i < quarry.getHeight() - 2; i++) {
                        for (int j = 2; j < quarry.getWidth() - 2; j++) {
//                            if ((i == 1 && j == 1) || (i == 1 && j == quarry.getWidth() - 2)
//                                    || (i == quarry.getHeight() - 2 && j == 1)
//                                    || (i == quarry.getHeight() - 2 && j == quarry.getWidth() - 2)) {
//                                continue;
//                            }
                            if (quarry.getItem(i, j) != null) continue;
                            if (App.getInstance().eventRandom(10)) {
                                Mineral mineral = new Mineral(1, 1, ForagingMineralTypes.getRandom());
                                mineral.setCoordinate(new Coordinate(i, j));
                                quarry.addItem(mineral);
                            }
                        }
                    }
                }
            }
        }
        */
        generateForaging(20);
        Game.getInstance().whenMapLoaded();
    }
}
