package com.example.salonprojekt;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;


public class AppointmentController extends BaseController implements Initializable {

    @FXML
    private CheckBox myCheckBox;

    @FXML
    private TableView<Appointment> timeTable;

    @FXML
    private TableColumn<Appointment, String> name;

    @FXML
    private TableColumn<Appointment, String> number;

    @FXML
    private TableColumn<Appointment, String> gender;

    @FXML
    private TableColumn<Appointment, String> treatment;

    @FXML
    private TableColumn<Appointment, Double> price;

    @FXML
    private TableColumn<Appointment, Integer> duration;

    @FXML
    private TableColumn<Appointment, Integer> time;

    @FXML
    private TableColumn<Appointment, String> barbar;

    @FXML
    private TableColumn<Appointment, String> status;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        //en listerner holder vist øje med når der sker ændringer. holder TableView opdateret
        myCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            updateTableData(newValue);
        });

        // indlæser data, så tabellen opdatere
        updateTableData(myCheckBox.isSelected());
    }

    private void updateTableData(boolean isChecked) {
        AppointmentRepository appointmentRepository = new AppointmentRepository();
        //den her linje ligger gettingOtherTable og gettingTable hvilken en der køre er styret af CheckBox
        ObservableList<Appointment> appointmentData = isChecked ? appointmentRepository.gettingOtherTable() : appointmentRepository.gettingTable();

        System.out.println("Antal rækker hentet: " + appointmentData.size());

        name.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        number.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        gender.setCellValueFactory(new PropertyValueFactory<>("customerGender"));
        treatment.setCellValueFactory(new PropertyValueFactory<>("treatmentName"));
        price.setCellValueFactory(new PropertyValueFactory<>("treatmentPrice"));
        duration.setCellValueFactory(new PropertyValueFactory<>("treatmentDuration"));
        time.setCellValueFactory(new PropertyValueFactory<>("appointmentDatetime"));
        barbar.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        timeTable.setItems(appointmentData);
    }

    @FXML
    private void switchToCreate() throws IOException {

        if (sceneManager != null) {
            sceneManager.switchTo("create");
        } else {
            System.out.println("sceneManager is not initialized!");
        }
    }
    @FXML
    private void switchToEdit() throws IOException {
        Appointment selectedRow = timeTable.getSelectionModel().getSelectedItem(); // Henter den valgte række.
        if (selectedRow == null) {
            showAlert("Ingen række valgt", "Vælg en række og prøv igen");
            return;
        }
        // Henter data fra den valgte aftale
        String tableName = selectedRow.getCustomer_name();
        String tablePhone = selectedRow.getCustomerPhone();
        String tableGender = selectedRow.getCustomerGender();
        String tableTreatment = selectedRow.getTreatmentName();
        LocalDate tableDate = LocalDate.from(selectedRow.getAppointmentDatetime());
        LocalTime tableTime = LocalTime.from(selectedRow.getAppointmentDatetime());
        String tableEmployee = selectedRow.getEmployeeName();
        String tableStatus = selectedRow.getStatus();

        double extraCost = AppointmentRepository.getExtraCost(tablePhone, tableEmployee, selectedRow.getAppointmentDatetime());
        int extraTime = AppointmentRepository.getExtraTime(tablePhone, tableEmployee, selectedRow.getAppointmentDatetime());

        //henter også lige status, bliver ikke sendt videre men bruges til et check
        if (tableStatus.equals("cancelled") || tableStatus.equals("closed")) {
            showAlert("Allerede lukket", "Du kan ikke ændre en allerede afsluttet bestilling");
            return;
        }

        System.out.println(tableName + " received");
        System.out.println(tableEmployee + " received");

        if (sceneManager != null) {
            // Skift til 'edit' scenen og send dataen med.
            sceneManager.switchToWithData("edit", tableName, tablePhone, tableGender, tableTreatment, tableDate, tableTime, tableEmployee, extraCost, extraTime);
        } else {
            System.out.println("sceneManager is not initialized!");
        }
    }

    @FXML
    private void switchToStart() throws IOException {

        if (sceneManager != null) {
            sceneManager.switchTo("login");
        } else {
            System.out.println("sceneManager is not initialized!");
        }
    }

    @FXML
    private void handleCancelAppointment() {
        Appointment selectedAppointment = timeTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            AppointmentRepository appointmentRepository = new AppointmentRepository();
            //de her 4 bruges til at opdaterer status på den korrekte, for at undgå Id
            appointmentRepository.updateAppointmentStatus(
                    selectedAppointment.getCustomer_name(),
                    selectedAppointment.getCustomerPhone(),
                    selectedAppointment.getAppointmentDatetime(),
                    selectedAppointment.getEmployeeName(),
                    "cancelled"
            );
            updateTableData(myCheckBox.isSelected()); // holder den stadig opdateret.. tror måske man kan lave en timeTable.refresh();
        } else {
            showAlert("Ingen aftale valgt", "Vælg venligst en aftale først for at aflyse.");
        }
    }

    @FXML
    private void handleCloseAppointment() {
        Appointment selectedAppointment = timeTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            AppointmentRepository appointmentRepository = new AppointmentRepository();
            appointmentRepository.updateAppointmentStatus(
                    selectedAppointment.getCustomer_name(),
                    selectedAppointment.getCustomerPhone(),
                    selectedAppointment.getAppointmentDatetime(),
                    selectedAppointment.getEmployeeName(),
                    "closed"
            );
            updateTableData(myCheckBox.isSelected()); // holder tabel opdateret
        } else {
            showAlert("Ingen aftale valgt", "Vælg venligst en aftale først for at afslutte.");
        }
    }

    // alert! :D
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);  // den er bare null indtil videre
        alert.setContentText(message); //udskrevet test er valgt når vi bruger den

        // det har gør vidst at man kan lukke pop op vinduet
        alert.showAndWait();
    }

}

