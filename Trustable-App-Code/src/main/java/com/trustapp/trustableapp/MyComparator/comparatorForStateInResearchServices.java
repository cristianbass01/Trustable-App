package com.trustapp.trustableapp.MyComparator;

import com.trustapp.trustableapp.DataClass.State;

import java.util.Comparator;

/**
 * Comparator for state items, checks countryCode, ID of the provider, service type and status of the state object
 */
public class comparatorForStateInResearchServices implements Comparator<State> {
    /**
     * Compares the two given states
     * @param state the first object to be compared.
     * @param otherState the second object to be compared.
     * @return 1 if the countryCode, the provider's ID or the name of the service type of the first state is grater that the second's
     *          -1 if the countryCode, the provider's ID or the name of the service type of the first state is lesser that the second's
     *          the comparison of the status otherwise
     */
    @Override
    public int compare(State state, State otherState) {
        if(state.getService().getCountryCode().compareTo(otherState.getService().getCountryCode()) > 0) return 1;
        else if(state.getService().getCountryCode().compareTo(otherState.getService().getCountryCode()) < 0) return -1;
        else if(state.getService().getTspId().compareTo(otherState.getService().getTspId()) > 0) return 1;
        else if(state.getService().getTspId().compareTo(otherState.getService().getTspId()) < 0) return -1;
        else if(state.getServiceType().getServiceType().compareTo(otherState.getServiceType().getServiceType()) < 0) return 1;
        else if(state.getServiceType().getServiceType().compareTo(otherState.getServiceType().getServiceType()) > 0) return -1;
        else return state.getStatus().compareTo(otherState.getStatus());

    }
}
