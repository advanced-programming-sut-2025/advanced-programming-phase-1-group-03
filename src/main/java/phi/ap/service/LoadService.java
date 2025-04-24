package phi.ap.service;

import phi.ap.model.App;
import phi.ap.model.User;
import phi.ap.model.data.AppData;
import phi.ap.model.data.LoggedInUser;
import phi.ap.utils.Crypto;
import phi.ap.utils.FileManager;

public class LoadService {
    public void loadAppData() {
        AppData data = new FileManager().readAppData();
        App.getInstance().setUsers(data.getUsers());

        LoggedInUser loggedInUser = data.getLoggedInUser();
        User loggedIn = loggedInUser != null ?
                App.getInstance().getUserService().getUserByUsername(loggedInUser.getUsername()) : null;
        if(loggedIn == null || !Crypto.hash(loggedIn.getNickname()).equals(loggedInUser.getNickname()))
            loggedIn = null;
        App.getInstance().setLoggedInUser(loggedIn);
    }
}
