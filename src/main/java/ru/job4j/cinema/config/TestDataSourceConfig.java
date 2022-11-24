package ru.job4j.cinema.config;

import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestDataSourceConfig {

    public DataSource dataSource() {
        Properties properties = readConfigFile();
        return DataSourceBuilder
                .create()
                .username(properties.getProperty("jdbc.username"))
                .password(properties.getProperty("jdbc.password"))
                .url(properties.getProperty("jdbc.url"))
                .driverClassName(properties.getProperty("jdbc.driver"))
                .build();
    }

    private Properties readConfigFile() {
        try (FileInputStream fis = new FileInputStream("src/test/resources/db.properties")) {
            Properties properties = new Properties();
            properties.load(fis);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Can't read file");
        }
    }

}