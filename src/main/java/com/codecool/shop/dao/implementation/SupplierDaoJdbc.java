package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.Jdbc.Utils;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoJdbc implements SupplierDao {

    private static SupplierDaoJdbc instance = null;


    private SupplierDaoJdbc() {

    }

    public static SupplierDaoJdbc getInstance() {
        if (instance == null) {
            instance = new SupplierDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {
        String insertTableSQL = "INSERT INTO supplier"
                + "(name,description) VALUES"
                + "(?,?)";
        try (Connection dbConnection = Utils.getConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(insertTableSQL);
            preparedStatement.setString(1, supplier.getName());
            preparedStatement.setString(2, supplier.getDescription());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Supplier find(int id) {
        try (Connection dbConnection = Utils.getConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM supplier WHERE id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getSupplier(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Supplier getSupplier(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            Supplier result = new Supplier(resultSet.getString("name"), resultSet.getString("description"));
            result.setId(resultSet.getInt("id"));
            return result;
        } else {
            return null;
        }
    }

    @Override
    public Supplier find(String name) {
        try (Connection dbConnection = Utils.getConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM supplier WHERE name = ?;");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getSupplier(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void remove(int id) {
        try (Connection dbConnection = Utils.getConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement("DELETE FROM supplier WHERE id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Supplier> getAll() {
        List<Supplier> allSupplier = new ArrayList<>();
        try (Connection dbConnection = Utils.getConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM supplier;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Supplier result = new Supplier(resultSet.getString("name"), resultSet.getString("description"));
                result.setId(resultSet.getInt("id"));
                allSupplier.add(result);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allSupplier;
    }
}
