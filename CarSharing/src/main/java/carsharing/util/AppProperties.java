package carsharing.util;

import java.io.*;
import java.util.Properties;

/**
 * Storing application properties
 */
public class AppProperties {
    private static final Properties properties;
    private static final String propertiesPath;

    private static String dbName;

    static {
//        propertiesPath = "./src/carsharing/db/application.properties";
        propertiesPath = "./CarSharing/src/main/resources/application.properties";
        properties = new Properties();
        try (Reader reader = new BufferedReader(new FileReader(propertiesPath))) {
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbName = "";
    }

    private static void save() {
        try {
            properties.store(new FileWriter(propertiesPath), "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setDbName(String dbName) {
        AppProperties.dbName = dbName;
    }

    public static String getDbUrl() {
        return properties.getProperty("dbUrl") + dbName;
    }

    public static String getResources() {
        return properties.getProperty("resources");
    }

    public static String getMigrations() {
        return properties.getProperty("migrations");
    }

    public static String getVersion() {
        return properties.getProperty("version");
    }

    public static void setVersion(String version) {
        properties.setProperty("version", version);
        save();
    }
}
