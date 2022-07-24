package carsharing.db;

import carsharing.util.AppProperties;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

/**
 * Singleton
 * Makes necessary migrations and updates version in properties
 */
public class MigrationManager {
    private static MigrationManager instance;

    public static MigrationManager getInstance() {
        if (instance == null) {
            instance = new MigrationManager();
        }
        return instance;
    }

    private MigrationManager() {
    }

    /**
     * Does all migrations that was created since current version in application.properties
     */
    public void migrate() {
        String migrationsPath = AppProperties.getMigrations();
        String version = removeFormattingFromVersion(AppProperties.getVersion());
        if (version == null) {
            return;
        }

        // Get migrations that are newer version than current
        List<String> latestMigrations = getLatestMigrations(getAllMigrations(), version);

        // Update version
        String latestVersion = latestMigrations.size() > 0 ? versionOf(latestMigrations.get(latestMigrations.size() - 1)) : version;
        AppProperties.setVersion(formatVersion(latestVersion));

        // Do migrations
        String dbUrl = AppProperties.getDbUrl();
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            connection.setAutoCommit(true);
            ScriptRunner sqlScriptRunner = new ScriptRunner(connection);
            sqlScriptRunner.setLogWriter(null);
            sqlScriptRunner.setSendFullScript(true);

            for (String migrationFileName : latestMigrations) {
                Reader reader = new BufferedReader(new FileReader(migrationsPath + migrationFileName));
                sqlScriptRunner.runScript(reader);
            }
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Adding dots between parts
    private String formatVersion(String version) {
        return version == null ? null : String.valueOf(version.charAt(0)) + '.' + version.charAt(1) + '.' + version.charAt(2);
    }

    // Remove dots from version (concatenate parts)
    private String removeFormattingFromVersion(String formattedVersion) {
        return formattedVersion == null ? null : String.valueOf(formattedVersion.charAt(0)) + formattedVersion.charAt(2) + formattedVersion.charAt(4);
    }

    // Gets filenames of all migration files
    private List<String> getAllMigrations() {
        String migrationsFolder = AppProperties.getMigrations();
        File[] files = new File(migrationsFolder).listFiles();

        List<String> migrationsFilenames = new ArrayList<>();
        if (files == null) {
            return migrationsFilenames;
        }

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".sql")) {
                migrationsFilenames.add(file.getName());
            }
        }

        return migrationsFilenames;
    }

    // Remove old migrations and sort by version
    private List<String> getLatestMigrations(List<String> migrationFilenames, String version) {
        List<String> latestMigrations = new ArrayList<>();
        VersionComparator comparator = new VersionComparator();
        for (String migrationFileName : migrationFilenames) {
            if (comparator.compare(migrationFileName, version) <= 0) {
                continue;
            }
            latestMigrations.add(migrationFileName);
        }
        latestMigrations.sort(new VersionComparator());
        return latestMigrations;
    }

    private static class VersionComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            return versionOf(o1).compareTo(versionOf(o2));
        }
    }

    private static String versionOf(String s) {
        return s.length() <= 3 ? s : s.substring(1, 4);
    }
}
