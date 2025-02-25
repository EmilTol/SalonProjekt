package com.example.salonprojekt;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

import java.io.IOException;

public class LoginController {
    private LoginDatabaseHandler loginDatabaseHandler = new LoginDatabaseHandler();

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label statusLabel;

    @FXML
    public void initialize() {
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        Login login = new Login(username, password);
        boolean isValid = loginDatabaseHandler.validateLogin(login);

        if (isValid) {
            try {
                Parent newRoot = FXMLLoader.load(getClass().getResource("salon2.fxml"));
                usernameField.getScene().setRoot(newRoot);
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fejl");
                alert.setHeaderText("Fejl");
                alert.setContentText("Fejl");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login fejl");
            alert.setHeaderText(null);
            alert.setContentText("Forkert brugernavn eller kode");
            alert.showAndWait();
        }
    }
}