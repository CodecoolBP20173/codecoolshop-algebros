package com.codecool.shop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JDBCUtils {

    private static final String DATABASE = System.getenv("database");
    private static final String DB_USER = System.getenv("databaseUsername");
    private static final String DB_PASSWORD = System.getenv("databasePassword");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }
}
