package com.kry.monitor.rest;

import com.kry.monitor.entity.RequestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceList {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServiceList.class);

    private static List<RequestInfo> requestInfoListCatch;

    public ServiceList() {
        LOGGER.info("ServiceList Bean created");
        requestInfoListCatch = new ArrayList<>();
    }

    public static void add(RequestInfo requestInfo) {
        requestInfoListCatch.add(requestInfo);
    }

    public static List<RequestInfo> fetchList() {
        return requestInfoListCatch;
    }

    public static void pushList(List<RequestInfo> infoList) {
        requestInfoListCatch=infoList;
    }

    public static void clear(List<RequestInfo> infoList) {
        requestInfoListCatch = new ArrayList<>();
    }
}
