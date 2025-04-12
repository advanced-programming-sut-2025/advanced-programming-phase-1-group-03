package phi.ap.model;

import phi.ap.model.enums.Weather;
import phi.ap.model.items.Item;

import java.util.ArrayList;

public class Game {
    // instance of game
    private static Game instance = null;

    private final ArrayList<Player> players = new ArrayList<Player>();

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    private ArrayList<Item> items;
    private Weather currentWeather;
        private Weather tomorrowWeather;
}
