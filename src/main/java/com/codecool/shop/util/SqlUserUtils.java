package com.codecool.shop.util;

import com.codecool.shop.dao.Jdbc.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class SqlUserUtils {
    public static void addUser(HashMap<String, String> userInfo) {
        try {
            Connection connection = Utils.getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO users (username,password,email,salt) VALUES (?,?,?,?);");
            stmt.setString(1, userInfo.get("name"));
            stmt.setString(2, userInfo.get("hashedpwd"));
            stmt.setString(3, userInfo.get("email"));
            stmt.setString(4, userInfo.get("salt"));
            stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static HashMap<String, String> getUser(String email) {
        HashMap<String, String> userInfo = new HashMap<>();
        try {
            Connection connection = Utils.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
            stmt.setString(1, email);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                userInfo.put("id", result.getString("id"));
                userInfo.put("password", result.getString("password"));
                userInfo.put("salt", result.getString("salt"));
                userInfo.put("email", result.getString("email"));
                userInfo.put("username", result.getString("username"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userInfo;
    }
}
