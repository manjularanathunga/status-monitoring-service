package com.kry.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@ComponentScan({"com.kry.monitor.service", "com.kry.monitor.repository"})
@SpringBootApplication
public class StatusMonitoringServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatusMonitoringServiceApplication.class, args);
    }

    @Bean(name = "postgresDS")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/MSMR");
        dataSource.setUsername("postgres");
        dataSource.setPassword("docker");
        return dataSource;
    }
}
