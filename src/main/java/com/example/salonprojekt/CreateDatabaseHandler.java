package com.example.salonprojekt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CreateDatabaseHandler {

    public boolean insertAppointment(Create appointment) {
        int treatmentDuration = getTreatmentDurationById(appointment.getTreatmentId());
        if (treatmentDuration == -1) {
            System.out.println("Behandlingens varighed kunne ikke findes.");
            return false;
        }

        // Tjek om tiden er ledig for denne frisør
        boolean isAvailable = isTimeSlotAvailable(appointment.getEmployeeId(), appointment.getAppointmentDatetime(), treatmentDuration + appointment.getExtraTime());
        if (!isAvailable) {
            System.out.println("Tidsrummet er allerede optaget for denne frisør.");
            return false;
        }

        String query = "INSERT INTO Appointment (customer_name, customer_phone, customer_gender, treatment_id, " +
                "appointment_datetime, employee_id, status, extra_time, extra_cost) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getconnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, appointment.getCustomerName());
            preparedStatement.setString(2, appointment.getCustomerPhone());
            preparedStatement.setString(3, appointment.getCustomerGender());
            preparedStatement.setInt(4, appointment.getTreatmentId());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(appointment.getAppointmentDatetime()));
            preparedStatement.setInt(6, appointment.getEmployeeId());
            preparedStatement.setString(7, appointment.getStatus());
            preparedStatement.setInt(8, appointment.getExtraTime());
            preparedStatement.setDouble(9, appointment.getExtraCost());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isTimeSlotAvailable(int employeeId, LocalDateTime newAppointmentStart, int duration) {
        String query = "SELECT COUNT(*) FROM Appointment " +
                "WHERE employee_id = ? " +
                "AND status = 'open' " +
                "AND appointment_datetime < ? + INTERVAL ? MINUTE " +
                "AND appointment_datetime + INTERVAL (SELECT standard_duration + extra_time FROM Treatment WHERE id = Appointment.treatment_id) MINUTE > ?";

        try (Connection connection = DatabaseConnection.getconnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, employeeId);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(newAppointmentStart));
            preparedStatement.setInt(3, duration);
            preparedStatement.setTimestamp(4, Timestamp.valueOf(newAppointmentStart));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    return false; // Der findes allerede en booking i det tidsrum
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public int getTreatmentDurationById(int treatmentId) {
        String query = "SELECT standard_duration FROM Treatment WHERE id = ?";

        try (Connection connection = DatabaseConnection.getconnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, treatmentId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("standard_duration");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public ObservableList<String> getTreatmentNames() {
        ObservableList<String> treatmentList = FXCollections.observableArrayList();
        String query = "SELECT name FROM Treatment";
        try (Connection connection = DatabaseConnection.getconnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                treatmentList.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return treatmentList;
    }

    public ObservableList<String> getEmployeeNames() {
        ObservableList<String> employeeList = FXCollections.observableArrayList();
        String query = "SELECT full_name FROM Employee";
        try (Connection connection = DatabaseConnection.getconnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                employeeList.add(resultSet.getString("full_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    public int getTreatmentIdByName(String treatmentName) {
        String query = "SELECT id FROM Treatment WHERE name = ?";
        try (Connection connection = DatabaseConnection.getconnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, treatmentName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getEmployeeIdByName(String employeeName) {
        String query = "SELECT id FROM Employee WHERE full_name = ?";
        try (Connection connection = DatabaseConnection.getconnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, employeeName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public double getTreatmentPriceByName(String treatmentName) {
        String query = "SELECT standard_price FROM Treatment WHERE name = ?";
        try (Connection connection = DatabaseConnection.getconnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, treatmentName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("standard_price");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
