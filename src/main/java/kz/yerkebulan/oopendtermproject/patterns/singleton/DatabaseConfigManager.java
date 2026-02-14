package kz.yerkebulan.oopendtermproject.patterns.singleton;

public class DatabaseConfigManager {
    private static DatabaseConfigManager instance;
    private final String dbUrl;

    private DatabaseConfigManager() {
        this.dbUrl = "jdbc:postgresql://aws-1-eu-central-2.pooler.supabase.com:5432/postgres?user=postgres.rglgtkujtexehppimeai&password=6qlL5e0865lW1ayd";
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
