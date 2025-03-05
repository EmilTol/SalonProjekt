package com.example.salonprojekt;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class EditController extends BaseController implements Initializable {

    private String name;
    private String phone;
    private String gender;
    private String treatment;
    private LocalDateTime time;
    private String employee;

    @FXML
    private ComboBox<String> genderBox;

    @FXML
    private ComboBox<String> treatmentBox;

    @FXML
    private ComboBox<String> hairPeopleBox;

    @FXML
    private DatePicker timePicker;

    @FXML
    private TextField textfieldName;

    @FXML
    private TextField textfieldPhone;

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

    public void setAppointment(String name, String phone, String gender, String treatment, LocalDateTime time, String employee) {
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.treatment = treatment;
        this.time = time;
        this.employee = employee;

        System.out.println(employee + " modtaget i edit");

        textfieldName.setText(name);
        textfieldPhone.setText(phone);
        genderBox.setValue(gender);
        treatmentBox.setValue(treatment);
        timePicker.setValue(LocalDate.from(time));
        hairPeopleBox.setValue(employee);
    }

    public void switchBack () throws IOException {
        sceneManager.switchTo("table");
    }
}
