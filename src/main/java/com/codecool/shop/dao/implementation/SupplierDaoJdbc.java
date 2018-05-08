package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.Jdbc.Utils;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SupplierDaoJdbc implements SupplierDao {

    private static SupplierDaoJdbc instance = null;


    private SupplierDaoJdbc(){

    }

    public static SupplierDaoJdbc getInstance(){
        if(instance==null){
            instance=new SupplierDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {
        Connection dbConnection=null;
        String insertTableSQL = "INSERT INTO supplier"
                + "(name,description) VALUES"
                + "(?,?)";
        try {
            dbConnection = Utils.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement(insertTableSQL);
            preparedStatement.setString(1, supplier.getName());
            preparedStatement.setString(2, supplier.getDescription());
            preparedStatement.executeUpdate(); } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Supplier find(int id) {
        return null;
    }

    @Override
    public Supplier find(String name) {
        try {
            Connection dbConnection = Utils.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM supplier WHERE name = ?;");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Supplier result = new Supplier(resultSet.getString("name"),resultSet.getString("description"));
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

    }

    @Override
    public List<Supplier> getAll() {
        return null;
    }
}
