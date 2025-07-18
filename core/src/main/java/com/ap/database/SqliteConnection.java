package com.ap.database;

import com.badlogic.gdx.utils.GdxRuntimeException;

import java.sql.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class SqliteConnection {
    private Connection connection;

    public boolean connect() {
        String url = "jdbc:sqlite:data.db";
        try {
            connection = DriverManager.getConnection(url);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public void createTables() {
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL UNIQUE,
                    password VARCHAR(25) NOT NULL,
                    email VARCHAR(20) NOT NULL,
                    gender VARCHAR(10) NOT NULL,
                    nickname TEXT NOT NULL,
                    securityQuestionId INTERGER,
                    securityQuestion TEXT
                );
                """;
        try {
            var statement = connection.createStatement();
            statement.execute(sql);
        } catch (Exception e) {
            throw new GdxRuntimeException(e.getMessage());
        }
    }

    public ResultSet runSql(final String sql, PreparedStatementConsumer prepareStatementConsumer) {
        try {
            var statement = connection.prepareStatement(sql);
            prepareStatementConsumer.accept(statement);
            return statement.executeQuery();
        } catch (Exception e) {
            throw new GdxRuntimeException(e.getMessage());
        }
    }
    public void runSqlWithoutResult(final String sql, PreparedStatementConsumer prepareStatementConsumer) {
        try(var statement = connection.prepareStatement(sql)) {
            prepareStatementConsumer.accept(statement);
            statement.execute();
        } catch (Exception e) {
            throw new GdxRuntimeException(e.getMessage());
        }
    }
    public Connection getConnection() {
        return connection;
    }

    @FunctionalInterface
    public interface PreparedStatementConsumer {
        void accept(PreparedStatement preparedStatement) throws SQLException;
    }
}
