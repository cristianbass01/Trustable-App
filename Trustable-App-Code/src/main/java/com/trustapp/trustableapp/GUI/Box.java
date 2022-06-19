package com.trustapp.trustableapp.GUI;

import com.trustapp.trustableapp.Application;
import com.trustapp.trustableapp.Controller.BoxListController;
import com.trustapp.trustableapp.DataClass.*;
import com.trustapp.trustableapp.DataLists.*;
import com.trustapp.trustableapp.Error.LaunchAlert;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

/**
 * Class that manages the 'Box' instances created for the graphic interface.
 * Boxes contain instances such as the controllers of a nation, a provider, a service,
 * a state or a service type object.
 * It is not a selectable object, it can contain item objects (or other boxes).
 * This class operates with the BoxListController class
 */
public class Box {
    /**
     * Creates a box containing a nation object, passing the parameter to this box's controller
     * @param nation nation to insert in the box
     * @return the controller of the box if the creation of it is successful, null otherwise (launching an error alert)
     */
    public static BoxListController newBox(Nation nation){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Application.class.getResource("box-list.fxml"));
            fxmlLoader.load();
            BoxListController boxListController = fxmlLoader.getController();
            boxListController.setData(nation);
            NationData.getInstance().addBoxController(nation, boxListController);
            return boxListController;
        }
        catch (IOException e){
            LaunchAlert.noGUI();
        }
        return null;
    }

    /**
     * Creates a box containing a provider object, passing the parameter to this box's controller
     * @param provider provider to insert in the box
     * @return the controller of the box if the creation of it is successful, null otherwise (launching an error alert)
     */
    public static BoxListController newBox(Provider provider){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Application.class.getResource("box-list.fxml"));
            fxmlLoader.load();
            BoxListController boxListController = fxmlLoader.getController();
            boxListController.setData(provider);
            ProviderData.getInstance().addBoxController(provider, boxListController);
            return boxListController;
        }
        catch (IOException e){
            LaunchAlert.noGUI();
        }
        return null;
    }

    /**
     * Creates a box containing a service object, passing the parameter to this box's controller
     * @param service nation to insert in the box
     * @return the controller of the box if the creation of it is successful, null otherwise (launching an error alert)
     */
    public static BoxListController newBox(Service service){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Application.class.getResource("box-list.fxml"));
            fxmlLoader.load();
            BoxListController boxListController = fxmlLoader.getController();
            boxListController.setData(service);
            ServiceData.getInstance().addBoxController(service, boxListController);
            return boxListController;
        }
        catch (IOException e){
            LaunchAlert.noGUI();
        }
        return null;
    }

    /**
     * Creates a box containing a state object, passing the parameter to this box's controller
     * @param state state to insert in the box
     * @return the controller of the box if the creation of it is successful, null otherwise (launching an error alert)
     */
    public static BoxListController newBox(State state){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Application.class.getResource("box-list.fxml"));
            fxmlLoader.load();
            BoxListController boxListController = fxmlLoader.getController();
            boxListController.setData(state);
            StateData.getInstance().addBoxController(state, boxListController);
            return boxListController;
        }
        catch (IOException e){
            LaunchAlert.noGUI();
        }
        return null;
    }

    /**
     * Creates a box containing a service type object, passing the parameter to this box's controller
     * @param serviceType service type to insert in the box
     * @return the controller of the box if the creation of it is successful, null otherwise (launching an error alert)
     */
    public static BoxListController newBox(ServiceType serviceType){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Application.class.getResource("box-list.fxml"));
            fxmlLoader.load();
            BoxListController boxListController = fxmlLoader.getController();
            boxListController.setData(serviceType);
            ServiceTypeData.getInstance().addBoxController(serviceType, boxListController);
            return boxListController;
        }
        catch (IOException e){
            LaunchAlert.noGUI();
        }
        return null;
    }

}
