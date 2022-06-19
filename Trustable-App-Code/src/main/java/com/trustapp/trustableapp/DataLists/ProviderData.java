package com.trustapp.trustableapp.DataLists;

import com.trustapp.trustableapp.Controller.BoxListController;
import com.trustapp.trustableapp.Controller.ItemListController;
import com.trustapp.trustableapp.ControllerSavers.MainControllerInstance;
import com.trustapp.trustableapp.DataClass.Nation;
import com.trustapp.trustableapp.DataClass.Provider;
import com.trustapp.trustableapp.GUI.Box;
import com.trustapp.trustableapp.GUI.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import java.util.*;

/**
 * class containing data structures on nations and items/boxes associated with the providers
 * This class implements the GoF pattern singleton
 */
public class ProviderData {
    //IMPLEMENTING SINGLETON
    /** single instance of the only object of type ProviderData */
    private static ProviderData single_instance = null;
    /**
     * the constructor is declared as private, this way the objects can be created only by
     * the method getInstance()
     */
    private ProviderData() { }
    /**
     * method used in the singleton implementation. Creates a ProviderData object if it's the first time
     * the class is used. Otherwise, it returns the instance already initialized. By doing so only
     * one ProviderData object exists.
     * @return ProviderData object that is the only instance existing
     */
    public static ProviderData getInstance()
    {
        //case when it's the first time getInstance is called
        if (single_instance == null)
            single_instance = new ProviderData();

        return single_instance;
    }

    //PRIVATE VARIABLES
    /** set containing all the providers downloaded through the API*/
    private SortedSet<Provider> allProviders = new TreeSet<>();
    /** list of visible boxes associated to the providers*/
    private static final List<BoxListController> visibleBoxControllers = new ArrayList<>();
    /** list of item controllers associated to visible providers */
    private final List<ItemListController> visibleItemControllers = new ArrayList<>();

    /** set of currently selected providers */
    private final SortedSet<Provider> providerSelected = new TreeSet<>();
    /** set of providers that were selected at first but deselected throughout the search*/
    private final SortedSet<Provider> providerDeselected = new TreeSet<>();

    /** list of names of providers that are actually selected. It corresponds to the lateral list of providers*/
    private final ObservableList<String> providerListViewData = FXCollections.observableArrayList();

    /**
     * map of all box controllers associated to the providers. <br>
     * Key: country code + tspId <br>
     * Entry: card controller associated to the provider that has the key country code and tspId
     */
    private final SortedMap<Provider, BoxListController> providerBoxControllerMap = new TreeMap<>();
    /**
     * map of all item controllers associated to the providers. <br>
     * Key: country code + tspId <br>
     * Entry: card controller associated to the provider that has the key country code and tspId
     */
    private final SortedMap<Provider, ItemListController> providerItemControllerMap = new TreeMap<>();

    //METHODS

    /**
     * adds the box controller to the set containing all box controllers
     * @param provider name of the provider relative to the box controller
     * @param boxController box controller associated to the provider
     */
    public void addBoxController(Provider provider, BoxListController boxController){
        providerBoxControllerMap.put(provider, boxController);
    }

    /**
     * Returns the box controller associated to the provider
     * (if existing, if not it creates a new box controller for the provider)
     * @param provider
     * @return controller associated to the provider's properties
     */
    public BoxListController getBoxController(Provider provider){
        BoxListController boxListController = providerBoxControllerMap.get(provider);
        if(boxListController == null)
            boxListController = Box.newBox(provider);
        return boxListController;
    }

    /**
     * adds the item controller to the set of items controllers
     * @param provider
     * @param itemController box controller associated to the properties of the provider
     */
    public void addItemController(Provider provider, ItemListController itemController){
        providerItemControllerMap.put(provider, itemController);
    }
    /**
     * Returns the item controller associated to the given provider
     * (if existing, if not it creates a new item controller for the provider)
     * @param provider associated to the provider
     * @return item controller associated to the provider's properties
     */
    public ItemListController getItemController(Provider provider){
        ItemListController itemListController = providerItemControllerMap.get(provider);
        if(itemListController == null)
            itemListController = Item.newItem(provider);
        return itemListController;
    }


    /**
     * Method that returns the list of all visible item controllers
     * @return visibleItemControllers list of all visible item controllers
     */
    public List<ItemListController> getVisibleItemControllers() {
        return visibleItemControllers;
    }

    /**Returns the number of visible item controllers
     * @return number of visible item controllers
     */
    public int numberOfVisibleItemControllers(){
        return visibleItemControllers.size();
    }

    /**
     * adds the given box to the list of visible box controllers
     * @param boxController box controller to add to the list
     */
    public void addBoxVisible(BoxListController boxController){
        visibleBoxControllers.add(boxController);
    }

    /**
     * adds a item list controller to the list of visible items controllers
     * @param itemListController item controller to add to the list
     */
    public void addItemVisible(ItemListController itemListController){
        visibleItemControllers.add(itemListController);
    }

    /**
     * provider to add to the list of providers names that are selected.
     * It removes the string "All" when an item is selected.
     * @param stringToAdd
     */
    public void addToListView(String stringToAdd){
        providerListViewData.remove("All");
        providerListViewData.add(stringToAdd);
        Collections.sort(providerListViewData);
    }

    /**
     * sets the list of providers name that are selected to the initial status by adding the string "All"
     */
    public void resetListView(){
        providerListViewData.clear();
        providerListViewData.add("All");
    }

    /**
     * removes a provider name from the list of provider names.
     * if the list is empty the method adds the String "All"
     * @param stringToRemove provider to remove from the list of providers names
     */
    public void removeToListView(String stringToRemove){
        providerListViewData.remove(stringToRemove);
        if(providerListViewData.size() == 0) providerListViewData.add("All");
    }

    /**
     * Returns the list of selected providers relative to the one on the left corner of the screen.
     * @return providerListViewData list of selected providers
     */
    public ObservableList<String> getListViewData(){
        return providerListViewData;
    }

    /**
     * tells whether the list of names of providers selected is "All"
     * @return true if the current status is "All" <br>
     *         false otherwise
     */
    public boolean needAllSelection(){
        return providerListViewData.get(0).equals("All");
    }

    /**
     * Returns all the providers that were selected at first but got deselected throughout the search.
     * @return providerDeselected : all deselected providers
     */
    public SortedSet<Provider> getDeselected(){
        return providerDeselected;
    }

    /**
     * Returns all the selected providers
     * @return providerSelected : all selected providers
     */
    public SortedSet<Provider> getSelected(){
        return providerSelected;
    }

    /** removes all providers from the set of deselected providers*/
    public void clearDeselected(){
        providerDeselected.clear();
    }

    /**
     * removes all item controllers from the map of selected item controllers
     */
    public void clearSelected(){
        for(Provider provider: providerSelected){
            getItemController(provider).deselectItem();
        }
        providerSelected.clear();
        resetListView();
    }

    /**
     * Tells whether or not there is a visible box that contains the provider
     * @param provider relative to the wanted box
     * @return true if the box associated to the properties of the provider is visible
     */
    public boolean isBoxVisible(Provider provider){
        return visibleBoxControllers.contains(getBoxController(provider));
    }

    /**
     * Sets the provider's item controller and its nation box visible
     * @param provider  relative to the item controller and the nation box to set visible
     */
    public void insertItemIn(Provider provider){
        addItemVisible(getItemController(provider));
        Nation nation = provider.getNation();
        (NationData.getInstance()).getBoxController(nation).addItemInList((getItemController(provider)).getAnchorPane());

        if(!NationData.getInstance().isBoxVisible(nation))
            NationData.getInstance().insertBoxIn(nation);
    }

    /**
     *  adds a box controller relative to the provider in the box of the given provider's nation and sets it visible
     * @param provider provider relative to the newly added box
     */
    public void insertBoxIn(Provider provider){
        addBoxVisible(getBoxController(provider));
        Nation nation = provider.getNation();
        (NationData.getInstance()).getBoxController(nation).addItemInList((getBoxController(provider)).getAnchorPane());

        if(!NationData.getInstance().isBoxVisible(nation))
            NationData.getInstance().insertBoxIn(nation);
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
     * removes all items from the list of boxes associated to providers and that are visible
     */
    public void clearAllBox() {
        for(BoxListController boxListController : visibleBoxControllers){
            ((VBox) boxListController.getAnchorPane().getParent()).getChildren().remove(boxListController.getAnchorPane());
        }
        visibleBoxControllers.clear();
    }

    /**
     * adds the given provider to the set of selected providers, to the list view and selects its controller
     * @param provider provider to add to the selected providers
     */
    public void addSelected(Provider provider) {
        providerSelected.add(provider);
        addToListView(provider.getName());
        MainControllerInstance.getInstance().refreshProviderView();
        getItemController(provider).selectItem();
    }

    /**
     * Adds all the given providers to the set of selected providers
     * @param providerSortedSet
     */
    public void addAllSelected(SortedSet<Provider> providerSortedSet){
        for(Provider provider: providerSortedSet)
            addSelected(provider);
    }

    /**
     * removes a provider from the set of selected provider, from the list view and deselects its controller
     * @param provider provider to remove
     */
    public void removeSelected(Provider provider) {
        providerSelected.remove(provider);
        removeToListView(provider.getName());
        MainControllerInstance.getInstance().refreshProviderView();
        getItemController(provider).deselectItem();
    }

    /**
     * Adds all providers passed as parameter to the deselected providers.
     * Removes from the list of selected providers the ones present in the set passed as a parameter.
     * @param providersToDeselect  set of nations to mark as deselected
     */
    public void addAllDeselected(SortedSet<Provider> providersToDeselect) {
        providerDeselected.addAll(providersToDeselect);
        for (Provider provider : providersToDeselect){
            removeSelected(provider);
            getItemController(provider).deselectItem();
        }
    }

    /**
     * Initializes the set allProviders, containing all the providers, with the set passed as parameter.
     * @param providers set of providers with which initialize the ProviderData's set allProviders
     */
    public void setAllProviders(SortedSet<Provider> providers) {
        allProviders = providers;
    }

    /**
     * Returns the set containing all the providers.
     * @return allProviders, the set containing all the providers
     */
    public SortedSet<Provider> getAllProviders() {
        return allProviders;
    }

    /**
     * Removes from the set of deselected providers all the providers contained in the set passed as parameter.
     * @param selected set of the providers to remove from the providerDeselected set
     */
    public void removeAllDeselected(SortedSet<Provider> selected) {
        providerDeselected.removeAll(selected);
    }
}
