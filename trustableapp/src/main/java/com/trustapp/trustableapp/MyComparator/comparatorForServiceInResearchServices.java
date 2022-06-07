package com.trustapp.trustableapp.MyComparator;

import com.trustapp.trustableapp.DataClass.Service;

import java.util.Comparator;
/**
 * Comparator for service items, checks countryCode,ID of the provider, service type, status and the service's status of the service object
 */
public class comparatorForServiceInResearchServices implements Comparator<Service> {
    /**
     * Compares the two given services
     * @param service the first object to be compared.
     * @param otherService the second object to be compared.
     * @return 1 if the countryCode, the provider's ID, the service type or the status of the first state is grater that the second's
     *          -1 if the countryCode or the provider's ID of the first state is lesser that the second's
     *          the comparison of the services' ID otherwise
     */
    @Override
    public int compare(Service service, Service otherService) {
        if(service.getCountryCode().compareTo(otherService.getCountryCode()) > 0) return 1;
        else if(service.getCountryCode().compareTo(otherService.getCountryCode()) < 0) return -1;
        else if(service.getTspId().compareTo(otherService.getTspId()) > 0) return 1;
        else if(service.getTspId().compareTo(otherService.getTspId()) < 0) return -1;
        else if(service.getCurrentStatus().getServiceType().getServiceType().compareTo(otherService.getCurrentStatus().getServiceType().getServiceType()) < 0) return 1;
        else if(service.getCurrentStatus().getServiceType().getServiceType().compareTo(otherService.getCurrentStatus().getServiceType().getServiceType()) > 0) return -1;
        else if(service.getCurrentStatus().getStatus().compareTo(otherService.getCurrentStatus().getStatus()) > 0) return 1;
        else if(service.getCurrentStatus().getStatus().compareTo(otherService.getCurrentStatus().getStatus()) < 0) return -1;
        else return service.getServiceId().compareTo(otherService.getServiceId());
    }
}
