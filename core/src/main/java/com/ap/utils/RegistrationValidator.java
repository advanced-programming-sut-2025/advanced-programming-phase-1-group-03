package com.ap.utils;

import com.ap.database.SqliteConnection;
import com.ap.model.Result;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.regex.Pattern;

public class RegistrationValidator {
    private final SqliteConnection sqlite;

    public RegistrationValidator(SqliteConnection sqlite) {
        this.sqlite = sqlite;
    }

    /**
     * This method check that is username unique
     * @param username Username wants to register
     * @return If true someone already picked this username
     */
    public boolean duplicateUsername(String username) {
        var sql = """
                SELECT COUNT(*) FROM users WHERE username = ?;
                """;
        try (var statement = sqlite.getConnection().prepareStatement(sql)) {
            statement.setString(1, username);
            var rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            throw new GdxRuntimeException(e.getMessage());
        }
        return false;
    }

    /**
     * This method check that is username valid
     * @param username Given username
     * @return If true, it means username is valid
     */
    public boolean usernameValidity(String username){
        String regex = "[A-Za-z0-9\\-]+";
        return Pattern.compile(regex).matcher(username).matches();
    }

    /**
     * This method check that is email valid
     * @param email Given email
     * @return If true, it means email is valid
     */
    public boolean emailValidity(String email){
        String regex = "^(?![?><,\"';:\\\\\\/|\\]\\[}{+=)(*&^%$#!])(?!.*\\.\\.)(?=[a-zA-Z0-9])[a-zA-Z0-9.\\-_]" +
                "*[a-zA-Z0-9]@(?=[a-zA-Z0-9])[a-zA-Z0-9\\-]*[a-zA-Z0-9]\\.[a-zA-Z]{2,}$";
        return Pattern.compile(regex).matcher(email).matches();
    }

    /**
     * This method check that is password valid
     * @param password Given password
     * @return Suitable error message when failed
     */
    public Result<String> passwordValidity(String password){
        if(password.length() < 8)
            return new Result<>(false, "password must be at least 8 characters");
        if(!password.matches(".*\\d.*"))
            return new Result<>(false, "password must contain at least one digit");
        if(!password.matches(".*[A-Z].*"))
            return new Result<>(false, "password must contain at least one uppercase letter");
        if(!password.matches(".*[a-z].*"))
            return new Result<>(false, "password must contain at least one lowercase letter");
        if(!password.matches(".*[?><,\"';:\\\\/|}{+=)(*&^%@$#!].*"))
            return new Result<>(false, "password must contain at least one special character");
        return new Result<>(true, "");
    }

}
