package com.kry.monitor.service;

import com.kry.monitor.entity.RequestInfo;
import com.kry.monitor.error.DataNotFoundException;
import com.kry.monitor.rest.ServiceStatus;

import java.util.List;
import java.util.Map;


public interface RequestInfoService {

    List<RequestInfo> fetchServiceByStatus(boolean b);

    RequestInfo saveRequestInfo(RequestInfo requestInfo);

    RequestInfo updateStatusById(Long serviceId, String serviceStatus) throws DataNotFoundException;

    void updateDatabase(Map<String, ServiceStatus> serviceList);
}

