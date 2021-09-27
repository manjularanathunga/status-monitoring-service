package com.kry.monitor.service;

import com.kry.monitor.entity.RequestInfo;
import com.kry.monitor.error.DataNotFoundException;
import com.kry.monitor.rest.ServiceStatus;

import java.util.List;
import java.util.Map;


public interface RequestInfoService {

    List<RequestInfo> fetchServices();

    List<RequestInfo> fetchServiceByStatus(boolean b);

    RequestInfo saveRequestInfo(RequestInfo requestInfo) throws Exception;

    RequestInfo updateStatusById(Long serviceId, ServiceStatus serviceStatus) throws DataNotFoundException;

    void catchSyncToDatabase();

    void databaseSyncToCatch();

    RequestInfo fetchByRequestInfoById(Long serviceId) throws DataNotFoundException;

    void deleteRequestInfo(Long serviceId) throws DataNotFoundException;

    RequestInfo updateRequestInfo(Long serviceId, RequestInfo requestInfo) throws DataNotFoundException;

    RequestInfo fetchByRequestInfoByName(String serviceName);

    List<RequestInfo> fetchDashboardServices();

    List<RequestInfo> fetchDashboardServiceByUserId(Long userId);
}

