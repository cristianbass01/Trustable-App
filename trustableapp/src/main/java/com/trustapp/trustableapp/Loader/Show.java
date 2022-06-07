package com.trustapp.trustableapp.Loader;

import com.trustapp.trustableapp.DataClass.*;
import com.trustapp.trustableapp.DataLists.*;

import java.util.SortedSet;

/**
 * Class that manages the visualization of the items in the page
 */
public class Show {
    /**
     * inserts the given services in the relative providers
     * @param serviceToShow services to insert in the page
     */
    public static void showAllServicesFromProvider(SortedSet<Service> serviceToShow) {
        for(Service service : serviceToShow)
            ServiceData.getInstance().insertItemInProvider(service);
    }

    /**
     * inserts the given services in the relative states
     * @param serviceToShow services to insert in the page
     */
    public static void showAllServicesFromState(SortedSet<Service> serviceToShow) {
        for(Service service : serviceToShow)
            ServiceData.getInstance().insertItemInState(service);
    }

    /**
     * inserts the given providers in the providerData list of instances
     * @param providers providers to insert in the page
     */
    public static void showAllProviders(SortedSet<Provider> providers){
        for(Provider provider : providers)
            ProviderData.getInstance().insertItemIn(provider);
    }

    /**
     * inserts the given nations in the nationData list of instances
     * @param nations nations to insert in the page
     */
    public static void showAllNations(SortedSet<Nation> nations){
        for(Nation nation : nations)
            NationData.getInstance().insertCardIn(nation);
    }

    /**
     * inserts the given service types in the serviceTypeData list of instances
     * @param serviceTypes service types to insert in the page
     */
    public static void showAllServiceType(SortedSet<ServiceType> serviceTypes){
        for(ServiceType serviceType : serviceTypes)
            ServiceTypeData.getInstance().insertItemIn(serviceType);
    }

    /**
     * inserts the given states in the relative services
     * @param states states to insert in the page
     */
    public static void showAllStateInService(SortedSet<State> states){
        for(State state : states)
            StateData.getInstance().insertItemInService(state);
    }

    /**
     * inserts the given states in the relative service types
     * @param states states to insert in the page
     */
    public static void showAllStateInServiceType(SortedSet<State> states){
        for(State state : states)
            StateData.getInstance().insertItemInServiceType(state);
    }
}
