package com.trustapp.trustableapp.API;

import com.trustapp.trustableapp.DataClass.*;
import com.trustapp.trustableapp.DataLists.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public class DataInitializer {
    //IMPLEMENTING SINGLETON
    /** single instance of the only object of type */
    private static DataInitializer single_instance = null;
    /** the constructor is declared as private, this way the objects can be created only by
     * the method getInstance()
     */
    private DataInitializer() { }
    /**
     * method used in the singleton implementation. Creates a NationData object if it's the first time
     * the class is used. Otherwise, it returns the instance already initialized. By doing so only
     * one object exists.
     * @return NationData object that is the only instance existing
     */
    public static DataInitializer getInstance()
    {
        //case when it's the first time getInstance is called
        if (single_instance == null)
            single_instance = new DataInitializer();

        return single_instance;
    }

    //VARIABLES
    private NationData nationData = NationData.getInstance();

    /**
     * method that initializes the data of nations and calls initializeProviders with data from an API
     */
    public void initializeData(){
        //initialization of nations
        GetFromAPI<Nation> nationGetFromAPI = new GetFromAPI<>("https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/countries_list");
        nationData.setAllNations(new TreeSet<>(nationGetFromAPI.apiToList(Nation[].class)));

        //get data from API and pass it to initializeProviders(...)
        GetFromAPI<Provider> providerGetFromAPI = new GetFromAPI<>("https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/tsp_list");
        initializeProviders(providerGetFromAPI.apiToList(Provider[].class));

    }


    /**
     * insertion of providers and other data in the correct Country (by using NationData)
     */
    private void initializeProviders(List<Provider> providerList) {
        int counter = 0;
        List<ServiceType> tempServiceType = new LinkedList<>();
        List<State> tempState = new LinkedList<>();
        List<Service> tempService = new LinkedList<>();

        for(Nation nat: nationData.getAllNations()){
            List<Provider> providers = new ArrayList<>();
            while(counter < providerList.size() && nat.getCountryCode().equals(providerList.get(counter).getCountryCode())){
                Provider prov = providerList.get(counter);
                providers.add(prov);
                prov.setNation(nat); //DONE
                for(Service service : prov.getServices()){
                    service.setProvider(prov);
                    service.getCurrentStatus().setService(service);

                    tempState.add(service.getCurrentStatus());
                    tempService.add(service);

                    for (ServiceType serviceType: service.getqServiceTypes()){
                        serviceType.setProvider(prov);
                        service.getCurrentStatus().setServiceType(serviceType);
                    }
                }
                for (ServiceType serviceType: prov.getqServiceTypes()) {
                    serviceType.setProvider(prov);
                    tempServiceType.add(serviceType);
                }

                counter++;
            }
            nat.setProviderList(providers);
        }
        ProviderData.getInstance().setAllProviders(new TreeSet<>(providerList));
        ServiceTypeData.getInstance().setAllServiceType(new TreeSet<>(tempServiceType));
        ServiceData.getInstance().setAllServices(new TreeSet<>(tempService));
        StateData.getInstance().setAllStates(new TreeSet<>(tempState));
    }
}
