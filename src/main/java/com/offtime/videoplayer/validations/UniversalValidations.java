package com.offtime.videoplayer.validations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.offtime.videoplayer.exceptions.InvalidUniqueIdException;
import com.offtime.videoplayer.utils.AllMicroservicesData;

@Component
public class UniversalValidations {

    @Autowired
    AllMicroservicesData allMicroservicesData;
    
    private static final Logger logger = LoggerFactory.getLogger(UniversalValidations.class);
    
    public void validateUniqueId(Integer pathUniqueId) {
        if (!pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.warn("Status check for uniqueId {}: Not Found", pathUniqueId);
            throw new InvalidUniqueIdException(String.format("Invalid unique id: %d", pathUniqueId));
        }
        logger.info("Status check for uniqueId {}: Found", pathUniqueId);
    }

}
