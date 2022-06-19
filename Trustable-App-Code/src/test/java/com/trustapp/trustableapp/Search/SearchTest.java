package com.trustapp.trustableapp.Search;

import com.trustapp.trustableapp.DataClass.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {
    private SortedSet<Nation> natFullSet;
    private Nation it;
    private Nation be;
    private Nation hr;

    private SortedSet<Provider> provFullSet;
    private Provider prov1;
    private Provider prov2;
    private Provider prov3;

    private SortedSet<ServiceType> servTypeFullSet;
    private ServiceType servTypeA;
    private ServiceType servTypeB;
    private ServiceType servTypeC;

    private SortedSet<Service> servFullSet;
    private Service servX;
    private Service servY;
    private Service servZ;

    private SortedSet<State> stateFullSet;
    private State stateX;
    private State stateY;
    private State stateZ;

    @BeforeEach
    public void setUp() {
        //creation of sets
        natFullSet = new TreeSet<>();
        provFullSet = new TreeSet<>();
        servTypeFullSet = new TreeSet<>();
        servFullSet = new TreeSet<>();
        stateFullSet = new TreeSet<>();

        //creation of a countries
        it = new Nation("IT","Italy");
        be = new Nation("BE", "Belgium");
        hr = new Nation("HR", "Croatia");

        //initializing services
        servX = new Service("IT", null,0, "serviceX","null", 0, "", "http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/stateX");
        servY = new Service("BE", null,0, "serviceY","null", 0, "", "http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/stateY");
        servZ = new Service("HR", null,0, "serviceZ","null", 0, "", "http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/stateZ");

        List<Service> servList1 = new ArrayList<>();
        List<Service> servList2 = new ArrayList<>();
        List<Service> servList3 = new ArrayList<>();

        servList1.add(servX);
        servList2.add(servY);
        servList3.add(servZ);

        prov1 = new Provider("IT","prov1", servList1, "",0, it);
        prov2 = new Provider("BE","prov2", servList2, "",0, be);
        prov3 = new Provider("HR","prov3", servList3, "",0, hr);

        servX.setProvider(prov1);
        servY.setProvider(prov2);
        servZ.setProvider(prov3);

        //creation of empty lists of string that corresponds to list of serviceTypes
        List<String> serviceTypeListA = new ArrayList<>();
        List<String> serviceTypeListB = new ArrayList<>();
        List<String> serviceTypeListC = new ArrayList<>();

        //filling the lists
        serviceTypeListA.add("servTypeA");
        serviceTypeListB.add("servTypeB");
        serviceTypeListC.add("servTypeC");

        //adding a list to each provider
        prov1.setqServiceTypes(serviceTypeListA);
        prov2.setqServiceTypes(serviceTypeListB);
        prov3.setqServiceTypes(serviceTypeListC);

        servX.setqServiceTypes(serviceTypeListA);
        servY.setqServiceTypes(serviceTypeListB);
        servZ.setqServiceTypes(serviceTypeListC);

        //creating empty lists of providers
        List<Provider> provList1 = new LinkedList<>();
        List<Provider> provList2 = new LinkedList<>();
        List<Provider> provList3 = new LinkedList<>();

        //adding a provider to each list
        provList1.add(prov1);
        provList2.add(prov2);
        provList3.add(prov3);

        //inserting the lists of providers to the nations
        it.setProviderList(provList1);
        be.setProviderList(provList2);
        hr.setProviderList(provList3);

        //initializing the serviceTypes
        servTypeA = new ServiceType("servTypeA",0, "IT");
        servTypeB = new ServiceType("servTypeB",0, "BE");
        servTypeC = new ServiceType("servTypeC",0, "HR");

        //initializing states
        stateX = new State("http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/stateX", servX);
        stateY = new State("http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/stateY", servY);
        stateZ = new State("http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/stateZ", servZ);

        servX.setCurrentStatus("http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/stateX");
        servY.setCurrentStatus("http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/stateY");
        servZ.setCurrentStatus("http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/stateZ");

        //setting providers of services
        servTypeA.setProvider(prov1);
        servTypeB.setProvider(prov2);
        servTypeC.setProvider(prov3);

        //setting serviceTypes of state
        servX.getCurrentStatus().setServiceType(servTypeA);
        servY.getCurrentStatus().setServiceType(servTypeB);
        servZ.getCurrentStatus().setServiceType(servTypeC);

        //setting serviceTypes of state
        stateX.setServiceType(servTypeA);
        stateY.setServiceType(servTypeB);
        stateZ.setServiceType(servTypeC);

        prov1.getqServiceTypes().first().setProvider(prov1);
        prov2.getqServiceTypes().first().setProvider(prov2);
        prov3.getqServiceTypes().first().setProvider(prov3);

        //adding states to stateFullSet
        stateFullSet.add(stateX);
        stateFullSet.add(stateY);
        stateFullSet.add(stateZ);

        //adding serviceTypes to the set of service types
        servTypeFullSet.add(servTypeA);
        servTypeFullSet.add(servTypeB);
        servTypeFullSet.add(servTypeC);

        //adding services to servFullSet
        servFullSet.add(servX);
        servFullSet.add(servY);
        servFullSet.add(servZ);

        //filling the natFullSet with all nations
        natFullSet.add(it);
        natFullSet.add(be);
        natFullSet.add(hr);

        //filling the provFullSet with all providers
        provFullSet.add(prov1);
        provFullSet.add(prov2);
        provFullSet.add(prov3);
    }

    //METHODS

    /**
     * tests searchNation by searching "a"
     */
    @Test
    void searchNation1() {
        SortedSet<Nation> temp = Search.searchNation(natFullSet,"a");
        SortedSet<Nation> expected = new TreeSet<>();
        expected.add(hr);
        expected.add(it);
        assertTrue(temp.equals(expected));
    }

    /**
     * tests searchNation by searching "it"
     */
    @Test
    void searchNation2() {
        SortedSet<Nation> temp = Search.searchNation(natFullSet,"it");
        SortedSet<Nation> expected = new TreeSet<>();
        expected.add(it);
        assertTrue(temp.equals(expected));
    }
    /**
     * tests searchNation by searching "" (Should get the fullSet)
     */
    @Test
    void searchNation3() {
        SortedSet<Nation> temp = Search.searchNation(natFullSet,"");
        assertTrue(temp.equals(natFullSet));
    }

    /**
     * tests searchProvider by searching "1" (Should get prov1)
     */
    @Test
    void searchProvider1() {
        SortedSet<Provider> temp = Search.searchProvider(natFullSet, "1");
        SortedSet<Provider> expected = new TreeSet<>();
        expected.add(prov1);
        assertEquals(expected, temp);
    }
    /**
     * tests searchProvider by searching "prov" (Should get all prov)
     */
    @Test
    void searchProvider2() {
        SortedSet<Provider> temp = Search.searchProvider(natFullSet, "prov");
        SortedSet<Provider> expected = new TreeSet<>();
        expected.add(prov1);
        expected.add(prov2);
        expected.add(prov3);
        assertEquals(expected, temp);
    }

    /**
     * testing by searching only 1 serviceType
     */
    @Test
    void searchServiceType1() {
        SortedSet<ServiceType> temp = Search.searchServiceType(provFullSet, "servTypeA");

        //creation of expected set
        SortedSet<ServiceType> expected = new TreeSet<>();
        expected.add(servTypeA);

        assertEquals(expected, temp);
    }

    /**
     * testing by searching all serviceTypes
     */
    @Test
    void searchServiceType2() {
        SortedSet<ServiceType> temp = Search.searchServiceType(provFullSet, "servType");

        //creation of expected set
        SortedSet<ServiceType> expected = new TreeSet<>();
        expected.add(servTypeA);
        expected.add(servTypeB);
        expected.add(servTypeC);

        assertEquals(expected, temp);
    }
    /**
     * testing by searching all serviceTypes
     */
    @Test
    void searchServiceType3() {
        SortedSet<ServiceType> temp = Search.searchServiceType(provFullSet, "");

        //creation of expected set
        SortedSet<ServiceType> expected = new TreeSet<>();
        expected.add(servTypeA);
        expected.add(servTypeB);
        expected.add(servTypeC);

        assertEquals(expected, temp);
    }

    /**
     * testing by searching one service
     */
    @Test
    void searchServiceByProvider1() {
        SortedSet<Service> temp = Search.searchServiceByProvider(provFullSet, "serviceX");

        //creation of expected set
        SortedSet<Service> expected = new TreeSet<>();
        expected.add(servX);

        assertEquals(expected, temp);
    }
    /**
     * testing by searching all services
     */
    @Test
    void searchServiceByProvider2() {
        SortedSet<Service> temp = Search.searchServiceByProvider(provFullSet, "service");

        //creation of expected set
        SortedSet<Service> expected = new TreeSet<>();
        expected.add(servX);
        expected.add(servY);
        expected.add(servZ);

        assertEquals(expected, temp);
    }


    @Test
    void searchStateByServiceType() {
        SortedSet<State> temp = Search.searchStateByServiceType(servTypeFullSet, "s");

        //creation of expected set
        SortedSet<State> expected = new TreeSet<>();
        expected.add(stateX);
        expected.add(stateY);
        expected.add(stateZ);

        assertEquals(expected, temp);
    }
    
    @Test
    void searchStateByService() {
        SortedSet<State> temp = Search.searchStateByService(servFullSet, "stateX");

        //creation of expected set
        SortedSet<State> expected = new TreeSet<>();
        expected.add(stateX);

        assertEquals(0, expected.first().compareTo(temp.first()));
    }
    
    @Test
    void searchServiceByStrings() {
        SortedSet<String> servTypeToSearch = new TreeSet<>();
        servTypeToSearch.add("servTypeA");

        SortedSet<String> stateToSearch = new TreeSet<>();
        stateToSearch.add("stateX");

        SortedSet<Service> temp = Search.searchServiceByStrings(provFullSet,servTypeToSearch,stateToSearch);

        SortedSet<Service> expected = new TreeSet<>();
        expected.add(servX);

        assertEquals(expected, temp);
    }

    @Test
    void searchStateByStrings() {
        SortedSet<String> servTypeToSearch = new TreeSet<>();
        servTypeToSearch.add("servTypeA");

        SortedSet<State> temp = Search.searchStateByStrings(provFullSet, servTypeToSearch);

        SortedSet<State> expected = new TreeSet<>();
        expected.add(stateX);

        assertEquals(expected, temp);
    }

    @Test
    void searchServiceByState() {
        SortedSet<Service> temp = Search.searchServiceByState(servTypeFullSet, stateFullSet, "serviceX");

        SortedSet<Service> expected = new TreeSet<>();
        expected.add(servX);

        assertEquals(expected, temp);
    }
}