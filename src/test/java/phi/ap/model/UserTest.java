package phi.ap.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void givenEmailWhenTwoAtSignThenInvalid(){
        User user = new User();
        Assertions.assertFalse(user.setEmail("parsa.sab@zei@gmail.com"));
    }

    @Test
    public void givenEmailWhenBeforeAtSignContainInvalidCharacterThenInvalid(){
        User user = new User();
        Assertions.assertFalse(user.setEmail("parsa.sa!bzei@gmail.com"));
        Assertions.assertFalse(user.setEmail("parsa.sa%bzei@gmail.com"));
        Assertions.assertFalse(user.setEmail("parsa.sa&bzei@gmail.com"));
        Assertions.assertFalse(user.setEmail("parsa.sa*bzei@gmail.com"));
    }
    @Test
    public void givenEmailWhenBeforeAtSignInvalidStartAndEndThenInvalid(){
        User user = new User();
        Assertions.assertFalse(user.setEmail(".parsa.sabzei@gmail.com"));
        Assertions.assertFalse(user.setEmail("parsa.sabzei.@gmail.com"));
        Assertions.assertFalse(user.setEmail("-parsa.sabzei@gmail.com"));
        Assertions.assertFalse(user.setEmail("parsa.sabzei_@gmail.com"));
    }
    @Test
    public void givenEmailWhenTwoConsecutiveDotThenInvalid(){
        User user = new User();
        Assertions.assertFalse(user.setEmail("parsa..sabzei@gmail.com"));
        Assertions.assertFalse(user.setEmail("pa..rsasabzei@gmail.com"));
        Assertions.assertTrue(user.setEmail("pa.rsas.abzei@gmail.com"));
    }
    @Test
    public void givenEmailAfterAtSignContainDotThenInvalid(){
        User user = new User();
        Assertions.assertFalse(user.setEmail("parsa.sabzei@gmailcom"));
        Assertions.assertTrue(user.setEmail("parsa.sabzei@gmail.com"));
        Assertions.assertFalse(user.setEmail("pa..rsasabzei@gmail..com"));
    }
    @Test
    public void givenEmailWhenDomainFewerThanTwoCharactersThenInvalid(){
        User user = new User();
        Assertions.assertFalse(user.setEmail("parsa.sabzei@gmail.c"));
        Assertions.assertFalse(user.setEmail("parsa.sabzei@gmail.g"));
    }
    @Test
    public void givenEmailWhenAfterAtSignContainInvalidCharactersThenInvalid(){
        User user = new User();
        Assertions.assertFalse(user.setEmail("parsa.sabzei@gmai@l.com"));
        Assertions.assertFalse(user.setEmail("parsa.sabzei@gmai_l.com"));
        Assertions.assertFalse(user.setEmail("parsa.sabzei@g!mail.com"));
    }
    @Test
    public void givenEmailWhenAfterAtSignDoesntStartWithNumberOfLetterThenInvalid(){
        User user = new User();
        Assertions.assertFalse(user.setEmail("parsa.sabzei@%gmail.com"));
        Assertions.assertFalse(user.setEmail("parsa.sabzei@-gmai_l.com"));
        Assertions.assertFalse(user.setEmail("parsa.sabzei@_gmail.com"));
        Assertions.assertFalse(user.setEmail("parsa.sabzei@gmail.com_"));
    }
    @Test
    public void givenEmailWhenContainsInvalidCharactersThenInvalid(){
        User user = new User();
        Assertions.assertFalse(user.setEmail("parsa.sabz$ei@gmail.com"));
        Assertions.assertFalse(user.setEmail("parsa.sabz?ei@gmail.com"));
        Assertions.assertFalse(user.setEmail("parsa.sabzei@gm<ail.com"));
        Assertions.assertFalse(user.setEmail("par>sa.sabzei@gmail.com"));
        Assertions.assertFalse(user.setEmail("parsa'sabzei@gmail.com"));
        Assertions.assertFalse(user.setEmail("par{ssabzei@gmail.com"));
        Assertions.assertFalse(user.setEmail("parssabzei@gma}il.com"));
        Assertions.assertFalse(user.setEmail("parsa.sabzei@gm%ail.com"));
        Assertions.assertFalse(user.setEmail("parsa.sabzei@gm]ail.com"));
    }

    @Test
    public void givenPasswordWhenHasFewerThan8CharactersThenInvalid(){
        User user = new User();
        Result<String> expected = new Result<>(false, "password must be at least 8 characters");
        Result<String> result = User.passwordValidity("p2rsaP@");
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void givenPasswordWhenOnlyHasSmallLettersThenInvalid(){
        User user = new User();
        Result<String> expected = new Result<>(false, "password must contain at least one uppercase letter");
        Result<String> result = User.passwordValidity("parsadrgd@2");
        Assertions.assertEquals(expected, result);
    }
    @Test
    public void givenPasswordWhenDoesntHaveDigitThenInvalid(){
        User user = new User();
        Result<String> expected = new Result<>(false, "password must contain at least one digit");
        Result<String> result = User.passwordValidity("parsadrgSd@");
        Assertions.assertEquals(expected, result);
    }
    @Test
    public void givenPasswordWhenOnlyHasBigLettersThenInvalid(){
        User user = new User();
        Result<String> expected = new Result<>(false, "password must contain at least one lowercase letter");
        Result<String> result = User.passwordValidity("PSDFLSJKDS@2");
        Assertions.assertEquals(expected, result);
    }
    @Test
    public void givenPasswordWhenDoesntHaveSpecialCharsThenInvalid(){
        User user = new User();
        Result<String> expected = new Result<>(false, "password must contain at least one special character");
        Result<String> result = User.passwordValidity("PSDdsfFLSJKDS2");
        Assertions.assertEquals(expected, result);
        result = User.passwordValidity("sdkfjaaSDf234");
        Assertions.assertEquals(expected, result);
    }
}
