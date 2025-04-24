package phi.ap.service;

import phi.ap.model.User;

import java.util.ArrayList;
import java.util.Arrays;

public class UserService {
    private ArrayList<User> users;
    public UserService(ArrayList<User> users) {
        this.users = users;
    }
    public User getUserByUsername(String username){
        return users.stream()
                .filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }
    public void add(User user) {
        users.add(user);
    }
}
