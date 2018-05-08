package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.dao.Jdbc.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProductDaoJdbc implements ProductDao{

    private static ProductDaoJdbc instance= null;

    private ProductDaoJdbc(){

    }

    public static ProductDaoJdbc getInstance(){
        if(instance==null){
            instance=new ProductDaoJdbc();
        }
        return instance;
    }


    @Override
    public void add(Product product) {
        Connection dbConnection=null;
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
            preparedStatement.executeUpdate(); } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Product find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return null;
    }
}
