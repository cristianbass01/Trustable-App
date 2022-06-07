package com.trustapp.trustableapp.DataLists;

import com.trustapp.trustableapp.Controller.BoxListController;
import com.trustapp.trustableapp.Controller.NationCardController;
import com.trustapp.trustableapp.ControllerSavers.MainControllerInstance;
import com.trustapp.trustableapp.DataClass.Nation;
import com.trustapp.trustableapp.GUI.Box;
import com.trustapp.trustableapp.GUI.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.util.*;

/**
 * class containing data structures about the nations and items/boxes associated to nations
 * This class implements the GoF pattern singleton
 */
public class NationData {
    //IMPLEMENTING SINGLETON
    /** single instance of the only object of type NationData */
    private static NationData single_instance = null;
    /** the constructor is declared as private, this way the objects can be created only by
     * the method getInstance()
     */
    private NationData() { }
    /**
     * method used in the singleton implementation. Creates a NationData object if it's the first time
     * the class is used. Otherwise, it returns the instance already initialized. By doing so only
     * one NationData object exists.
     * @return NationData object that is the only instance existing
     */
    public static NationData getInstance()
    {
        //case when it's the first time getInstance is called
        if (single_instance == null)
            single_instance = new NationData();

        return single_instance;
    }

    //PRIVATE VARIABLES
    /** set containing all nations */
    private SortedSet<Nation> nationsList = new TreeSet<>();
    /** set containing nations that are selected at the moment */
    private final SortedSet<Nation> nationSelected = new TreeSet<>();
    /** set containing nations that were selected at first, but deselected throughout the search */
    private final SortedSet<Nation> nationDeselected = new TreeSet<>();

    /** set containing visible nation cards */
    private final SortedSet<NationCardController> visibleCardControllers = new TreeSet<>();

    /** set containing visible boxes that are assigned to a nation */
    private final SortedSet<BoxListController> visibleBoxControllers = new TreeSet<>();

    /** list of names of currently selected nations. It corresponds to the lateral list view*/
    private final ObservableList<String> nationListViewData = FXCollections.observableArrayList();

    /** map of all nation card controllers <br>
     * Key: country <br>
     * Entry: corresponding nation card controller
     */
    private final SortedMap<Nation, NationCardController> nationCardControllerMap = new TreeMap<>();
    /** map of all nations box controllers <br>
     * Key: country <br>
     * Entry: corresponding box controller
     */
    private final SortedMap<Nation, BoxListController> nationBoxControllerMap = new TreeMap<>();

    //METHODS
    /** method that, when called, returns a set containing all nations
     * @return nationList , a set of all nations
     */
    public SortedSet<Nation> getAllNations() { return nationsList; }

    /** method that given a set of nations, initializes the instance of NationList with the contained nations
     * @param nationsList set of nations for the initialization of the instance nationList of this class
     */
    public void setAllNations(SortedSet<Nation> nationsList) { (NationData.getInstance()).nationsList = nationsList; }

    /**
     *
     * A Card Controller is added to the map of Nation Card Controllers,
     * initializing it with a pair made by a nation and the corresponding nation card.
     * @param nation country
     * @param cardController card controller corresponding to the country code
     */
    public void addCardController(Nation nation, NationCardController cardController){
        nationCardControllerMap.put(nation, cardController);
    }

    /**
     * Adds the given nation to the list of selected nations, to the list view and selects its card controller
     * @param nation nation that has to be moved to the selected countries
     */
    public void addSelected(Nation nation){
        nationSelected.add(nation);
        addToListView(nation.getCountryCode() + " - " +  nation.getCountryName() );
        MainControllerInstance.getInstance().refreshNationView();
        getCardController(nation).selectCard();
    }

    /**
     * Adds all the given nations to the set of selected nations
     * @param nationsList set of nations to add to the selected ones
     */
    public void addAllSelected(SortedSet<Nation> nationsList){
        for(Nation nation : nationsList)
            addSelected(nation);
    }
    /**
     * Removes the given nation from the set of the selected nations,
     * from the list view and deselects its card controller
     * @param nation nation to deselect
     */
    public void removeSelected(Nation nation){
        nationSelected.remove(nation);
        removeToListView(nation.getCountryCode() + " - " +  nation.getCountryName() );
        MainControllerInstance.getInstance().refreshNationView();
        getCardController(nation).deselectCard();
    }

    /**
     * Returns the nation card controller of the nation given, if it exists, otherwise the method creates its card.
     * @param nation country of the wanted nation card controller
     * @return  card controller associated to the nation passed as a parameter or the controller created for it.
     */
    public NationCardController getCardController(Nation nation){
        NationCardController nationCardController =  nationCardControllerMap.get(nation);
        if(nationCardController == null)
            nationCardController = Card.newCard(nation);
        return nationCardController;
    }

    /**Method that, when called, returns the set of visible card controllers
     * @return set containing all the visible card controllers
     */
    public SortedSet<NationCardController> getVisibleCardControllers() {
        return visibleCardControllers;
    }

    /**Method that returns the number of the visible nation card controllers
     * @return number of nation card controllers that are visible
     */
    public int numberOfVisibleCardControllers(){
        return visibleCardControllers.size();
    }

    /**
     * Adds a country name to the list of names of currently selected countries
     * (if it's present it removes the "All" status)
     * @param stringToAdd name to be added
     */
    public void addToListView(String stringToAdd){
        nationListViewData.remove("All");
        nationListViewData.add(stringToAdd);
        Collections.sort(nationListViewData);
    }

    /**
     * removes a country name from the list of names of currently selected countries
     * (if there is no country it adds the "All" status)
     * @param stringToRemove name to be added
     */
    public void removeToListView(String stringToRemove){
        nationListViewData.remove(stringToRemove);
        if(nationListViewData.size() == 0) nationListViewData.add("All");
    }

    /**Method that returns the lateral list of selected nations
     * @return nationListViewData the nation list visible on the left side of the screen
     */
    public ObservableList<String> getListViewData(){
        return nationListViewData;
    }

    /**
     * tells whether the list of names of countries selected is "All"
     * @return true if the current status is "All" <br>
     *         false otherwise
     */
    public boolean needAllSelection(){
        return nationListViewData.get(0).equals("All");
    }

    /**Method that returns the set containing all the nations selected in the first page,
     * then deselected throughout the search
     * @return nationDeselected set of deselected nations
     */
    public SortedSet<Nation> getDeselected(){ return nationDeselected; }
    /** removes all nations from the set of deselected nations*/
    public void clearDeselected(){
        nationDeselected.clear();
    }

    /**
     * removes the given nations from the set of deselected nations
     * @param nationsToRemove subset of nations to be removed from the set of deselected nations
     */
    public void removeAllDeselected(SortedSet<Nation> nationsToRemove){
        nationDeselected.removeAll(nationsToRemove);
    }

    /**
     * Returns the list containing all the selected nations
     * @return nationSelected the list of the selected nations
     */
    public SortedSet<Nation> getSelected() { return nationSelected; }


    /**
     * Adds a nation (managed by a card controller) to the tile pane of nations, attaching it with an anchorPane,
     * and to the visible card controller list.
     * @param nation nation to add to the tile pane
     */
    public void insertCardIn(Nation nation){
        MainControllerInstance.getInstance().getTilePaneOfNations().getChildren().add(getCardController(nation).getAnchorPane());
        visibleCardControllers.add(getCardController(nation));
    }

    /**
     * Method that inserts the box Controller of the given nation to the main page
     * and adds it to the visible box controllers list.
     * @param nation nation of which box is inserted in the main page
     */
    public void insertBoxIn(Nation nation){
        MainControllerInstance.getInstance().getvBox().getChildren().add(getBoxController(nation).getAnchorPane());
        visibleBoxControllers.add(getBoxController(nation));
    }

    /**
     * Tells whether or not a nation box is visible
     * @param nation nation corresponding to the nation box
     * @return true if the box is associated with a visible nation
     *         false otherwise
     */
    public boolean isBoxVisible(Nation nation){
        return visibleBoxControllers.contains(getBoxController(nation));
    }

    /**
     * gets the box controller corresponding to the nation passed as parameter (if existing, otherwise created)
     * @param nation nation corresponding to the wanted box controller
     * @return box controller of the nation passed as a parameter
     */
    public BoxListController getBoxController(Nation nation){
        BoxListController boxListController =  nationBoxControllerMap.get(nation);
        if(boxListController == null)
            boxListController = Box.newBox(nation);
        return boxListController;
    }

    /**
     * adds a box list to the map that is associated with the given nation to its box controller
     * @param nation nation associated with the box controller
     * @param boxListControllerNation   box controller associated with the country
     */
    public void addBoxController(Nation nation, BoxListController boxListControllerNation) {
        nationBoxControllerMap.put(nation, boxListControllerNation);
    }

    /**
     * Adds all nations passed as parameter to the deselected nations and removes from the selected nations the ones
     * present in the set passed as a parameter
     * @param nationToDeselect  set of nations to mark as deselected
     */
    public void addAllDeselected(SortedSet<Nation> nationToDeselect) {
        nationDeselected.addAll(nationToDeselect);
        for (Nation nation : nationToDeselect){
            removeSelected(nation);
            getCardController(nation).deselectCard();
        }
    }

    /**
     * Removes all items from the list of visible boxes associated to nations
     */
    public void clearAllBox() {
        for(BoxListController boxListController : visibleBoxControllers){
            ((VBox) boxListController.getAnchorPane().getParent()).getChildren().remove(boxListController.getAnchorPane());
        }
        visibleBoxControllers.clear();
    }

    /**
     * Removes all cards from the tile pane
     */
    public void clearAllCard() {
        for(NationCardController nationCardController : visibleCardControllers){
            ((TilePane) nationCardController.getAnchorPane().getParent()).getChildren().remove(nationCardController.getAnchorPane());
        }
        visibleCardControllers.clear();
    }

    /**
     * removes all items from nationSelected
     */
    public void clearSelected(){
        for(NationCardController cardController: nationCardControllerMap.values()){
            cardController.deselectCard();
        }
        nationSelected.clear();
        resetListView();
    }

    /**
     * Resets the listView, in the left corner of the screen
     */
    public void resetListView(){
        nationListViewData.clear();
        nationListViewData.add("All");
    }
}
