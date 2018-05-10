package com.codecool.shop.controller;

import com.codecool.shop.dao.Jdbc.Utils;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoJdbc;
import com.codecool.shop.dao.implementation.SupplierDaoJdbc;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.cartItem;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductControllerJdbc {
    private static ProductCategoryDaoJdbc productCategoryDataStoreJdbc = ProductCategoryDaoJdbc.getInstance();
    private static SupplierDao supplierDataStoreJdbc = SupplierDaoJdbc.getInstance();

    public static void add(int id, int userid, int quantity) {
        Connection dbConnection;
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

    static void update(int quantity, int id) {
        Connection dbConnection;
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

    static int findQuantity(int id) {

        try {
            Connection dbConnection = Utils.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT quantity FROM shoppingcart WHERE productid = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("quantity");
            } else {
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private static Product findProductByProductId(int id) {
        try {
            Connection dbConnection = Utils.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM products WHERE id=?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                Float defaultPrice = resultSet.getFloat("defaultprice");
                String defaultCurrency = resultSet.getString("defaultcurrency");
                String description = resultSet.getString("description");
                ProductCategory category = productCategoryDataStoreJdbc.find(resultSet.getString("productcategory"));
                Supplier supplier = supplierDataStoreJdbc.find(resultSet.getString("supplier"));
                return new Product(name, defaultPrice, defaultCurrency, description, category, supplier);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONArray getShoppingCart() {
        JSONArray cartItems = new JSONArray();
        try {
            Connection dbConnection = Utils.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM shoppingcart;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = findProductByProductId(resultSet.getInt("productid"));

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", product.getName());
                int quantity = resultSet.getInt("quantity");
                int id = resultSet.getInt("productid");
                jsonObject.put("id",id);
                jsonObject.put("quantity", quantity);
                jsonObject.put("price", product.getDefaultPrice() * quantity);
                cartItems.add(jsonObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartItems;
    }
    static void updateIncrement(int id) {
        Connection dbConnection;
        try {
            dbConnection = Utils.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement("UPDATE shoppingcart SET quantity=? WHERE productid=?");
            preparedStatement.setInt(1, findQuantity(id)+1);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
