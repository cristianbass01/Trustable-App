package com.trustapp.trustableapp.DataClass;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTypeTest {

    ServiceType serviceType;
    Provider provider;

    @BeforeEach
    void setUp() {
        serviceType = new ServiceType("QCertESig", 1, "IT");
        provider = new Provider("IT", "Banca d'Italia", new LinkedList<Service>(), "VATIT-00950501007", 1, new Nation("IT", "Italia"));
        serviceType.setProvider(provider);
    }

    @Test
    void getServiceType() {
        assertEquals("QCertESig", serviceType.getServiceType());
    }

    @Test
    void setServiceType() {
        serviceType.setServiceType("QCertESeal");
        assertEquals("QCertESeal", serviceType.getServiceType());
    }

    @Test
    void getTspId() {
        assertEquals(1, serviceType.getTspId());
    }

    @Test
    void getCountryCode() {
        assertEquals("IT", serviceType.getCountryCode());
    }

    @Test
    void setTspId() {
        serviceType.setTspId(2);
        assertEquals(2, serviceType.getTspId());
    }

    @Test
    void setCountryCode() {
        serviceType.setCountryCode("GB");
        assertEquals("GB", serviceType.getCountryCode());
    }

    @Test
    void getProvider() {
        assertEquals(0, serviceType.getProvider().compareTo(provider));
    }

    @Test
    void setProvider() {
        Provider prov = new Provider("IT", "Intesa San Paolo", new LinkedList<Service>(), "VATIT-00950501007", 1, new Nation("IT", "Italia"));
        serviceType.setProvider(prov);
        assertEquals(0, serviceType.getProvider().compareTo(prov));
    }

    @Test
    void compareTo() {
        assertEquals(0, serviceType.compareTo(new ServiceType("QCertESig", 1, "IT"))); }
}
