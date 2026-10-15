package com.stadium.dao;


import com.stadium.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    public void getAllCustomers() {// reads customers from database

        String sql = "SELECT * FROM customers";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                int id = resultSet.getInt("customer_id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");

                System.out.println(id + " | " + name + " | " + email + "|" + password);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCustomer(String name, String address, String email, String contact, String country, String username, String password)
    {
        String sql = """
                INSERT INTO customers(name,address,email,contact,country,username,password)
                VALUES (?,?,?,?,?,?,?)
                """;
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,name);
            statement.setString(2,address);
            statement.setString(3,email);
            statement.setString(4,contact);
            statement.setString(5,country);
            statement.setString(6,username);
            statement.setString(7,password);

            int rowsInserted = statement.executeUpdate();

            System.out.println(rowsInserted + "customer added");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void findCustomerById(int customerId) {
        /*
    finds customers by provided ID from the database
    */

        String sql = "SELECT * FROM customers WHERE customer_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                System.out.println("ID: " + resultSet.getInt("customer_id"));
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("Contact: " + resultSet.getString("contact"));
                System.out.println("Email: " + resultSet.getString("email"));

            } else {
                System.out.println("Customer not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCustomerEmail(int customerId, String newEmail) {

        String sql = """
            UPDATE customers
            SET email = ?
            WHERE customer_id = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, newEmail);
            statement.setInt(2, customerId);

            int rowsUpdated = statement.executeUpdate();

            System.out.println(rowsUpdated + " customer updated.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(int customerId) {

        String sql = "DELETE FROM customers WHERE customer_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);

            int rowsDeleted = statement.executeUpdate();

            System.out.println(rowsDeleted + " customer deleted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean loginCustomer(String username, String password) {

        String sql = """
            SELECT customer_id
            FROM customers
            WHERE username = ?
              AND password = ?
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {

                return resultSet.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getCustomerId(String username, String password) {

        String sql = """
            SELECT customer_id
            FROM customers
            WHERE username = ?
              AND password = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt("customer_id");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

}