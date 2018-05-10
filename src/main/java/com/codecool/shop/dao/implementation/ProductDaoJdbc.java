package com.codecool.shop.dao.implementation;

import com.codecool.shop.config.Initializer;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.dao.Jdbc.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJdbc implements ProductDao {

    private static ProductDaoJdbc instance = null;
    private ProductCategoryDaoJdbc productCategoryDataStoreJdbc = ProductCategoryDaoJdbc.getInstance();
    private SupplierDao supplierDataStoreJdbc = SupplierDaoJdbc.getInstance();


    private ProductDaoJdbc() {

    }

    public static ProductDaoJdbc getInstance() {
        if (instance == null) {
            instance = new ProductDaoJdbc();
        }
        return instance;
    }


    @Override
    public void add(Product product) {
        Connection dbConnection = null;
        String insertTableSQL = "INSERT INTO products"
                + "(name,description,defaultprice,defaultcurrency,productcategory,supplier) VALUES"
                + "(?,?,?,?,?,?)";
        try {
            dbConnection = Utils.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement(insertTableSQL);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setFloat(3, product.getDefaultPrice());
            preparedStatement.setString(4, String.valueOf(product.getDefaultCurrency()));
            preparedStatement.setString(5, (product.getProductCategory().getName()));
            preparedStatement.setString(6, (product.getSupplier().getName()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Product find(int id) {

        try {
            Connection dbConnection = Utils.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM products WHERE id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Product result = new Product(resultSet.getString("name"), Float.parseFloat(resultSet.getString("defaultprice")), resultSet.getString("defaultcurrency"), resultSet.getString("description"), productCategoryDataStoreJdbc.find(resultSet.getString("productcategory")), supplierDataStoreJdbc.find(resultSet.getString("supplier")));
                result.setId(resultSet.getInt("id"));
                return result;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void remove(int id) {
        Connection dbConnection = null;
        try {
            dbConnection = Utils.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement("DELETE FROM products WHERE id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Product> getAll() {
        List<Product> allProducts = new ArrayList<>();
        try {
            Connection dbConnection = Utils.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM products;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // TODO change to join instead of guerry in querry
                Product result = new Product(resultSet.getString("name"), Float.parseFloat(resultSet.getString("defaultprice")), resultSet.getString("defaultcurrency"), resultSet.getString("description"), productCategoryDataStoreJdbc.find(resultSet.getString("productcategory")), supplierDataStoreJdbc.find(resultSet.getString("supplier")));
                result.setId(resultSet.getInt("id"));
                allProducts.add(result);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allProducts;
    }


    @Override
    public List<Product> getBy(Supplier supplier) {
        List<Product> productsBySupplier = new ArrayList<>();
        try {
            Connection dbConnection = Utils.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM products WHERE supplier = ?;");
            preparedStatement.setString(1, supplier.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product result = new Product(resultSet.getString("name"), Float.parseFloat(resultSet.getString("defaultprice")), resultSet.getString("defaultcurrency"), resultSet.getString("description"), productCategoryDataStoreJdbc.find(resultSet.getString("productcategory")), supplierDataStoreJdbc.find(resultSet.getString("supplier")));
                result.setId(resultSet.getInt("id"));
                productsBySupplier.add(result);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productsBySupplier;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        List<Product> productsByCategory = new ArrayList<>();
        try {
            Connection dbConnection = Utils.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM products WHERE productcategory = ?;");
            preparedStatement.setString(1, productCategory.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product result = new Product(resultSet.getString("name"), Float.parseFloat(resultSet.getString("defaultprice")), resultSet.getString("defaultcurrency"), resultSet.getString("description"), productCategoryDataStoreJdbc.find(resultSet.getString("productcategory")), supplierDataStoreJdbc.find(resultSet.getString("supplier")));
                result.setId(resultSet.getInt("id"));
                productsByCategory.add(result);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productsByCategory;
    }

    @Override
    public List<Product> getBy(Supplier supplier, ProductCategory productCategory) {
        List<Product> productsByCategory = new ArrayList<>();
        try {
            Connection dbConnection = Utils.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM products WHERE productcategory = ? AND supplier = ? ;");
            preparedStatement.setString(1, productCategory.getName());
            preparedStatement.setString(2, supplier.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product result = new Product(resultSet.getString("name"), Float.parseFloat(resultSet.getString("defaultprice")), resultSet.getString("defaultcurrency"), resultSet.getString("description"), productCategoryDataStoreJdbc.find(resultSet.getString("productcategory")), supplierDataStoreJdbc.find(resultSet.getString("supplier")));
                result.setId(resultSet.getInt("id"));
                productsByCategory.add(result);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productsByCategory;
    }
}
