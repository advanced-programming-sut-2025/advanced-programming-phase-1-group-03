package phi.ap.model.data;

import phi.ap.model.App;
import phi.ap.model.User;

import java.util.ArrayList;

public class AppData {
    private LoggedInUser loggedInUser;
    private ArrayList<User> users;
    public AppData(LoggedInUser user, ArrayList<User> users) {
        this.loggedInUser = user;
        this.users = users;
    }

    public LoggedInUser getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(LoggedInUser user) {
        this.loggedInUser = user;
    }

    public static AppData build(){
        AppData data = new AppData(LoggedInUser.build(), App.getInstance().getUsers());
        return data;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
    public ArrayList<User> getUsers() {
        if(users == null) users = new ArrayList<>();
        return users;
    }
}
