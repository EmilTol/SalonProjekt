package com.example.salonprojekt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneManager sceneManager = new SceneManager(stage);
        if (sceneManager == null) {
            System.out.println("SceneManager was not initialized!");
        } else {
            System.out.println("SceneManager initialized successfully.");
        }
        //hvis man skal skifte side skal man brug navnet f.eks. "login"
        sceneManager.addScene("login","salon1.fxml",660,440);
        sceneManager.addScene("table","salon2.fxml",660,440);
        sceneManager.addScene("create","salon3.fxml",660,440);
        sceneManager.addScene("edit","salon4.fxml",660,440);

        sceneManager.switchTo("login");

//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("salon1.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 660, 440);
//        stage.setTitle("goddag!");
//        stage.setScene(scene);
//        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}