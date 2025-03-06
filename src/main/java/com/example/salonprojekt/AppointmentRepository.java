package com.example.salonprojekt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

public class AppointmentRepository {


    private DatabaseConnection dc;

    public ObservableList<Appointment> gettingTable() { //Henter en liste over åbne aftaler ( kan ses i a.status = open )
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

        try (Connection conn = dc.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String customer_name = resultSet.getString("customer_name");
                String customerPhone = resultSet.getString("customer_phone");
                String customerGender = resultSet.getString("customer_gender");
                String treatmentName = resultSet.getString("treatment_name");
                Double treatmentPrice = resultSet.getDouble("treatment_price");
                int treatmentDuration = resultSet.getInt("treatment_duration");
                //der eksister ikke en LocalDateTime i resultSet. så man skal converter det til timestamp
                Timestamp timestamp = resultSet.getTimestamp("appointment_datetime");
                LocalDateTime appointmentDatetime = timestamp.toLocalDateTime();
                String employeeName = resultSet.getString("employee_name");
                String status = resultSet.getString("status");

                Appointment appointment = new Appointment(customer_name, customerPhone, customerGender, treatmentName, treatmentPrice,
                        treatmentDuration, appointmentDatetime, employeeName, status); //
                list.add(appointment); //Tilføjer til listen
            }


        } catch (SQLException e) {
            System.err.println("Error loading: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public ObservableList<Appointment> gettingOtherTable() { //Same, men med færdige / aflyste
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

        try (Connection conn = dc.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String customer_name = resultSet.getString("customer_name");
                String customerPhone = resultSet.getString("customer_phone");
                String customerGender = resultSet.getString("customer_gender");
                String treatmentName = resultSet.getString("treatment_name");
                Double treatmentPrice = resultSet.getDouble("treatment_price");
                int treatmentDuration = resultSet.getInt("treatment_duration");
                //der eksister ikke en LocalDateTime i resultSet. så man skal converter det til timestamp
                Timestamp timestamp = resultSet.getTimestamp("appointment_datetime");
                LocalDateTime appointmentDatetime = timestamp.toLocalDateTime();
                String employeeName = resultSet.getString("employee_name");
                String status = resultSet.getString("status");

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
        // opdatere status på aftale.
        try (Connection connection = dc.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newStatus);
            preparedStatement.setString(2, customerName);
            preparedStatement.setString(3, customerPhone);
            preparedStatement.setTimestamp(4, Timestamp.valueOf(appointmentDatetime));
            preparedStatement.setString(5, employeeName);

            int rowsUpdated = preparedStatement.executeUpdate();
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

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, customerPhone);
            preparedStatement.setString(2, employeeName);
            preparedStatement.setTimestamp(3, Timestamp.valueOf(appointmentDatetime));

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                extraCost = resultSet.getDouble("extra_cost");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return extraCost;
    }

    public static int getExtraTime(String customerPhone, String employeeName, LocalDateTime appointmentDatetime) {
        int extraTime = 0;
        String query = "SELECT extra_time FROM Appointment WHERE customer_phone = ? AND employee_id = (SELECT id FROM Employee WHERE full_name = ?) AND appointment_datetime = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, customerPhone);
            preparedStatement.setString(2, employeeName);
            preparedStatement.setTimestamp(3, Timestamp.valueOf(appointmentDatetime));

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                extraTime = resultSet.getInt("extra_time");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return extraTime;
    }
}
