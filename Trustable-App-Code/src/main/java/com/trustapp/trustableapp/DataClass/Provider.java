package com.trustapp.trustableapp.DataClass;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * this class was generated with the website "https://www.jsonschema2pojo.org/" in order to
 * reflect the structure of the data returned by the API at
 * "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/tsp_list"
 */
public class Provider implements Comparable<Provider>{
    //VARIABLES
    /** provider's countryCode, referred to the provider's nation*/
    private String countryCode;
    /** provider's name */
    private String name;
    /** set of the service types of the provider's services*/
    private final SortedSet<ServiceType> qServiceTypes = new TreeSet<>();
    /** list of the provider's services, set null by default */
    private List<Service> services = null;
    /** the provider's electronic commerce badge */
    private String trustmark;
    /** provider's ID */
    private Integer tspId;
    /** The nation to which the provider belongs*/
    private Nation nation;

    //METHODS
    public Provider() {}
    public Provider(String countryCode, String name, List<Service> services, String trustmark, Integer tspId, Nation nation) {
        this.countryCode = countryCode;
        this.name = name;
        this.services = services;
        this.trustmark = trustmark;
        this.tspId = tspId;
        this.nation = nation;
    }

    /**
     * Returns the provider's countryCode
     * @return countryCode, the provider's nation's countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the provider's countryCode
     * @param countryCode
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * Returns the provider's name
     * @return the provider's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the provider's name
     * @param name name of the provider
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the set of the service types of the services of the provider
     * @return set of the service types of the provider
     */
    public SortedSet<ServiceType> getqServiceTypes() {
        return qServiceTypes;
    }

    /**
     * Sets the set of the service types of the services of the provider
     * @param qServiceTypes
     */
    public void setqServiceTypes(List<String> qServiceTypes) {
        for(String qServiceTypeString : qServiceTypes){
            this.qServiceTypes.add(new ServiceType(qServiceTypeString, this.tspId, this.countryCode));
        }

    }

    /**
     * Returns the provider's services
     * @return services, the set of the provider's services
     */
    public List<Service> getServices() {
        return services;
    }

    /**
     * Sets the provider's services
     * @param services
     */
    public void setServices(List<Service> services) {
        this.services = services;
    }

    /**
     * Returns the provider's Trustmark
     * @return
     */
    public String getTrustmark() {
        return trustmark;
    }

    /**
     * Sets the provider's Trustmark
     * @param trustmark
     */
    public void setTrustmark(String trustmark) {
        this.trustmark = trustmark;
    }

    /**
     * Returns the provider's TspId
     * @return
     */
    public Integer getTspId() {
        return tspId;
    }

    /**
     * Sets the provider's TspId
     * @param tspId
     */
    public void setTspId(Integer tspId) {
        this.tspId = tspId;
    }

    /**
     * Returns the nation of which the provider belongs
     * @return
     */
    public Nation getNation() {
        return nation;
    }

    /**
     * Sets the provider's nation with the given parameter
     * @param nation provider's nation
     */
    public void setNation(Nation nation) {
        this.nation = nation;
    }

    /**
     * Returns the provider's parameters in a string
     * @return string describing the provider
     */
    @Override
    public String toString() {
        String toAdd = "";
        if(this.qServiceTypes == null)
            toAdd += "\n<null>";
        else {
            for(ServiceType temp : this.qServiceTypes) {
                toAdd +="\n       " + temp.getServiceType();
            }
        }

        return "Provider : \n" +
                "   Provider name : \n       " +
                ((this.name == null) ? "<null>" : this.name) +
                '\n' +
                "   Tsp ID : \n       " +
                ((this.tspId == null) ? "<null>" : this.tspId) +
                '\n' +
                "   Q service types : " +
                toAdd +
                '\n' +
                "   Trustmark : \n       " +
                ((this.trustmark == null) ? "<null>" : this.trustmark) +
                '\n';
    }

    /**
     * Returns a string with the provider's parameters
     * @return string with the provider's parameters
     */
    public String parametersToString() {
        return ((this.name == null) ? "<null>" : this.name) +
                ((this.tspId == null) ? "<null>" : this.tspId) +
                ((this.qServiceTypes == null) ? "<null>" : this.qServiceTypes) +
                ((this.trustmark == null) ? "<null>" : this.trustmark);
    }

    /**
     * compares two providers by the nation they belong to and if they have the same name they are then compared by
     * provider's name
     * @param otherProvider the object to be compared.
     * @return 1 if this provider country is lexicographically greater than the other provider country.
     *           If they belong to the same country the method returns 1 if this provider name is lexicographically
     *           greater than the other country name
     *
     *         0 if the services belong to the same country and have the same name
     *
     *         -1 in the other remaining cases
     */
    @Override
    public int compareTo(Provider otherProvider) {
        if(this.countryCode.compareTo(((Provider)otherProvider).countryCode) > 0) return 1;
        else if(this.countryCode.compareTo(((Provider)otherProvider).countryCode) < 0) return -1;
        else return this.tspId.compareTo(((Provider)otherProvider).tspId);
    }
}
