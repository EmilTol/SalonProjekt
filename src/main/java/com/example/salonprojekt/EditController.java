package com.example.salonprojekt;

import javafx.scene.control.Alert;
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
import java.time.LocalTime;
import java.util.ResourceBundle;

public class EditController extends BaseController implements Initializable {

    private String name;
    private String phone;
    private String gender;
    private String treatment;
    private LocalDate date;
    private LocalTime time;
    private String employee;
    private double extraCost;
    private int extraTime;

    @FXML
    private ComboBox<String> genderBox;

    @FXML
    private ComboBox<String> treatmentBox;

    @FXML
    private ComboBox<String> hairPeopleBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField timePicker;

    @FXML
    private TextField textfieldName;

    @FXML
    private TextField textfieldPhone;

    @FXML private TextField price;

    @FXML private TextField duration;

    @FXML
    private TextField fieldExtraPrice;

    @FXML
    private TextField fieldExtraTime;

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

    public void setAppointment(String name, String phone, String gender, String treatment, LocalDate date, LocalTime time, String employee, double extraCost, int extraTime) {
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.treatment = treatment;
        this.date = date;
        this.time = time;
        this.employee = employee;
        this.extraCost = extraCost;
        this.extraTime = extraTime;


        System.out.println(employee + " modtaget i edit");

        textfieldName.setText(name);
        textfieldPhone.setText(phone);
        genderBox.setValue(gender);
        treatmentBox.setValue(treatment);
        datePicker.setValue(date);
        timePicker.setText(String.valueOf(time));
        hairPeopleBox.setValue(employee);
        fieldExtraPrice.setText(String.valueOf(extraCost));
        fieldExtraTime.setText(String.valueOf(extraTime));
    }

    public void updateAppointment() throws IOException {
        // vores "nye" værdier hvis man har ændret noget
        String updatedName = textfieldName.getText();
        String updatedPhone = textfieldPhone.getText();
        String updatedGender = genderBox.getValue();
        String updatedTreatment = treatmentBox.getValue();
        LocalDate updatedDate = datePicker.getValue();
        String updatedTimeString = timePicker.getText();
        String updatedEmployee = hairPeopleBox.getValue();

        double updatedExtraCost;
        int updatedExtraTime;

        try {
            updatedExtraCost = Double.parseDouble(fieldExtraPrice.getText());
            updatedExtraTime = Integer.parseInt(fieldExtraTime.getText());
        } catch (NumberFormatException e) {
            showAlert("Fejl", "Ekstra tid og pris skal være numeriske værdier!");
            return;
        }

        // samler dato og tid igen
        LocalTime updatedTime = LocalTime.parse(updatedTimeString);
        LocalDateTime updatedDateTime = LocalDateTime.of(updatedDate, updatedTime);

        // updatere databasen
        boolean success = EditDatabaseHandler.updateAppointment(
                name, phone, LocalDateTime.of(date, time), // meget vigtig vi bruger dem og ikke updated
                updatedName, updatedPhone, updatedGender,
                updatedTreatment, updatedDateTime, updatedEmployee,
                updatedExtraCost, updatedExtraTime
        );

        // Tjek om opdateringen lykkedes gg ig
        if (success) {
            showAlert("Succes", "Aftalen er opdateret!");
            switchBack();
        } else {
            showAlert("Fejl", "Kunne ikke opdatere aftalen.");
        }
    }


    public void switchBack () throws IOException {
        sceneManager.switchTo("table");
    }
    // alert! :D
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);  // den er bare null indtil videre
        alert.setContentText(message); //udskrevet test er valgt når vi bruger den

        // det har gør vidst at man kan lukke pop op vinduet
        alert.showAndWait();
    }
}
