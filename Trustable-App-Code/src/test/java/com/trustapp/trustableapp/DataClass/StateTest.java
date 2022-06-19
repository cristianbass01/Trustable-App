package com.trustapp.trustableapp.DataClass;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {

    State state;
    Service service;
    @BeforeEach
    void setUp() {
        service = new Service("IT", new Provider("IT", "Banca d'Italia", new LinkedList<Service>(),"VATIT-00950501007",1 ,new Nation("IT", "Italia" )  ),1, "CN","null", 1, "link", "http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/withdrawn");
        state = new State("http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/withdrawn", service);
    }

    @Test
    void getStatus() { assertEquals("withdrawn", state.getStatus()); }

    @Test
    void getUrlString() {
        assertEquals("http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/withdrawn", state.getUrlString());
    }

    @Test
    void setStatus() {
        state.setStatus("http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/granted",service);
        assertEquals("granted", state.getStatus());
    }

    @Test
    void getService() {
        assertEquals(service, state.getService());
    }

    @Test
    void setService() {
        Service auxService= new Service("IT", new Provider("IT", "Banca d'Italia", new LinkedList<Service>(),"VATIT-00950501007",1 ,new Nation("IT", "Italia" )  ),1, "CN","null", 1, "link", "http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/withdrawn");
        state.setService(auxService);
        assertEquals(auxService,state.getService());
    }

    @Test
    void getServiceType() {
        //service type of state not set
        assertEquals(null, state.getServiceType());
        // set service type
        ServiceType st= new ServiceType("QCertESig", 1, "IT");
        state.setServiceType(st);
        assertEquals(st,state.getServiceType());
    }

    @Test
    void setServiceType() {
        ServiceType st= new ServiceType("QCertESig",1,"IT");
        state.setServiceType(st);
        assertEquals(st,state.getServiceType());
    }

    @Test
    void compareTo() {
        ServiceType st= new ServiceType("nameServiceType",1,"IT");
        state.setServiceType(st);
        assertEquals(0,state.compareTo(state));
    }

}