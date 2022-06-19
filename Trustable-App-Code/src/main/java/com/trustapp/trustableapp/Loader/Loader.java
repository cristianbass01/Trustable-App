package com.trustapp.trustableapp.Loader;

import com.trustapp.trustableapp.Controller.ItemListController;
import com.trustapp.trustableapp.Controller.NationCardController;
import com.trustapp.trustableapp.ControllerSavers.MainControllerInstance;
import com.trustapp.trustableapp.DataClass.*;
import com.trustapp.trustableapp.DataLists.*;
import com.trustapp.trustableapp.Search.Search;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Class that manages page loadings and their reload.
 */
public class Loader {


    //SINGLETON CONST TO MAKE THE CODE MORE READABLE
    /** variable that corresponds to doing XClass.getInstance() */
    private static final NationData nationData = NationData.getInstance();
    /** variable that corresponds to doing XClass.getInstance() */
    private static final ProviderData providerData = ProviderData.getInstance();
    /** variable that corresponds to doing XClass.getInstance() */
    private static final ServiceData serviceData = ServiceData.getInstance();
    /** variable that corresponds to doing XClass.getInstance() */
    private static final ServiceTypeData serviceTypeData = ServiceTypeData.getInstance();
    /** variable that corresponds to doing XClass.getInstance() */
    private static final StateData stateData = StateData.getInstance();

    /**
     * inserts all the nations in nationToShow (parameter) into the page
     * @param nationToShow list of nations to load in the page
     */
    public static void loadNationPage(SortedSet<Nation> nationToShow){
        clearAll();
        nationData.clearDeselected();
        Show.showAllNations(nationToShow);
    }

    /**
     * inserts all the providers in providersToShow (parameter) into the page
     * @param providersToShow list of providers to load in the page
     */
    public static void loadProviderPage( SortedSet<Provider> providersToShow){
        clearAll();
        providerData.clearDeselected();
        Show.showAllProviders(providersToShow);
    }

    /**
     * inserts all the services in servicesToShow (parameter) into the page (loaded from the provider page)
     * @param servicesToShow list of services to load in the page
     */
    public static void loadServicePageFromProvider(SortedSet<Service> servicesToShow){
        clearAll();
        Show.showAllServicesFromProvider(servicesToShow);

        SortedSet<Nation> nationToDeselect = new TreeSet<>(nationData.getSelected());

        for(Service service : servicesToShow)
            nationToDeselect.remove(service.getProvider().getNation());

        nationData.addAllDeselected(nationToDeselect);
    }
    /**
     * inserts all the services in servicesToShow (parameter) into the page (loaded from the state page)
     * @param servicesToShow list of services to load in the page
     */
    public static void loadServicePageFromState(SortedSet<Service> servicesToShow){
        clearAll();
        Show.showAllServicesFromState(servicesToShow);

        SortedSet<Nation> nationToDeselect = new TreeSet<>(nationData.getSelected());
        SortedSet<Provider> providersToDeselect = new TreeSet<>(providerData.getSelected());
        SortedSet<ServiceType> serviceTypesToDeselect = new TreeSet<>(serviceTypeData.getSelected());

        for(Service service : servicesToShow) {
            nationToDeselect.remove(service.getProvider().getNation());
            providersToDeselect.remove(service.getProvider());
            serviceTypesToDeselect.remove(service.getCurrentStatus().getServiceType());
        }

        nationData.addAllDeselected(nationToDeselect);
        providerData.addAllDeselected(providersToDeselect);
        serviceTypeData.addAllDeselected(serviceTypesToDeselect);
    }

    /**
     * inserts all the service types in serviceTypesToShow (parameter) into the page
     * @param serviceTypesToShow list of service types to load in the page
     */
    public static void loadServiceTypePage( SortedSet<ServiceType> serviceTypesToShow){
        clearAll();
        serviceTypeData.clearDeselected();
        Show.showAllServiceType(serviceTypesToShow);

        SortedSet<Nation> nationToDeselect = new TreeSet<>(nationData.getSelected());

        for(ServiceType serviceType : serviceTypesToShow)
            nationToDeselect.remove(serviceType.getProvider().getNation());

        nationData.addAllDeselected(nationToDeselect);
    }

    /**
     * inserts all the states in statesToShow (parameter) into the page (loaded from the service page)
     * @param statesToShow list of states to load in the page
     */
    public static  void loadStatePageFromService(SortedSet<State> statesToShow) {
        clearAll();
        Show.showAllStateInService(statesToShow);

        SortedSet<Nation> nationsToDeselect = new TreeSet<>(nationData.getSelected());
        SortedSet<Provider> providersToDeselect = new TreeSet<>(providerData.getSelected());

        for(State state : statesToShow){
            nationsToDeselect.remove(state.getService().getProvider().getNation());
            providersToDeselect.remove(state.getService().getProvider());
        }

        nationData.addAllDeselected(nationsToDeselect);
        providerData.addAllDeselected(providersToDeselect);
    }

    /**
     * inserts all the states in statesToShow (parameter) into the page (loaded from the service type page)
     * @param statesToShow list of states to load in the page
     */
    public static void loadStatePageFromServiceType(SortedSet<State> statesToShow) {
        clearAll();
        Show.showAllStateInServiceType(statesToShow);

        SortedSet<Nation> nationsToDeselect = new TreeSet<>(nationData.getSelected());
        SortedSet<Provider> providersToDeselect = new TreeSet<>(providerData.getSelected());

        for(State state : statesToShow){
            nationsToDeselect.remove(state.getService().getProvider().getNation());
            providersToDeselect.remove(state.getService().getProvider());
        }

        nationData.addAllDeselected(nationsToDeselect);
        providerData.addAllDeselected(providersToDeselect);
    }

    /**
     * clear the screen from the visible boxes of different types
     */
    private static void clearAll(){
        stateData.clearAllItems();
        stateData.clearAllBox();

        serviceTypeData.clearAllBox();
        serviceTypeData.clearAllItems();

        serviceData.clearAllBox();
        serviceData.clearAllItems();

        providerData.clearAllItems();
        providerData.clearAllBox();

        nationData.clearAllBox();
        nationData.clearAllCard();
    }

    /**
     * Reloads the current page, differentiating the operations to do so based on what page we are in.
     */
    public static void reloadPage() {
        switch (MainControllerInstance.getInstance().getPage()){
            case NATION -> Loader.loadNationPage(nationData.getAllNations());
            case PROVIDER -> {
                for (Nation nat: nationData.getDeselected()) {
                    nationData.addSelected(nat);
                }
                nationData.clearDeselected();
                Loader.loadProviderPage(Search.searchProvider(nationData.getSelected(), null));
            }
            case TYPES -> {
                for (Provider prov: providerData.getDeselected()) {
                    providerData.addSelected(prov);

                    NationCardController nationController = nationData.getCardController(prov.getNation());
                    if(!nationController.isvItemBoxSelected()){
                        nationData.addSelected(prov.getNation());
                    }
                }
                nationData.removeAllDeselected(nationData.getSelected());
                providerData.clearDeselected();
                Loader.loadServiceTypePage(Search.searchServiceType(providerData.getSelected() ,null));
            }
            case SERVICE -> {
                for (Provider prov: providerData.getDeselected()) {
                    providerData.addSelected(prov);

                    NationCardController nationController = nationData.getCardController(prov.getNation());
                    if(!nationController.isvItemBoxSelected()){
                        nationData.addSelected(prov.getNation());
                    }
                }
                nationData.removeAllDeselected(nationData.getSelected());
                providerData.clearDeselected();
                Loader.loadServicePageFromProvider(Search.searchServiceByProvider(providerData.getSelected() ,null));
            }
            case STATE -> {
                for(ServiceType serviceType : serviceTypeData.getDeselected()){
                    serviceTypeData.addSelected(serviceType);

                    ItemListController providerController = providerData.getItemController(serviceType.getProvider());
                    if(!providerController.isvItemBoxSelected()){
                        providerData.addSelected(serviceType.getProvider());
                    }

                    NationCardController nationController = nationData.getCardController(serviceType.getProvider().getNation());
                    if(!nationController.isvItemBoxSelected()){
                        nationData.addSelected(serviceType.getProvider().getNation());
                    }
                }
                nationData.removeAllDeselected(nationData.getSelected());
                providerData.removeAllDeselected(providerData.getSelected());
                serviceTypeData.clearDeselected();
                Loader.loadStatePageFromServiceType(Search.searchStateByServiceType(serviceTypeData.getSelected(), null));
            }
        }
    }
}
