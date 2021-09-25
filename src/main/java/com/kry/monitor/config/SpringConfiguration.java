package com.kry.monitor.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>Title: SpringConfiguration.java</p>
 * <p>Description: Contains springboot configurations</p>
 * @author Manjula Ranathunga
 * @version 1.0
 */

@ConditionalOnProperty(name = "schedule.enabled", matchIfMissing = true)
@EnableAsync
@EnableScheduling
@ComponentScan({"com.kry.monitor"})
@Configuration
public class SpringConfiguration {

}