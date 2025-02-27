package com.example.salonprojekt;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditController extends BaseController implements Initializable {

    @FXML
    private ComboBox<String> genderBox;

    @FXML
    private ComboBox<String> treatmentBox;

    @FXML
    private ComboBox<String> hairPeopleBox;

    @FXML private TextField price;

    @FXML private TextField duration;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        EditDatabaseHandler editDatabaseHandler = new EditDatabaseHandler();
        genderBox.setItems(FXCollections.observableArrayList("M", "F"));

        hairPeopleBox.setItems(FXCollections.observableArrayList(editDatabaseHandler.gettingHairPeople()));

        treatmentBox.setItems(FXCollections.observableArrayList(editDatabaseHandler.gettingTreatments()));

        treatmentBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Edit selectedTreatment = editDatabaseHandler.getTreatmentDetails(newValue);
                if (selectedTreatment != null) {
                    price.setText(String.valueOf(selectedTreatment.getStandardPrice()));
                    duration.setText(String.valueOf(selectedTreatment.getStandardDuration()));
                }
            }
        });
    }

    public void switchBack () throws IOException {
        sceneManager.switchTo("table");
    }
}
