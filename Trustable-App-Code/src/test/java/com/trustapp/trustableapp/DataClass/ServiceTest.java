package com.trustapp.trustableapp.DataClass;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    Service service;

    ServiceType servTypeA;
    ServiceType servTypeB;
    ServiceType servTypeC;

    @BeforeEach
    void setUp() {
        service = new Service("IT", new Provider("IT", "Banca d'Italia", new LinkedList<Service>(),"VATIT-00950501007",1 ,new Nation("IT", "Italia" )  ),1, "CN","null", 1, "link", "http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/withdrawn");
        servTypeA = new ServiceType("servTypeA",1, "IT");
        servTypeB = new ServiceType("servTypeB",1, "IT");
        servTypeC = new ServiceType("servTypeC",1, "IT");
    }

    @Test
    void getCountryCode() { assertEquals("IT", service.getCountryCode()); }

    @Test
    void setCountryCode() {
        service.setCountryCode("GB");
        assertEquals("GB", service.getCountryCode());
    }

    @Test
    void getCurrentStatus() { assertEquals("withdrawn", service.getCurrentStatus().getStatus()); }

    @Test
    void setCurrentStatus() {
        service.setCurrentStatus("http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/granted");
        assertEquals("granted", service.getCurrentStatus().getStatus());
    }

    @Test
    void getqServiceTypes() {
        //void set, service's types not initialized
        SortedSet<ServiceType> voidSet= new TreeSet<>();
        assertEquals(voidSet, service.getqServiceTypes());

        //add set of service types (with the tstID and CountryCode of the service
        voidSet.add(servTypeA);
        voidSet.add(servTypeB);
        voidSet.add(servTypeC);

        List<String> newList= Arrays.asList("servTypeA","servTypeB","servTypeC");

        service.setqServiceTypes(newList);

        assertEquals(voidSet,service.getqServiceTypes());
    }


    @Test
    void setqServiceTypes() {
        SortedSet<ServiceType> newSet= new TreeSet<>();
        newSet.add(servTypeA);
        newSet.add(servTypeB);
        newSet.add(servTypeC);
        List<String> newList= Arrays.asList("servTypeA","servTypeB","servTypeC");
        service.setqServiceTypes(newList);
        assertEquals(newSet,service.getqServiceTypes());
    }

    @Test
    void getServiceId() { assertEquals(1 , service.getServiceId()); }

    @Test
    void setServiceId() {
        service.setServiceId(2);
        assertEquals(2 , service.getServiceId());
    }

    @Test
    void getServiceName() { assertEquals("CN", service.getServiceName()); }

    @Test
    void setServiceName() {
        service.setServiceName("GC");
        assertEquals("GC", service.getServiceName());
    }

    @Test
    void getTob() { assertEquals("null", service.getTob()); }

    @Test
    void setTob() {
        service.setTob("valid");
        assertEquals("valid", service.getTob());
    }

    @Test
    void getTspId() { assertEquals(1, service.getTspId()); }

    @Test
    void setTspId() {
        service.setTspId(2);
        assertEquals(2, service.getTspId());
    }

    @Test
    void getType() { assertEquals( "link", service.getType()); }

    @Test
    void setType() {
        service.setType("notlink");
        assertEquals( "notlink", service.getType());
    }

    @Test
    void getProvider() { assertEquals(0, service.getProvider().compareTo(new Provider("IT", "Banca d'Italia", new LinkedList<Service>(),"VATIT-00950501007",1 ,new Nation("IT", "Banca d'Italia" )  )));    }

    @Test
    void setProvider() {
        service.setProvider(new Provider("IT", "Intesa San Paolo", new LinkedList<Service>(),"VATIT-00950501007",1 ,new Nation("IT", "Banca d'Italia" )  ));
        assertEquals(0, service.getProvider().compareTo(new Provider("IT", "Intesa San Paolo", new LinkedList<Service>(),"VATIT-00950501007",1 ,new Nation("IT", "Banca d'Italia" ))));
    }

    @Test
    void compareTo() { assertEquals( 0, service.compareTo(service)); }
}