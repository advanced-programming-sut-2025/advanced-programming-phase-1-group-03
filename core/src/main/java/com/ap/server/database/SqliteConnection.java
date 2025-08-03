package com.ap.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteConnection {
    public static SqliteConnection instance = new SqliteConnection();

    public Connection connection;

    public void init() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:rooms.db");
    }
}
