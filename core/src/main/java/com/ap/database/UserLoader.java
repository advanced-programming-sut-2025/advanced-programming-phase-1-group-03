package com.ap.database;

import com.ap.model.GameData;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.sql.PreparedStatement;

public class UserLoader {
    public static String getLoggedInUserNickname(SqliteConnection sqlite) {
        if(GameData.getInstance().getLoggedUserUsername() == null) {
            return "Guest";
        }
        var sql = """
                SELECT nickname FROM users WHERE username = ?;
                """;
        var result = sqlite.runSql(sql, (PreparedStatement ps) -> {
            ps.setString(1, GameData.getInstance().getLoggedUserUsername());
        });
        try {
            if (result.next()) {
                return result.getString(1);
            } else {
                GameData.getInstance().setLoggedUserUsername(null);
                return "Guest";
            }
        }catch (Exception e) {
            throw new GdxRuntimeException(e.getMessage());
        }
    }

    public static Integer getLoggedInUserMaximumCoin(SqliteConnection sqlite) {
        if(GameData.getInstance().getLoggedUserUsername() == null) {
            throw new GdxRuntimeException("You are not logged in");
        }
        var sql = """
                SELECT maximumCoin FROM users WHERE username = ?;
                """;
        return getFirstInt(sqlite, sql);
    }

    public static int getLoggedInUserGamesCount(SqliteConnection sqlite) {
        if(GameData.getInstance().getLoggedUserUsername() == null) {
            throw new GdxRuntimeException("You are not logged in");
        }
        var sql = """
                SELECT gamesCount FROM users WHERE username = ?;
                """;
        return getFirstInt(sqlite, sql);
    }

    private static int getFirstInt(SqliteConnection sqlite, String sql) {
        var result = sqlite.runSql(sql, (PreparedStatement ps) -> {
            ps.setString(1, GameData.getInstance().getLoggedUserUsername());
        });
        try {
            if (result.next()) {
                return result.getInt(1);
            }
        }catch (Exception e) {
            throw new GdxRuntimeException(e.getMessage());
        }
        return 0;
    }

    public static String getLoggedInUserEmail(SqliteConnection sqlite) {
        var sql = """
                SELECT email FROM users WHERE username = ?;
                """;
        var result = sqlite.runSql(sql, (PreparedStatement ps) -> {
            ps.setString(1, GameData.getInstance().getLoggedUserUsername());
        });
        try {
            if (result.next()) {
                return result.getString(1);
            }
        }catch (Exception e) {
            throw new GdxRuntimeException(e.getMessage());
        }
        return "";
    }

    public static int getLoggedInUserAvatarIndex(SqliteConnection sqlite) {
        if(GameData.getInstance().getLoggedUserUsername() == null) {
            throw new GdxRuntimeException("You are not logged in");
        }
        var sql = """
                SELECT avatarIndex FROM users WHERE username = ?;
                """;
        return getFirstInt(sqlite, sql);
    }

    public static void changeAvatarIndex(SqliteConnection sqlite, int i) {
        var sql = """
                UPDATE users SET avatarIndex = ? WHERE username = ?;
                """;
        sqlite.runSqlWithoutResult(sql, (PreparedStatement ps) -> {
            ps.setInt(1, i);
            ps.setString(2, GameData.getInstance().getLoggedUserUsername());
        });
    }

    public static void changeUsername(SqliteConnection sqlite, String username) {
        var sql = """
                UPDATE users SET username = ? WHERE username = ?;
                """;
        sqlite.runSqlWithoutResult(sql, (PreparedStatement ps) -> {
            ps.setString(1, username);
            ps.setString(2, GameData.getInstance().getLoggedUserUsername());
        });
    }

    public static void changeNickname(SqliteConnection sqlite, String nickname) {
        var sql = """
                UPDATE users SET nickname = ? WHERE username = ?;
                """;
        sqlite.runSqlWithoutResult(sql, (PreparedStatement ps) -> {
            ps.setString(1, nickname);
            ps.setString(2, GameData.getInstance().getLoggedUserUsername());
        });
    }

    public static void changeEmail(SqliteConnection sqlite, String email) {
        var sql = """
                UPDATE users SET email = ? WHERE username = ?;
                """;
        sqlite.runSqlWithoutResult(sql, (PreparedStatement ps) -> {
            ps.setString(1, email);
            ps.setString(2, GameData.getInstance().getLoggedUserUsername());
        });
    }

    public static void changePassword(SqliteConnection sqlite, String password) {
        var sql = """
                UPDATE users SET password = ? WHERE username = ?;
                """;
        sqlite.runSqlWithoutResult(sql, (PreparedStatement ps) -> {
            ps.setString(1, password);
            ps.setString(2, GameData.getInstance().getLoggedUserUsername());
        });
    }
}
