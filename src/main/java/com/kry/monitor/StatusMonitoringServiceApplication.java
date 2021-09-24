package com.kry.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@ComponentScan({"com.kry.monitor.service", "com.kry.monitor.repository","com.kry.monitor.controller","com.kry.monitor.error"})
@SpringBootApplication
public class StatusMonitoringServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatusMonitoringServiceApplication.class, args);
    }

}
