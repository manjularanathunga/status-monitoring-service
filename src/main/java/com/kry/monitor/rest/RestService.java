package com.kry.monitor.rest;

import com.kry.monitor.StatusMonitoringServiceApplication;
import com.kry.monitor.entity.RequestInfo;
import com.kry.monitor.error.DataNotFoundException;
import org.asynchttpclient.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${kry.default.response}")
    private String defaultResponse;

    @Value("${kry.default.condition}")
    private String defaultCondition;

    @Async
    public void pushRequestAsync(List<RequestInfo> infoList) {
        try {
            for (RequestInfo req : infoList) {
                String currentService = defaultResponse;
                String serviceKey = req.getServiceID()+"_"+req.getServiceName();

                Request request = Dsl.get(req.getRequestUrl()).build();
                ListenableFuture<Response> listenableFuture = asyncHttpClient
                        .executeRequest(request);
                listenableFuture.addListener(() -> {
                    ServiceStatus serviceStatus = new ServiceStatus();
                    try {
                        serviceStatus = ServiceStatusCache.getCatchStatus(serviceKey);
                        if(serviceStatus == null){
                            serviceStatus = ServiceStatus.builder().serviceID(req.getServiceID().toString()).serviceName(req.getServiceName()).updatedAt(new Date()).build();
                        }
                        LOGGER.debug("Service name results {} "+ serviceStatus.getServiceName() + " | " +listenableFuture.get().getResponseBody());
                        if(defaultCondition.contains(listenableFuture.get().getResponseBody())){
                            serviceStatus.setStatus(listenableFuture.get().getResponseBody());
                        }else{
                            serviceStatus.setStatus(currentService);
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        LOGGER.info("Request (e1) not success due to " + e.getMessage());
                        serviceStatus.setStatus(currentService);
                    }
                    try {
                        ServiceStatusCache.setCacheStatus(serviceKey,serviceStatus);
                    } catch (DataNotFoundException e) {
                        LOGGER.info("Request (e2) not success due to " + e.getMessage());
                    }
                }, Executors.newCachedThreadPool());
            }
            LOGGER.debug("ServiceStatusCache.print() "+ ServiceStatusCache.print());
        } catch (Exception e) {
            LOGGER.info("Request (e3) not success due to " + e.getMessage());
            e.printStackTrace();
        }
    }
}
