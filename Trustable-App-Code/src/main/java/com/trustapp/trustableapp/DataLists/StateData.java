package com.trustapp.trustableapp.DataLists;

import com.trustapp.trustableapp.Controller.BoxListController;
import com.trustapp.trustableapp.Controller.ItemListController;
import com.trustapp.trustableapp.ControllerSavers.MainControllerInstance;
import com.trustapp.trustableapp.DataClass.Service;
import com.trustapp.trustableapp.DataClass.ServiceType;
import com.trustapp.trustableapp.DataClass.State;
import com.trustapp.trustableapp.GUI.Box;
import com.trustapp.trustableapp.GUI.Item;
import com.trustapp.trustableapp.MyComparator.comparatorForStateInResearchServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import java.util.*;
/**
 * class containing data structures about the states and items/boxes associated to states.
 * This class implements the GoF pattern singleton
 */
public class StateData {
    //IMPLEMENTING SINGLETON
    /** single instance of the only object of type StateData */
    private static StateData single_instance = null;
    /** set that contains all the states of the services*/
    private SortedSet<State> allStates;

    /**
     * the constructor is declared as private, this way the objects can be created only by
     * the method getInstance()
     */
    private StateData() { }
    /**
     * method used in the singleton implementation. Creates a StateData object if it's the first time
     * the class is used. Otherwise, it returns the instance already initialized. By doing so only
     * one StateData object exists.
     * @return StateData object that is the only instance existing
     */
    public static StateData getInstance()
    {
        //case when it's the first time getInstance is called
        if (single_instance == null)
            single_instance = new StateData();

        return single_instance;
    }

    //PRIVATE VARIABLES
    /** list of visible boxes associated to the states */
    private final List<BoxListController> visibleBoxControllers = new ArrayList<>();
    /** list of visible item controllers */
    private final List<ItemListController> visibleItemControllers = new ArrayList<>();

    /** set of currently selected states */
    private final SortedSet<State> stateSelected = new TreeSet<>();

    /** list of names of states that are actually selected. It corresponds to the lateral list of states*/
    private final ObservableList<String> stateListViewData = FXCollections.observableArrayList();

    /**
     * map of all box controllers associated to the states.
     */
    private final SortedMap<State, BoxListController> stateBoxControllerMap = new TreeMap<>(new comparatorForStateInResearchServices());
    /**
     * map of all item controllers associated to the states.
     */
    private final SortedMap<State, ItemListController> stateItemControllerMap = new TreeMap<>();



    //METHODS


    /**
     * add a box associated to the parameters passed to the map that contains the other state boxes
     * @param state state to be added
     * @param boxController referring to the state
     */
    public void addBoxController(State state, BoxListController boxController){
        stateBoxControllerMap.put(state, boxController);
    }

    /**
     * Returns the box controller of the given state (if existing, otherwise it creates it)
     * @param state whose box is the one searched
     * @return box controller associated with the parameters passed
     */
    public BoxListController getBoxController(State state){
        BoxListController boxListController =  stateBoxControllerMap.get(state);
        if(boxListController == null)
            boxListController = Box.newBox(state);
        return boxListController;
    }


    /**
     * adds the state's item controller to the set of items controllers
     * @param state to check
     * @param itemController referred to the state
     */
    public void addItemController(State state, ItemListController itemController) {
        stateItemControllerMap.put(state, itemController);
    }

    /**
     * Returns the item controller of the given state
     * @param state to check
     * @return the state's item controller
     */
    public ItemListController getItemController(State state){
        ItemListController itemListController = stateItemControllerMap.get(state);
        if(itemListController == null)
            itemListController = Item.newItem(state);
        return itemListController;
    }


    /**
     * Returns the visible item controllers of the state instances
     * @return visibleItemControllers , list of all the visible item controllers
     */
    public List<ItemListController> getVisibleItemControllers() {
        return visibleItemControllers;
    }

    /**
     * returns the set containing all the selected states
     * @return the set of the selected states
     */
    public SortedSet<State> getSelected() {
        return stateSelected;
    }


    /**
     * adds a box to the list of visible box controllers
     * @param boxController box controller to add to the list
     */
    public void addBoxVisible(BoxListController boxController){
        visibleBoxControllers.add(boxController);
    }

    /**
     * adds a list item controller to the list of visible items controllers
     * @param itemListController item controller to add to the list
     */
    public void addItemVisible(ItemListController itemListController){
        visibleItemControllers.add(itemListController);
    }

    /**
     * Adds the string of states to the list of states that are referred in the tab in the left corner of the screen.
     * It removes the string "All" when an item is selected
     * @param stringToAdd
     */
    public void addToListView(String stringToAdd){
        stateListViewData.remove("All");
        stateListViewData.add(stringToAdd);
        Collections.sort(stateListViewData);
    }

    /**
     * removes a state name from the list of services names and if the list is empty
     * add the String "All"
     * @param stringToRemove service to remove from the list of service names
     */
    public void removeToListView( String stringToRemove){
        stateListViewData.remove(stringToRemove);
        if(stateListViewData.size() == 0) stateListViewData.add("All");
    }

    /**
     * sets the list of states name that are selected to the initial status by adding the string "All"
     */
    public void resetListView(){
        stateListViewData.clear();
        stateListViewData.add("All");
    }

    /**
     * Returns the list view data, the list of states referred in the left corner of the screen
     * @return stateListViewData list of states
     */
    public ObservableList<String> getListViewData(){
        return stateListViewData;
    }

    /**
     * removes all item controllers from the map of selected item controllers
     */
    public void clearSelected(){
        for(ItemListController itemListController: stateItemControllerMap.values()){
            itemListController.deselectItem();
        }
        stateSelected.clear();
        resetListView();
    }

    /**
     * tells whether the list of names of states selected is "All"
     * @return true if the current status is "All" <br>
     *         false otherwise
     */
    public boolean needAllSelection(){
        return stateListViewData.get(0).equals("All");
    }

    /**
     * Inserts the state's box in its service's box and sets it visible
     * @param state
     */
    public void insertItemInService(State state){
        addItemVisible(getItemController(state));
        Service service = state.getService();
        (ServiceData.getInstance()).getBoxController(service).addItemInList((getItemController(state)).getAnchorPane());

        if(!ServiceData.getInstance().isBoxVisible(service))
            ServiceData.getInstance().insertBoxIn(service);
    }

    /**
     * Inserts the state's item in the relative service type's box and sets it visible
     * @param state
     */
    public void insertItemInServiceType(State state){
        addItemVisible(getItemController(state));
        ServiceType serviceType = state.getServiceType();
        (ServiceTypeData.getInstance()).getBoxController(serviceType).addItemInList((getItemController(state)).getAnchorPane());

        if (!ServiceTypeData.getInstance().isBoxVisible(serviceType))
            ServiceTypeData.getInstance().insertBoxIn(serviceType);
    }

    /**
     * Inserts the state's box in the relative service type's box and sets it visible
     * @param state
     */
    public void insertBoxInServiceType(State state){
        addBoxVisible(getBoxController(state));
        ServiceType serviceType = state.getServiceType();
        (ServiceTypeData.getInstance()).getBoxController(serviceType).addItemInList((getBoxController(state)).getAnchorPane());

        if (!ServiceTypeData.getInstance().isBoxVisible(serviceType))
            ServiceTypeData.getInstance().insertBoxIn(serviceType);

    }

    /**
     * Checks if the given state's box is visible
     * @param state to check
     * @return true if the box is visible, false otherwise
     */
    public boolean isBoxVisible(State state) {
        return visibleBoxControllers.contains(getBoxController(state));
    }

    /**
     * removes all item controllers from the list of visible item controllers
     */
    public void clearAllItems() {
        for(ItemListController itemListController : visibleItemControllers){
            ((VBox) itemListController.getAnchorPane().getParent()).getChildren().remove(itemListController.getAnchorPane());
        }
        visibleItemControllers.clear();
    }

    /**
     * removes all items from the list of boxes associated to states and that are visible
     */
    public void clearAllBox() {
        for(BoxListController boxListController : visibleBoxControllers){
            ((VBox) boxListController.getAnchorPane().getParent()).getChildren().remove(boxListController.getAnchorPane());
        }
        visibleBoxControllers.clear();
    }

    /**
     * adds a state to the set of selected states, to the list view and selects its item
     * @param state state to add
     */
    public void addSelected(State state) {
        stateSelected.add(state);
        addToListView(state.getStatus());
        MainControllerInstance.getInstance().refreshStateView();
        getItemController(state).selectItem();
    }

    /**
     * Adds the given states to the list of selected states
     * @param stateSortedSet set of states to add to the list of selected states
     */
    public void addAllSelected(SortedSet<State> stateSortedSet){
        for(State state : stateSortedSet)
            addSelected(state);
    }

    /**
     * removes the given state from the set of selected states, from the listView and deselects it.
     * @param state state to remove
     */
    public void removeSelected(State state) {
        stateSelected.remove(state);
        removeToListView(state.getStatus());
        MainControllerInstance.getInstance().refreshStateView();
        getItemController(state).deselectItem();
    }

    /**
     * Initializes the set containing all states with the set given
     * @param states set of states
     */
    public void setAllStates(TreeSet<State> states) {
        this.allStates = states;
    }

    /**
     * Returns a set containing all the states, translated in Strings
     * @return set of states (in strings)
     */
    public SortedSet<String> getAllStatesString(){
        List<String> tempStatesString = new LinkedList<>();
        for(State state : allStates)
            tempStatesString.add(state.getStatus());
        return new TreeSet<>(tempStatesString);
    }

    /**
     * Returns the set containing all the states
     * @return set of states
     */
    public SortedSet<State> getAllStates(){
        return allStates;
    }

    /**
     * Returns the number of visible state items
     * @return number of visible states' item controllers
     */
    public int numberOfVisibleItemControllers() {
        return visibleItemControllers.size();
    }
}