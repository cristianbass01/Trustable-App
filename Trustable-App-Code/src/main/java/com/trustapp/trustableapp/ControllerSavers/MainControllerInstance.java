package com.trustapp.trustableapp.ControllerSavers;

import com.trustapp.trustableapp.Controller.MainController;

/**
 * Class that represents the instance of the main controller of the page, the one that allows the search
 * and the visualization of the items
 */
public class MainControllerInstance {
    /** instance */
    private static MainController mainController;
    /** sets the content of the instance */
    public static void setInstance(MainController mainCont){
        mainController = mainCont;
    }

    /**
     * Returns the instance
     * @return instance of the main controller
     */
    public static MainController getInstance(){
        return mainController;
    }
}
