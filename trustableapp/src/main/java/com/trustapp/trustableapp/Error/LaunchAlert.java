package com.trustapp.trustableapp.Error;

import javafx.scene.control.Alert;

/**
 *  Class that manages the messages of error given to the user.
 */
public class LaunchAlert {
    /**
     * Error given when there are no selectable items on the current page.
     * There are no services or status given for this selection of parameters.
     */
    public static void noItemNext(){
        Alert alert = new Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("No selectable Item");
        alert.setHeaderText(null);
        alert.setContentText("It is not possible to use the 'Next' button, as there are no services available for the selected items.");

        alert.showAndWait();
    }

    /**
     * Error given when there is no internet connection, necessary for the download all the items for the query.
     */
    public  static void noConnection(){
        Alert alert = new Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("No internet connection");
        alert.setHeaderText(null);
        alert.setContentText("It is not possible to download the items without an internet connection.");

        alert.showAndWait();
        System.exit(1);
    }

    /**
     * Error given when there is no conversion of API.
     * @see <a https://esignature.ec.europa.eu/efda/tl-browser/#/screen/home>Help</a>
     */
    public static void noApiConversion(){
        Alert alert = new Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error: no conversion of API");
        alert.setHeaderText(null);
        alert.setContentText("It is not possible to convert the current API. Try to reload the application. If the problem persist go to https://esignature.ec.europa.eu/efda/tl-browser/#/screen/home");

        alert.showAndWait();
        System.exit(1);
    }

    /**
     * Error given when there is no creation of the graphic interface.
     */
    public static void noGUI(){
        Alert alert = new Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error: missed creation of GUI");
        alert.setHeaderText(null);
        alert.setContentText("Something went wrong with the creation of the graphic interface. Try to reload the application.");

        alert.showAndWait();
        System.exit(1);
    }

    /**
     * Error given when the link for the state of the service cannot be opened.
     */
    public static void stateError(){
        Alert alert = new Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error: cannot open service state");
        alert.setHeaderText(null);
        alert.setContentText(null);

        alert.showAndWait();
        System.exit(1);
    }
}
