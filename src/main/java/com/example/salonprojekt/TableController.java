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
import java.util.ResourceBundle;


public class TableController extends BaseController implements Initializable {

    @FXML
    private CheckBox myCheckBox;

    @FXML
    private TableView<Table> timeTable;

    @FXML
    private TableColumn<Table, String> name;

    @FXML
    private TableColumn<Table, String> number;

    @FXML
    private TableColumn<Table, String> gender;

    @FXML
    private TableColumn<Table, String> treatment;

    @FXML
    private TableColumn<Table, Double> price;

    @FXML
    private TableColumn<Table, Integer> duration;

    @FXML
    private TableColumn<Table, Integer> time;

    @FXML
    private TableColumn<Table, String> barbar;

    @FXML
    private TableColumn<Table, String> status;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        //en listerner holder vist øje med når der sker ændringer. holder TableView opdateret
        myCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            updateTableData(newValue);
        });

        // indlæser data, så tabellen opdatere
        updateTableData(myCheckBox.isSelected());
    }

    private void updateTableData(boolean isChecked) {
        TableDatabaseHandler tableDatabaseHandler = new TableDatabaseHandler();
        //den her linje ligger gettingOtherTable og gettingTable hvilken en der køre er styret af CheckBox
        ObservableList<Table> tableData = isChecked ? tableDatabaseHandler.gettingOtherTable() : tableDatabaseHandler.gettingTable();

        System.out.println("Antal rækker hentet: " + tableData.size());

        name.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        number.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        gender.setCellValueFactory(new PropertyValueFactory<>("customerGender"));
        treatment.setCellValueFactory(new PropertyValueFactory<>("treatmentName"));
        price.setCellValueFactory(new PropertyValueFactory<>("treatmentPrice"));
        duration.setCellValueFactory(new PropertyValueFactory<>("treatmentDuration"));
        time.setCellValueFactory(new PropertyValueFactory<>("appointmentDatetime"));
        barbar.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        timeTable.setItems(tableData);
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
        if (sceneManager != null) {
            sceneManager.switchTo("edit");
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
        Table selectedAppointment = timeTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            TableDatabaseHandler dbHandler = new TableDatabaseHandler();
            //de her 4 bruges til at opdaterer status på den korrekte, for at undgå Id
            dbHandler.updateAppointmentStatus(
                    selectedAppointment.getCustomer_name(),
                    selectedAppointment.getCustomerPhone(),
                    selectedAppointment.getAppointmentDatetime(),
                    selectedAppointment.getEmployeeName(),
                    "cancelled"
            );
            updateTableData(myCheckBox.isSelected()); // holder den stadig opdateret
        } else {
            showAlert("Ingen aftale valgt", "Vælg venligst en aftale først for at aflyse.");
        }
    }

    @FXML
    private void handleCloseAppointment() {
        Table selectedAppointment = timeTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            TableDatabaseHandler dbHandler = new TableDatabaseHandler();
            dbHandler.updateAppointmentStatus(
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

