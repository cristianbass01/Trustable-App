package com.trustapp.trustableapp.Search;

import com.trustapp.trustableapp.DataClass.*;
import com.trustapp.trustableapp.DataLists.*;
import com.trustapp.trustableapp.MyComparator.comparatorForServiceInResearchServices;
import com.trustapp.trustableapp.MyComparator.comparatorForServiceInResearchStatus;
import com.trustapp.trustableapp.MyComparator.comparatorForStateInResearchServices;
import com.trustapp.trustableapp.MyComparator.comparatorForStateInResearchStatus;

import java.util.LinkedList;
import java.util.SortedSet;
import java.util.TreeSet;

public class Search {

    //VARIABLES TO SIMPLIFY THE WRITING OF SINGLETON VARIABLES
    /** variable that corresponds to doing XClass.getInstance() */
    private static final NationData nationData = NationData.getInstance();
    /** variable that corresponds to doing XClass.getInstance() */
    private static final ProviderData providerData = ProviderData.getInstance();
    /** variable that corresponds to doing XClass.getInstance() */
    private static final StateData stateData = StateData.getInstance();
    /** variable that corresponds to doing XClass.getInstance() */
    private static final ServiceData serviceData = ServiceData.getInstance();
    /** variable that corresponds to doing XClass.getInstance() */
    private final ServiceTypeData serviceTypeData = ServiceTypeData.getInstance();

    //METHODS
    /**
     * Search nations that fills specific features :
     * - they are nations in nationForResearch (if null the search is performed on all nations)
     * - they contain nationString in their name
     *
     * @param nationForResearch nations to search in
     *                          if null: searches between all nations contained in DataLists
     * @param nationString    string that must be contained in nations name
     * @return a SortedSet of nations found
     */
    public static SortedSet<Nation> searchNation(SortedSet<Nation> nationForResearch, String nationString){
        LinkedList<Nation> nationToReturn = new LinkedList<>();

        if(nationForResearch == null)
            nationForResearch = nationData.getAllNations();

        for (Nation nat : nationForResearch) {
            if (nationString != null && nat.parametersToString().toLowerCase().contains(nationString.toLowerCase()))
                nationToReturn.add(nat);
            if(nationString == null)
                nationToReturn.add(nat);
        }
        return new TreeSet<>(nationToReturn);
    }

    /**
     * Search providers that fills specific features :
     * - they are provider of nations in nationForResearch (if null the search is performed on all nations)
     * - they contain providerString in their name
     *
     * @param nationForResearch nations to search for providers
     *                          if null: searches a provider between all providers of all nations contained in DataLists
     * @param providerString    string that must be contained in providers name
     * @return a SortedSet of providers found
     */
    public static SortedSet<Provider> searchProvider(SortedSet<Nation> nationForResearch, String providerString){
        LinkedList<Provider> providerToReturn = new LinkedList<>();

        //case when the user doesn't pass any SortedSet
        if(nationForResearch == null)
            nationForResearch = nationData.getAllNations();

        //search
        for(Nation nation : nationForResearch){
            for(Provider provider : nation.getProviderList()){
                if(providerString != null && provider.parametersToString().toLowerCase().contains(providerString.toLowerCase()))
                    providerToReturn.add(provider);
                if( providerString == null)
                    providerToReturn.add(provider);
            }
        }
        return new TreeSet<>(providerToReturn);
    }

    /**
     * Search serviceTypes that fills specific features :
     * - they are serviceTypes contained in providers of providerForResearch (if null the search is performed on all providers)
     * - they contain serviceTypeString in their name
     *
     * @param providerForResearch providers to search for serviceType
     *                          if null: searches a serviceType between all serviceTypes of all providers contained in DataLists
     * @param serviceTypeString string that must be contained in providers name
     * @return a SortedSet of serviceTypes found
     */
    public static SortedSet<ServiceType> searchServiceType(SortedSet<Provider> providerForResearch, String serviceTypeString){
        LinkedList<ServiceType> serviceTypeToReturn = new LinkedList<>();

        //case when the search has to be performed on all providers
        if(providerForResearch == null)
            providerForResearch = ProviderData.getInstance().getAllProviders();

        //search
        for(Provider provider : providerForResearch){
            for(ServiceType serviceType : provider.getqServiceTypes()){
                if(serviceTypeString != null && serviceType.parametersToString().toLowerCase().contains(serviceTypeString.toLowerCase()))
                    serviceTypeToReturn.add(serviceType);
                if(serviceTypeString == null)
                    serviceTypeToReturn.add(serviceType);
            }
        }
        return new TreeSet<>(serviceTypeToReturn);
    }

    /**
     * Search services that fills specific features :
     * - they are services that are in the stateForResearch (if null the search is performed on all states)
     * - they contain serviceString in their name
     *
     * @param serviceTypeSortedSet servicetype to search in
     * @param stateForResearch states to search from for services
     *                          if null: searches a service between all services of all states contained in DataLists
     * @param serviceString string that must be contained in services names
     * @return a SortedSet of services found
     */
    public static SortedSet<Service> searchServiceByState(SortedSet<ServiceType> serviceTypeSortedSet ,SortedSet<State> stateForResearch, String serviceString){
        SortedSet<Service> serviceToReturn = new TreeSet<>(new comparatorForServiceInResearchServices());

        if(stateForResearch == null)
             stateForResearch = StateData.getInstance().getAllStates();

        for(State state : stateForResearch){
            for(Service service : state.getServiceType().getProvider().getServices()) {
                if (serviceString != null && service.parametersToString().toLowerCase().contains(serviceString.toLowerCase()))
                    serviceToReturn.add(service);
                if (serviceString == null && service.getCurrentStatus().getStatus().compareTo(state.getStatus()) == 0 && serviceTypeSortedSet.contains(state.getServiceType()) && service.getqServiceTypes().contains(state.getServiceType()) && serviceTypeSortedSet.contains(service.getCurrentStatus().getServiceType()))
                    serviceToReturn.add(service);
            }
        }
        return new TreeSet<>(serviceToReturn);
    }

    /**
     * Search services that fills specific features :
     * - they are services that are in the providerForResearch (if null the search is performed on all providers)
     * - they contain serviceString in their name
     *
     * @param providerForResearch providers to search from for services
     *                            if null: searches a service between all services of all providers contained in DataLists
     * @param serviceString string that must be contained in services names
     * @return a SortedSet of services found
     */
    public static SortedSet<Service> searchServiceByProvider(SortedSet<Provider> providerForResearch, String serviceString){
        SortedSet<Service> serviceToReturn = new TreeSet<>(new comparatorForServiceInResearchStatus());

        if(providerForResearch == null)
            providerForResearch = ProviderData.getInstance().getAllProviders();

        for(Provider provider : providerForResearch) {
            for(Service service : provider.getServices()) {
                if (serviceString != null && service.parametersToString().toLowerCase().contains(serviceString.toLowerCase()))
                    serviceToReturn.add(service);
                if (serviceString == null)
                    serviceToReturn.add(service);
            }
        }
        return serviceToReturn;
    }

    /**
     * Search states that fills specific features :
     * - they are states that are in the serviceTypeForResearch (if null the search is performed on all serviceTypes)
     * - they contain stateString in their name
     *
     * @param serviceTypeForResearch serviceTypes to search from for states
     *                               if null: searches a state between all states of all serviceTypes contained in DataLists
     * @param stateString string that must be contained in states names
     * @return a SortedSet of states found
     */
    public static SortedSet<State> searchStateByServiceType(SortedSet<ServiceType> serviceTypeForResearch, String stateString){
        SortedSet<State> stateToReturn = new TreeSet<>(new comparatorForStateInResearchServices());

        for(ServiceType serviceType : serviceTypeForResearch) {
            for(Service service : serviceType.getProvider().getServices()){
                if(service.getqServiceTypes().contains(serviceType)) {
                    if (stateString != null && service.getCurrentStatus().getStatus().toLowerCase().contains(stateString.toLowerCase()))
                        stateToReturn.add(service.getCurrentStatus());
                    if (stateString == null && service.getCurrentStatus().getServiceType().compareTo(serviceType) == 0)
                        stateToReturn.add(service.getCurrentStatus());
                }
            }
        }
        return stateToReturn;
    }

    /**
     * Search states that fills specific features :
     * - they are states that are in the serviceForResearch (if null the search is performed on all services)
     * - they contain stateString in their name
     *
     * @param serviceForResearch service to search from for states
     *                               if null: searches a state between all states of all services contained in DataLists
     * @param stateString string that must be contained in states names
     * @return a SortedSet of states found
     */
    public static SortedSet<State> searchStateByService(SortedSet<Service> serviceForResearch, String stateString){
        SortedSet<State> stateToReturn = new TreeSet<>(new comparatorForStateInResearchStatus());
        for(Service service : serviceForResearch){
            if(stateString != null && service.getCurrentStatus().getStatus().toLowerCase().contains(stateString.toLowerCase()))
                stateToReturn.add(service.getCurrentStatus());
            if(stateString == null)
                stateToReturn.add(service.getCurrentStatus());
        }
        return stateToReturn;
    }

    /**
     *
     * Search services that fills specific features :
     *  - they are services that are in the providers (if null the search is performed on all providers)
     *  - they contain a servType string in their serviceTypes
     *  - they contain the state string passed as a parameter in their states
     * @param providers providers to search from, null if the search has to be performed in all providers of providerLists
     * @param servType set of strings to search in serviceTypes
     * @param state set of strings of states to search in states
     * @return set of services that fill the features for the search
     */
    public static SortedSet<Service> searchServiceByStrings( SortedSet<Provider> providers, SortedSet<String> servType, SortedSet<String> state) {
        SortedSet<Service> serviceRequired = new TreeSet<>(new comparatorForServiceInResearchServices());

        if(providers == null)
            providers = providerData.getAllProviders();

        SortedSet<ServiceType> tempServiceType = new TreeSet<>();
        for(String serviceType : servType){
            tempServiceType.addAll(searchServiceType(providers, serviceType));
        }

        SortedSet<State> tempState = new TreeSet<>();
        for(String states : state){
            tempState.addAll(searchStateByServiceType(tempServiceType, states));
        }

        serviceRequired.addAll(searchServiceByState(tempServiceType, tempState, null));

        return serviceRequired;
    }

    /**
     * Search the state of the services operating on string-type objects.
     * @param providers providers in which services the state is searched, if null the state is searched in the services of all providers
     * @param servType set of strings of service types of the services the user wants to search in
     * @return the states found with the given parameters
     */
    public static SortedSet<State> searchStateByStrings( SortedSet<Provider> providers, SortedSet<String> servType) {
        SortedSet<State> statesRequired = new TreeSet<>(new comparatorForStateInResearchServices());

        if(providers == null)
            providers = providerData.getAllProviders();

        SortedSet<ServiceType> tempServiceType = new TreeSet<>();
        for(String serviceType : servType){
            tempServiceType.addAll(searchServiceType(providers, serviceType));
        }

        statesRequired.addAll(searchStateByServiceType(tempServiceType, null));
        return statesRequired;
    }
}




