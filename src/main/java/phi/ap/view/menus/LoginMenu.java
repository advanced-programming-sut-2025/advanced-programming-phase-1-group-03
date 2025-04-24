package phi.ap.view.menus;

import phi.ap.Controller.MenuControllers.LoginMenuController;
import phi.ap.model.App;
import phi.ap.model.Result;
import phi.ap.model.enums.Menus.Menu;
import phi.ap.model.enums.commands.CommonCommands;
import phi.ap.model.enums.commands.LoginMenuCommands;
import phi.ap.view.AppMenu;
import phi.ap.view.AppView;

import java.util.List;
import java.util.regex.Matcher;

public class LoginMenu extends AppMenu {
    LoginMenuController controller = new LoginMenuController();
    @Override
    public void check(String input) {
        Matcher matcher = null;
        if((matcher = CommonCommands.MenuExit.getMatcher(input)) != null){
            App.getInstance().changeMenu(Menu.ExitMenu);
        }else if((matcher = LoginMenuCommands.Register.getMatcher(input)) != null){
            String username = matcher.group("username").trim();
            String password = matcher.group("password").trim();
            String passwordConfirm = matcher.group("passwordConfirm").trim();
            String nickname = matcher.group("nickname").trim();
            String email = matcher.group("email").trim();
            String gender = matcher.group("gender").trim();

            //generate random password
            if(password.equals("random")){
                while(true) {
                    password = controller.generateRandomPassword();
                    passwordConfirm = password;
                    System.out.println("is \"" + password + "\" a good password?(yes/no)");
                    String answer = AppView.scanner.nextLine();
                    if(answer.equals("yes"))
                        break;
                }
            }
            Result<String> result = controller.register(username, password, passwordConfirm, nickname, email, gender);
            System.out.println(result);
            if(!result.success)
                return;
            String[] questions = {"What is your dad's name?", "What is name of your first school?",
                    "What is month of your birthday?"};
            while(true){
                System.out.println("Pick one question from list below: \n" +
                        "1."+questions[0]+"\n" +
                        "2."+questions[1]+"\n"+
                        "3."+questions[2]+"\n");
                String inp = AppView.scanner.nextLine();
                if((matcher = LoginMenuCommands.PickQuestion.getMatcher(inp)) == null){
                    System.out.println("select question...");
                    continue;
                }
                int questionNumber = Integer.parseInt(matcher.group("questionNumber").trim())-1;
                String answer = matcher.group("answer").trim();
                String answerConfirm = matcher.group("answerConfirm").trim();
                Result<String> newResult = controller.pickQuestion(username, questions[questionNumber], answer, answerConfirm);
                System.out.println(newResult);
                if(newResult.success)
                    break;
            }
        }else if((matcher = CommonCommands.MenuEnter.getMatcher(input)) != null){
            String menu = matcher.group("menu").trim();
            App.getInstance().changeMenu(Menu.fromString(menu));
        }else if((matcher = LoginMenuCommands.Login.getMatcher(input)) != null){
            String username = matcher.group("username").trim();
            String password = matcher.group("password").trim();
            boolean stay = matcher.group("stay") != null;
            Result<String> result = controller.login(username, password, stay);
            System.out.println(result);
            if(result.success)
                App.getInstance().changeMenu(Menu.MainMenu);
        }else if((matcher = LoginMenuCommands.ForgetPassword.getMatcher(input)) != null){
            String username = matcher.group("username").trim();
            Result<String> result = controller.forgetPassword(username);
            System.out.println(result);
            if(!result.success)
                return;
            while(true){
                String inp = AppView.scanner.nextLine();
                Result<String> newResult = controller.checkSecurityQuestion(username, inp);
                System.out.println(newResult);
                if(newResult.success)
                    break;
            }
            while(true){
                String newPass = AppView.scanner.nextLine();
                Result<String> newResult = controller.changePassword(username, newPass, newPass.equals("random"));
                System.out.println(newResult);
                if(newResult.success)
                    break;
            }
        }else{
            super.check(input);
        }
    }

    @Override
    public void start() {

    }
}
