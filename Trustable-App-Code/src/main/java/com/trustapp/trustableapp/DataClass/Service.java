package com.trustapp.trustableapp.DataClass;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * this class was generated with the website "https://www.jsonschema2pojo.org/" in order to
 * reflect part of the structure of the data returned by the API at
 * "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/tsp_list"
 */
public class Service implements Comparable<Service>{
    //VARIABLES
    /** Service's nation's ID */
    private String countryCode;
    /** Status of the service */
    private final State currentStatus = new State();
    /** Service's provider */
    private Provider provider;
    /** Set of all the provider's service types */
    private final SortedSet<ServiceType> qServiceTypes = new TreeSet<>();
    /** Service's ID */
    private Integer serviceId;
    /** Name of the service */
    private String serviceName;
    /** service's terms of business */
    private String tob;
    /** service's provider's ID*/
    private Integer tspId;
    /** service's type */
    private String type;

    //METHODS

    public Service() {
    }
    
    public Service(String countryCode, Provider provider, Integer serviceId, String serviceName, String tob, Integer tspId, String type, String state) {
        this.countryCode = countryCode;
        this.provider = provider;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.tob = tob;
        this.tspId = tspId;
        this.type = type;
        this.currentStatus.setStatus( state,this);
    }



    /**
     * returns the service's nation's countryCode
     * @return
     */
    public String getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * retruns the provider current status
     * @return provider's status
     */
    public State getCurrentStatus() {
        return currentStatus;
    }
    public void setCurrentStatus(String currentStatus) {
        this.currentStatus.setStatus(currentStatus, this);
    }

    /**
     * Returns the set of service types
     * @return set of all service types
     */
    public SortedSet<ServiceType> getqServiceTypes() {
        return qServiceTypes;
    }
    public void setqServiceTypes(List<String> qServiceTypes) {
        for(String qServiceTypeString : qServiceTypes){
            this.qServiceTypes.add(new ServiceType(qServiceTypeString, this.tspId, this.countryCode));
        }
    }

    /**
     * Returns the service's ID
     * @return service's ID
     */
    public Integer getServiceId() {
        return serviceId;
    }
    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * Returns the service's name
     * @return name of the service
     */
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public String getTob() {
        return tob;
    }
    public void setTob(String tob) {
        this.tob = tob;
    }

    /**
     * Returns the ID of the providers of which the service belongs
     * @return service's provider's ID
     */
    public Integer getTspId() {
        return tspId;
    }

    public void setTspId(Integer tspId) {
        this.tspId = tspId;
    }

    /**
     * Returns the service's type
     * @return the type of the service
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the service
     * @param type service's type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * returns the provider of the service
     * @return service's provider
     */
    public Provider getProvider() {
        return provider;
    }

    /**
     * Sets the service's provider
     * @param provider provider of the service
     */
    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    /**
     * Returns a description of the service and its parameters
     * @return string of description of the service and its parameters
     */
    @Override
    public String toString() {
        return "Service : \n" +
                "   Service Name = " +
                ((this.serviceName == null) ? "<null>" : this.serviceName) +
                '\n' +
                "   Service ID = " +
                ((this.serviceId == null) ? "<null>" : this.serviceId) +
                '\n' +
                "   Current status = " +
                ((this.currentStatus == null) ? "<null>" : this.currentStatus) +
                '\n' +
                "   Q service types = " +
                ((this.qServiceTypes == null) ? "<null>" : this.qServiceTypes) +
                '\n' +
                "   Tob = " +
                ((this.tob == null) ? "<null>" : this.tob) +
                '\n' +
                "   Tsp ID = " +
                ((this.tspId == null) ? "<null>" : this.tspId) +
                '\n' +
                "   Type = " +
                ((this.type == null) ? "<null>" : this.type) +
                '\n';
    }

    /**
     * Returns a string containing the service's parameters
     * @return service's parameters in a string
     */
    public String parametersToString() {
        return ((this.serviceName == null) ? "<null>" : this.serviceName) + ((this.serviceId == null) ? "<null>" : this.serviceId) + ((this.currentStatus == null) ? "<null>" : this.currentStatus) + ((this.qServiceTypes == null) ? "<null>" : this.qServiceTypes) +((this.tob == null) ? "<null>" : this.tob) + ((this.tspId == null) ? "<null>" : this.tspId) + ((this.type == null) ? "<null>" : this.type);
    }


    /**
     * compares two services firstly by comparing the country they belong to.
     * If they belong to the same country they are then compared by tspId.
     * If they also have the same tspId they are compared by service name
     * @param otherService the object to be compared.
     * @return 1 if the country code of this service is lexicographically greater than the other
     *           or if the country code is the same but the tpsId of this service is greater than
     *           the other tpsId. If they also have the same tspId then 1 means the service name of this
     *           service is lexicographically greater than the other service name
     *
     *         0 if the services belong to the same country, have the same tpsId and the same service name
     *
     *         -1 in the remaining cases
     */
    @Override
    public int compareTo(Service otherService) {
        if(this.countryCode.compareTo(((Service)otherService).countryCode) > 0) return 1;
        else if(this.countryCode.compareTo(((Service)otherService).countryCode) < 0) return -1;
        else if(this.tspId.compareTo(((Service)otherService).tspId) > 0) return 1;
        else if(this.tspId.compareTo(((Service)otherService).tspId) < 0) return -1;
        else return this.serviceId.compareTo(((Service)otherService).serviceId);

    }

}
