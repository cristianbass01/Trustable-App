package com.trustapp.trustableapp.DataLists;

import com.trustapp.trustableapp.Controller.BoxListController;
import com.trustapp.trustableapp.Controller.ItemListController;
import com.trustapp.trustableapp.ControllerSavers.MainControllerInstance;
import com.trustapp.trustableapp.DataClass.Nation;
import com.trustapp.trustableapp.DataClass.Provider;
import com.trustapp.trustableapp.DataClass.Service;
import com.trustapp.trustableapp.DataClass.State;
import com.trustapp.trustableapp.GUI.Box;
import com.trustapp.trustableapp.GUI.Item;
import com.trustapp.trustableapp.MyComparator.comparatorForServiceInResearchServices;
import com.trustapp.trustableapp.MyComparator.comparatorForServiceInResearchStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import java.util.*;

/**
 * class containing data structures about the services and items/boxes associated to services.
 * This class implements the GoF pattern singleton
 */
public class ServiceData {
    //IMPLEMENTING SINGLETON
    /** single instance of the only object of type ServiceData */
    private static ServiceData single_instance = null;

    /**
     * Set containing all the services downloaded though the API
     */
    private SortedSet<Service> allServices = new TreeSet<>();

    /**
     * the constructor is declared as private, this way the objects can be created only by
     * the method getInstance()
     */
    private ServiceData() { }
    /**
     * method used in the singleton implementation. Creates a ServiceData object if it's the first time
     * the class is used. Otherwise, it returns the instance already initialized. By doing so only
     * one ServiceData object exists.
     * @return ServiceData object that is the only instance existing
     */
    public static ServiceData getInstance()
    {
        //case when it's the first time getInstance is called
        if (single_instance == null)
            single_instance = new ServiceData();

        return single_instance;
    }

    //PRIVATE VARIABLES
    /** list of visible boxes associated to the services*/
    private final List<BoxListController> visibleBoxControllers = new ArrayList<>();
    /** list of visible item controllers associated to the services*/
    private final List<ItemListController> visibleItemControllers = new ArrayList<>();

    /** set of currently selected services */
    private final SortedSet<Service> serviceSelected = new TreeSet<>();

    /** list of names of  services that are actually selected. It corresponds to the lateral list of services*/
    private final ObservableList<String> serviceListViewData = FXCollections.observableArrayList();

    /**
     * Creates a map of all box controllers associated to the services.
     */
    private final SortedMap<Service, BoxListController> serviceBoxControllerMap = new TreeMap<>(new comparatorForServiceInResearchStatus());

    /**
     * Creates map of all item controllers associated to the services.
     */
    private final SortedMap<Service, ItemListController> serviceItemControllerMap = new TreeMap<>();



    //METHODS
    /**
     * Adds box controller to the set of box controllers
     * @param service service associated to the box to insert in the set
     * @param boxController box to insert in the set
     */
    public void addBoxController(Service service, BoxListController boxController){
        serviceBoxControllerMap.put(service, boxController);
    }

    /**Returns the box controller of the given service
     * @param service related to the wanted box controller
     * @return box controller associated to the parameters passed
     */
    public BoxListController getBoxController(Service service){
        BoxListController boxListController = serviceBoxControllerMap.get(service);
        if(boxListController == null)
            boxListController = Box.newBox(service);
        return boxListController;
    }

    /**
     * adds the item controller of the given service to the set of items controllers
     * @param service  whose item controller will be added to the set of item controllers
     * @param itemController controller associated to the properties of the service
     */
    public void addItemController(Service service, ItemListController itemController){
        serviceItemControllerMap.put(service, itemController);
    }

    /**
     * Returns item controller associated to the given service
     * @param service whose item controller is returned
     * @return item associated to the service's properties
     */
    public ItemListController getItemController(Service service){
        ItemListController itemListController = serviceItemControllerMap.get(service);
        if(itemListController == null)
            itemListController = Item.newItem(service);
        return itemListController;
    }


    /**
     * Returns the list of all visible services' item controllers
     * @return visibleItemControllers , list of all  visible services' item controllers
     */
    public List<ItemListController> getVisibleItemControllers() {
        return visibleItemControllers;
    }

    /**
     * @return number of item controllers that are visible
     */
    public int numberOfVisibleItemControllers(){
        return visibleItemControllers.size();
    }

    /**
     * Returns a set of all the selected services
     * @return serviceSelected, a set of all the selected services
     */
    public SortedSet<Service> getSelected() {
        return serviceSelected;
    }

    /**
     * method that deletes all box controllers from the list of visible box controllers
     */
    public void noBoxVisible() {
        visibleBoxControllers.clear();
    }
    /**
     * method that deletes all item controllers from the list of visible items controllers
     */
    public void noItemVisible(){
        visibleItemControllers.clear();
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
     * service to add to the list of services name that are selected.
     * It removes the string "All" when an item is selected
     * @param stringToAdd string to add to the list on the left corner of the screen
     */
    public void addToListView(String stringToAdd){
        serviceListViewData.remove("All");
        serviceListViewData.add(stringToAdd);
        Collections.sort(serviceListViewData);
    }

    /**
     * removes a service name from the list of services names and if the list is empty
     * add the String "All"
     * @param stringToRemove service to remove from the list of service names
     */
    public void removeToListView( String stringToRemove){
        serviceListViewData.remove(stringToRemove);
        if(serviceListViewData.size() == 0) serviceListViewData.add("All");
    }

    /**
     * sets the list of services name that are selected to the initial status by adding the string "All"
     */
    public void resetListView(){
        serviceListViewData.clear();
        serviceListViewData.add("All");
    }

    /**
     * @return serviceListViewData, the list of all selected services seen in the tab in left corner of the screen
     */
    public ObservableList<String> getListViewData(){
        return serviceListViewData;
    }

    /**
     * removes all item controllers from the map of selected item controllers and resets the listView
     */
    public void clearSelected(){
        for(Service service: serviceSelected){
            getItemController(service).deselectItem();
        }
        serviceSelected.clear();
        resetListView();
    }

    /**
     * tells whether the list of names of services selected is "All"
     * @return true if the current status is "All" <br>
     *         false otherwise
     */
    public boolean needAllSelection(){
        return serviceListViewData.get(0).equals("All");
    }

    /**
     * initializes the given service's item and sets its service box visible
     * @param service
     */
    public void insertItemInProvider(Service service){
        addItemVisible(getItemController(service));
        Provider provider = service.getProvider();
        (ProviderData.getInstance()).getBoxController(provider).addItemInList((getItemController(service)).getAnchorPane());

        if (!ProviderData.getInstance().isBoxVisible(provider))
            ProviderData.getInstance().insertBoxIn(provider);

    }

    /**
     * Inserts the given service's status box in the service's box
     * @param service whose box is going to contain the state box
     */
    public void insertItemInState(Service service){
        addItemVisible(getItemController(service));
        State state = service.getCurrentStatus();
        (StateData.getInstance()).getBoxController(state).addItemInList((getItemController(service)).getAnchorPane());

        if (!StateData.getInstance().isBoxVisible(state))
            StateData.getInstance().insertBoxInServiceType(state);

    }

    /**
     *  adds a box controller corresponding to the given service in the service's provider's box and sets it visible.
     * @param service
     */
    public void insertBoxIn(Service service){

        addBoxVisible(getBoxController(service));
        Provider provider = service.getProvider();
        (ProviderData.getInstance()).getBoxController(provider).addItemInList((getBoxController(service)).getAnchorPane());

        if (!ProviderData.getInstance().isBoxVisible(provider))
            ProviderData.getInstance().insertBoxIn(provider);
    }

    /**
     * tells whether the box associated to a service contains the provider box and if they are visible
     * @param service whose box controller is going to be checked
     * @return true if the box associated to the properties of the service is visible and contained in the provider's box <br>
     *          false otherwise
     *
     */
    public boolean isBoxVisible(Service service) {
        return visibleBoxControllers.contains(getBoxController(service));
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
     * removes all items from the list of visible boxes associated to the services
     */
    public void clearAllBox() {
        for(BoxListController boxListController : visibleBoxControllers){
            ((VBox) boxListController.getAnchorPane().getParent()).getChildren().remove(boxListController.getAnchorPane());
        }
        visibleBoxControllers.clear();
    }

    /**
     * adds the given service to the set of selected services, to the list view and selects its controller
     * @param service service to add to the selected services
     */
    public void addSelected(Service service) {
        serviceSelected.add(service);
        addToListView(service.getServiceName());
        MainControllerInstance.getInstance().refreshServiceView();
        getItemController(service).selectItem();
    }

    /**
     * Adds all the given services to the set of selected services
     * @param serviceSortedSet set of services to add
     */
    public void addAllSelected(SortedSet<Service> serviceSortedSet){
        for(Service service : serviceSortedSet)
            addSelected(service);
    }

    /**
     * removes a service from the set of selected services, from the list view and deselects its controller
     * @param service provider to remove
     */
    public void removeSelected(Service service) {
        serviceSelected.remove(service);
        removeToListView(service.getServiceName());
        MainControllerInstance.getInstance().refreshServiceView();
        getItemController(service).deselectItem();
    }

    /**
     * Initializes the class' set containing all the services with the given set of services
     * @param allServices set of services
     */
    public void setAllServices(SortedSet<Service> allServices) {
        this.allServices = allServices;
    }

    /**
     * Returns all services
     * @return allServices , set containing all services
     */
    public SortedSet<Service> getAllServices(){
        return allServices;
    }
}
