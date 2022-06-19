package com.trustapp.trustableapp.DataClass;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NationTest {

    Nation nation;
    List<Provider> providerList;
    Provider prov1;
    Provider prov2;
    Provider prov3;
    Nation it;
    Nation be;
    Nation hr;
    @BeforeEach
    void setUp() {
        prov1 = new Provider("IT","prov1", new ArrayList<>(), "",0, it);
        prov2 = new Provider("BE","prov2", new ArrayList<>(), "",0, be);
        prov3 = new Provider("HR","prov3", new ArrayList<>(), "",0, hr);
        providerList = new LinkedList<>();
        providerList.add(prov1);
        providerList.add(prov2);
        nation = new Nation("IT", "Italy");
        nation.setProviderList(providerList);
    }

    @Test
    void getCountryName() {
        assertEquals("Italy", nation.getCountryName());
    }
    @Test
    void setCountryName() {
        Nation temp = new Nation("IT", "Italy");
        temp.setCountryName("Sweden");
        assertEquals("Sweden", temp.getCountryName());
    }

    @Test
    void setCountryCode() {
        Nation temp = new Nation("IT", "Italy");
        temp.setCountryCode("SW");
        assertEquals("SW", temp.getCountryCode());
    }

    @Test
    void getCountryCode() {
        assertEquals("IT", nation.getCountryCode());
    }

    @Test
    void getProviderList() { assertEquals(providerList , nation.getProviderList());    }

    @Test
    void setProviderList() {
        List<Provider> tempProviderList = new LinkedList<>();
        tempProviderList.add(prov1);
        tempProviderList.add(prov3);
        nation.setProviderList(tempProviderList);
        assertEquals(tempProviderList, nation.getProviderList());
    }

    @Test
    void compareTo() { assertEquals(0, nation.compareTo(new Nation("IT", "Italy")));    }

}