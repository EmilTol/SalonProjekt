package com.example.salonprojekt;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class CreateController extends BaseController implements Initializable {

    @FXML
    private TextField customerNameField;

    @FXML
    private TextField customerPhoneField;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private ComboBox<String> treatmentComboBox;

    @FXML
    private ComboBox<String> employeeComboBox;

    @FXML
    private DatePicker appointmentDatePicker;

    @FXML
    private TextField appointmentTimeField;

    @FXML
    private TextField basePriceField;

    @FXML
    private TextField extraCostField;

    @FXML
    private TextField extraTimeField;

    private CreateRepository createRepository = new CreateRepository(); //Vores repo for create funktioner

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genderComboBox.getItems().addAll("M", "F"); //ComboBox med 2 muligheder som passer med db muligheder

        ObservableList<String> treatments = createRepository.getTreatmentNames(); //Henter mulige behandlinger og viser i combobox
        treatmentComboBox.setItems(treatments);

        ObservableList<String> employees = createRepository.getEmployeeNames(); //Same shit
        employeeComboBox.setItems(employees);

        treatmentComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> { //"Lytter", altså holder øje med valget i treatment så vi kan få en baseprice importeret
            if (newVal != null) {
                double standardPrice = createRepository.getTreatmentPriceByName(newVal); // Henter standardpris på treatmen
                basePriceField.setText(String.valueOf(standardPrice));
            } else {
                basePriceField.clear();
            }
        });
    }

    @FXML
    private void handleCreateAppointment(ActionEvent event) { //Læser værdier fra vores felter (.trim så mellemrum bliver fjernet og vi ikke får mærkelig data)
        String customerName = customerNameField.getText().trim();
        String customerPhone = customerPhoneField.getText().trim();
        String customerGender = genderComboBox.getValue();
        String treatmentName = treatmentComboBox.getValue();
        String employeeName = employeeComboBox.getValue();
        LocalDate appointmentDate = appointmentDatePicker.getValue();
        String appointmentTime = appointmentTimeField.getText().trim();

        if (customerName.isEmpty() || customerPhone.isEmpty() || customerGender == null || treatmentName == null || employeeName == null || appointmentDate == null || appointmentTime.isEmpty()) {
            showAlert("Input Error", "Alle felter skal udfyldes"); // tjekker for tomme felter, hvis der er nogen får vi en showAlert
            return;
        }

        LocalTime time; // Tiden på tiden,
        try {
            time = LocalTime.parse(appointmentTime);
        } catch (Exception e) {
            showAlert("Time Format Error", "Tid skal være i formatet HH:mm");
            return;
        }
        LocalDateTime appointmentDateTime = LocalDateTime.of(appointmentDate, time); // Her sammensætter vi vores tid fra LocalData og localtime, hvilket giver os vores datetime

        double extraCost = 0.0; // Vores ekstra tid og ekstra pris
        int extraTime = 0;
        if (!extraCostField.getText().trim().isEmpty()) {
            try {
                extraCost = Double.parseDouble(extraCostField.getText().trim());
            } catch (NumberFormatException e) { // Numberformatex
                showAlert("Input Error", "Ekstra pris skal være et tal");
                return;
            }
        }
        if (!extraTimeField.getText().trim().isEmpty()) {
            try {
                extraTime = Integer.parseInt(extraTimeField.getText().trim()); // TJEK AT DET VIRKER RIGTIG! HUSK.
            } catch (NumberFormatException e) {
                showAlert("Input Error", "Ekstra tid skal være et heltal");
                return;
            }
        }

        int treatmentId = createRepository.getTreatmentIdByName(treatmentName);
        int employeeId = createRepository.getEmployeeIdByName(employeeName);
        int treatmentDuration = createRepository.getTreatmentDurationById(treatmentId);

        boolean isAvailable = createRepository.isTimeSlotAvailable(employeeId, appointmentDateTime, treatmentDuration + extraTime); // Tjekker om tiden er ledig - det er helt galt.
        if (!isAvailable) {
            showAlert("Tid kke tilgængelt", "Tid ikke tilgæng");
            return;
        }
        // Her laver vi et create objekt for at samle dataen før vi smider det ind i db
        Create appointment = new Create(customerName, customerPhone, customerGender, treatmentId, appointmentDateTime, employeeId, "open", extraTime, extraCost);

        boolean success = createRepository.insertAppointment(appointment); // Kalder repo
        if (success) { // hvis det gik godt, så viser vi succes skifter side, og clear felter.
            showAlert("Success", "Aftalen er oprettet");
            clearFields();
            try {
                sceneManager.switchTo("table");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Error", "Fejl ved oprettelse af aftalen"); // Fejl, enten den her, eller tidspunkt optaget fejl
        }
    }

    private void showAlert(String title, String message) { // Laver en standard alert box ( gør det mere rent )
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() { // Rydder felter når aftale er oprettet.
        customerNameField.clear();
        customerPhoneField.clear();
        genderComboBox.setValue(null);
        treatmentComboBox.setValue(null);
        employeeComboBox.setValue(null);
        appointmentDatePicker.setValue(null);
        appointmentTimeField.clear();
        basePriceField.clear();
        extraCostField.clear();
        extraTimeField.clear();
    }

    @FXML
    private void switchToScene(ActionEvent event) throws IOException { // Smart lille sceneskift
        if (sceneManager != null) {
            sceneManager.switchTo("table");
        } else {
            System.out.println("sceneManager is not initialized!");
        }
    }
}
