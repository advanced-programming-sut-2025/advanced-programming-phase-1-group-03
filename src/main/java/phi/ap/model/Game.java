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

    private Integer gameID;
    private Map map;
    private ArrayList<Item> items;
    private Weather currentWeather;
    private Weather tomorrowWeather;
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Friendship> friendships = new ArrayList<>();
    private User gameLoader;
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
        players.add(new Player(user));
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
            if (players.get(i).getUser().getUsername().equals(currentPlayer.getUser().getUsername())) {
                if (i < players.size() - 1) {
                    currentPlayer = players.get(i + 1);
                } else {
                    currentPlayer = players.get(0);
                }
                break;
            }
        }
    }
    public void initializeGame() {
        //TODO
    }

    public Integer getGameID() {
        return gameID;
    }
}
