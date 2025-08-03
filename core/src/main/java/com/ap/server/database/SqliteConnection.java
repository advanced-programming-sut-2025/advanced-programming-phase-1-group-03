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
        Statement stmt = connection.createStatement();
        stmt.execute("""
            CREATE TABLE IF NOT EXISTS rooms (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                isPrivate INTEGER NOT NULL,
                password TEXT,
                ownerName TEXT NOT NULL,
                currentPlayers INTEGER DEFAULT 0
            );
        """);
    }
}
