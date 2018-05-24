package com.codecool.shop.model;

import com.codecool.shop.util.JDBCUtils;
import com.codecool.shop.dao.interfaces.SupplierDao;
import com.codecool.shop.dao.implementation.jdbc.ProductCategoryDaoJdbc;
import com.codecool.shop.dao.implementation.jdbc.SupplierDaoJdbc;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class OrderJdbc {
    private static ProductCategoryDaoJdbc productCategoryDataStoreJdbc = ProductCategoryDaoJdbc.getInstance();
    private static SupplierDao supplierDataStoreJdbc = SupplierDaoJdbc.getInstance();

    public static void add(int id, int userid, int quantity) {
        try (Connection dbConnection = JDBCUtils.getConnection()) {
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

        try (Connection dbConnection = JDBCUtils.getConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement("UPDATE shoppingcart SET quantity=? WHERE productid=?");
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int findQuantity(int id) {

        try (Connection dbConnection = JDBCUtils.getConnection()) {
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
        try (Connection dbConnection = JDBCUtils.getConnection()) {
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

    public static JSONArray getShoppingCart(int userId) {
        JSONArray cartItems = new JSONArray();
        try (Connection dbConnection = JDBCUtils.getConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM shoppingcart WHERE userid=?;");
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = findProductByProductId(resultSet.getInt("productid"));

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", product.getName());
                jsonObject.put("id", resultSet.getInt("productid"));
                int quantity = resultSet.getInt("quantity");
                jsonObject.put("quantity", quantity);
                jsonObject.put("price", product.getDefaultPrice());
                cartItems.add(jsonObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartItems;
    }

    public static void removeItemFromCart(int productId, int userId) {
        try (Connection dbConnection = JDBCUtils.getConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement("DELETE FROM shoppingcart WHERE productid = ? AND userid=?;");
            preparedStatement.setInt(1, productId);
            preparedStatement.setInt(2, userId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateIncrement(int id, int userId) {
        Connection dbConnection;
        try {
            dbConnection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement("UPDATE shoppingcart SET quantity=? WHERE productid=? AND userid=?");
            preparedStatement.setInt(1, findQuantity(id) + 1);
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(3, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateDecrement(int id, int userId) {
        Connection dbConnection;
        try {
            dbConnection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement("UPDATE shoppingcart SET quantity=? WHERE productid=? AND userid=?");
            preparedStatement.setInt(1, findQuantity(id) - 1);
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(3, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addOrder(int userid) {
        try (Connection dbConnection = JDBCUtils.getConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement("INSERT INTO orderlist (status,products,totalprice,userid) VALUES (?,?,?,?)");
            preparedStatement.setString(1, "checked");
            preparedStatement.setString(2, getShoppingCart(userid).toString());
            preparedStatement.setFloat(3, getTotalPrice(userid));
            preparedStatement.setInt(4, userid);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static float getTotalPrice(int id) {
        float price = 0;
        try (Connection dbConnection = JDBCUtils.getConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM shoppingcart WHERE userid=?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                price += findProductByProductId(resultSet.getInt("productid")).getDefaultPrice() * resultSet.getInt("quantity");
            }
            return price;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void removeFromOrder(int userId) {
        try (Connection dbConnection = JDBCUtils.getConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement("DELETE FROM shoppingcart WHERE userid=?");
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static float getTotalPriceOfOrder(int userId) {
        float totalPrice = 0;
        try (Connection dbConnection = JDBCUtils.getConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM shoppingcart WHERE userid=?;");
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = findProductByProductId(resultSet.getInt("productid"));
                int quantity = resultSet.getInt("quantity");
                totalPrice += (product != null ? product.getDefaultPrice() : 0) * quantity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalPrice;
    }

    public static HashMap<String, String> getUserInfo(int userId) {
        HashMap<String, String> userInfo = new HashMap<>();
        try (Connection dbConnection = JDBCUtils.getConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT fullname,zip,city,address,email FROM users WHERE id=?;");
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            userInfo.put("name", resultSet.getString("fullname"));
            userInfo.put("zip", resultSet.getString("zip"));
            userInfo.put("city", resultSet.getString("city"));
            userInfo.put("address", resultSet.getString("address"));
            userInfo.put("email", resultSet.getString("email"));


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userInfo;
    }
}
