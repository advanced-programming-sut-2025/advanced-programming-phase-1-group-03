package phi.ap.Controller.MenuControllers;

import phi.ap.model.App;
import phi.ap.model.Result;
import phi.ap.model.SecurityQuestion;
import phi.ap.model.User;
import phi.ap.model.enums.Gender;
import phi.ap.utils.Crypto;

import java.util.Random;

public class LoginMenuController {
    public Result<String> login(String username, String password, String stayLogged) {
        return null;
    }
    public Result<String> forgetPassword(String username) {
        return null;
    }
    public Result<String> answerSecurityQuestion(String answer) {
        return null;
    }

    public Result<String> register(String username, String password, String passwordConfirm,
                                String nickname, String email, String gender) {
        //add random number until username become unique
        while(App.getInstance().getUserService().getUserByUsername(username) != null)
            username += new Random().nextInt(100) + "";

        User user = new User();
        if(!user.setUsername(username))
            return new Result<>(false, "username is not valid...");
        if(!user.setEmail(email))
            return new Result<>(false, "email is not valid...");
        if(!User.passwordValidity(password).success)
            return User.passwordValidity(password);
        //Encrypt password and set
        user.setPassword(Crypto.hash(password));
        if(!password.equals(passwordConfirm))
            return new Result<>(false, "password and confirmation password do not match try again...");
        user.setUsername(username);
        Gender convertedGender = Gender.fromString(gender);
        if(convertedGender == null)
            return new Result<>(false, "gender is not valid...");
        user.setGender(convertedGender);
        user.setNickname(nickname);
        App.getInstance().getUserService().add(user);
        return new Result<>(true, "Registration was successful...");
    }

    public Result<String> pickQuestion(String username, String questionText,
                                       String answer, String answerConfirm) {
        if(!answer.equals(answerConfirm))
            return new Result<>(false, "answer and confirm answer do not match...");
        User user = App.getInstance().getUserService().getUserByUsername(username);

        if(user == null)
            throw new RuntimeException();
        user.setSecurityQuestion(new SecurityQuestion(questionText, answer));
        return new Result<>(true, "ok....");
    }
}
