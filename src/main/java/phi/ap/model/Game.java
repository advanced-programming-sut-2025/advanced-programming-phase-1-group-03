package phi.ap.model;

import phi.ap.model.enums.Weather;
import phi.ap.model.items.Item;
import phi.ap.model.items.relations.Friendship;

import java.util.ArrayList;

public class Game {
    // instance of game
    private static Game instance = null;

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    private ArrayList<Item> items;
    private Weather currentWeather;
    private Weather tomorrowWeather;
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Friendship> friendships = new ArrayList<>();


}
