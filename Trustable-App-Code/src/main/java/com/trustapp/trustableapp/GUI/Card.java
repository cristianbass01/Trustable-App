package com.trustapp.trustableapp.GUI;

import com.trustapp.trustableapp.Application;
import com.trustapp.trustableapp.Controller.NationCardController;
import com.trustapp.trustableapp.DataClass.Nation;
import com.trustapp.trustableapp.DataLists.NationData;
import com.trustapp.trustableapp.Error.LaunchAlert;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

/**
 * Class that represents a selectable graphic object.
 * It can contain the controller of nation-type objects.
 * It differs from the Item class for the graphic implementation.
 */
public class Card {
    /**
     * Method that creates a new card object and initializes it with the given parameter.
     * @param nation nation to insert in the card
     * @return the controller of the card if the creation of it is successful, null otherwise (launching an error alert)
     */
    public static NationCardController newCard(Nation nation) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Application.class.getResource("card-item.fxml"));
            fxmlLoader.load();
            NationCardController cardController = fxmlLoader.getController();
            cardController.setData(nation);
            NationData.getInstance().addCardController(nation, cardController);
            return cardController;
        }
        catch (IOException e){
            LaunchAlert.noGUI();
        }
        return null;
    }
}
