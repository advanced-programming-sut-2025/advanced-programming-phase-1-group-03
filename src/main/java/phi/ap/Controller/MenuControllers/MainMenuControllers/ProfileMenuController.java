package phi.ap.Controller.MenuControllers.MainMenuControllers;

import phi.ap.Controller.MenuControllers.LoginMenuController;
import phi.ap.model.App;
import phi.ap.model.Result;
import phi.ap.model.User;
import phi.ap.utils.Crypto;

public class ProfileMenuController {
    public Result<String> changeUsername(String username) {
        User user = App.getInstance().getLoggedInUser();
        if(user.getUsername().equals(username))
            return new Result<>(false, "Provided username is same as the previous one");
        if(!user.setUsername(username))
            return new Result<>(false, "Invalid username");
        return new Result<>(true, "Username successfully changed");
    }
    public Result<String> changeNickname(String nickname) {
        User user = App.getInstance().getLoggedInUser();
        if(user.getNickname().equals(nickname))
            return new Result<>(false, "Provided nickname is same as the previous one");
        user.setNickname(nickname);
        return new Result<>(true, "Nickname successfully changed");
    }
    public Result<String> changeEmail(String email) {
        User user = App.getInstance().getLoggedInUser();
        if(user.getEmail().equals(email))
            return new Result<>(false, "Provided nickname is same as the previous one");
        if(!user.setEmail(email))
            return new Result<>(false, "Invalid email");
        return new Result<>(true, "Email successfully changed");
    }
    public Result<String> changePassword(String newPassword, String oldPassword) {
        User user = App.getInstance().getLoggedInUser();
        if(!user.getPassword().equals(Crypto.hash(oldPassword)))
            return new Result<>(false, "Old Password is incorrect");
        if(oldPassword.equals(newPassword))
            return new Result<>(false, "Provided password is same as the previous one");
        return new LoginMenuController().changePassword(App.getInstance().getLoggedInUser().getUsername(), newPassword, false);
    }
    public Result<String> showUserInfo() {
        User user = App.getInstance().getLoggedInUser();

        StringBuilder builder = new StringBuilder();
        builder.append("Username: ").append(user.getUsername()).append("\n");
        builder.append("Nickname: ").append(user.getNickname()).append("\n");
        builder.append("Hashed Password: ").append(user.getPassword()).append("\n");
        builder.append("Email: ").append(user.getEmail()).append("\n");

        //TODO highest money in a game
        //TODO number of games played
        return new Result<>(true, builder.toString());
    }
}
