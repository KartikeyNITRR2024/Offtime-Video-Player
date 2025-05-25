package com.offtime.videoplayer.utils;

import java.util.Optional;

//import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.offtime.videoplayer.entities.ConfigTable;
import com.offtime.videoplayer.entities.ConfigUsed;
import com.offtime.videoplayer.repos.ConfigTableRepo;
import com.offtime.videoplayer.repos.ConfigUsedRepo;

@Component
public class PropertyLoader {
	 private Long configId;
	 public ConfigTable configTable;
	 private static final Logger logger = LoggerFactory.getLogger(PropertyLoader.class);

	    @Autowired
	    private ConfigTableRepo configTableRepo;
	    
	    @Autowired
	    private ConfigUsedRepo configUsedRepo;

	    public void updatePropertyLoader() {
	        try {
	        	logger.info("Fetching currently config used.");
	            ConfigUsed configUsed = configUsedRepo.findById(1L).get();
//	        	ConfigUsed configUsed = new ConfigUsed(1L, 1);
	            configId = configUsed.getId();
	            logger.debug("Fetching configuration for configId: {}", configId);
	            Optional<ConfigTable> configTableOpt = configTableRepo.findById(configId);
	            if (configTableOpt.isPresent()) {
	            	configTable = configTableOpt.get();
	                logger.info("Configurtion values are: {}", configTable);
	            } else {
		            configTable = new ConfigTable(1L, 10, 5, 31536000, 2, 50);
	                logger.warn("No configuration found for configId: {}", configId);
	            }
	            logger.debug("All Configurations are: {}", configTable);
	        } catch (Exception e) {
	            configTable = new ConfigTable(1L, 10, 5, 31536000, 2, 50);
	            logger.error("An error occurred while fetching configuration: {}", e.getMessage(), e);
	        }
	    }
}
