package com.example.salonprojekt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

public class TableDatabaseHandler {


    private DatabaseConnection dc;

    public ObservableList<Table> gettingTable() {
        ObservableList<Table> list = FXCollections.observableArrayList();

        String query = "SELECT \n" +
                "    a.customer_name, \n" +
                "    a.customer_phone, \n" +
                "    a.customer_gender, \n" +
                "    t.name AS treatment_name, \n" +
                "    t.standard_price AS treatment_price,\n" +
                "    a.appointment_datetime, \n" +
                "    e.full_name AS employee_name,\n" +
                "    a.status\n" +
                "FROM Appointment a\n" +
                "JOIN Treatment t ON a.treatment_id = t.id\n" +
                "JOIN Employee e ON a.employee_id = e.id;";

        try (Connection conn = dc.getconnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String customer_name = rs.getString("customer_name");
                String customerPhone = rs.getString("customer_phone");
                String customerGender = rs.getString("customer_gender");
                String treatmentName = rs.getString("treatment_name");
                Double treatmentPrice = rs.getDouble("treatment_price");
                //der eksister ikke en LocalDateTime i rs. så man skal converter det til timestamp
                Timestamp timestamp = rs.getTimestamp("appointment_datetime");
                LocalDateTime appointmentDatetime = timestamp.toLocalDateTime();
                String employeeName = rs.getString("employee_name");
                String status = rs.getString("status");

                Table table = new Table(customer_name, customerPhone, customerGender, treatmentName, treatmentPrice,
                        appointmentDatetime, employeeName, status);
                list.add(table);
            }


        } catch (SQLException e) {
            System.err.println("Error loading: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
}
