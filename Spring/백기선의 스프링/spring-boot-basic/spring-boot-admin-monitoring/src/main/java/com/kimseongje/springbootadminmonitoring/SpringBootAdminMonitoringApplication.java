package com.kimseongje.springbootadminmonitoring;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class SpringBootAdminMonitoringApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAdminMonitoringApplication.class, args);
    }

}
