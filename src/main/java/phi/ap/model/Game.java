package phi.ap.model;

import jdk.jfr.Frequency;
import phi.ap.model.enums.Weather;
import phi.ap.model.enums.npcStuff.NPCTypes;
import phi.ap.model.items.Item;
import phi.ap.model.items.buildings.ShippingBin;
import phi.ap.model.items.relations.Friendship;
import phi.ap.model.npcStuff.NPC;

import java.util.ArrayList;

public class Game {
    // instance of game
    private static Game instance = null;
    private static int maxGameID = 0;
    public static int getNewGameID() {
        return ++maxGameID;
    }
    public static Game getInstance() {
        return instance;
    }
    public static void setInstance(Game game) {
        instance = game;
    }

    public Game(Integer gameID, ArrayList<User> users) {
        this.gameID = gameID;
        for (User user : users) {
            addUser(user);
        }
        this.gameLoader = users.getFirst();
        this.currentPlayer = players.getFirst();
    }

    private final Integer gameID;
    private Map map;
    private WeatherManager weatherManager = new WeatherManager();
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Friendship> friendships = new ArrayList<>();
    private User gameLoader;
    private Date date = new Date(Date.START_HOUR); // zaman aghaz bazi 9 sobhe
    private Player currentPlayer;
    private StoreManager storeManager;
    private ArrayList<ShippingBin> shippingBins = new ArrayList<>();
    private ArrayList<NPC> npcs = new ArrayList<>();

    public void LoadFriends() {
        for(int i = 0; i < players.size(); i++) {
            for(int j = i + 1; j < players.size(); j++) {
                new Friendship(players.get(i), players.get(j), 0);
            }
        }
    }

    public void addShippingBin(ShippingBin ship){
        shippingBins.add(ship);
    }

    public ArrayList<ShippingBin> getShippingBins() {
        return shippingBins;
    }

    public NPC getNPC(NPCTypes npc) {
        for (NPC npc1 : npcs) {
            if (npc1.getType() == npc) return npc1;
        }
        return null;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
    public void addUser(User user) {
        Player player = new Player(user);
        players.add(player);
        player.init();
    }

    public User getGameLoader() {
        return gameLoader;
    }

    public void setGameLoader(User gameLoader) {
        this.gameLoader = gameLoader;
    }

    public Player getPlayerByUserName(String userName) {
        for(Player player : this.players) {
            if(player.getUser().getUsername().equals(userName))
                return player;
        }
        return null;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public void goNextPlayer() {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).equals(currentPlayer)) {
                if (i < players.size() - 1)
                    currentPlayer = players.get(i + 1);
                 else
                    currentPlayer = players.getFirst();
                break;
            }
        }
    }

    public Integer getGameID() {
        return gameID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public WeatherManager getWeatherManager() {
        return weatherManager;
    }

    public void setWeatherManager(WeatherManager weatherManager) {
        this.weatherManager = weatherManager;
    }

    public StoreManager getStoreManager() {
        return storeManager;
    }

    public void whenMapLoaded() {
        this.storeManager = new StoreManager();
    }

    public Boolean isNear(Player player1, Player player2) {
        int diffx = player1.getLocation().getX() -
                player2.getLocation().getX();
        int diffy = player1.getLocation().getY() -
                player2.getLocation().getY();
        if(diffx > 1 || diffx < -1 || diffy > 1 || diffy < -1)
            return false;
        return true;
    }

    public ArrayList<NPC> getNpcs() {
        return npcs;
    }
}
