package phi.ap.model;

import phi.ap.model.enums.Menus.Menu;
import phi.ap.service.*;

import java.util.ArrayList;
import java.util.Random;

public class App {
    private static App instance = null;
    private User loggedInUser;
    private Random rand = new Random(123);
    private ArrayList<User> users = new ArrayList<>();
    private Menu menu = Menu.LoginMenu;
    private boolean saveUser;

    public static App getInstance() {
        if (instance == null)
            instance = new App();
        return instance;
    }

    private App() {}

    public Menu getMenu() {
        return menu;
    }
    public void changeMenu(Menu newMenu) {
        menu = newMenu;
        menu.start();
    }
    public UserService getUserService(){
        return new UserService(this.users);
    }
    public GameService getGameService(){
        if (Game.getInstance() == null) return null;
        return new GameService(Game.getInstance());
    }
    public MapService getMapService(){
        Map map = null;
        if (Game.getInstance() != null) map = Game.getInstance().getMap();
        return new MapService(map);
    }
    public PlayerService getPlayerService(){
        return new PlayerService(Game.getInstance());
    }
    public void load(){
        LoadService loadService = new LoadService();
        loadService.loadAppData();
    }

    public ArrayList<User> getUsers() {
        return users;
    }
    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public int getRandomNumber(int l, int r) {
        if (l == r) return l;
        return rand.nextInt(r - l + 1) + l;
    }

    public boolean eventRandom(int probabilityPercent) { //probability from 1 to 100
        return getRandomNumber(1, 100) <= probabilityPercent;
    }

    public boolean isSaveUser() {
        return saveUser;
    }

    public void setSaveUser(boolean saveUser) {
        this.saveUser = saveUser;
    }
}
