package com.trustapp.trustableapp.DataClass;

import java.util.List;

/**
 * this class was created in order to reflect the structure of the data returned by the API
 * at the URL:"https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/countries_list"
 * with the addition of a list of Providers
 */
public class Nation implements Comparable<Nation>{
    //VARIABLES
    /** Id of the nation */
    private String countryCode;
    /** Name of the nation */
    private String countryName;
    /** List of the nation's providers */
    private List<Provider> providerList;

    //CONSTRUCTORS
    /** Default constructor of the class */
    public Nation(){}

    /**
     * Constructor of the class that initializes its name and countryCode
     * @param countryCode id of the nation
     * @param name name of the nation
     */
    public Nation(String countryCode, String name) {
        this.countryCode = countryCode;
        this.countryName = name;
    }

    //METHODS

    /**
     * Sets the nation's countryCode
     * @param countryCode , the nation's ID
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * Returns the nation's countryCode
     * @return countryCode, the nation's ID
     */
    public String getCountryCode() { return countryCode;}

    /**
     * Sets the nation's name
     * @param countryName , name of the nation
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Returns the nation's name
     * @return the name of the nation
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Retruns the nation's provider list
     * @return list of the nation's providers
     */
    public List<Provider> getProviderList() {
        return providerList;
    }

    /**
     * Initializes the nation's provider list with the given list of providers
     * @param providerList list of providers
     */
    public void setProviderList(List<Provider> providerList) {
        this.providerList = providerList;
    }

    /**
     * Compares the nation to the one passed as parameter
     * @param otherNation the object to be compared.
     * @return 1 if this nation's countryCode is lexicographically greater than the other nation's.
     *          0 if the nations have the same countryCode
     *          -1 in the other remaining cases
     */
    @Override
    public int compareTo(Nation otherNation) {
        return this.countryCode.compareTo(((Nation)otherNation).countryCode);
    }

    /**
     * Describes the nation and its parameters
     * @return a string describing the nation's characteristics
     */
    @Override
    public String toString() {
        return "Nation : \n" +
                "   CountryCode = " + countryCode + '\n' +
                "   CountryName = " + countryName + '\n';
    }

    /**
     * Returns the parameters of the nation as a string
     * @return a string of the nation parameters
     */
    public String parametersToString() {
        return countryCode + countryName;
    }
}
