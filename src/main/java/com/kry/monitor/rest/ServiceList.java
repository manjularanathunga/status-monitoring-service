package com.kry.monitor.rest;

import com.kry.monitor.entity.RequestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceList {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServiceList.class);

    private static List<RequestInfo> requestInfoListCatch = new ArrayList<>();

    public static void add(RequestInfo requestInfo) {
        requestInfoListCatch.add(requestInfo);
    }

    public static List<RequestInfo> fetchList() {
        return requestInfoListCatch;
    }

    public static void pushList(List<RequestInfo> infoList) {
        LOGGER.info("Service list has been updated...");
        requestInfoListCatch = infoList;
    }

    public static void clear(List<RequestInfo> infoList) {
        requestInfoListCatch = new ArrayList<>();
    }
}
