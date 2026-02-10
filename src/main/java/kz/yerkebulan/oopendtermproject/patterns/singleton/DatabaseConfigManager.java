package kz.yerkebulan.oopendtermproject.patterns.singleton;

public class DatabaseConfigManager {
    private static DatabaseConfigManager instance;
    private final String dbUrl;

    private DatabaseConfigManager() {
        this.dbUrl = "jdbc:postgresql://localhost:1111/postgres";
    }

    public static DatabaseConfigManager getInstance() {
        if (instance == null) {
            instance = new DatabaseConfigManager();
        }
        return instance;
    }

    public String getDbUrl() {
        return dbUrl;
    }
}
