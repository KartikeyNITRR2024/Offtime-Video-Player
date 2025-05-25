package com.offtime.videoplayer.validations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.offtime.videoplayer.exceptions.InvalidUniqueIdException;
import com.offtime.videoplayer.exceptions.InvalidUserException;
import com.offtime.videoplayer.utils.PropertyLoader;

@Component
public class UserValidations {

    private static final Logger logger = LoggerFactory.getLogger(UserValidations.class);

    @Autowired
    PropertyLoader propertyLoader;

    public void validateUser(String uniqueCode) {
        int min = propertyLoader.configTable.getUrlLengthMinLength();
        int max = propertyLoader.configTable.getUrlLengthMaxLength();

        if (uniqueCode.length() < min || uniqueCode.length() > max) {
            throw new InvalidUserException(
                String.format("Unique code should be greater than %d and less than %d", min, max));
        }
    }
}
