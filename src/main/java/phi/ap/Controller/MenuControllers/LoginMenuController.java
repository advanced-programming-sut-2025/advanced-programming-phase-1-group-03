package phi.ap.Controller.MenuControllers;

import phi.ap.model.App;
import phi.ap.model.Result;
import phi.ap.model.SecurityQuestion;
import phi.ap.model.User;
import phi.ap.model.enums.Gender;
import phi.ap.utils.Crypto;
import phi.ap.utils.FileManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class LoginMenuController {
    public Result<String> login(String username, String password, boolean stayLogged) {
        User user = App.getInstance().getUserService().getUserByUsername(username);
        if(user == null)
            return new Result<>(false, "Username is not valid");
        if(!user.getPassword().equals(Crypto.hash(password)))
            return new Result<>(true, "Password is incorrect");

        if(stayLogged) {
            App.getInstance().setLoggedInUser(user);
        }else
            App.getInstance().setLoggedInUser(null);
        new FileManager().writeAppData();
        return new Result<>(true, "Login successful");
    }
    public Result<String> forgetPassword(String username) {
        User user = App.getInstance().getUserService().getUserByUsername(username);
        if(user == null)
            return new Result<>(false, "user not found");
        return new Result<>(true, user.getSecurityQuestion().getQuestion());
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
        new FileManager().writeAppData();
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

    public String generateRandomPassword() {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String specials = "?><,\"';:/|][}{+=)(*&^%$#!";
        StringBuilder builder = new StringBuilder();

        //adding two special characters
        builder.append(specials.charAt(new Random().nextInt(specials.length())));
        builder.append(new Random().nextInt( 10));
        while(!User.passwordValidity(builder.toString()).success) {
            builder.append(alphabet.charAt(new Random().nextInt(alphabet.length())));
        }
        Character[] chars = new Character[builder.toString().length()];
        for(int i = 0; i < builder.toString().length(); i++)
            chars[i] = builder.toString().charAt(i);
        Collections.shuffle(Arrays.asList(chars));
        StringBuilder shuffled = new StringBuilder();
        for (Character aChar : chars) shuffled.append(aChar);
        return shuffled.toString();
    }

    public Result<String> checkSecurityQuestion(String username, String answerOfSecurityQuestion) {
        User user = getUser(username);
        if(!user.getSecurityQuestion().getAnswer().equals(answerOfSecurityQuestion))
            return new Result<>(false, "Oh, It's not correct...");
        return new Result<>(true, "enter your new password(or type random):");
    }

    public Result<String> changePassword(String username, String newPass, boolean isRandom) {
        User user = getUser(username);
        if(isRandom)
            newPass = generateRandomPassword();
        if(!User.passwordValidity(newPass).success)
            return new Result<>(false, User.passwordValidity(newPass).data);
        user.setPassword(Crypto.hash(newPass));
        return new Result<>(true, "Password changed to " + newPass + "\nnow try to login...");
    }

    private User getUser(String username){
        User user = App.getInstance().getUserService().getUserByUsername(username);
        if(user == null)
            throw new RuntimeException();
        return user;
    }

    public Result<String> logout() {
        App.getInstance().setLoggedInUser(null);
        new FileManager().writeAppData();
        return new Result<>(true, "Logout successful...");
    }
}
