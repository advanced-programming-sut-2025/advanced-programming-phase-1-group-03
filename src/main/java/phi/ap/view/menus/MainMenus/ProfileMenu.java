package phi.ap.view.menus.MainMenus;

import phi.ap.Controller.MenuControllers.MainMenuControllers.ProfileMenuController;
import phi.ap.model.App;
import phi.ap.model.Result;
import phi.ap.model.enums.Menus.Menu;
import phi.ap.model.enums.commands.CommonCommands;
import phi.ap.model.enums.commands.MainMenuCommands;
import phi.ap.model.enums.commands.ProfileManuCommands;
import phi.ap.view.AppMenu;

import java.util.regex.Matcher;

public class ProfileMenu extends AppMenu {
    ProfileMenuController controller = new ProfileMenuController();
    @Override
    public void check(String input) {
        Matcher matcher = null;
        if((matcher = ProfileManuCommands.ChangeUsername.getMatcher(input)) != null){
            System.out.println(controller.changeUsername(matcher.group("username")));
        } else if((matcher = ProfileManuCommands.ChangeNickname.getMatcher(input)) != null){
            System.out.println(controller.changeNickname(matcher.group("nickname")));
        } else if((matcher = ProfileManuCommands.ChangePassword.getMatcher(input)) != null){
            System.out.println(controller.changePassword(matcher.group("newPassword"), matcher.group("oldPassword")));
        } else if((matcher = ProfileManuCommands.ChangeEmail.getMatcher(input)) != null){
            System.out.println(controller.changeEmail(matcher.group("email")));
        }  else if((matcher = ProfileManuCommands.ShowUserInfo.getMatcher(input)) != null){
            System.out.println(controller.showUserInfo());
        } else if ((matcher = CommonCommands.MenuExit.getMatcher(input)) != null){
            App.getInstance().changeMenu(Menu.MainMenu);
        } else{
            super.check(input);
        }
    }

    @Override
    public void start() {
        System.out.println("Redirecting to profile menu...");
    }
}
