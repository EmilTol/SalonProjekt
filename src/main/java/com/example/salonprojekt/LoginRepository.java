package com.example.salonprojekt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginRepository {
    private Connection connection;

    public LoginRepository() {
        connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.err.println("Ingen forbindelse");
        }
    }

    public boolean validateLogin(Login login) {
        String query = "SELECT * FROM employee WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, login.getUsername());
            preparedStatement.setString(2, login.getPassword());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}