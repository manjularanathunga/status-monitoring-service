package com.kry.monitor;

import com.kry.monitor.entity.RequestUser;
import com.kry.monitor.rest.RestService;
import com.kry.monitor.rest.ServiceList;
import com.kry.monitor.rest.ServiceStatusCache;
import com.kry.monitor.service.RequestInfoService;
import com.kry.monitor.service.RequestUserService;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.Dsl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.Date;


@SpringBootApplication
public class StatusMonitoringServiceApplication implements CommandLineRunner {

    private final static Logger LOGGER = LoggerFactory.getLogger(StatusMonitoringServiceApplication.class);

    @Autowired
    RequestInfoService requestInfoService;
    @Autowired
    private RestService restService;

    @Autowired
    RequestUserService requestUserService;


    public static void main(String[] args) {
        SpringApplication.run(StatusMonitoringServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Status Monitoring Service Application Started ...!");
        requestInfoService.databaseSyncToCatch();
        RequestUser adminUser = RequestUser.builder().userID(1L).userName("admin").password("123456").displayName("Admin User")
                .dateCreated(new Date()).userStatus(true).build();

        RequestUser standardUser = RequestUser.builder().userID(2L).userName("user").password("123456").displayName("Standerd User")
                .dateCreated(new Date()).userStatus(true).build();

        RequestUser adminUserDB = requestUserService.fetchByUserByName("admin");
        if(adminUserDB == null){
            requestUserService.saveUser(adminUser);
        }
        RequestUser standardUserDB = requestUserService.fetchByUserByName("user");
        if(standardUserDB == null){
            requestUserService.saveUser(standardUser);
        }
    }

    @Scheduled(fixedDelayString = "${schedule.http.fixed.delay}", initialDelayString = "${schedule.http.initial.delay}")
    public void schedulePushHttpRequest() throws InterruptedException {
        LOGGER.info("Calling schedule Push HttpRequest ..." + (new Date()));
        restService.pushRequestAsync(ServiceList.fetchList());
    }

    @Scheduled(fixedDelayString = "${schedule.dbsync.fixed.delay}", initialDelayString = "${schedule.dbsync.initial.delay}")
    public void catchSyncToDatabase() throws InterruptedException {
        LOGGER.info("Calling schedule DB Sync ..." + (new Date()));
        requestInfoService.catchSyncToDatabase();
    }

    @Bean
    public AsyncHttpClient asyncHttpClient() {
        DefaultAsyncHttpClientConfig.Builder clientBuilder = Dsl.config()
                .setConnectTimeout(500);
        return Dsl.asyncHttpClient(clientBuilder);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}


