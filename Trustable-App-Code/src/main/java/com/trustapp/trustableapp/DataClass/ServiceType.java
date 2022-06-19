package com.trustapp.trustableapp.DataClass;

/**
 * class representing the type of service that is described by a string
 */
public class ServiceType implements Comparable<ServiceType>{

    /** name of the service type */
    private String serviceType;
    /** id of the provider that offers thisservice type */
    private Integer tspId;
    /** countryCode of the nation that offers this service type */
    private String countryCode;
    /** service type's provider*/
    private Provider provider;

    /**
     * Constructor, sets the service type's name, ID and countryCode
     * @param serviceType name of the service type
     * @param tspId ID of the provider that offers this service type
     * @param countryCode ID of the country that offers this service type
     */
    public ServiceType(String serviceType, Integer tspId, String countryCode) {
        this.serviceType = serviceType;
        this.tspId = tspId;
        this.countryCode = countryCode;
    }

    /**
     * Returns the name of the service type
     * @return service type's name
     */
    public String getServiceType() { return serviceType; }

    /**
     * Sets the name of the service type with the string given
     * @param serviceType service type's name
     */
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    /**
     * Returns the ID of the provider that offers the service referred to this service type
     * @return ID of the provider
     */
    public Integer getTspId() {
        return tspId;
    }

    /**
     * Returns the ID of the nation that offers this service type
     * @return service type's countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets ID of the provider that offers the service referred to this service type
     * @param tspId ID of the provider
     */
    public void setTspId(Integer tspId) {
        this.tspId = tspId;
    }

    /**
     * Sets the ID of the nation that provides the service referred to this service type
     * @param countryCode ID of the nation
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * Returns the name of the provider that offers this service type
     * @return provider of this service type
     */
    public Provider getProvider() {
        return provider;
    }

    /**
     * Sets the provider of this service type
     * @param provider service type's provider
     */
    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    /**
     * compares two services by using the lexicographical order
     * @param otherServiceType the object to be compared.
     * @return the value 0 if the text in the other service type is equal to the text of this box;<br>
     *         a value less than 0 if the text in this service type is lexicographically less than the text in the other;
     *         and a value greater than 0 if the text in this service type is lexicographically greater than the text in the
     *         other.
     */
    @Override
    public int compareTo(ServiceType otherServiceType) {
        if(this.countryCode.compareTo(((ServiceType)otherServiceType).countryCode) <0) return -1;
        else if(this.countryCode.compareTo(((ServiceType)otherServiceType).countryCode) >0) return 1;
        else if(this.tspId.compareTo(((ServiceType)otherServiceType).tspId) <0) return -1;
        else if(this.tspId.compareTo(((ServiceType)otherServiceType).tspId) >0) return 1;
        else return this.serviceType.compareTo(((ServiceType)otherServiceType).serviceType);

    }

    /**
     * Returns a string that describes the service type and its parameters
     * @return string of description of the instance
     */
    @Override
    public String toString() {
        return this.countryCode + " " + this.tspId + " " + serviceType;
    }

    /**
     * Returns a description of the service type's parameters
     * @return parameters of the instance in a string
     */
    public String parametersToString() {
        return this.countryCode + " " + this.tspId + " " + serviceType;
    }

}
