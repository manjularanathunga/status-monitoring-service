package com.kry.monitor.rest;

import com.kry.monitor.StatusMonitoringServiceApplication;
import com.kry.monitor.entity.RequestInfo;
import com.kry.monitor.error.DataNotFoundException;
import org.asynchttpclient.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;


@Service
public class RestService {

    private final static Logger LOGGER = LoggerFactory.getLogger(StatusMonitoringServiceApplication.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AsyncHttpClient asyncHttpClient;

    @Async
    public void pushRequestAsync(List<RequestInfo> infoList) {
        try {
            for (RequestInfo req : infoList) {
                Request request = Dsl.get(req.getRequestUrl()).build();
                ListenableFuture<Response> listenableFuture = asyncHttpClient
                        .executeRequest(request);
                listenableFuture.addListener(() -> {
                    Response response = null;
                    try {
                        response = listenableFuture.get();
                        ServiceStatus serviceStatus = ServiceStatus.builder().
                                status(response.getResponseBody()).reqResponseTime(new Date()).build();
                        ServiceStatusCatch.setCatchStatus(String.valueOf(req.getServiceID()), serviceStatus);
                    } catch (InterruptedException | ExecutionException | DataNotFoundException e) {
                        ServiceStatus serviceStatus = ServiceStatus.builder().
                                status("FAIL").reqResponseTime(new Date()).build();
                        try {
                            ServiceStatusCatch.setCatchStatus(String.valueOf(req.getServiceID()), serviceStatus);
                        } catch (DataNotFoundException ex) {
                            LOGGER.info("Request not success with catch " + e.getMessage());
                        }
                        LOGGER.info("Request not success due to " + e.getMessage());
                    }
                }, Executors.newCachedThreadPool());
            }
        } catch (Exception e) {
            LOGGER.info("Request not success due to " + e.getMessage());
            e.printStackTrace();
        }
    }
}
