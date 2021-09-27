package com.kry.monitor.rest;

import com.kry.monitor.entity.RequestInfo;
import com.kry.monitor.error.DataNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceStatusCatch {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServiceStatusCatch.class);

    private static Map<String, ServiceStatus> catchStatus = new ConcurrentHashMap();

    public static ServiceStatus getCatchStatus(String keyId) {
        return catchStatus.get(keyId);
    }

    public static void setCatchStatus(String keyId, ServiceStatus values) throws DataNotFoundException {
        catchStatus.put("service_"+keyId.trim(), values);
    }

    public static void initCatch(List<RequestInfo> infoList) {
        clearCache();
        infoList.forEach((e) -> {
            catchStatus.put(e.getServiceID()+"_"+e.getServiceName(),ServiceStatus.builder()
                    .serviceID(e.getServiceID().toString())
                    .serviceName(e.getServiceName()).updatedAt(new Date()).status("FAIL").build());
        });
        LOGGER.info("ServiceStatusCatch{} : " + catchStatus);
    }

    public static Map<String, ServiceStatus> getAllServices() {
        return catchStatus;
    }

    public static int size() {
        return catchStatus.size();
    }

    public static String print() {
        return catchStatus.toString();
    }

    public static void clearCache() {
        catchStatus.clear();
    }
}
