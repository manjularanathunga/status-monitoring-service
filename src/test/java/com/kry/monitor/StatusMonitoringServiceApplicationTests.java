package com.kry.monitor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan({"com.kry.monitor"})
@Configuration
@SpringBootTest
class StatusMonitoringServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
