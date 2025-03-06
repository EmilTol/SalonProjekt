package com.example.salonprojekt;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends BaseController {
    private LoginRepository loginRepository = new LoginRepository();

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel;

    @FXML
    public void initialize() { //Kaldes automatisk når scenen indlæses.
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event)  {
        String username = usernameField.getText(); // henter brugernavn fra felter
        String password = passwordField.getText(); // Henter password fra felter

        Login login = new Login(username, password); // Laver et login objekt med data fra felter
        boolean isValid = loginRepository.validateLogin(login); // sender det til repo for at tjekke om det er der - Ja, meget sikkert i know.
        if (isValid) {
            try {
                sceneManager.switchTo("table"); // SKifter til table scene hvis valid
                usernameField.clear(); // clear felt
                passwordField.clear(); // clear eflt

            } catch (IOException e) { // burde have en bedre fejl honestly.
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fejl");
                alert.setHeaderText("Fejl");
                alert.setContentText("Fejl");
                alert.showAndWait();
            }
        } else { // Hvis kode forkert får vi denne fejlbesked.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login fejl");
            alert.setHeaderText(null);
            alert.setContentText("Forkert brugernavn eller kode");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { // uhhhh? Ikke helt sikker
    }
}