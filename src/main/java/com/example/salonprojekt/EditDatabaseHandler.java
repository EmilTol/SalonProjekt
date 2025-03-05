package com.example.salonprojekt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

public class EditDatabaseHandler {

    private DatabaseConnection dc;

    public ObservableList<String> gettingHairPeople() {
        ObservableList<String> list = FXCollections.observableArrayList();
        String query = "SELECT full_name FROM Employee";

        try (Connection conn = dc.getconnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(rs.getString("full_name"));
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

        try (Connection conn = dc.getconnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(rs.getString("name"));
            }

        } catch (SQLException e) {
            System.err.println("Error loading treatments: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public Edit getTreatmentDetails(String treatmentName) {
        String query = "SELECT name, standard_duration, standard_price FROM Treatment WHERE name = ?";

        try (Connection conn = dc.getconnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, treatmentName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Edit(
                        rs.getString("name"),
                        rs.getInt("standard_duration"),
                        rs.getDouble("standard_price")
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

        try (Connection conn = DatabaseConnection.getconnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, newName);
            pstmt.setString(2, newPhone);
            pstmt.setString(3, newGender);
            pstmt.setTimestamp(4, Timestamp.valueOf(newDateTime));
            pstmt.setDouble(5, newExtraCost);
            pstmt.setInt(6, newExtraTime);
            pstmt.setString(7, newTreatment);
            pstmt.setString(8, newEmployee);

            // det er her de "gamle" værdier er vigtige og bliver brugt
            pstmt.setString(9, oldName); // oprindelige kundes navn
            pstmt.setString(10, oldPhone); // oprindelige telefonnummer
            pstmt.setTimestamp(11, Timestamp.valueOf(oldDateTime)); // oprindelige datetime

            // Eksekver opdateringen
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // Returner true hvis mindst én række blev opdateret
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Returner false hvis der opstod en fejl, ikke fucking rediger hvis du ik vil redigere
        }
    }
        }

