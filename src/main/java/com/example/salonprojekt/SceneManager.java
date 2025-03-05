package com.example.salonprojekt;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class SceneManager  {
    //kan bruge Scenemanager til at kontrollere alle skift man har behov for
   private Stage stage;
   private final Map<String, Scene> scenes = new HashMap<>();
    private final Map<String, Object> controllers = new HashMap<>();

   public SceneManager (Stage stage) {
       this.stage = stage;
   }
    public void addScene(String name, String fxmlPath, int width, int height) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root, width, height);
            scenes.put(name, scene);

            // Gem controlleren, det hjælper vist med at få WithData til at fungere, siden vi skal bruge EditController der
            Object controller = loader.getController();
            controllers.put(name, controller);

            // Hvis controlleren er en instans af BaseController, kan vi sætte sceneManager, derfor skal alle controller
            //lige huske at implementere BaseController
            if (controller instanceof BaseController) {
                BaseController baseController = (BaseController) controller;
                baseController.setSceneManager(this);
            } else {
                System.out.println("Controller is not an instance of BaseController: " + controller.getClass().getSimpleName());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Kunne ikke loade scenen: " + name);
        }
    }

   public void switchTo (String sceneName) throws IOException {
       if (scenes.containsKey(sceneName)) {
           stage.setScene(scenes.get(sceneName));
           stage.show();
       }
       else {
           System.out.println("Scene " + sceneName + " findes ikke!");
       }
   }

    public void switchToWithData(String sceneName, String name, String phone, String gender, String treatment, LocalDateTime time, String employee) throws IOException {
        if (scenes.containsKey(sceneName)) {
            Scene scene = scenes.get(sceneName);

            //SwitchToWithData er enlig kun for at sende data til EditController
            EditController editController = (EditController) controllers.get(sceneName);

            //Tjekekr lige om null, og så bruger vi methoden i editController
            if (editController != null) {
                editController.setAppointment(name, phone, gender, treatment, time, employee);
            }

            // Skift til scenen, dens arbejde
            stage.setScene(scene);
            stage.show();
        } else {
            System.out.println("Scene " + sceneName + " findes ikke!");
        }
    }
}

