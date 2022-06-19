package com.trustapp.trustableapp.Controller;

import com.trustapp.trustableapp.ControllerSavers.MainControllerInstance;
import com.trustapp.trustableapp.DataClass.*;
import com.trustapp.trustableapp.DataLists.*;
import com.trustapp.trustableapp.Error.LaunchAlert;
import com.trustapp.trustableapp.Loader.Loader;
import com.trustapp.trustableapp.Search.Search;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.*;

/**
 * Class that manages the graphic interface and the interaction between the user and the application
 */
public class MainController implements Initializable {
    //FXML VARIABLES

    @FXML
    private Accordion accord;
    @FXML
    private VBox vBoxAccordion;
    @FXML
    private Tab advanceResearch;
    @FXML
    private Button closeDisplayBtn;
    @FXML
    private Label displayLabel;
    @FXML
    private ScrollPane displayScroll;
    @FXML
    private Label labelLeft;
    @FXML
    private ListView<String> nationListView;
    @FXML
    private TitledPane nationTitledPane;
    @FXML
    private Button nextButtonStatus;
    @FXML
    private Button nextButtonServices;
    @FXML
    private Label pageLabelOfStatus;
    @FXML
    private Label pageLabelServices;
    @FXML
    private Button previousButtonStatus;
    @FXML
    private Button previousButtonServices;
    @FXML
    private ListView<String> providerListView;
    @FXML
    private TitledPane providerTitledPane;
    @FXML
    private ScrollPane scrollStatus;
    @FXML
    private ScrollPane scrollServices;
    @FXML
    private TextField searchBarStatus;
    @FXML
    private TextField searchBarResearch;
    @FXML
    private TextField searchBarServices;
    @FXML
    private ChoiceBox<String> searchByChoiceBox;
    @FXML
    private ListView<String> serviceListView;
    @FXML
    private TitledPane serviceTitledPane;
    @FXML
    private ListView<String> serviceTypesListView;
    @FXML
    private TitledPane serviceTypesTitledPane;
    @FXML
    private ListView<String> statusListView;
    @FXML
    private TitledPane stateTitledPane;
    @FXML
    private TabPane tabPane;
    @FXML
    private TilePane tilePaneOfNations = getNewTilePane();
    @FXML
    private static final VBox vBox = new VBox();
    @FXML
    private ScrollPane scrollResearch;
    @FXML
    private Button servicesResearch;
    @FXML
    private Button statusResearch;
    @FXML
    private VBox researchOfVBox;
    @FXML
    private HBox serviceHBox;
    @FXML
    private HBox serviceTypeHBox;
    @FXML
    private HBox stateOfServiceHBox;
    @FXML
    private Button advanceSearchBtn;
    @FXML
    private Separator separator1;
    @FXML
    private Separator separator2;
    @FXML
    private Separator separator3;
    @FXML
    private TextField searchTextNation;
    @FXML
    private TextField searchTextProvider;
    @FXML
    private TextField searchTextService;
    @FXML
    private CheckComboBox<String> comboBoxServiceType;
    @FXML
    private CheckComboBox<String> comboBoxState;

    /** selectable list view used for the searchbar in "Research tab" */
    private static final ObservableList<String> searchByChoiceBoxList = FXCollections.observableArrayList();

    /**
     *  observable list used in "advanced research" -> "seach of services" -> "ServiceType:"
     */
    private static final ObservableList<String> comboBoxServiceTypeData = FXCollections.observableArrayList();
    /**
     *  observable list used in "advanced research" -> "seach of services" -> "State of service:"
     */
    private static final ObservableList<String> comboBoxStateData = FXCollections.observableArrayList();

    //ENUM
    /** types of pages */
    public enum PAGE{ NATION, PROVIDER, SERVICE, TYPES, STATE};
    /** types of tabs */
    public enum TAB { RESEARCHOFSERVICES, RESEARCHOFSTATUS, RESEARCH, ADVANCERESEARCH};

    //VARIABLES
    /** number of page the user is currently viewing */
    private PAGE page = PAGE.NATION;
    /**
     *  used from advanced research to take into account which type of advanced research
     *  is selected
     *  value: true if the search of service is selected
     *         false if the search of status is selected
     */
    private boolean serviceSelected;

    //SINGLETON CONST TO MAKE THE CODE MORE READABLE
    /** variable that corresponds to doing XClass.getInstance() */
    private final NationData nationData = NationData.getInstance();
    /** variable that corresponds to doing XClass.getInstance() */
    private final ProviderData providerData = ProviderData.getInstance();
    /** variable that corresponds to doing XClass.getInstance() */
    private final ServiceData serviceData = ServiceData.getInstance();
    /** variable that corresponds to doing XClass.getInstance() */
    private final ServiceTypeData serviceTypeData = ServiceTypeData.getInstance();
    /** variable that corresponds to doing XClass.getInstance() */
    private final StateData stateData = StateData.getInstance();


    //METHODS

    /**
     * Returns the vBox object of the class
     * @return vBox, graphic piece of the interface
     */
    public VBox getvBox(){
        return vBox;}

    /**
     * Returns the tilePane that contains all the nation cards
     * @return tilePaneOfNation, graphic piece of the interface
     */
    public TilePane getTilePaneOfNations(){
        return tilePaneOfNations;
    }

    /**
     * Returns the page visualized in the app
     * @return page visualized
     */
    public PAGE getPage(){
        return this.page;
    }
    /**
     * Method that is called by default when Loader.loading the application.
     * It comes from the interface "Initializable"
     *
     * @param url
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resourceBundle
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MainControllerInstance.setInstance(this);
        tabPaneInitializer();
        advancedResearchPage();
        listViewInitializer();
        searchInitializer();

        nationData.clearSelected();
        Loader.loadNationPage(nationData.getAllNations());
        nationPage();
    }

    /**
     * changes some graphic parts according to the tab selected
     * (and creates a newTilePane because the same cannot be used for all tabs)
     */
    private void tabPaneInitializer(){
        tabPane.getSelectionModel().select(TAB.RESEARCHOFSERVICES.ordinal());
        tilePaneOfNations = getNewTilePane();

        tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            if(newTab == tabPane.getTabs().get(TAB.RESEARCHOFSERVICES.ordinal())){
                tilePaneOfNations = getNewTilePane();
                nationData.clearSelected();
                Loader.loadNationPage(nationData.getAllNations());
                nationPage();
            } else if(newTab == tabPane.getTabs().get(TAB.RESEARCHOFSTATUS.ordinal())){
                tilePaneOfNations = getNewTilePane();
                nationData.clearSelected();
                Loader.loadNationPage(nationData.getAllNations());
                nationPage();
            } else if (newTab == tabPane.getTabs().get(TAB.ADVANCERESEARCH.ordinal())) {
                accord.getPanes().removeAll(nationTitledPane, providerTitledPane, serviceTitledPane, serviceTypesTitledPane, stateTitledPane);
                advancedResearchPage();
            }
        });
    }

    /**
     * method used to creare a new tile pane (needed because we weren't able to transfer the tile pane
     * when changing tab)
     */
    private TilePane getNewTilePane(){
        TilePane tilePane = new TilePane(20,30);
        tilePane.setAlignment(Pos.CENTER);
        tilePane.setPrefColumns(10);
        tilePane.setPadding(new Insets(20,10,20,10));
        return tilePane;
    }

    /**
     * Method that differentiate the methods of search based on what type of object the user wants to search for
     */
    private void searchInitializer(){
        searchByChoiceBoxList.addAll("Nation", "Provider", "Service Type", "State", "Service");
        searchByChoiceBox.setItems(searchByChoiceBoxList);
        searchByChoiceBox.setValue("Nation");

        searchBarStatus.textProperty().addListener((o, oldValue, newValue) -> {
            switch (page){
                case NATION -> Loader.loadNationPage(Search.searchNation(null, newValue));
                case PROVIDER -> Loader.loadProviderPage(Search.searchProvider(nationData.getSelected(), newValue));
                case SERVICE -> Loader.loadServicePageFromProvider(Search.searchServiceByProvider(providerData.getSelected(), newValue));
                case STATE -> Loader.loadStatePageFromService(Search.searchStateByService(serviceData.getSelected(), newValue));
            }

        });

        searchBarServices.textProperty().addListener((o, oldValue, newValue) -> {
            switch (page){
                case NATION -> Loader.loadNationPage(Search.searchNation(null, newValue));
                case PROVIDER -> Loader.loadProviderPage(Search.searchProvider(nationData.getSelected(), newValue));
                case TYPES -> Loader.loadServiceTypePage(Search.searchServiceType(providerData.getSelected(), newValue));
                case STATE -> Loader.loadStatePageFromServiceType(Search.searchStateByServiceType(serviceTypeData.getSelected(), newValue));
                case SERVICE -> Loader.loadServicePageFromState(Search.searchServiceByState(serviceTypeData.getSelected(),stateData.getSelected(), newValue));
            }
        });
    }

    /**
     * Method that manages the search box of the Research tab
     * @param event click of the mouse on the search button
     */
    @FXML
    void researchTab(ActionEvent event) {
        switch (searchByChoiceBox.getValue()){
            case "Nation" -> {
                SortedSet<Nation> nationToShow = Search.searchNation(null, searchBarResearch.getText());
                nationData.clearSelected();
                Loader.loadNationPage(nationToShow);
                scrollResearch.setContent(tilePaneOfNations);
                page = PAGE.NATION;
            }
            case "Provider" -> {
                SortedSet<Provider> tempProvider = Search.searchProvider(null, searchBarResearch.getText());
                Loader.loadProviderPage(tempProvider);
                scrollResearch.setContent(MainControllerInstance.getInstance().getvBox());
                page = PAGE.PROVIDER;
            }
            case "Service Type" -> {
                Loader.loadServiceTypePage(Search.searchServiceType(providerData.getAllProviders(), searchBarResearch.getText()));
                scrollResearch.setContent(MainControllerInstance.getInstance().getvBox());
                page = PAGE.TYPES;
            }
            case "Service" -> {
                Loader.loadServicePageFromProvider(Search.searchServiceByProvider(providerData.getAllProviders(), searchBarResearch.getText()));
                scrollResearch.setContent(MainControllerInstance.getInstance().getvBox());
                page = PAGE.SERVICE;
            }
            case "State" -> {
                Loader.loadStatePageFromService(Search.searchStateByService(serviceData.getAllServices(),searchBarResearch.getText()));
                scrollResearch.setContent(MainControllerInstance.getInstance().getvBox());
                page = PAGE.STATE;
            }
        }
        nationData.getSelected().clear();
        providerData.clearSelected();
        serviceData.clearSelected();
    }

    /**
     * method that connects all the ListViews with the relative ObservableLists and initializes the ListViewData with "all"
     */
    private void listViewInitializer() {
        nationData.addToListView("All");
        nationListView.setItems(nationData.getListViewData());

        providerData.addToListView("All");
        providerListView.setItems(providerData.getListViewData());

        serviceData.addToListView("All");
        serviceListView.setItems(serviceData.getListViewData());

        serviceTypeData.addToListView("All");
        serviceTypesListView.setItems(serviceTypeData.getListViewData());

        stateData.addToListView("All");
        statusListView.setItems(stateData.getListViewData());

        displayScroll.setVisible(false);
        displayScroll.setManaged(false);

        closeDisplayBtn.setVisible(false);
        closeDisplayBtn.setManaged(false);
    }

    /**
     * method that is called when there are selectable items on the page, after having pressed the 'Next' button.
     */
    @FXML
    void nextPage(ActionEvent event) {
        if(tabPane.getSelectionModel().isSelected(TAB.RESEARCHOFSERVICES.ordinal())) {
            switch (page) {
                case NATION -> {
                    if((NationData.getInstance()).numberOfVisibleCardControllers()==0)
                        LaunchAlert.noItemNext();
                    else {
                        providerData.clearSelected();
                        //selecting all nations if the user hasn't selected any nation or has selected them all
                        if (nationData.needAllSelection())
                            selectAll(true);

                        Loader.loadProviderPage(Search.searchProvider(nationData.getSelected(), null));
                        providerPage();
                    }
                }
                case PROVIDER -> {
                    if((ProviderData.getInstance()).numberOfVisibleItemControllers()==0)
                        LaunchAlert.noItemNext();
                    else {
                        serviceTypeData.clearSelected();
                        //selecting all providers if the user hasn't selected any provider or has selected them all
                        if (providerData.needAllSelection())
                            selectAll(true);
                        Loader.loadServiceTypePage(Search.searchServiceType(providerData.getSelected(), null));
                        serviceTypePage();
                    }
                }
                case TYPES -> {
                    if((ServiceTypeData.getInstance()).numberOfVisibleItemControllers()==0)
                        LaunchAlert.noItemNext();
                    else {
                        stateData.clearSelected();
                        if (serviceTypeData.needAllSelection())
                            selectAll(true);
                        searchBarServices.setText("");
                        Loader.loadStatePageFromServiceType(Search.searchStateByServiceType(serviceTypeData.getSelected(), null));
                        statePage();
                    }
                }
                case STATE -> {
                    if((StateData.getInstance()).numberOfVisibleItemControllers()==0)
                        LaunchAlert.noItemNext();
                    else {
                        serviceData.clearSelected();

                        if (stateData.needAllSelection())
                            selectAll(true);
                        searchBarServices.setText("");
                        Loader.loadServicePageFromState(Search.searchServiceByState(serviceTypeData.getSelected(), stateData.getSelected(), null));
                        servicePage();
                    }
                }

            }
        }
        else if(tabPane.getSelectionModel().isSelected(TAB.RESEARCHOFSTATUS.ordinal())) {
            switch (page) {
                case NATION -> {
                    if((NationData.getInstance()).numberOfVisibleCardControllers()==0)
                        LaunchAlert.noItemNext();
                    else{
                        providerData.clearSelected();
                        //selecting all nations if the user hasn't selected any nation or has selected them all
                        if (nationData.needAllSelection())
                            selectAll(true);
                        Loader.loadProviderPage(Search.searchProvider(nationData.getSelected(), null));
                        providerPage();
                    }
                }
                case PROVIDER -> {
                    if((ProviderData.getInstance()).numberOfVisibleItemControllers()==0)
                        LaunchAlert.noItemNext();
                    else {
                        serviceData.clearSelected();
                        //selecting all providers if the user hasn't selected any provider or has selected them all
                        if (providerData.needAllSelection())
                            selectAll(true);
                        Loader.loadServicePageFromProvider(Search.searchServiceByProvider(providerData.getSelected(), null));
                        servicePage();
                    }
                }
                case SERVICE -> {
                    if((ServiceData.getInstance()).numberOfVisibleItemControllers()==0)
                        LaunchAlert.noItemNext();
                    else {
                        //selecting all services if the user hasn't selected any service or has selected them all
                        stateData.clearSelected();

                        if (serviceData.needAllSelection())
                            selectAll(true);
                        Loader.loadStatePageFromService(Search.searchStateByService(serviceData.getSelected(), null));
                        statePage();
                    }
                }
            }
        }
    }

    /**
     * method that is called when the user presses the button used to move to the previous page
     */
    @FXML
    void previousPage(ActionEvent event) {
        if(tabPane.getSelectionModel().isSelected(TAB.RESEARCHOFSERVICES.ordinal())) {
            switch (page) {
                case PROVIDER -> nationPage();
                case TYPES -> providerPage();
                case STATE -> serviceTypePage();
                case SERVICE -> statePage();
            }
        }
        else if(tabPane.getSelectionModel().isSelected(TAB.RESEARCHOFSTATUS.ordinal())) {
            switch (page) {
                case PROVIDER -> nationPage();
                case SERVICE -> providerPage();
                case STATE -> servicePage();
            }
        }
        Loader.reloadPage();
    }

    /**
     * sets some graphic components as visible and hides others in order to correctly show the page for the country
     * selection
     */
    private void nationPage(){
        searchByChoiceBox.setValue("Nation");
        page = PAGE.NATION;

        accord.getPanes().clear();
        accord.getPanes().add(nationTitledPane);

        if(tabPane.getSelectionModel().isSelected(TAB.RESEARCHOFSERVICES.ordinal())) {
            scrollServices.setContent(tilePaneOfNations);
            previousButtonServices.setVisible(false);
            nextButtonServices.setVisible(true);
            pageLabelServices.setText("page 1/5");
        }
        else if (tabPane.getSelectionModel().isSelected(TAB.RESEARCHOFSTATUS.ordinal())) {
            scrollStatus.setContent(tilePaneOfNations);
            nextButtonStatus.setVisible(true);
            previousButtonStatus.setVisible(false);   //removing the "previous" button from the country page
            pageLabelOfStatus.setText("page 1/4");
        }
        else if (tabPane.getSelectionModel().isSelected(TAB.RESEARCH.ordinal()))
            scrollResearch.setContent(tilePaneOfNations);
        else if (tabPane.getSelectionModel().isSelected(TAB.ADVANCERESEARCH.ordinal())) {
            scrollServices.setContent(tilePaneOfNations);
        }

        accord.setExpandedPane(nationTitledPane);

        vBoxAccordion.setManaged(true);
        vBoxAccordion.setVisible(true);

        displayScroll.setManaged(false);
        displayScroll.setVisible(false);

        closeDisplayBtn.setManaged(false);
        closeDisplayBtn.setVisible(false);

        labelLeft.setText("Selected items");
        searchBarStatus.setText("");
        searchBarResearch.setText("");
        searchBarServices.setText("");
    }

    /**
     * sets some graphic components as visible and hides others in order to correctly show the page for the provider
     * selection
     */
    private void providerPage(){
        searchByChoiceBox.setValue("Provider");
        page = PAGE.PROVIDER;

        accord.getPanes().clear();
        accord.getPanes().addAll(nationTitledPane, providerTitledPane);

        if(tabPane.getSelectionModel().isSelected(TAB.RESEARCHOFSERVICES.ordinal())) {
            scrollServices.setContent(MainControllerInstance.getInstance().getvBox());
            nextButtonServices.setVisible(true);
            previousButtonServices.setVisible(true);
            pageLabelServices.setText("page 2/5");
        }
        else if (tabPane.getSelectionModel().isSelected(TAB.RESEARCHOFSTATUS.ordinal())){
            scrollStatus.setContent(MainControllerInstance.getInstance().getvBox());
            //gestisco i navigation button
            previousButtonStatus.setVisible(true);
            nextButtonStatus.setVisible(true);
            pageLabelOfStatus.setText("page 2/4");
        }

        else if (tabPane.getSelectionModel().isSelected(TAB.RESEARCH.ordinal()))
            scrollResearch.setContent(MainControllerInstance.getInstance().getvBox());
        else if (tabPane.getSelectionModel().isSelected(TAB.ADVANCERESEARCH.ordinal())) {
            scrollServices.setContent(MainControllerInstance.getInstance().getvBox());
        }

        accord.setExpandedPane(providerTitledPane);

        vBoxAccordion.setManaged(true);
        vBoxAccordion.setVisible(true);

        displayScroll.setManaged(false);
        displayScroll.setVisible(false);

        closeDisplayBtn.setManaged(false);
        closeDisplayBtn.setVisible(false);

        labelLeft.setText("Selected items");

        searchBarStatus.setText("");
        searchBarResearch.setText("");
        searchBarServices.setText("");
    }

    /**
     * sets some graphic components as visible and hides others in order to correctly show the page for the service
     * selection
     */
    private void servicePage(){

        searchByChoiceBox.setValue("Service");
        page = PAGE.SERVICE;

        if(tabPane.getSelectionModel().isSelected(TAB.RESEARCHOFSERVICES.ordinal())){
            scrollServices.setContent(MainControllerInstance.getInstance().getvBox());
            nextButtonServices.setVisible(false);
            previousButtonServices.setVisible(true);

            accord.getPanes().clear();
            accord.getPanes().addAll(nationTitledPane, providerTitledPane, serviceTypesTitledPane, stateTitledPane, serviceTitledPane);

            pageLabelServices.setText("page 5/5");
        }
        else if (tabPane.getSelectionModel().isSelected(TAB.RESEARCHOFSTATUS.ordinal())) {
            scrollStatus.setContent(MainControllerInstance.getInstance().getvBox());

            //gestisco i navigation button
            previousButtonStatus.setVisible(true);
            nextButtonStatus.setVisible(true);

            accord.getPanes().clear();
            accord.getPanes().addAll(nationTitledPane, providerTitledPane, serviceTitledPane);

            pageLabelOfStatus.setText("page 3/4");
        }
        else if (tabPane.getSelectionModel().isSelected(TAB.RESEARCH.ordinal()))
            scrollResearch.setContent(MainControllerInstance.getInstance().getvBox());
        else if (tabPane.getSelectionModel().isSelected(TAB.ADVANCERESEARCH.ordinal())) {
            scrollServices.setContent(MainControllerInstance.getInstance().getvBox());
        }


        accord.setExpandedPane(serviceTitledPane);

        vBoxAccordion.setManaged(true);
        vBoxAccordion.setVisible(true);

        displayScroll.setManaged(false);
        displayScroll.setVisible(false);

        closeDisplayBtn.setManaged(false);
        closeDisplayBtn.setVisible(false);

        labelLeft.setText("Selected items");

        searchBarStatus.setText("");
        searchBarResearch.setText("");
        searchBarServices.setText("");
    }

    /**
     * sets some graphic components as visible and hides others in order to correctly show the page for the service type
     * selection
     */
    private void serviceTypePage(){

        searchByChoiceBox.setValue("Service Type");
        page = PAGE.TYPES;

        if(tabPane.getSelectionModel().isSelected(TAB.RESEARCHOFSERVICES.ordinal())) {
            scrollServices.setContent(MainControllerInstance.getInstance().getvBox());

            nextButtonServices.setVisible(true);
            previousButtonServices.setVisible(true);

            accord.getPanes().clear();
            accord.getPanes().addAll(nationTitledPane, providerTitledPane, serviceTypesTitledPane);

            accord.setExpandedPane(serviceTypesTitledPane);

            pageLabelServices.setText("page 3/5");
        }
        else if (tabPane.getSelectionModel().isSelected(TAB.RESEARCHOFSTATUS.ordinal())){
            scrollStatus.setContent(MainControllerInstance.getInstance().getvBox());

            previousButtonStatus.setVisible(true);
            nextButtonStatus.setVisible(true);

        }
        else if (tabPane.getSelectionModel().isSelected(TAB.RESEARCH.ordinal()))
            scrollResearch.setContent(MainControllerInstance.getInstance().getvBox());
        else if (tabPane.getSelectionModel().isSelected(TAB.ADVANCERESEARCH.ordinal())) {
            scrollServices.setContent(MainControllerInstance.getInstance().getvBox());
        }

        //navigation buttons:

        vBoxAccordion.setManaged(true);
        vBoxAccordion.setVisible(true);

        displayScroll.setManaged(false);
        displayScroll.setVisible(false);

        closeDisplayBtn.setManaged(false);
        closeDisplayBtn.setVisible(false);

        labelLeft.setText("Selected items");

        searchBarStatus.setText("");
        searchBarResearch.setText("");
        searchBarServices.setText("");
    }
    /**
     * sets some graphic components as visible and hides others in order to correctly show the page for the state
     */
    private void statePage(){
        searchByChoiceBox.setValue("State");
        page = PAGE.STATE;

        if(tabPane.getSelectionModel().isSelected(TAB.RESEARCHOFSERVICES.ordinal())){
            scrollServices.setContent(MainControllerInstance.getInstance().getvBox());

            accord.getPanes().clear();
            accord.getPanes().addAll(nationTitledPane, providerTitledPane, serviceTypesTitledPane, stateTitledPane);

            accord.setExpandedPane(stateTitledPane);

            nextButtonServices.setVisible(true);
            previousButtonServices.setVisible(true);

            pageLabelServices.setText("page 4/5");

        }
        else if(tabPane.getSelectionModel().isSelected(TAB.RESEARCHOFSTATUS.ordinal())){

            accord.getPanes().clear();
            accord.getPanes().addAll(nationTitledPane, providerTitledPane, serviceTitledPane, stateTitledPane);

            previousButtonStatus.setVisible(true);
            nextButtonStatus.setVisible(false);

            accord.setExpandedPane(stateTitledPane);

            scrollStatus.setContent(MainControllerInstance.getInstance().getvBox());

            pageLabelOfStatus.setText("page 4/4");
        } else if (tabPane.getSelectionModel().isSelected(TAB.RESEARCH.ordinal()))
            scrollResearch.setContent(MainControllerInstance.getInstance().getvBox());
        else if (tabPane.getSelectionModel().isSelected(TAB.ADVANCERESEARCH.ordinal())) {
            scrollServices.setContent(MainControllerInstance.getInstance().getvBox());
        }

        vBoxAccordion.setManaged(true);
        vBoxAccordion.setVisible(true);

        displayScroll.setManaged(false);
        displayScroll.setVisible(false);

        closeDisplayBtn.setManaged(false);
        closeDisplayBtn.setVisible(false);

        labelLeft.setText("Selected items");

        searchBarStatus.setText("");
        searchBarResearch.setText("");
        searchBarServices.setText("");
    }

    /**
     * method called when the user presses the button select all or wants to move to next page without selecting nothing
     * @param select true if you want to select all items
     *               false if you want to deselect all items
     */
    public void selectAll(boolean select) {
        switch (page){
            case NATION:
                for (NationCardController nat: nationData.getVisibleCardControllers()) {
                    if(select && !nat.isvItemBoxSelected()){ //case in which you need to select all items and this item isn't selected
                        nationData.addSelected(nat.getNation());
                    } else if (!select && nat.isvItemBoxSelected()) { //case in which you need to deselect all items and this item is selected
                        nationData.removeSelected(nat.getNation());
                    }
                }
                break;
            case PROVIDER:
                for (ItemListController prov: providerData.getVisibleItemControllers()) {
                    if(select && !prov.isvItemBoxSelected()){ //case in which you need to select all items and this item isn't selected
                        providerData.addSelected(prov.getProvider());
                    } else if (!select && prov.isvItemBoxSelected()) { //case in which you need to deselect all items and this item is selected
                        providerData.removeSelected(prov.getProvider());
                    }
                }
                break;
            case SERVICE:
                for (ItemListController serv: serviceData.getVisibleItemControllers()) {
                    if(select && !serv.isvItemBoxSelected()){ //case in which you need to select all items and this item isn't selected
                        serviceData.addSelected(serv.getService());
                    } else if (!select && serv.isvItemBoxSelected()) { //case in which you need to deselect all items and this item is selected
                        serviceData.removeSelected(serv.getService());
                    }
                }
                break;
            case TYPES:
                for (ItemListController serviceType: serviceTypeData.getVisibleItemControllers()) {
                    if(select && !serviceType.isvItemBoxSelected()){ //case in which you need to select all items and this item isn't selected
                        serviceTypeData.addSelected(serviceType.getServiceType());
                    } else if (!select && serviceType.isvItemBoxSelected()) { //case in which you need to deselect all items and this item is selected
                        serviceTypeData.removeSelected(serviceType.getServiceType());
                    }
                }
                break;
            case STATE:
                for (ItemListController state: stateData.getVisibleItemControllers()) {
                    if(select && !state.isvItemBoxSelected()){ //case in which you need to select all items and this item isn't selected
                        stateData.addSelected(state.getState());
                    } else if (!select && state.isvItemBoxSelected()) { //case in which you need to deselect all items and this item is selected
                        stateData.removeSelected(state.getState());
                    }
                }
                break;
        }
    }

    /**Method that refreshes the list view of providers*/
    public void refreshProviderView(){ providerListView.refresh(); }
    /**Method that refreshes the list view of nations*/
    public void refreshNationView(){ nationListView.refresh(); }
    /**Method that refreshes the list view of services*/
    public void refreshServiceView(){ serviceListView.refresh(); }
    /**Method that refreshes the list view of service types*/
    public void refreshServiceTypeView(){ serviceTypesListView.refresh(); }
    /**Method that refreshes the list view of states*/
    public void refreshStateView(){ statusListView.refresh(); }

    /**
     * method called when the button deselect all is pressed
     */
    @FXML
    void deselectAllItems(ActionEvent event) {
        selectAll(false);
    }

    /**
     * method called when the button select all is pressed
     */
    @FXML
    void selectAllItems(ActionEvent event) {
        selectAll(true);
    }

    /**
     * method that sets some items as visible and others as not visible when the user wants to use the "Display" mode
     * (that enables him to see more info)
     * @param stringToShow string to show as info about the selected item
     */
    public void display(String stringToShow){
        vBoxAccordion.setVisible(false);
        vBoxAccordion.setManaged(false);

        displayScroll.setVisible(true);
        displayScroll.setManaged(true);

        closeDisplayBtn.setVisible(true);
        closeDisplayBtn.setManaged(true);

        labelLeft.setText("Info");

        displayLabel.setText(stringToShow);
    }

    /**
     * method that hides and shows some items in the lateral bar, called when the user wants to close the "Display" mode
     */
    @FXML
    void closeDisplay(ActionEvent event) {
        vBoxAccordion.setManaged(true);
        vBoxAccordion.setVisible(true);

        displayScroll.setManaged(false);
        displayScroll.setVisible(false);

        closeDisplayBtn.setManaged(false);
        closeDisplayBtn.setVisible(false);

        labelLeft.setText("Selected items");
    }


    /**
     * Method that changes the visibility of the items in the last tab when the research for services button is pressed
     * @param event press of the research for services button
     */
    @FXML
    void serviceResearch(ActionEvent event){
        changeCssServicesResearch();
    }

    /**
     * Sets visible and managed the service type research box and the service state research box,
     * along with the nation and provider research boxes
     * The service box is not visible as it is the one parameter the user is looking for.
     */
    void changeCssServicesResearch(){
        servicesResearch.getStyleClass().add("button-active");
        statusResearch.getStyleClass().remove("button-active");
        serviceSelected =true;

        researchOfVBox.setVisible(true);
        researchOfVBox.setManaged(true);

        serviceHBox.setVisible(false);
        serviceHBox.setManaged(false);
        serviceTypeHBox.setVisible(true);
        serviceTypeHBox.setManaged(true);
        stateOfServiceHBox.setVisible(true);
        stateOfServiceHBox.setManaged(true);

        separator1.setVisible(false);
        separator1.setManaged(false);
        separator2.setVisible(true);
        separator2.setManaged(true);
        separator3.setVisible(true);
        separator3.setManaged(true);

        advanceSearchBtn.setManaged(true);
        advanceSearchBtn.setVisible(true);

        accord.getPanes().clear();
        accord.getPanes().addAll(nationTitledPane, providerTitledPane);
    }

    /**
     * Method that changes the visibility of the items in the last tab when the research for status button is pressed
     * @param event press of the research for services button
     */
    @FXML
    void statusResearch(ActionEvent event){
        changeCssStatusResearch();
    }
    /**
     * Sets visible and managed the service research box along with the nation and provider research boxes
     * The service type and service state boxes are not visible as it is the parameters the user is looking for.
     */
    void changeCssStatusResearch(){
        statusResearch.getStyleClass().add("button-active");
        servicesResearch.getStyleClass().remove("button-active");
        serviceSelected =false;

        researchOfVBox.setVisible(true);
        researchOfVBox.setManaged(true);

        serviceHBox.setVisible(true);
        serviceHBox.setManaged(true);
        serviceTypeHBox.setVisible(false);
        serviceTypeHBox.setManaged(false);
        stateOfServiceHBox.setVisible(false);
        stateOfServiceHBox.setManaged(false);

        separator1.setVisible(true);
        separator1.setManaged(true);
        separator2.setVisible(false);
        separator2.setManaged(false);
        separator3.setVisible(false);
        separator3.setManaged(false);

        advanceSearchBtn.setManaged(true);
        advanceSearchBtn.setVisible(true);

        accord.getPanes().clear();
        accord.getPanes().addAll(nationTitledPane, providerTitledPane, serviceTitledPane);

    }

    /**
     * Method that manages the Advanced research in the last tab.
     * @param event press of the 'Search!' button
     */
    @FXML
    public void advanceSearch(ActionEvent event){
        if(serviceSelected) {
            SortedSet<String> checkServiceTypes =  new TreeSet<>(comboBoxServiceType.getCheckModel().getCheckedItems());
            if(checkServiceTypes.size() == 0)
                checkServiceTypes = new TreeSet<>(comboBoxServiceType.getItems());

            SortedSet<String> checkStates = new TreeSet<>(comboBoxState.getCheckModel().getCheckedItems());
            if(checkStates.size() == 0)
                checkStates = new TreeSet<>(comboBoxState.getItems());

            tabPane.getSelectionModel().select(TAB.RESEARCHOFSERVICES.ordinal());
            Loader.loadServicePageFromState(Search.searchServiceByStrings(providerData.getSelected(), checkServiceTypes, checkStates));

            SortedSet<ServiceType> serviceTypeSortedSet = serviceTypeData.getAllServiceType();
            for(ServiceType serviceType : serviceTypeSortedSet){
                if(checkServiceTypes.contains(serviceType.getServiceType()))
                    serviceTypeData.addSelected(serviceType);
            }

            servicePage();
        } else{
            tabPane.getSelectionModel().select(TAB.RESEARCHOFSTATUS.ordinal());
            Loader.loadStatePageFromService(Search.searchStateByService(serviceData.getSelected(), null));
            statePage();
        }
    }

    /**
     * initializes the lists used in the advance research tab
     * but doesn't make them visible
     */
    private void advancedResearchPage(){
        researchOfVBox.setManaged(false);
        researchOfVBox.setVisible(false);
        advanceSearchBtn.setManaged(false);
        advanceSearchBtn.setVisible(false);

        comboBoxState.getItems().addAll(stateData.getAllStatesString());
        comboBoxServiceType.getItems().addAll(serviceTypeData.getAllServiceTypeToString());

        searchTextNation.setText("");
        searchTextProvider.setText("");
        searchTextService.setText("");

        searchTextNation.textProperty().addListener((o, oldValue, newValue) -> {
            nationData.clearSelected();
            SortedSet<Nation> tempNationSearch = Search.searchNation(nationData.getAllNations(), newValue);
            nationData.addAllSelected(tempNationSearch);

            providerData.clearSelected();
            SortedSet<Provider> tempProviderSearch = Search.searchProvider(nationData.getSelected(), searchTextProvider.getText());
            providerData.addAllSelected(tempProviderSearch);

            if(serviceSelected) {
                serviceTypeData.clearSelected();
                SortedSet<ServiceType> tempServiceTypeSearch = Search.searchServiceType(providerData.getSelected().size() == 0 ? providerData.getAllProviders() : providerData.getSelected(), null);
                SortedSet<String> tempServiceTypeSearchStrings = new TreeSet<>();
                for (ServiceType serviceType : tempServiceTypeSearch)
                    tempServiceTypeSearchStrings.add(serviceType.getServiceType());

                comboBoxServiceType.getItems().clear();
                comboBoxServiceType.getItems().addAll(tempServiceTypeSearchStrings);

                stateData.clearSelected();
                SortedSet<State> tempStateSearch = Search.searchStateByServiceType(tempServiceTypeSearch, null);
                SortedSet<String> tempStateSearchStrings = new TreeSet<>();
                for (State state : tempStateSearch)
                    tempStateSearchStrings.add(state.getStatus());

                comboBoxState.getItems().clear();
                comboBoxState.getItems().addAll(tempStateSearchStrings);
            } else {
                serviceData.clearSelected();
                SortedSet<Service> tempServiceSearch = Search.searchServiceByProvider(providerData.getSelected().size() == 0 ? providerData.getAllProviders() : providerData.getSelected(), searchTextService.getText());
                serviceData.addAllSelected(tempServiceSearch);
            }

            accord.setExpandedPane(nationTitledPane);
        });

        searchTextProvider.textProperty().addListener((o, oldValue, newValue) -> {
            providerData.clearSelected();
            SortedSet<Provider> tempProviderSearch = Search.searchProvider(nationData.getSelected(), newValue);
            providerData.addAllSelected(tempProviderSearch);

            if(serviceSelected) {
                serviceTypeData.clearSelected();
                SortedSet<ServiceType> tempServiceTypeSearch = Search.searchServiceType(providerData.getSelected().size() == 0 ? providerData.getAllProviders() : providerData.getSelected(), null);
                SortedSet<String> tempServiceTypeSearchStrings = new TreeSet<>();
                for (ServiceType serviceType : tempServiceTypeSearch)
                    tempServiceTypeSearchStrings.add(serviceType.getServiceType());

                comboBoxServiceType.getItems().clear();
                comboBoxServiceType.getItems().addAll(tempServiceTypeSearchStrings);

                stateData.clearSelected();
                SortedSet<State> tempStateSearch = Search.searchStateByServiceType(tempServiceTypeSearch, null);
                SortedSet<String> tempStateSearchStrings = new TreeSet<>();
                for (State state : tempStateSearch)
                    tempStateSearchStrings.add(state.getStatus());

                comboBoxState.getItems().clear();
                comboBoxState.getItems().addAll(tempStateSearchStrings);
            } else {
                serviceData.clearSelected();
                SortedSet<Service> tempServiceSearch = Search.searchServiceByProvider(providerData.getSelected().size() == 0 ? providerData.getAllProviders() : providerData.getSelected(), searchTextService.getText());
                serviceData.addAllSelected(tempServiceSearch);
            }

            accord.setExpandedPane(providerTitledPane);
        });

        searchTextService.textProperty().addListener((o, oldValue, newValue) -> {
            serviceData.clearSelected();
            SortedSet<Service> tempServiceSearch = Search.searchServiceByProvider(providerData.getSelected().size() == 0 ? providerData.getAllProviders() : providerData.getSelected(), newValue);
            serviceData.addAllSelected(tempServiceSearch);

            accord.setExpandedPane(serviceTitledPane);
        });

        comboBoxServiceType.addEventHandler(ComboBox.ON_HIDDEN, event -> {
            SortedSet<String> tempServiceTypeSearch = new TreeSet<>(comboBoxServiceType.getCheckModel().getCheckedItems());
            if(tempServiceTypeSearch.size() == 0)
                tempServiceTypeSearch = new TreeSet<>(comboBoxServiceType.getItems());

            stateData.clearSelected();
            SortedSet<State> tempStateSearch = Search.searchStateByStrings(providerData.getSelected().size() == 0 ? providerData.getAllProviders() : providerData.getSelected(), tempServiceTypeSearch);
            SortedSet<String> tempStateSearchStrings = new TreeSet<>();
            for (State state : tempStateSearch)
                tempStateSearchStrings.add(state.getStatus());

            comboBoxState.getItems().clear();
            comboBoxState.getItems().addAll(tempStateSearchStrings);
        });

    }
}
