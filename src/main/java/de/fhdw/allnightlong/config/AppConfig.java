package de.fhdw.allnightlong.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class AppConfig {
    private Properties properties;

    public AppConfig() {
        properties = new Properties();
        try {
            properties.load(loadConfig("config.properties"));
        } catch (IOException e) {
            System.out.println("Fehler beim Laden der Konfigurationsdatei: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getApiKey(String keyName) {
        return properties.getProperty(keyName);
    }


    private static InputStream loadConfig(String resourcePath) {
        ClassLoader classLoader = AppConfig.class.getClassLoader();

        return classLoader.getResourceAsStream(resourcePath);
    }
}


