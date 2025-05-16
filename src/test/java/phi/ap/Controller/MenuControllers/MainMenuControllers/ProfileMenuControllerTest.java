package phi.ap.Controller.MenuControllers.MainMenuControllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import phi.ap.Controller.MenuControllers.LoginMenuController;
import phi.ap.model.App;
import phi.ap.model.Result;
import phi.ap.utils.Crypto;

public class ProfileMenuControllerTest {
    ProfileMenuController profileMenuController = new ProfileMenuController();
    @Test
    public void givenChangeUsernameWhenSameUsernameThenReturnError() {
        login();
        Result<String> result = profileMenuController.changeUsername("Parsa");
        Result<String> expected = new Result<String>(false, "Provided username is same as the previous one");
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void givenChangeUsernameWhenInvalidUsernameThenReturnError() {
        login();
        Result<String> result = profileMenuController.changeUsername("_Parsa");
        Result<String> expected = new Result(false, "Invalid username");
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void givenChangeUsernameWhenEveryThingOkThenOk() {
        login();
        Result<String> result = profileMenuController.changeNickname("parsa");
        Assertions.assertEquals("parsa",App.getInstance().getLoggedInUser().getUsername());
    }

    @Test
    public void givenChangeNicknameWhenSameUsernameThenReturnError() {
        login();
        Result<String> result = profileMenuController.changeNickname("nickname");
        Result<String> expected = new Result<String>(false, "Provided nickname is same as the previous one");
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void givenChangeNicknameWhenEveryThingOkThenOk() {
        login();
        Result<String> result = profileMenuController.changeNickname("name");
        Assertions.assertEquals("name",App.getInstance().getLoggedInUser().getNickname());
    }

    @Test
    public void givenChangePasswordWhenOldPasswordIsIncorrectThenReturnError() {
        login();
        Result<String> result = profileMenuController.changePassword("ParsaParsa85##", "ParsaParsa8@#");
        Result<String> expected = new Result<String>(false, "Old Password is incorrect");
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void givenChangePasswordWhenNewPassSameOldPassThenReturnError() {
        login();
        Result<String> result = profileMenuController.changePassword("ParsaParsa85@@","ParsaParsa85@@");
        Result<String> expected = new Result(false, "Provided password is same as the previous one");
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void givenChangePasswordWhenEveryThingOkThenOk() {
        login();
        Result<String> result = profileMenuController.changePassword("ParsaParsa85##","ParsaParsa85@@");
        Assertions.assertEquals(Crypto.hash("ParsaParsa85##"),App.getInstance().getLoggedInUser().getPassword());
    }

    @Test
    public void givenChangeEmailWhenSameEmailThenReturnError() {
        login();
        Result<String> result = profileMenuController.changeEmail("iliasar.dar@gmail.com");
        Result<String> expected = new Result<String>(false, "Provided email is same as the previous one");
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void givenChangeEmailWhenInvalidEmailThenReturnError() {
        login();
        Result<String> result = profileMenuController.changeEmail("iliasar..dar@gmail.com");
        Result<String> expected = new Result(false, "Invalid email");
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void givenEmailUsernameWhenEveryThingOkThenOk() {
        login();
        Result<String> result = profileMenuController.changeEmail("parsa.sabzei@gmail.com");
        Assertions.assertEquals("parsa.sabzei@gmail.com",App.getInstance().getLoggedInUser().getEmail());
    }


    private void login(){
        LoginMenuController loginMenuController = new LoginMenuController();
        loginMenuController.register("Parsa", "ParsaParsa85@@",
                "ParsaParsa85@@","nickname", "iliasar.dar@gmail.com", "male");
        loginMenuController.login("Parsa", "ParsaParsa85@@", false);
    }
}
