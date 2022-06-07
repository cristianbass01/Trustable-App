package com.trustapp.trustableapp.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trustapp.trustableapp.Error.LaunchAlert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * class that enables to get a list of Object of type T from an API request to the link
 * it was constructed with
 * @param <T> type of object expected from the API get
 */
public class GetFromAPI<T> {
    //VARIABLES
    /** URL associated to this GetFromAPI */
    private final String URLname;

    //METHODS
    /**
     * constructor of the class
     * @param URLname URL to use to get the JSON data
     */
    public GetFromAPI(String URLname) { this.URLname = URLname; }

    /**
     * @return returns the URL (to which the program will connect to) used to initialize the class
     */
    @Override
    public String toString() { return URLname; }

    /**
     * method that gets JSON data from the URL used when initializing the data
     * and translates it to String
     * @return data from URL converted to String
     */
    public String getData() {
        URL oracle = null;
        URLConnection yc = null;
        BufferedReader in = null;

        try {
            oracle = new URL(URLname);
            yc = oracle.openConnection();
            in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

        }
        catch (MalformedURLException e) { //catch required by new URL()
            LaunchAlert.noConnection();
        }
        catch (IOException e) { //catch required by openConnection and BufferedReader
            LaunchAlert.noConnection();
        }

        //insertion of data from "in" into the String inputLine
        String inputLine = null;
        try {
            if((inputLine = in.readLine()) == null){
                LaunchAlert.noApiConversion();
            }
        } catch (IOException e) {
            e.printStackTrace();
            LaunchAlert.noApiConversion();
        }

        return inputLine;
    }

    /**
     * return a List of Objects provided if the Object reflects the structure of the JSON string
     * returned by the API call
     *
     * @param typeParameterClass type of Object the JSON string (returned by the API) reflects
     * @return List of Objects provided
     */
    public List<T> apiToList(Class<T[]> typeParameterClass) {
        List<T> listToReturn = null;

        try {
            listToReturn = new ArrayList<>(List.of((T[]) (new ObjectMapper()).readValue(getData(), typeParameterClass)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            LaunchAlert.noApiConversion();
        }

        return listToReturn;
    }
}