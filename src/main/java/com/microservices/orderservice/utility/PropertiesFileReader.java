package com.microservices.orderservice.utility;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The utility methods include accessing properties from the properties file.
 *
 * @author priyanshu
 * @version 1.0
 * @since 23/02/2024
 */
@Slf4j
public final class PropertiesFileReader {

    /**
     * Private constructor to prevent instantiation.
     */
    private PropertiesFileReader() {

    }

    /**
     * Returns the properties from the properties file.
     *
     * @return The properties from the properties file.
     */
    public static Properties getProperties(String filePath) {
        Properties properties = new Properties();
        try {
            File file = ResourceUtils.getFile(filePath);
            try (InputStream in = new FileInputStream(file)) {
                properties.load(in);
            }
        } catch (IOException e) {
            log.error("Failed to read {} file: {}", filePath, e.getMessage());
        }
        return properties;
    }
}
