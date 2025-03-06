package com.example.salonprojekt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

public class EditRepository {

    private DatabaseConnection dc;

    public ObservableList<String> gettingHairPeople() {
        ObservableList<String> list = FXCollections.observableArrayList();
        String query = "SELECT full_name FROM Employee";

        try (Connection connection = dc.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                list.add(resultSet.getString("full_name"));
            }

        } catch (SQLException e) {
            System.err.println("Error loading: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public ObservableList<String> gettingTreatments() {
        ObservableList<String> list = FXCollections.observableArrayList();
        String query = "SELECT name FROM Treatment";

        try (Connection connection = dc.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                list.add(resultSet.getString("name"));
            }

        } catch (SQLException e) {
            System.err.println("Error loading treatments: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public Edit getTreatmentDetails(String treatmentName) {
        String query = "SELECT name, standard_duration, standard_price FROM Treatment WHERE name = ?";

        try (Connection connection = dc.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, treatmentName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Edit(
                        resultSet.getString("name"),
                        resultSet.getInt("standard_duration"),
                        resultSet.getDouble("standard_price")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error fetching treatment details: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public static boolean updateAppointment(
            String oldName, String oldPhone, LocalDateTime oldDateTime,
            String newName, String newPhone, String newGender,
            String newTreatment, LocalDateTime newDateTime,
            String newEmployee, double newExtraCost, int newExtraTime) {

        String query = "UPDATE Appointment a " +
                "JOIN Treatment t ON a.treatment_id = t.id " +
                "JOIN Employee e ON a.employee_id = e.id " +
                "SET a.customer_name = ?, " +
                "    a.customer_phone = ?, " +
                "    a.customer_gender = ?, " +
                "    a.appointment_datetime = ?, " +
                "    a.extra_cost = ?, " +
                "    a.extra_time = ?, " +
                "    a.treatment_id = (SELECT id FROM Treatment WHERE name = ?), " +
                "    a.employee_id = (SELECT id FROM Employee WHERE full_name = ?) " +
                "WHERE a.customer_name = ? AND a.customer_phone = ? AND a.appointment_datetime = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, newPhone);
            preparedStatement.setString(3, newGender);
            preparedStatement.setTimestamp(4, Timestamp.valueOf(newDateTime));
            preparedStatement.setDouble(5, newExtraCost);
            preparedStatement.setInt(6, newExtraTime);
            preparedStatement.setString(7, newTreatment);
            preparedStatement.setString(8, newEmployee);

            // det er her de "gamle" værdier er vigtige og bliver brugt
            preparedStatement.setString(9, oldName); // oprindelige kundes navn
            preparedStatement.setString(10, oldPhone); // oprindelige telefonnummer
            preparedStatement.setTimestamp(11, Timestamp.valueOf(oldDateTime)); // oprindelige datetime

            // Eksekver opdateringen
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0; // Returner true hvis mindst én række blev opdateret
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Returner false hvis der opstod en fejl, ikke fucking rediger hvis du ik vil redigere
        }
    }
}
