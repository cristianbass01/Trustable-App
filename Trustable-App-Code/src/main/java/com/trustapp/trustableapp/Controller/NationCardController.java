package com.trustapp.trustableapp.Controller;

import com.trustapp.trustableapp.Application;
import com.trustapp.trustableapp.ControllerSavers.MainControllerInstance;
import com.trustapp.trustableapp.DataClass.Nation;
import com.trustapp.trustableapp.DataLists.NationData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.Objects;
/**
 * this item represents the selectable part of the graphic page, can control an item containing nations
 */
public class NationCardController implements Comparable{
    //FMXL VARIABLES
    /** AnchorPane associated to the whole box*/
    @FXML
    private AnchorPane anchorPane;
    /** text label */
    @FXML
    private Label countryCodeLabel;
    /** Flag image */
    @FXML
    private ImageView imgView;
    /** label of the country's name */
    @FXML
    private Label countryNameLabel;
    /** VBox where anchor panes can be added to add them to the internal list */
    @FXML
    private VBox vItemBox;



    //PRIVATE VARIABLES
    /** indicates whether the box has been selected */
    private boolean vItemBoxSelected = false;
    /** nation shown by the box */
    private Nation nation;
    /** image corresponding to the flag of the nation */
    private Image img;

    //METHODS
    /**
     * sets the box parameters according to the data of the nation passed as a parameter
     * @param nation nation to assign to the box
     */
    public void setData(Nation nation) {
        this.nation = nation;

        //setting labels that are show in the box
        countryNameLabel.setText(this.nation.getCountryName());
        countryCodeLabel.setText(this.nation.getCountryCode());

        //Search for the flag by countryCode
        img = new Image(Objects.requireNonNull(Application.class.getResource("img/" + nation.getCountryCode().toLowerCase() + ".png")).toExternalForm(), 100, 70, false, false);
        if(img.isError()){
            img = new Image(Objects.requireNonNull(Application.class.getResource("img/img_not_found.png")).toExternalForm(),100, 70, false, false);
        }
        imgView.setImage(img);
    }

    /**
     * method that handles the different types of clicks on the nation card
     */
    @FXML
    public void nationSelection(MouseEvent event) {
        if(event.getClickCount() == 2){ //case when the user double-clicks on a nationCard
            MainControllerInstance.getInstance().display(nation.toString());
        }
        else if(event.getClickCount() == 1) {   //case when the user clicks only once on a nationCard
            if (!vItemBoxSelected) {//item not selected -> make it selected
                NationData.getInstance().addSelected(this.nation);
            } else { //item selected -> make it not selected
                NationData.getInstance().removeSelected(this.nation);
            }
        }
    }

    /** change the nation card background color to the one used for selected nations and set it as selected */
    public void selectCard(){
        vItemBox.setStyle("-fx-background-color: #2B2B2B;" + '\n' + "    -fx-background-radius: 20;");
        vItemBoxSelected = true;
    }

    /** change the nation card background color to the one used for not selected nations and set it as not selected */
    public void deselectCard(){
        vItemBox.setStyle("-fx-background-color: #5C6B73;" + '\n' + "    -fx-background-radius: 20;");
        vItemBoxSelected = false;
    }

    /**
     * see if the box is currently selected
     * @return true if the box is selected
     *         false if the box is not selected
     */
    public boolean isvItemBoxSelected() {
        return vItemBoxSelected;
    }

    /** @return nation assigned to this nation card */
    public Nation getNation() { return nation; }

    /** @return Image of the flag of the country assigned to this nation card */
    public Image getImg() { return img; }

    /** @return anchor pane to which this nation card has been attached*/
    public AnchorPane getAnchorPane() { return this.anchorPane; }

    /**
     * compare this nation considering the lexographical order of the card label
     * @param otherCard the object to be compared.
     * @return the value 0 if the argument label text is equal to this label;
     *         a value less than 0 if this label is lexicographically less than the argument label;
     *         and a value greater than 0 if this label is lexicographically greater than the argument label.
     */
    @Override
    public int compareTo(Object otherCard) {
        return this.countryCodeLabel.getText().compareTo((((NationCardController)otherCard).countryCodeLabel.getText()));
    }
}
