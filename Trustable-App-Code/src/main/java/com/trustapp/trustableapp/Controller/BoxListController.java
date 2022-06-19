package com.trustapp.trustableapp.Controller;

import com.trustapp.trustableapp.DataClass.*;
import com.trustapp.trustableapp.DataLists.NationData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


/**
 * This class represents one of the boxes used in the item list that is placed in the main part of
 * the graphic interface.
 * It has an AnchorPane associated to the whole box and inside it has a VBox.
 * Other boxes or items can be added to the internal part of the box by passing their AnchorPane.
 * There are mainly 3 types of boxes and each type has it own setData("Type") method
 * To be noted that boxes are the graphic part that is not selectable.
 * In order to create a selectable item you should use ItemListController
 */
public class BoxListController implements Comparable{
    //FXML VARIABLES
    /** AnchorPane associated to the whole box*/
    @FXML
    private AnchorPane anchorPane;

    /** text label */
    @FXML
    private Label label;
    /** part that shows the flag image */
    @FXML
    private ImageView flagImg;
    /** VBox where anchor panes can be added to add them to the internal list */
    @FXML
    private VBox listItem;


    //METHODS
    /**
     * insert the data from a nation into a box and by doing so this becomes a
     * box describing a nation
     * @param nat nation from which the data must be shown (flag and name)
     */
    public void setData(Nation nat) {
        flagImg.setImage((NationData.getInstance()).getCardController(nat).getImg());
        label.setText(nat.getCountryCode() + " - " + nat.getCountryName());
    }

    /**
     * insert the data from a provider into a box and by doing so this becomes a
     * box describing a provider
     * @param prov provider from which the data must be shown (name)
     */
    public void setData(Provider prov) {
        label.setText(prov.getName());
        flagImg.setManaged(false);
        flagImg.setVisible(false);
    }

    /**
     * insert the data from a service into a box and by doing so this becomes a
     * box describing a service
     * @param serv service from which the data must be shown (name)
     */
    public void setData(Service serv) {
        label.setText(serv.getServiceName());
        flagImg.setManaged(false);
        flagImg.setVisible(false);
    }

    /**
     * insert the data from a service type into a box and by doing so this becomes a
     * box describing a service type
     * @param serviceType service type from which the data must be shown (name)
     */
    public void setData(ServiceType serviceType) {
        label.setText(serviceType.getServiceType());
        flagImg.setManaged(false);
        flagImg.setVisible(false);
    }

    /**
     * insert the data from a state into a box and by doing so this becomes a
     * box describing a state
     * @param state state from which the data must be shown (name)
     */
    public void setData(State state) {
        label.setText(state.getStatus());
        flagImg.setManaged(false);
        flagImg.setVisible(false);
    }

    /**
     * adds the AnchorPane passed as a parameter to this box by inserting it in the vBox
     * @param anchorPane AnchorPane that has to be inserted in one of the rows of the vBox
     */
    public void addItemInList(AnchorPane anchorPane) {
        listItem.getChildren().add(anchorPane);
    }

    /**
     * tells whether the anchorPane passed as a parameter is present in one of the vBox rows
     * @param anchorPane anchor pane to be searched in the vBox rows
     * @return true if the anchor pane is present
     *         false if the anchor pane is not present
     */
    public boolean contains(AnchorPane anchorPane){
        return listItem.getChildren().contains(anchorPane);
    }

    /**
     * this method is used to get the anchor pane that corresponds to the whole box
     * (including the vBox with the anchor panes inserted in it)
     * @return anchor pane associated with to the whole box
     */
    public AnchorPane getAnchorPane() { return this.anchorPane; }

    /**
     * removes all the anchorpanes added to the vBox inside this box
     */
    public void clearChildren(){ listItem.getChildren().clear(); }

    /**
     * implements Comparable interface and compares BoxListController by the text in the label
     * @param otherBox the object to be compared.
     * @return the value 0 if the text in the otherBox is equal to the text of this box;
     *         a value less than 0 if the text in this box is lexicographically less than the text in the argument box;
     *         and a value greater than 0 if the text in this box is lexicographically greater than the text in the
     *         argument box.
     */
    @Override
    public int compareTo(Object otherBox) {
        return this.label.getText().compareTo((((BoxListController)otherBox).label.getText()));
    }

}
