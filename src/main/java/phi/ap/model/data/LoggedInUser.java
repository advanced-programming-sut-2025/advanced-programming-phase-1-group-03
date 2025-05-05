package phi.ap.model.data;

import phi.ap.model.App;
import phi.ap.utils.Crypto;

public class LoggedInUser {
    private String username;
    private String nickname;
    public LoggedInUser(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public static LoggedInUser build(){
        if(App.getInstance().getLoggedInUser() == null || !App.getInstance().isSaveUser())
            return null;
        return new LoggedInUser(App.getInstance().getLoggedInUser().getUsername(),
                Crypto.hash(App.getInstance().getLoggedInUser().getNickname()));
    }
}
