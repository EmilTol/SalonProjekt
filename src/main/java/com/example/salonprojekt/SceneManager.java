package com.example.salonprojekt;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager  {
    //kan bruge Scenemanager til at kontrollere alle skift man har behov for
   private Stage stage;
   private final Map<String, Scene> scenes = new HashMap<>();

   public SceneManager (Stage stage) {
       this.stage = stage;
   }
   public void addScene(String name, String fxmlPath, int width, int height) throws IOException {
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
           Parent root = loader.load();
           Scene scene = new Scene(root, width, height);
           scenes.put(name, scene);

           Object controller = loader.getController();
           if (controller instanceof BaseController) {
               BaseController baseController = (BaseController) controller;
               baseController.setSceneManager(this);

           } else {
               //fortæller hvis jeg ikke ordentlig sat op BaseController
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
}
