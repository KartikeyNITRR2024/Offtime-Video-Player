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

    private static final Logger logger = LoggerFactory.getLogger(PropertyLoader.class);

    public static ConfigTable configTable;

    private static ConfigTableRepo configTableRepo;
    private static ConfigUsedRepo configUsedRepo;

    @Autowired
    public PropertyLoader(ConfigTableRepo configTableRepo, ConfigUsedRepo configUsedRepo) {
        PropertyLoader.configTableRepo = configTableRepo;
        PropertyLoader.configUsedRepo = configUsedRepo;
        updatePropertyLoader();
    }

    public static void updatePropertyLoader() {
        try {
            logger.info("Fetching currently config used.");
            ConfigUsed configUsed = configUsedRepo.findById(1L).orElse(null);

            if (configUsed == null) {
                logger.warn("ConfigUsed not found. Using default.");
                configTable = getDefaultConfig();
                return;
            }

            configTable = configTableRepo.findById(configUsed.getId()).orElse(getDefaultConfig());

            logger.info("Loaded configuration: {}", configTable);
        } catch (Exception e) {
            logger.error("Failed to load configuration", e);
            configTable = getDefaultConfig();
        }
    }

    private static ConfigTable getDefaultConfig() {
        return new ConfigTable(1L, 10, 5, 31536000, 2, 50, "Asia/Kolkata");
    }

    public static ConfigTable getConfigTable() {
        return configTable;
    }
}
