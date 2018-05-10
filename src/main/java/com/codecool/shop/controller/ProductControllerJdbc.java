package com.codecool.shop.controller;

import com.codecool.shop.dao.Jdbc.Utils;
import com.codecool.shop.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductControllerJdbc {
    public static void add(int id, int userid, int quantity) {
        Connection dbConnection = null;
        try {
            dbConnection = Utils.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement("INSERT INTO shoppingcart (userid,productid,quantity) VALUES (?,?,?)");
            preparedStatement.setInt(1, userid);
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(3, quantity);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(int quantity, int id) {
        Connection dbConnection = null;
        try {
            dbConnection = Utils.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement("UPDATE shoppingcart SET quantity=? WHERE productid=?");
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int findQuantity(int id) {

        try {
            Connection dbConnection = Utils.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT quantity FROM shoppingcart WHERE productid = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int result = resultSet.getInt("quantity");
                return result;
            } else {
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static Product findProductByProductId(int id) {
        return null;
    }

    public static List<Product> getShoppingCart() {
        List<Product> shoppingCart = new ArrayList<>();
        try {
            Connection dbConnection = Utils.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM shoppingcart;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = findProductByProductId(resultSet.getInt("productid"));
                shoppingCart.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return shoppingCart;
    }
}
