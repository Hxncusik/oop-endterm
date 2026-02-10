package kz.yerkebulan.oopendtermproject;

import kz.yerkebulan.oopendtermproject.patterns.singleton.DatabaseConfigManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public DatabaseConfigManager dbConfigManager() {
        return DatabaseConfigManager.getInstance();
    }
}
