package com.trustapp.trustableapp;

import com.trustapp.trustableapp.API.DataInitializer;
import com.trustapp.trustableapp.Controller.MainController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

/**
 * Class that launches the application.
 * It creates the main controller and opens the window of visualization of the graphic interface.
 */
public class Application extends javafx.application.Application {
   
    /**
     * method called after launch when the application is ready to run
     * @param stage graphic interface
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        //initializing data that will be shown by the application
        DataInitializer.getInstance().initializeData();

        //loading the main scene and setting stage properties
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("main-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Trust-able-App");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * main method in this case starts the application (args is not necessary as it is not used)
     * @param args parameters passed for the launch of the application (not necessary)
     */
    public static void main(String[] args) {
        launch();
    }
}