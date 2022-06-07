package com.trustapp.trustableapp.DataClass;

import java.util.Comparator;

/**
 *  class representing the status of service. It's described by a string
 */
public class State implements Comparable<State> {
    /** Status: name and the URL that open the page of the service's state */
    private String status;
    /** Name of the service referred to the state */
    private Service service;
    /** type of the service referred to this status */
    private ServiceType serviceType;
    /** default constructor */
    public State(){}

    /**
     * Constructor that initializes the name of the status and its service
     * @param status state of the service
     * @param service referred to the status
     */
    public State(String status, Service service) {
        this.status = status;
        this.service = service;
    }

    /**
     * Returns the name of the status
     * @return name of the status
     */
    public String getStatus() {
        return status.substring(50);
    }

    /**
     * Returns the link referred to the status of the service
     * @return status, link URL
     */
    public String getUrlString(){
        return status;
    }

    /**
     * Sets the main variables of the instance
     * @param status name and link of the service's status
     * @param service referred to this status
     */
    public void setStatus(String status, Service service) {
        this.status = status;
        this.service = service;
    }

    /**
     * Returns the service referred to the status
     * @return status's service
     */
    public Service getService() {
        return service;
    }

    /**
     * Sets the status's service
     * @param service service referred to the status
     */
    public void setService(Service service) {
        this.service = service;
    }

    /**
     * Returns the service type of the service referred to the status
     * @return state's service type
     */
    public ServiceType getServiceType() {
        return serviceType;
    }

    /**
     * sets the service type of the service referred to this status
     * @param serviceType service type of the state's service
     */
    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * compares two states by using the lexicographical order
     * @param otherState the object to be compared.
     * @return the value 0 if the text in the other state is equal to the text of this box;
     *         a value less than 0 if the text in this state is lexicographically less than the text in the other;
     *         and a value greater than 0 if the text in this state is lexicographically greater than the text in the
     *         other.
     */

    @Override
    public int compareTo(State otherState) {
        if(this.serviceType.compareTo(((State)otherState).serviceType) >0) return 1;
        else if(this.serviceType.compareTo(((State)otherState).serviceType) <0) return -1;
        if(this.service.compareTo(((State)otherState).service) >0) return 1;
        else if(this.service.compareTo(((State)otherState).service) <0) return -1;
        else return this.status.compareTo(((State)otherState).status);

    }


    /**
     * Returns a string that describes the status and its parameters
     * @return string of description of the instance status
     */
    @Override
    public String toString() {
        return "State : \n" +
                "   State Name = " +
                ((this.getStatus() == null) ? "<null>" : this.getStatus()) +
                '\n' +
                "   Link = " +
                ((this.status == null) ? "<null>" : this.status) +
                '\n';
    }

    /**
     * Returns a description of the state's parameters
     * @return parameters of the instance in a string
     */
    public String parametersToString() {
        return getStatus();
    }
}
