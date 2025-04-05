package phi.ap.model;

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
}
