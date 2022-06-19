package com.trustapp.trustableapp.DataClass;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ProviderTest {

    Provider provider;

    private List<Service> servFullSet;

    ServiceType servTypeA;
    ServiceType servTypeB;
    ServiceType servTypeC;

    private Service servX;
    private Service servY;
    private Service servZ;
    @BeforeEach
    void setUp() {
        servX = new Service("IT", null,0, "serviceX","null", 0, "", "http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/stateX");
        servY = new Service("BE", null,0, "serviceY","null", 0, "", "http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/stateY");
        servZ = new Service("HR", null,0, "serviceZ","null", 0, "", "http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/stateZ");

        servFullSet = new LinkedList<>();
        servFullSet.add(servX);
        servFullSet.add(servY);
        servFullSet.add(servZ);

        servTypeA = new ServiceType("servTypeA",1, "IT");
        servTypeB = new ServiceType("servTypeB",1, "IT");
        servTypeC = new ServiceType("servTypeC",1, "IT");
        provider = new Provider("IT", "Banca d'Italia",  servFullSet,"VATIT-00950501007",1 ,new Nation("IT", "Italia" )  );


        List<String> newList= Arrays.asList("servTypeA","servTypeB");
        provider.setqServiceTypes(newList);
    }

    @Test
    void getCountryCode() { assertEquals("IT", provider.getCountryCode() );  }

    @Test
    void setCountryCode() {
        provider.setCountryCode("GB");
        assertEquals("GB", provider.getCountryCode() );
    }

    @Test
    void getName() { assertEquals("Banca d'Italia", provider.getName() );     }

    @Test
    void setName() {
        provider.setName("Intesa San Paolo");
        assertEquals("Intesa San Paolo", provider.getName() );
    }

    @Test
    void getqServiceTypes() {
        SortedSet<ServiceType> voidSet= new TreeSet<>();
        voidSet.add(servTypeA);
        voidSet.add(servTypeB);
        assertEquals( voidSet , provider.getqServiceTypes());
    }

    @Test
    void setqServiceTypes() {
        SortedSet<ServiceType> voidSet= new TreeSet<>();
        voidSet.add(servTypeA);
        voidSet.add(servTypeB);
        voidSet.add(servTypeC);

        List<String> newList = Arrays.asList("servTypeA","servTypeB","servTypeC");

        provider.setqServiceTypes(newList);

        assertEquals( voidSet , provider.getqServiceTypes());
    }

    @Test
    void getServices() {  assertEquals(servFullSet, provider.getServices());  }


    @Test
    void setServices() {
        List<Service> newList = new LinkedList<>();
        newList.add(servX);
        newList.add(servY);
        provider.setServices(newList);

        assertEquals(newList, provider.getServices());
    }

    @Test
    void getTrustmark() { assertEquals("VATIT-00950501007", provider.getTrustmark());    }

    @Test
    void setTrustmark() {
        provider.setTrustmark("VATIT-50000029929");
        assertEquals("VATIT-50000029929", provider.getTrustmark());
    }

    @Test
    void getTspId() { assertEquals(1, provider.getTspId()); }

    @Test
    void setTspId() {
        provider.setTspId(2);
        assertEquals(2, provider.getTspId());
    }

    @Test
    void getNation() { assertEquals(0, provider.getNation().compareTo(new Nation("IT", "Italia" ))); }

    @Test
    void setNation() {
        provider.setNation(new Nation("AT", "Austria"));
        assertEquals(0, provider.getNation().compareTo(new Nation("AT", "Austria" )));
    }

    @Test
    void compareTo() { assertEquals(0, provider.compareTo(new Provider("IT", "Banca d'Italia", new LinkedList<Service>(),"VATIT-00950501007",1 ,new Nation("IT", "Italia" )  )));    }
}