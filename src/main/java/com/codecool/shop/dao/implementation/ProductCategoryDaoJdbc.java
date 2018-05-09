package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.Jdbc.Utils;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoJdbc implements ProductCategoryDao{

    private static ProductCategoryDaoJdbc instance= null;

    private ProductCategoryDaoJdbc(){

    }

    public static ProductCategoryDaoJdbc getInstance(){
        if(instance==null){
            instance=new ProductCategoryDaoJdbc();
        }
        return instance;
    }


    @Override
    public void add(ProductCategory category) {
        Connection dbConnection=null;
        try {
            dbConnection = Utils.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement("INSERT INTO productcategory (name,description,department) VALUES (?,?,?)");
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setString(3, category.getDepartment());
            preparedStatement.executeUpdate(); } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ProductCategory find(int id) {
        try {
            Connection dbConnection = Utils.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM productcategory WHERE id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ProductCategory result = new ProductCategory(resultSet.getString("name"),resultSet.getString("department"),resultSet.getString("description"));
                return result;
            } else {
                return null;
            }

        } catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ProductCategory find(String name) {
        try {
            Connection dbConnection = Utils.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM productcategory WHERE name = ?;");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    ProductCategory result = new ProductCategory(resultSet.getString("name"),resultSet.getString("department"),resultSet.getString("description"));
                    return result;
                } else {
                    return null;
                }

            } catch(SQLException e){
                e.printStackTrace();
            }

            return null;
        }



    @Override
    public void remove(int id) {
        Connection dbConnection=null;
        try {
            dbConnection = Utils.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement("DELETE FROM productcategory WHERE id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate(); } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<ProductCategory> getAll() {
        List<ProductCategory>allProductCategory=new ArrayList<>();
        try {
            Connection dbConnection = Utils.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM productcategory;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                ProductCategory result = new ProductCategory(resultSet.getString("name"),resultSet.getString("department"),resultSet.getString("description"));
                allProductCategory.add(result);
            }

        } catch(SQLException e){
            e.printStackTrace();
        }

        return allProductCategory;
    }
}
