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
        }

