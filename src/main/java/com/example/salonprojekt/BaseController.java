package com.example.salonprojekt;


import java.net.URL;
import java.util.ResourceBundle;

public abstract class BaseController {
    protected SceneManager sceneManager;
    //alle controllere der vil gøre brug af SceneManager skal gøre brug af BaseController
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        System.out.println("SceneManager set to " + sceneManager);
    }

    public abstract void initialize(URL url, ResourceBundle resourceBundle);
}
