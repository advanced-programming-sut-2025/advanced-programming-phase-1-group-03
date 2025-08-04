package com.ap.server.database;

import com.ap.global.model.Gender;
import com.ap.global.model.Result;
import com.ap.global.utils.Crypto;
import com.ap.global.utils.RegistrationValidator;

import java.sql.PreparedStatement;

public class UserLoader {
    public static String getUserNickname(String username) {
        var sql = """
                SELECT nickname FROM users WHERE username = ?;
                """;
        var result = SqliteConnection.instance.runSql(sql, (PreparedStatement ps) -> {
            ps.setString(1, username);
        });
        try {
            if (result.next()) {
                return result.getString(1);
            }
        }catch (Exception e) {
        }
        return null;
    }
    public static Integer getUserMaximumCoin(String username) {
        var sql = """
                SELECT maximumCoin FROM users WHERE username = ?;
                """;
        return getFirstInt(sql, username);
    }

    public static int getUserGamesCount(String username) {
        var sql = """
                SELECT gamesCount FROM users WHERE username = ?;
                """;
        return getFirstInt(sql, username);
    }
    private static int getFirstInt(String sql, String username) {
        var result = SqliteConnection.instance.runSql(sql, (PreparedStatement ps) -> {
            ps.setString(1, username);
        });
        try {
            if (result.next()) {
                return result.getInt(1);
            }
        }catch (Exception ignored) {
        }
        return 0;
    }

    public static String getUserEmail(String username) {
        var sql = """
                SELECT email FROM users WHERE username = ?;
                """;
        var result = SqliteConnection.instance.runSql(sql, (PreparedStatement ps) -> {
            ps.setString(1, username);
        });
        try {
            if (result.next()) {
                return result.getString(1);
            }
        }catch (Exception ignored) {
        }
        return null;
    }
    public static int getUserAvatarIndex(String username) {
        var sql = """
                SELECT avatarIndex FROM users WHERE username = ?;
                """;
        return getFirstInt(sql, username);
    }

    public static void changeAvatarIndex(String username, int i) {
        var sql = """
                UPDATE users SET avatarIndex = ? WHERE username = ?;
                """;
        SqliteConnection.instance.runSqlWithoutResult(sql, (PreparedStatement ps) -> {
            ps.setInt(1, i);
            ps.setString(2, username);
        });
    }
    public static Result<String> changeUsername(String username, String newUsername) {
        var validator = new RegistrationValidator();
        if(!validator.usernameValidity(newUsername)) {
            return new Result<>(false, "Username is not valid");
        }
        if(validator.duplicateUsername(newUsername, SqliteConnection.instance)) {
            return new Result<>(false, "Username already exists");
        }
        var sql = """
                UPDATE users SET username = ? WHERE username = ?;
                """;
        SqliteConnection.instance.runSqlWithoutResult(sql, (PreparedStatement ps) -> {
            ps.setString(1, newUsername);
            ps.setString(2, username);
        });
        return new Result<>(true, "Username changed successfully");
    }

    public static void changeNickname(String username, String nickname) {
        var sql = """
                UPDATE users SET nickname = ? WHERE username = ?;
                """;
        SqliteConnection.instance.runSqlWithoutResult(sql, (PreparedStatement ps) -> {
            ps.setString(1, nickname);
            ps.setString(2, username);
        });
    }

    public static void changeEmail(String username, String email) {
        var sql = """
                UPDATE users SET email = ? WHERE username = ?;
                """;
        SqliteConnection.instance.runSqlWithoutResult(sql, (PreparedStatement ps) -> {
            ps.setString(1, email);
            ps.setString(2, username);
        });
    }

    public static void changePassword(String username, String password) {
        var sql = """
                UPDATE users SET password = ? WHERE username = ?;
                """;
        SqliteConnection.instance.runSqlWithoutResult(sql, (PreparedStatement ps) -> {
            ps.setString(1, password);
            ps.setString(2, username);
        });
    }

    public static int getSecurityQuestionId(String username) {
        var sql = """
                SELECT securityQuestionId FROM users WHERE username = ?;
                """;
        var result = SqliteConnection.instance.runSql(sql, (PreparedStatement ps) -> {
            ps.setString(1, username);
        });
        try {
            if (result.next()) {
                return result.getInt(1);
            }
        }catch (Exception ignored) {
        }
        return -1;
    }
    public static Result<String> signupUser(String username, String nickname,
                                  String email, String password, String confPassword, Gender gender, int secQuestionNumber, String securityQuestion) {
        var validator = new RegistrationValidator();

        if(validator.duplicateUsername(username, SqliteConnection.instance)) {
            return new Result<>(false, "A user with username already exists");
        }
        if(!validator.usernameValidity(username)) {
            return new Result<>(false, "Username is not valid");
        }
        if(!validator.emailValidity(email)) {
            return new Result<>(false, "Email is not valid");
        }
        if(!validator.passwordValidity(password).isSuccess()) {
            return validator.passwordValidity(password);
        }
        if(!password.equals(confPassword)) {
            return new Result<>(false, "Password and its confirmation don't match");
        }
        if(gender == null) {
            return new Result<>(false, "Please select gender");
        }

        var sql = """
                INSERT INTO users(username, password, email, gender, nickname, securityQuestionId, securityQuestion) VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        SqliteConnection.instance.runSqlWithoutResult(sql, (PreparedStatement stm) -> {
            stm.setString(1, username);
            stm.setString(2, Crypto.hash(password));
            stm.setString(3, email);
            stm.setString(4, gender.name());
            stm.setString(5, nickname);
            stm.setInt(6, secQuestionNumber);
            stm.setString(7, securityQuestion);
        });
        return new Result<>(true, "Registration was successful");
    }

    public static boolean passSecurityQ(String username, String securityQuestionAnswer) {
        var sql = """
                SELECT securityQuestion FROM users WHERE username = ?;
                """;
        var result = SqliteConnection.instance.runSql(sql, (PreparedStatement ps) -> {
            ps.setString(1, username);
        });
        try {
            if(result.next()) {
                return result.getString(1).equalsIgnoreCase(securityQuestionAnswer);
            }
        }catch (Exception ignored) {
        }
        return false;
    }

    public static Result<String> changePassword(String username, String password, String secQAns) {
        if(!passSecurityQ(username, secQAns)) {
            return new Result<>(false, "Security question is not correct");
        }
        var validator = new RegistrationValidator();

        var sql = """
                UPDATE users SET password = ? WHERE username = ?;
                """;
        if(!validator.passwordValidity(password).isSuccess()) {
            return validator.passwordValidity(password);
        }
        SqliteConnection.instance.runSqlWithoutResult(sql, (PreparedStatement ps) -> {
            ps.setString(1, Crypto.hash(password));
            ps.setString(2, username);
        });
        return new Result<>(true, "Password changed successfully");
    }
}
