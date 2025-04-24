package phi.ap.model;

import phi.ap.model.enums.Menus.Menu;
import phi.ap.service.LoadService;
import phi.ap.service.UserService;

import java.util.ArrayList;

public class App {
    private static App instance = null;
    private User loggedInUser;

    private ArrayList<User> users;
    private Menu menu = Menu.LoginMenu;

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
}
