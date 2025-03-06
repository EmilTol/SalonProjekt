package com.example.salonprojekt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

public class AppointmentDatabaseHandler {


    private DatabaseConnection dc;

    public ObservableList<Appointment> gettingTable() {
        ObservableList<Appointment> list = FXCollections.observableArrayList();

        String query = "SELECT \n" +
                "    a.customer_name, \n" +
                "    a.customer_phone, \n" +
                "    a.customer_gender, \n" +
                "    t.name AS treatment_name, \n" +
                "    (t.standard_price + a.extra_cost) AS treatment_price,\n" +
                "    (t.standard_duration + a.extra_time) AS treatment_duration,\n" +
                "    a.appointment_datetime, \n" +
                "    e.full_name AS employee_name,\n" +
                "    a.status\n" +
                "FROM Appointment a\n" +
                "JOIN Treatment t ON a.treatment_id = t.id\n" +
                "JOIN Employee e ON a.employee_id = e.id\n" +
                "WHERE a.status = 'open';";

        try (Connection conn = dc.getconnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String customer_name = rs.getString("customer_name");
                String customerPhone = rs.getString("customer_phone");
                String customerGender = rs.getString("customer_gender");
                String treatmentName = rs.getString("treatment_name");
                Double treatmentPrice = rs.getDouble("treatment_price");
                int treatmentDuration = rs.getInt("treatment_duration");
                //der eksister ikke en LocalDateTime i rs. så man skal converter det til timestamp
                Timestamp timestamp = rs.getTimestamp("appointment_datetime");
                LocalDateTime appointmentDatetime = timestamp.toLocalDateTime();
                String employeeName = rs.getString("employee_name");
                String status = rs.getString("status");

                Appointment appointment = new Appointment(customer_name, customerPhone, customerGender, treatmentName, treatmentPrice,
                        treatmentDuration, appointmentDatetime, employeeName, status);
                list.add(appointment);
            }


        } catch (SQLException e) {
            System.err.println("Error loading: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public ObservableList<Appointment> gettingOtherTable() {
        ObservableList<Appointment> list = FXCollections.observableArrayList();

        String query = "SELECT \n" +
                "    a.customer_name, \n" +
                "    a.customer_phone, \n" +
                "    a.customer_gender, \n" +
                "    t.name AS treatment_name, \n" +
                "    t.standard_price AS treatment_price,\n" +
                "    t.standard_duration AS treatment_duration,\n" +
                "    a.appointment_datetime, \n" +
                "    e.full_name AS employee_name,\n" +
                "    a.status\n" +
                "FROM Appointment a\n" +
                "JOIN Treatment t ON a.treatment_id = t.id\n" +
                "JOIN Employee e ON a.employee_id = e.id\n" +
                "WHERE a.status = 'closed' OR a.status = 'cancelled';";

        try (Connection conn = dc.getconnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String customer_name = rs.getString("customer_name");
                String customerPhone = rs.getString("customer_phone");
                String customerGender = rs.getString("customer_gender");
                String treatmentName = rs.getString("treatment_name");
                Double treatmentPrice = rs.getDouble("treatment_price");
                int treatmentDuration = rs.getInt("treatment_duration");
                //der eksister ikke en LocalDateTime i rs. så man skal converter det til timestamp
                Timestamp timestamp = rs.getTimestamp("appointment_datetime");
                LocalDateTime appointmentDatetime = timestamp.toLocalDateTime();
                String employeeName = rs.getString("employee_name");
                String status = rs.getString("status");

                Appointment appointment = new Appointment(customer_name, customerPhone, customerGender, treatmentName, treatmentPrice,
                        treatmentDuration, appointmentDatetime, employeeName, status);
                list.add(appointment);
            }


        } catch (SQLException e) {
            System.err.println("Error loading: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public void updateAppointmentStatus(String customerName, String customerPhone, LocalDateTime appointmentDatetime, String employeeName, String newStatus) {
        String query = "UPDATE Appointment SET status = ? WHERE customer_name = ? AND customer_phone = ? AND appointment_datetime = ? AND employee_id = (SELECT id FROM Employee WHERE full_name = ?)";

        try (Connection conn = dc.getconnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newStatus);
            stmt.setString(2, customerName);
            stmt.setString(3, customerPhone);
            stmt.setTimestamp(4, Timestamp.valueOf(appointmentDatetime));
            stmt.setString(5, employeeName);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Opdatering lykkedes for aftale: " + customerName + " på " + appointmentDatetime);
            } else {
                System.out.println("Ingen rækker blev opdateret. fuck");
            }

        } catch (SQLException e) {
            System.err.println("Fejl ved opdatering: " + e.getMessage());
        }

    }

    public static double getExtraCost(String customerPhone, String employeeName, LocalDateTime appointmentDatetime) {
        double extraCost = 0.0;
        String query = "SELECT extra_cost FROM Appointment WHERE customer_phone = ? AND employee_id = (SELECT id FROM Employee WHERE full_name = ?) AND appointment_datetime = ?";

        try (Connection conn = DatabaseConnection.getconnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, customerPhone);
            stmt.setString(2, employeeName);
            stmt.setTimestamp(3, Timestamp.valueOf(appointmentDatetime));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                extraCost = rs.getDouble("extra_cost");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return extraCost;
    }

    public static int getExtraTime(String customerPhone, String employeeName, LocalDateTime appointmentDatetime) {
        int extraTime = 0;
        String query = "SELECT extra_time FROM Appointment WHERE customer_phone = ? AND employee_id = (SELECT id FROM Employee WHERE full_name = ?) AND appointment_datetime = ?";

        try (Connection conn = DatabaseConnection.getconnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, customerPhone);
            stmt.setString(2, employeeName);
            stmt.setTimestamp(3, Timestamp.valueOf(appointmentDatetime));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                extraTime = rs.getInt("extra_time");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return extraTime;
    }
}
