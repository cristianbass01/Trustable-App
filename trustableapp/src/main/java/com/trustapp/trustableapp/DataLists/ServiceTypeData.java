package com.trustapp.trustableapp.DataLists;

import com.trustapp.trustableapp.Controller.BoxListController;
import com.trustapp.trustableapp.Controller.ItemListController;
import com.trustapp.trustableapp.ControllerSavers.MainControllerInstance;
import com.trustapp.trustableapp.DataClass.Provider;
import com.trustapp.trustableapp.DataClass.Service;
import com.trustapp.trustableapp.DataClass.ServiceType;
import com.trustapp.trustableapp.GUI.Box;
import com.trustapp.trustableapp.GUI.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import java.util.*;

/**
 * class containing data structures about the services types and items/boxes associated to service types.
 * This class implements the GoF pattern singleton
 */
public class ServiceTypeData {
    //IMPLEMENTING SINGLETON
    /** single instance of the only object of type ServiceTypesData */
    private static ServiceTypeData single_instance = null;
    /**
     * the constructor is declared as private, this way the objects can be created only by
     * the method getInstance()
     */
    private ServiceTypeData() { }
    /**
     * method used in the singleton implementation. Creates a ServiceTypesData object if it's the first time
     * the class is used. Otherwise, it returns the instance already initialized. By doing so only
     * one ServiceTypesData object exists.
     * @return ServiceTypesData object that is the only instance existing
     */
    public static ServiceTypeData getInstance()
    {
        //case when it's the first time getInstance is called
        if (single_instance == null)
            single_instance = new ServiceTypeData();

        return single_instance;
    }

    //PRIVATE VARIABLES
    /** list of visible boxes associated to service types */
    private final List<BoxListController> visibleBoxControllers = new ArrayList<>();
    /** list of visible item controllers associated to service types */
    private final List<ItemListController> visibleItemControllers = new ArrayList<>();

    /** set of currently selected service types */
    private final SortedSet<ServiceType> serviceTypesSelected = new TreeSet<>();

    /**
     * Set of service types that were initially selected but deselected throughout the search
     */
    private final SortedSet<ServiceType> serviceTypeDeselected = new TreeSet<>();

    /**
     * Map containing all the service types
     */
    private final SortedMap<String, ServiceType> allServiceTypes = new TreeMap<>();

    /** list of names of service types that are actually selected. It corresponds to the lateral list of service types*/
    private final ObservableList<String> serviceTypesListViewData = FXCollections.observableArrayList();

    /**
     * map of all box controllers associated to the service types.
     */
    private final SortedMap<ServiceType, BoxListController> serviceTypesBoxControllerMap = new TreeMap<>();
    /**
     * map of all item controllers associated to the service types.
     */
    private final SortedMap<ServiceType, ItemListController> serviceTypesItemControllerMap = new TreeMap<>();



    //METHODS

    /**
     * adds the given box controller to the set of box controllers
     * @param serviceType type of the service
     * @param boxController box controller associated to the service type
     */
    public void addBoxController(ServiceType serviceType , BoxListController boxController){
        serviceTypesBoxControllerMap.put(serviceType, boxController);
    }

    /**
     * Returns the given service type's box controller
     * @param serviceType
     * @return box controller associated to the parameters passed
     */
    public BoxListController getBoxController(ServiceType serviceType ){
        BoxListController boxListController = serviceTypesBoxControllerMap.get(serviceType);
        if(boxListController == null)
            boxListController = Box.newBox(serviceType);
        return boxListController;
    }

    /**
     * adds item controller to the set of items controllers
     * @param serviceType type of the service associated to the controller
     * @param itemController box controller associated to the properties of the service type
     */
    public void addItemController(ServiceType serviceType, ItemListController itemController){
        serviceTypesItemControllerMap.put(serviceType, itemController);
    }

    /**
     * Returns the item controller associated to the service type (creating it if it does not exist)
     * @param serviceType type of the service associated to the controller
     * @return item associated to the service type properties
     */
    public ItemListController getItemController(ServiceType serviceType ){
        ItemListController itemListController = serviceTypesItemControllerMap.get(serviceType);
        if(itemListController == null)
            itemListController = Item.newItem(serviceType);
        return itemListController;
    }


    /**
     * Returns the list of all the item controllers
     * @return visibleItemControllers , a list of all the visible service type's item controllers
     */
    public List<ItemListController> getVisibleItemControllers() { return visibleItemControllers; }

    /**Returns the number of visible instances of service type
     * @return number of item controllers that are visible
     */
    public int numberOfVisibleItemControllers() { return visibleItemControllers.size(); }

    /**
     * Returns the set containing the selected service types
     * @return serviceTypeSelected, the set of the selected service types
     */
    public SortedSet<ServiceType> getSelected() { return serviceTypesSelected; }


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
     * service type to add to the list of service types name that are selected.
     * It removes the string "All" when an item is selected
     * @param stringToAdd
     */
    public void addToListView(String stringToAdd){
        serviceTypesListViewData.remove("All");
        serviceTypesListViewData.add(stringToAdd);
        Collections.sort(serviceTypesListViewData);
    }

    /**
     * removes a service type name from the list of service types names and if the list is empty
     * add the String "All"
     * @param stringToRemove service to remove from the list of service types names
     */
    public void removeToListView( String stringToRemove){
        serviceTypesListViewData.remove(stringToRemove);
        if(serviceTypesListViewData.size() == 0) serviceTypesListViewData.add("All");
    }

    /**
     * sets the list of services types names that are selected to the initial status by adding the string "All"
     */
    public void resetListView(){
        serviceTypesListViewData.clear();
        serviceTypesListViewData.add("All");
    }

    /**
     * Returns the list of the visible service types, referring to the one on the left corner on the screen.
     * @return serviceTypesListViewData, the list containing all the visible service types
     */
    public ObservableList<String> getListViewData(){
        return serviceTypesListViewData;
    }

    /**
     * removes all item controllers from the map of selected item controllers
     */
    public void clearSelected(){
        for(ItemListController itemListController: serviceTypesItemControllerMap.values()){
            itemListController.deselectItem();
        }
        serviceTypesSelected.clear();
        resetListView();
    }

    /**
     * tells whether the list of names of service types selected is "All"
     * @return true if the current status is "All" <br>
     *         false otherwise
     */
    public boolean needAllSelection(){
        return serviceTypesListViewData.get(0).equals("All");
    }

    /**
     * Initializes the given service type item controller and sets its box visible
     * @param serviceType service type to check
     */
    public void insertItemIn(ServiceType serviceType){
        addItemVisible(getItemController(serviceType));
        Provider provider = serviceType.getProvider();
        (ProviderData.getInstance()).getBoxController(provider).addItemInList((getItemController(serviceType)).getAnchorPane());

        if(!ProviderData.getInstance().isBoxVisible(provider))
            ProviderData.getInstance().insertBoxIn(provider);
    }

    /**
     *  adds the box controller corresponding to the given service type to the box controller of the provider
     * @param serviceType service type to check
     */
    public void insertBoxIn(ServiceType serviceType){
        addBoxVisible(getBoxController(serviceType));
        Provider provider = serviceType.getProvider();
        (ProviderData.getInstance()).getBoxController(provider).addItemInList((getBoxController(serviceType)).getAnchorPane());

        if(!ProviderData.getInstance().isBoxVisible(provider))
            ProviderData.getInstance().insertBoxIn(provider);
    }

    /**
     * tells whether the box associated to a service contains the service's type box and if they are visible
     * @param  serviceType service type to check
     * @return true if the box associated to the properties of the service type is visible and contained in the service's box
     */
    public boolean isBoxVisible(ServiceType serviceType) {
        return visibleBoxControllers.contains(getBoxController(serviceType));
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
     * removes all items from the list of boxes associated to service types and that are visible
     */
    public void clearAllBox() {
        for(BoxListController boxListController : visibleBoxControllers){
            ((VBox) boxListController.getAnchorPane().getParent()).getChildren().remove(boxListController.getAnchorPane());
        }
        visibleBoxControllers.clear();
    }

    /**
     * adds a service type to the set of selected service types, to the listView and selects its controller
     * @param serviceType service type to add
     */
    public void addSelected(ServiceType serviceType) {
        serviceTypesSelected.add(serviceType);
        addToListView(serviceType.toString());
        MainControllerInstance.getInstance().refreshServiceTypeView();
        getItemController(serviceType).selectItem();
    }

    /**
     * Adds the given service types to the list of selected service types
     * @param serviceTypeSortedSet set of service types to add to the list of selected ones
     */
    public void addAllSelected(SortedSet<ServiceType> serviceTypeSortedSet){
        for(ServiceType serviceType : serviceTypeSortedSet)
            addSelected(serviceType);
    }

    /**
     * removes the given service type from the set of selected service types, from the list view and adds it
     * to the set of deselected items
     * @param serviceType service type to remove from the list of selected service types
     */
    public void removeSelected(ServiceType serviceType) {
        serviceTypesSelected.remove(serviceType);
        removeToListView(serviceType.toString());
        MainControllerInstance.getInstance().refreshServiceTypeView();
        getItemController(serviceType).deselectItem();
    }

    /**
     * Deselects the given service types and adds them to the list of deselected service types
     * (removing them from the selected list)
     * @param serviceTypesToDeselect set of service types to deselect
     */
    public void addAllDeselected(SortedSet<ServiceType> serviceTypesToDeselect) {
        serviceTypeDeselected.addAll(serviceTypesToDeselect);
        for (ServiceType serviceType : serviceTypesToDeselect){
            removeSelected(serviceType);
            getItemController(serviceType).deselectItem();
        }
    }

    /**
     * returns the service types that were once selected then deselected throughout the search
     * @return serviceTypeDeselected , set of deselected service types
     */
    public SortedSet<ServiceType> getDeselected(){
        return serviceTypeDeselected;
    }

    /**
     * Clears the set of selected service types
     */
    public void clearDeselected() {
        serviceTypeDeselected.clear();
    }

    /**
     * Initializes the class' set of all service types with the given set of service types
     * @param serviceTypes set of service types
     */
    public void setAllServiceType(TreeSet<ServiceType> serviceTypes) {
        for (ServiceType serviceType : serviceTypes){
            allServiceTypes.put(serviceType.getServiceType(), serviceType);
        }
    }

    /**
     * Returns a set containing all the service types converted to String
     * @return set of all the keys of the service types
     */
    public SortedSet<String> getAllServiceTypeToString(){
        return new TreeSet<>(allServiceTypes.keySet());
    }

    /**
     * Returns a set containing all the service types in the application
     * @return set of all service types
     */
    public SortedSet<ServiceType> getAllServiceType() {
        return new TreeSet<>(allServiceTypes.values());
    }
}
