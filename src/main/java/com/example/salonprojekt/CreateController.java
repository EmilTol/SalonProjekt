package com.example.salonprojekt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import java.io.IOException;

public class CreateController extends BaseController {

    @FXML
    private void switchToScene() throws IOException {
        if (sceneManager != null) {
            sceneManager.switchTo("table");
        } else {
            System.out.println("sceneManager is not initialized!");
        }
    }
}