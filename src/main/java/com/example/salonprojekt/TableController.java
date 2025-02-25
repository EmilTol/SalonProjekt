package com.example.salonprojekt;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class TableController extends BaseController implements Initializable {

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
    private TableColumn<Table, Integer> time;

    @FXML
    private TableColumn<Table, String> barbar;

    @FXML
    private TableColumn<Table, String> status;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        //her henter vi ting fra databaseHandler
        TableDatabaseHandler tableDatabaseHandler = new TableDatabaseHandler();
        ObservableList<Table> tableData = tableDatabaseHandler.gettingTable();


        System.out.println("Antal rækker hentet: " + tableData.size());

        name.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        number.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        gender.setCellValueFactory(new PropertyValueFactory<>("customerGender"));
        treatment.setCellValueFactory(new PropertyValueFactory<>("treatmentName"));
        price.setCellValueFactory(new PropertyValueFactory<>("treatmentPrice"));
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

