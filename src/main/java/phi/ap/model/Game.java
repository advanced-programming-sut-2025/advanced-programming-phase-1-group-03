package phi.ap.model;

import phi.ap.model.enums.Weather;
import phi.ap.model.items.Item;
import phi.ap.model.items.relations.Friendship;

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
    private ArrayList<Item> items;
    private Weather currentWeather;
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Friendship> friendships = new ArrayList<>();
    private User gameLoader;
    private Date date = new Date(Date.START_HOUR); // zaman aghaz bazi 9 sobhe
    private Player currentPlayer;

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
}
