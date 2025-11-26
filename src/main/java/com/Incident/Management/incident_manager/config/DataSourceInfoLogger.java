package com.Incident.Management.incident_manager.config;




import java.sql.Connection;
import java.sql.DatabaseMetaData;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSourceInfoLogger implements CommandLineRunner {

    private final DataSource dataSource;

    public DataSourceInfoLogger(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("--------------------------------------------------");
            System.out.println(" 🔍 DATABASE CONNECTION USED BY SPRING BOOT");
            System.out.println("--------------------------------------------------");
            System.out.println("URL        : " + meta.getURL());
            System.out.println("User       : " + meta.getUserName());
            System.out.println("Driver     : " + meta.getDriverName());
            System.out.println("DB Product : " + meta.getDatabaseProductName());
            System.out.println("Version    : " + meta.getDatabaseProductVersion());
            System.out.println("--------------------------------------------------");
        }
    }
}

