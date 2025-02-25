package com.example.salonprojekt;


public abstract class BaseController {
    protected SceneManager sceneManager;
    //alle controllere der vil gøre brug af SceneManager skal gøre brug af BaseController
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        System.out.println("SceneManager set to " + sceneManager);
    }
}
