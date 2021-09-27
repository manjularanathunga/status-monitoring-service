package com.kry.monitor.rest;

import com.kry.monitor.entity.RequestInfo;
import com.kry.monitor.error.DataNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceStatusCache {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServiceStatusCache.class);

    private static Map<String, ServiceStatus> cacheStatus = new ConcurrentHashMap();

    public static ServiceStatus getCatchStatus(String keyId) {
        return cacheStatus.get(keyId);
    }

    public static void setCacheStatus(String keyId, ServiceStatus values) throws DataNotFoundException {
        cacheStatus.put("service_"+keyId.trim(), values);
    }

    public static void initCache(List<RequestInfo> infoList) {
        clearCache();
        infoList.forEach((e) -> {
            cacheStatus.put(e.getServiceID()+"_"+e.getServiceName(),ServiceStatus.builder()
                    .serviceID(e.getServiceID().toString())
                    .serviceName(e.getServiceName()).updatedAt(new Date()).status("FAIL").build());
        });
        LOGGER.info("ServiceStatusCache{} : " + cacheStatus);
    }

    public static Map<String, ServiceStatus> getAllServices() {
        return cacheStatus;
    }

    public static int size() {
        return cacheStatus.size();
    }

    public static String print() {
        return cacheStatus.toString();
    }

    public static void clearCache() {
        cacheStatus.clear();
    }
}
