package com.example.salonprojekt;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

    }

