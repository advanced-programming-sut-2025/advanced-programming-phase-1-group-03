package phi.ap.Controller.MenuControllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import phi.ap.model.App;
import phi.ap.model.Result;

public class LoginMenuControllerTest {
    LoginMenuController loginMenuController = new LoginMenuController();

    @Test
    public void givenRegisterWhenInvalidUsernameThenReturnError() {
        Result<String> expected = new Result<>(false, "username is not valid...");
        Result<String> result = loginMenuController.register("test_we", "sample Pass", "sample Pass",
                "nickname", "SampleEmail", "gender");
        Assertions.assertEquals(expected, result);
    }
    @Test
    public void givenRegisterWhenDuplicateThenChangeUsername() {
        loginMenuController.register("Parsa", "ParsaParsa85@@",
                "ParsaParsa85@@","nickname", "ilia.sardar@gmail.com", "gender");
        Assertions.assertEquals(App.getInstance().getUserService().getUserByUsername("Parsa"), null);
    }
    @Test
    public void givenRegisterWhenInvalidEmailThenReturnError() {
        Result<String> expected = new Result<>(false, "email is not valid...");
        Result<String> result = loginMenuController.register("Parsa", "ParsaParsa85@@",
                "ParsaParsa85@@","nickname", "iliasar.dar@gmailcom", "gender");
        Assertions.assertEquals(expected, result);
    }
    @Test
    public void givenRegisterWhenPasswordAndPasswordConfirmAreNotSameThenReturnError() {
        Result<String> expected = new Result<>(false, "password and confirmation password do not match try again...");
        Result<String> result = loginMenuController.register("Parsa", "ParsaParddsa85@@",
                "ParsaParsa85@@","nickname", "iliasar.dar@gmail.com", "gender");
        Assertions.assertEquals(expected, result);
    }
    @Test
    public void givenRegisterWhenGenderIsNotValidThenReturnError() {
        LoginMenuController loginMenuController = new LoginMenuController();
        Result<String> expected = new Result<>(false, "gender is not valid...");
        Result<String> result = loginMenuController.register("Parsa", "ParsaParsa85@@",
                "ParsaParsa85@@","nickname", "iliasar.dar@gmail.com", "gay");
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void givenLoginWhenInvalidUsernameThenReturnError() {
        Result<String> expected = new Result<>(false, "Username is not valid");
        loginMenuController.register("Parsa", "ParsaParsa85@@",
                "ParsaParsa85@@","nickname", "iliasar.dar@gmail.com", "male");
        Result<String> result = loginMenuController.login("Test", "ParsaParsa85@@", false);
        Assertions.assertEquals(expected, result);
    }
    @Test
    public void givenLoginWhenInvalidPasswordThenReturnError() {
        Result<String> expected = new Result<>(false, "Password is incorrect");
        loginMenuController.register("Parsa", "ParsaParsa85@@",
                "ParsaParsa85@@","nickname", "iliasar.dar@gmail.com", "male");
        Result<String> result = loginMenuController.login("Parsa", "ParsaPars85@@", false);
        Assertions.assertEquals(expected, result);
    }
    @Test
    public void givenLoginThenLoginSuccess() {
        Result<String> expected = new Result<>(true, "Login successful");
        loginMenuController.register("Parsa", "ParsaParsa85@@",
                "ParsaParsa85@@","nickname", "iliasar.dar@gmail.com", "male");
        Result<String> result = loginMenuController.login("Parsa", "ParsaParsa85@@", false);
        Assertions.assertEquals(expected, result);
    }
    @Test
    public void givenForgetPasswordWhenInvalidUserThenError() {
        Result<String> expected = new Result<>(false, "user not found");
        loginMenuController.register("Parsa", "ParsaParsa85@@",
                "ParsaParsa85@@","nickname", "iliasar.dar@gmail.com", "male");
        loginMenuController.login("Parsa", "ParsaParsa85@@", false);
        Result<String> result = loginMenuController.forgetPassword("Pars");
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void givenPickQuestionWhenAnswerAndConfirmAnswerIsNotSameThenError() {
        Result<String> expected = new Result<>(false, "answer and confirm answer do not match...");
        loginMenuController.register("Parsa", "ParsaParsa85@@",
                "ParsaParsa85@@","nickname", "iliasar.dar@gmail.com", "male");
        loginMenuController.login("Parsa", "ParsaParsa85@@", false);
        Result<String> result = loginMenuController.pickQuestion("Parsa", "Test"
                , "ans", "answer");
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void givenPickQuestionWhenEveryThingOkThenSuccess() {
        Result<String> expected = new Result<>(false, "answer and confirm answer do not match...");
        loginMenuController.register("Parsa", "ParsaParsa85@@",
                "ParsaParsa85@@","nickname", "iliasar.dar@gmail.com", "male");
        loginMenuController.login("Parsa", "ParsaParsa85@@", false);
        loginMenuController.pickQuestion("Parsa", "Test"
                , "answer", "answer");
        Assertions.assertEquals(
                "answer",App.getInstance().getUserService().getUserByUsername("Parsa").getSecurityQuestion().getAnswer());
    }

    @Test
    public void givenLogoutThenSuccess() {
        loginMenuController.register("Parsa", "ParsaParsa85@@",
                "ParsaParsa85@@","nickname", "iliasar.dar@gmail.com", "male");
        loginMenuController.login("Parsa", "ParsaParsa85@@", false);
        loginMenuController.logout();
        Assertions.assertNull(App.getInstance().getLoggedInUser());
    }
}
