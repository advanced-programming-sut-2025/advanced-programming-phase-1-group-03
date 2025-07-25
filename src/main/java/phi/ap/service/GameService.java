package phi.ap.service;

import phi.ap.Controller.MenuControllers.MainMenuControllers.GameMenuController;
import phi.ap.model.*;
import phi.ap.model.enums.*;
import phi.ap.model.enums.npcStuff.NPCTypes;
import phi.ap.model.items.*;
import phi.ap.model.items.buildings.*;
import phi.ap.model.items.machines.craftingMachines.Scarecrow;
import phi.ap.model.items.machines.craftingMachines.Sprinkler;
import phi.ap.model.items.products.*;
import phi.ap.model.npcStuff.NPC;

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
        if (loc == null) return;
        Item topItem = loc.getGround().getTopItem(loc.getY(), loc.getX());
        if (!loc.getGround().getTopTile(loc.getY(), loc.getX()).isWalkable()) return;
        if (loc.getGround().getClass().getSimpleName().equals(Farm.class.getSimpleName())) {
            if (!force && App.getInstance().getRandomNumber(1, 100) > 1 * probBoost) return;
            if (!(topItem instanceof Dirt dirt)) return;
            if (dirt.isPlowed()) {
                return;
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

    public void waterRainyDay() {
        Map map = game.getMap();
        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                Item item = map.getTopItem(i, j);
                if (item instanceof Plant plant) {
                    if (!plant.isInGreenHouse()) {
                        plant.watering();
                    }
                }
            }
        }
    }

    public void doWeatherTasks() {
        WeatherManager w = Game.getInstance().getWeatherManager();
        if (w.getCurrentWeather() == Weather.Rain || w.getCurrentWeather() == Weather.Storm) {
            App.getInstance().getGameService().waterRainyDay();
        }
        game.getMap().getThunderedTiles().clear();
        if (w.getCurrentWeather() == Weather.Storm) {
            //thundeeeeeeeer
            for (Player player : game.getPlayers()) {
                Farm farm = player.getFarm();
                int c = 0;
                int maxTry = 10000;
                int tried = 0;
                while (c < 3) {
                    if (tried >= maxTry) break;
                    ++tried;
                    int y = App.getInstance().getRandomNumber(1, farm.getHeight() - 2);
                    int x = App.getInstance().getRandomNumber(1, farm.getWidth() - 2);
                    Result<String> res = thunderCoordinate(farm.getCoordinate().getY() + y,
                            farm.getCoordinate().getX() + x);
                    if (res.success) ++c;
                }
            }
        }
    }

    public Result<String> thunderCoordinate(int y, int x) {
        Location loc = App.getInstance().getMapService().getLocationOnMap(y, x);
        if (loc == null) return new Result<>(false, "invalid coordinate");
        for (Player player : game.getPlayers()) {
            Coordinate pCoord = player.getLocation().getGround().getTileCoordinateBaseMap(
                    player.getLocation().getY(), player.getLocation().getX());
            if (pCoord.equals(new Coordinate(y, x))) {
                return new Result<>(false, "that's good idea but no:)");
            }
        }
        if (loc.getGround() instanceof Greenhouse) {
            return new Result<>(false, "you can't thunder greenhouse");
        }
        if (!(loc.getGround() instanceof Farm || loc.getGround() instanceof NPCVillage
                || loc.getGround() instanceof Quarry)) {
            return new Result<>(false, "you can't thunder buildings");
        }
        Item item = game.getMap().getTopItem(y, x);
        switch (item) {
            case null :
                game.getMap().getThunderedTiles().add(new Coordinate(y, x));
                break;
//            case Dirt dirt :
//                game.getMap().getThunderedTiles().add(new Coordinate(y, x));
//                break;
            case Tree tree :
                tree.setThundered(true);
                game.getMap().getThunderedTiles().add(new Coordinate(y, x));
                break;
            case Crop crop :
                crop.delete();
                game.getMap().getThunderedTiles().add(new Coordinate(y, x));
                break;
            case Seed seed :
                seed.getFather().removeItem(seed);
                game.getMap().getThunderedTiles().add(new Coordinate(y, x));
                break;
            case Wood wood :
                Mineral mineral = new Mineral(1, 1, ForagingMineralTypes.Coal);
                mineral.setCoordinate(wood.getCoordinate());
                wood.getFather().addItem(mineral);
                game.getMap().getThunderedTiles().add(new Coordinate(y, x));
                break;
            case Stone stone :
                if (App.getInstance().getRandomNumber(1, 10) == 1) {
                    Mineral min = new Mineral(1, 1, ForagingMineralTypes.Diamond);
                    min.setCoordinate(stone.getCoordinate());
                    stone.getFather().addItem(min);
                    game.getMap().getThunderedTiles().add(new Coordinate(y, x));
                } else {
                    stone.getFather().removeItem(stone);
                    game.getMap().getThunderedTiles().add(new Coordinate(y, x));
                }
                break;
            case Product product :
                if (product.getName().equals("Grass")) {
                    product.getFather().removeItem(product);
                    game.getMap().getThunderedTiles().add(new Coordinate(y, x));
                }
                break;
            default:
                return new Result<>(false, "thunder has no effect there!");
        }
        return new Result<>(true, "⚡⚡");

    }

    public boolean isScarecrowGuard(int y, int x) {
        for (int i = y - Scarecrow.maxProtectingEdge; i <= y + Scarecrow.maxProtectingEdge; i++) {
            for (int j = x - Scarecrow.maxProtectingEdge; j <= x + Scarecrow.maxProtectingEdge; j++) {
                Item item = game.getMap().getTopItem(i, j);
                if (item instanceof Scarecrow scarecrow) {
                    int e = scarecrow.getProtectingEdge();
                    if (y <= i + e && y >= i - e && x <= j + e && x >= j - e) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void crowAttackTile(int y, int x) {
        if (isScarecrowGuard(y, x)) return;
        Item item = game.getMap().getTopItem(y, x);
        if (App.getInstance().getRandomNumber(1, 64) != 1) return;
        switch (item) {
            case Plant plant:
                if (plant.isInGreenHouse()) return;
                switch (plant) {
                    case Crop crop :
//                        if (crop.getGiant() != null) break;
                        crop.setLastHarvestDate(Game.getInstance().getDate());
                        if (crop.isOneTime() && crop.getGiant() == null) {
                            crop.delete();
                        }
                        break;
                    case Tree tree :
                        if (( tree.getLastHarvestDate() == null ||
                                tree.getHarvestRegrowthTime() <=
                                Game.getInstance().getDate().getRawDay() - tree.getLastHarvestDate().getRawDay()) &&
                                tree.getRemainingHarvestCycles() > 0) {
                            tree.setLastWateredDate(Game.getInstance().getDate());
                            tree.setRemainingHarvestCycles(tree.getRemainingHarvestCycles() - 1);
                        }
                        break;
                    default:
                        return;
                }
                break;
            case Seed seed:
                seed.getFather().removeItem(seed);
                break;
            case null, default:
                return;
        }
        return;
    }

    public void doCrowAttack() {
        for (int i = 1; i < game.getMap().getHeight() - 1; i++) {
            for (int j = 1; j < game.getMap().getWidth() - 1; j++) {
                crowAttackTile(i, j);
            }
        }
    }

    public void turnOnSprinklers() {
        for (int i = 1; i < game.getMap().getHeight() - 1; i++) {
            for (int j = 1; j < game.getMap().getWidth() - 1; j++) {
                Item item = game.getMap().getTopItem(i, j);
                if (item instanceof Sprinkler sprinkler) {
                    sprinkler.turnOn();
                }
            }
        }
    }

    public void initializeGame() {
        game.setMap(new Map());
        game.getWeatherManager().setWeathersInMorning();
        //adding farms
        for (Player player : game.getPlayers()) {

            if (player.getColor().isEmpty()) {
                switch (App.getInstance().getPlayerService().getPlayerIndex(player)) {
                    case 0:
                        player.setColor(Colors.fg(15));
                        break;
                    case 1:
                        player.setColor(Colors.fg(0));
                        break;
                    case 2:
                        player.setColor(Colors.fg(160));
                        break;
                    case 3:
                        player.setColor(Colors.fg(17));
                        break;
                }
            }
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
        for (Player player : game.getPlayers()) {
            player.InitializePlayer();
        }
        for (NPCTypes value : NPCTypes.values()) {
            if (value.getHouse() != null) {
                NPC npc = new NPC(value);
                Game.getInstance().getNpcs().add(npc);
                NPCHouse house = new NPCHouse(value.getHouse());
                NPCVillage village = game.getMap().getNPCVillage();
                village.addItem(house);
                Portal.makeMiddleDoor(house, village, TileType.Door.getTile());
                npc.setCoordinate(value.getHouse().getNpcCoordinate());
                house.addItem(npc);
            }
        }
        Game.getInstance().LoadFriends();
    }

    public void doNPCStuffAtNight() {
        for (Player player : game.getPlayers()) {
            for (NPC npc : game.getNpcs()) {
                if (npc.getState(player).getFriendshipLevel() >= 3) {
                    if (App.getInstance().getRandomNumber(1, 2) == 1) {
                        if (npc.getType().getPossibleGiftsToSend().isEmpty()) continue;
                        int ind = App.getInstance().getRandomNumber(0,
                                npc.getType().getPossibleGiftsToSend().size() - 1);
                        ItemStack stack = npc.getType().getPossibleGiftsToSend().get(ind);
                        player.getInventoryManager().addItem(new ItemStack(
                                getItem(stack.getItem().getName()), stack.getAmount()));
                        System.out.println(npc.getType().getNpcName() + " sent " + stack.getAmount() + " " + stack.getItem().getName() + "last night!");
                        break;
                        //TODO fix it some how :(
                    }
                }
            }
        }
    }
    public Item getItem(String name) {
        if(name.equals("Wood"))
            return new Wood(1, 1);
        if(name.equals("Stone") || StoneTypes.getType(name) != null)
            return new Stone(1, 1, StoneTypes.RegularStone);
        if(AnimalProductTypes.getType(name) != null)
            return new AnimalProduct(1, 1, AnimalProductTypes.getType(name));
        if(AnimalTypes.getType(name) != null)
            return new Animal(AnimalTypes.getType(name), 1, 1);
        if(CropsTypes.find(name) != null)
            return new Crop(1, 1 , CropsTypes.find(name));
        if(CraftingTypes.getType(name) != null)
            return CraftingTypes.getType(name).getRecipe().getResult().getItem();
        if(FishTypes.getType(name) != null)
            return new Fish(1, 1, FishTypes.getType(name));
        if(FoodTypes.getType(name) != null)
            return new Food(1, 1, FoodTypes.getType(name));
        if(ForagingCropsTypes.find(name) != null)
            return new Crop(1, 1, ForagingCropsTypes.find(name));
        if(ForagingMineralTypes.getType(name) != null)
            return new Mineral(1, 1, ForagingMineralTypes.getType(name));
        if(TreeTypes.find(name) != null)
            return new Tree(1, 1, TreeTypes.find(name), false);
        if(SeedTypes.find(name) != null)
            return new Seed(1, 1, SeedTypes.find(name));
        if(SaplingTypes.find(name) != null)
            return new Sapling(1, 1, SaplingTypes.find(name));
        if(StoneTypes.getType(name) != null)
            return new Stone(1, 1, StoneTypes.getType(name));
        if(SoilTypes.find(name) != null)
            return new Soil(1, 1, SoilTypes.find(name));
        if(ForagingMineralTypes.find(name) != null)
            return ForagingMineralTypes.find(name).getItem();
        if(ProductNames.find(name) != null) {
            return new Product(ProductNames.find(name));
        }
        if(FishTypes.find(name) != null){
            return new Fish(1, 1, FishTypes.find(name));
        }
        return null;
    }

}
