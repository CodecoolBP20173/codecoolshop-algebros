package com.codecool.shop.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class SqlUserUtils {
    public static void addUser(HashMap<String, String> userInfo) {
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO users (username,password,email,salt) VALUES (?,?,?,?);");
            stmt.setString(1, userInfo.get("name"));
            stmt.setString(2, userInfo.get("hashedpwd"));
            stmt.setString(3, userInfo.get("email"));
            stmt.setString(4, userInfo.get("salt"));
            stmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void updateUser(HashMap<String, String> userInfo) {
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("UPDATE users SET " +
                    "fullname = ?," +
                    "email = ?," +
                    "phone = ?," +
                    "zip = ?," +
                    "city = ?," +
                    "country = ?," +
                    "address = ?" +
                    "WHERE id = ?;");
            stmt.setString(1, userInfo.get("name"));
            stmt.setString(2, userInfo.get("email"));
            stmt.setString(3, userInfo.get("phone"));
            stmt.setString(4, userInfo.get("zip"));
            stmt.setString(5, userInfo.get("city"));
            stmt.setString(6, userInfo.get("country"));
            stmt.setString(7, userInfo.get("address"));
            stmt.setInt(8, Integer.parseInt(userInfo.get("userid")));
            stmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static HashMap<String, String> getUser(String email) {
        HashMap<String, String> userInfo = new HashMap<>();
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
            stmt.setString(1, email);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                userInfo.put("id", result.getString("id"));
                userInfo.put("password", result.getString("password"));
                userInfo.put("salt", result.getString("salt"));
                userInfo.put("email", result.getString("email"));
                userInfo.put("fullname", result.getString("fullname"));
                userInfo.put("username", result.getString("username"));
                userInfo.put("phone", result.getString("phone"));
                userInfo.put("country", result.getString("country"));
                userInfo.put("city", result.getString("city"));
                userInfo.put("zip", result.getString("zip"));
                userInfo.put("address", result.getString("address"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userInfo;
    }
}
