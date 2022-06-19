package com.trustapp.trustableapp.GUI;

import com.trustapp.trustableapp.Application;
import com.trustapp.trustableapp.Controller.ItemListController;
import com.trustapp.trustableapp.DataClass.*;
import com.trustapp.trustableapp.DataLists.*;
import com.trustapp.trustableapp.Error.LaunchAlert;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

/**
 * Class that represents a selectable object in the interface.
 * It can contain the controllers of services, providers, states and service types.
 */
public class Item {
    /**
     * Method that creates a new item object and initializes it with the given parameter.
     * @param service service to insert in the item object
     * @return the controller of the item if the creation of it is successful, null otherwise (launching an error alert)
     */
    public static ItemListController newItem(Service service){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Application.class.getResource("item-list.fxml"));
            fxmlLoader.load();
            ItemListController itemListController = fxmlLoader.getController();
            itemListController.setData(service);
            ServiceData.getInstance().addItemController(service, itemListController);
            return itemListController;
        }
        catch (IOException e){
            LaunchAlert.noGUI();
        }
        return null;
    }

    /**
     * Method that creates a new item object and initializes it with the given parameter.
     * @param provider provider to insert in the item object
     * @return the controller of the item if the creation of it is successful, null otherwise (launching an error alert)
     */
    public static ItemListController newItem(Provider provider){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Application.class.getResource("item-list.fxml"));
            fxmlLoader.load();
            ItemListController itemListController = fxmlLoader.getController();
            itemListController.setData(provider);
            ProviderData.getInstance().addItemController(provider, itemListController);
            return itemListController;
        }
        catch (IOException e){
            LaunchAlert.noGUI();
        }
        return null;
    }

    /**
     * Method that creates a new item object and initializes it with the given parameter.
     * @param state state to insert in the item object
     * @return the controller of the item if the creation of it is successful, null otherwise (launching an error alert)
     */
    public static ItemListController newItem(State state){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Application.class.getResource("item-list.fxml"));
            fxmlLoader.load();
            ItemListController itemListController = fxmlLoader.getController();
            itemListController.setData(state);
            StateData.getInstance().addItemController(state, itemListController);
            return itemListController;
        }
        catch (IOException e){
            LaunchAlert.noGUI();
        }
        return null;
    }

    /**
     * Method that creates a new item object and initializes it with the given parameter.
     * @param serviceType service type to insert in the item object
     * @return the controller of the item if the creation of it is successful, null otherwise (launching an error alert)
     */
    public static ItemListController newItem(ServiceType serviceType){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Application.class.getResource("item-list.fxml"));
            fxmlLoader.load();
            ItemListController itemListController = fxmlLoader.getController();
            itemListController.setData(serviceType);
            ServiceTypeData.getInstance().addItemController(serviceType, itemListController);
            return itemListController;
        }
        catch (IOException e){
            LaunchAlert.noGUI();
        }
        return null;
    }
}
