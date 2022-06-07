package com.trustapp.trustableapp.Controller;

import com.trustapp.trustableapp.*;
import com.trustapp.trustableapp.ControllerSavers.MainControllerInstance;
import com.trustapp.trustableapp.DataClass.Provider;
import com.trustapp.trustableapp.DataClass.Service;
import com.trustapp.trustableapp.DataClass.ServiceType;
import com.trustapp.trustableapp.DataClass.State;
import com.trustapp.trustableapp.DataLists.ProviderData;
import com.trustapp.trustableapp.DataLists.ServiceData;
import com.trustapp.trustableapp.DataLists.ServiceTypeData;
import com.trustapp.trustableapp.DataLists.StateData;
import com.trustapp.trustableapp.Error.LaunchAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * this item represents the selectable part of the graphic page, can control an item containing
 * providers, services, service types or states
 */
public class ItemListController {
    //FXML VARIABLES
    /** AnchorPane that is associated to the item that controlled by this controller */
    @FXML
    private AnchorPane anchorPane;

    /** HBox that contains the other items */
    @FXML
    private HBox itemListHBox;

    /** text part contained in the HBox */
    @FXML
    private Label itemLabel;
    /** link part contained in the HBox */
    @FXML
    private Hyperlink hyperLink;

    //PRIVATE VARIABLES
    /** true if this item is selected */
    private boolean vItemBoxSelected = false;

    /** provider corresponding to this item
     * it's null if this item is not associated to it
     */
    private Provider provider;
    /** service corresponding to this item
     * it's null if this item is not associated to it
     */
    private Service service;
    /** service type corresponding to this item
     * it's null if this item is not associated to it
     */
    private ServiceType serviceType;
    /** state corresponding to this item,
     * it's null if this item is not associated to it
     */
    private State state;

    /**
     * method that redirects the user to the link that is the state
     */
    @FXML
    void openState(ActionEvent event){
        if(Desktop.isDesktopSupported())
        {
            try {
                Desktop.getDesktop().browse(new URI(state.getUrlString()));
            } catch (IOException | URISyntaxException e1) {
                e1.printStackTrace();
                LaunchAlert.stateError();
            }
        }
    }

    /**
     * sets the item's data, makes visible and hides some graphic components in order to show the info
     * of the provider passed as a parameter
     * @param prov provider of which the properties should be visible
     */
    public void setData(Provider prov) {
        //hide the link used for the state
        hyperLink.setVisible(false);
        hyperLink.setManaged(false);

        //setting as visible the text and setting what it should say
        itemLabel.setManaged(true);
        itemLabel.setVisible(true);
        itemLabel.setText(prov.getName());
        
        this.provider = prov;
    }

    /**
     * sets the item's data, makes visible and hides some graphic components in order to show the info
     * of the service passed as a parameter
     * @param serv service of which the properties should be visible
     */
    public void setData(Service serv) {
        //hide the link used for the state
        hyperLink.setVisible(false);
        hyperLink.setManaged(false);

        //setting as visible the text and setting what it should say
        itemLabel.setManaged(true);
        itemLabel.setVisible(true);
        itemLabel.setText(serv.getServiceName());

        this.service = serv;
    }

    /**
     * sets the item's data, makes visible and hides some graphic components in order to show the info
     * of the service type passed as a parameter
     * @param serviceType service type of which the properties should be visible
     */
    public void setData(ServiceType serviceType){
        //hide the link used for the state
        hyperLink.setVisible(false);
        hyperLink.setManaged(false);

        //setting as visible the text and setting what it should say
        itemLabel.setManaged(true);
        itemLabel.setVisible(true);
        itemLabel.setText(serviceType.getServiceType());

        this.serviceType = serviceType;
    }

    /**
     * sets the item's data, makes visible and hides some graphic components in order to show the info
     * of the state passed as a parameter
     * @param state state of which the properties should be visible
     */
    public void setData(State state){
        //hide the text
        itemLabel.setManaged(false);
        itemLabel.setVisible(false);

        //make the link to an external source visible and set at what source it should point to
        hyperLink.setVisible(true);
        hyperLink.setManaged(true);
        hyperLink.setText(state.getStatus());

        this.state = state;
    }

    /**
     * method that handles the different types of clicks on the item
     */
    @FXML
    public void itemListSelection(MouseEvent event) {
        if(event.getClickCount() == 1) { //case when the user clicks only once on an item
            if (!vItemBoxSelected) {//item not selected -> make it selected
                selectItem();
                if (this.provider != null) ProviderData.getInstance().addSelected(this.provider);
                else if (this.service != null) ServiceData.getInstance().addSelected(this.service);
                else if (this.serviceType != null) ServiceTypeData.getInstance().addSelected(this.serviceType);
                else if (this.state != null) StateData.getInstance().addSelected(this.state);

            } else { //item is selected -> make it not selected
                deselectItem();
                if (this.provider != null)  //case when the item represents a providers
                    ProviderData.getInstance().removeSelected(this.provider);
                else if (this.service != null)  //case when the item represents a service
                    ServiceData.getInstance().removeSelected(this.service);
                else if (this.serviceType != null) ServiceTypeData.getInstance().removeSelected(this.serviceType);
                else if (this.state != null) StateData.getInstance().removeSelected(this.state);
            }
        }
        else if(event.getClickCount() == 2){ //case when the user double-clicks on an item
            if(provider != null) MainControllerInstance.getInstance().display( provider.toString());
            else if(service != null) MainControllerInstance.getInstance().display( service.toString());
            else if(state != null) MainControllerInstance.getInstance().display( state.toString());
            else if (this.serviceType != null) MainControllerInstance.getInstance().display(this.serviceType.toString());
        }
    }

    /**
     * changes the background color of the item when it's selected and sets it as selected
     */
    public void selectItem(){
        itemListHBox.setStyle("-fx-background-color: transparent;\n    -fx-background-radius: 20; \n    -fx-border-color: #5C6B73; \n    -fx-border-radius: 20;");
        vItemBoxSelected = true;
    }

    /**
     * changes the background color of the item when it's deselected and sets it as not selected
     */
    public void deselectItem(){
        itemListHBox.setStyle("-fx-background-color: #C2DFE3;" + '\n' + "    -fx-background-radius: 20;" +'\n' + "    -fx-border-color: transparent;");
        vItemBoxSelected = false;
    }

    /**
     * whether the item is selected
     * @return  true if the item is selected
     *          false if the item is not selected
     */
    public boolean isvItemBoxSelected() { return vItemBoxSelected;}

    /**
     * Returns the provider variable of this controller
     * @return controller's provider variable
     */
    public Provider getProvider() { return provider; }
    /**
     * Returns the service variable of this controller
     * @return controller's service variable
     */
    public Service getService() { return service; }
    /**
     * Returns the service type variable of this controller
     * @return controller's service type variable
     */
    public ServiceType getServiceType() { return serviceType; }
    /**
     * Returns the state variable of this controller
     * @return controller's state variable
     */
    public State getState() { return state; }

    /**
     * Returns the Controller's anchor pane
     * @return the anchor pane of the controller
     */
    public AnchorPane getAnchorPane() { return anchorPane; }
}
