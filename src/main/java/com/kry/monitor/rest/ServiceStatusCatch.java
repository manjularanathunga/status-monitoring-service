package com.kry.monitor.rest;

import com.kry.monitor.entity.RequestInfo;
import com.kry.monitor.error.DataNotFoundException;
import com.kry.monitor.service.RequestInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceStatusCatch {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServiceStatusCatch.class);

    private static final Map<String, ServiceStatus> catchStatus = new HashMap();
    @Autowired
    RequestInfoService requestInfoService;

    public static ServiceStatus getCatchStatus(String keyId) {
        return catchStatus.get(keyId);
    }

    public static void setCatchStatus(String keyId, ServiceStatus values) throws DataNotFoundException {
        LOGGER.info("Calling setCatchStatus .....");
        catchStatus.put(keyId, values);
    }

    public static void initCatch(List<RequestInfo> infoList) {
        clearCache();
        infoList.forEach((e) -> {
            catchStatus.put(e.getServiceID().toString(), new ServiceStatus(e.getServiceStatus(), new Date()));
        });
        System.out.println("ServiceStatusCatch{} : " + catchStatus);
    }

    public static Map<String, ServiceStatus> getAllServices() {
        return catchStatus;
    }

    public static void clearCache() {
        catchStatus.clear();
    }
}
