package com.kry.monitor.impl;

import com.kry.monitor.entity.RequestInfo;
import com.kry.monitor.error.DataNotFoundException;
import com.kry.monitor.repository.RequestInfoRepository;
import com.kry.monitor.rest.ServiceList;
import com.kry.monitor.rest.ServiceStatus;
import com.kry.monitor.rest.ServiceStatusCache;
import com.kry.monitor.service.RequestInfoService;
import org.apache.commons.validator.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class RequestInfoServiceImpl implements RequestInfoService {

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestUserServiceImpl.class);

    @Autowired
    RequestInfoRepository requestInfoRepository;

    @Override
    public List<RequestInfo> fetchServices() {
        return requestInfoRepository.findAll();
    }

    @Override
    public List<RequestInfo> fetchServiceByStatus(boolean b) {
        return requestInfoRepository.findAll();
    }

    @Override
    public RequestInfo saveRequestInfo(RequestInfo requestInfo) throws Exception {
        if(!IsMatch(requestInfo.getRequestUrl())){
            throw new Exception("Invalid url "+ requestInfo.getRequestUrl());
        }

        RequestInfo requestInfo1 = requestInfoRepository.save(requestInfo);
        if (requestInfo1 != null) {
            ServiceList.pushList(fetchServiceByStatus(true));
        }
        return requestInfo1;
    }

    @Override
    public RequestInfo updateStatusById(Long serviceId, ServiceStatus serviceStatus) throws DataNotFoundException {
        Optional<RequestInfo> opsReq = requestInfoRepository.findById(serviceId);
        if (!opsReq.isPresent()) {
            throw new DataNotFoundException("Request record not available");
        }
        RequestInfo dbReqInfo = opsReq.get();
        dbReqInfo.setServiceStatus(serviceStatus.getStatus());
        dbReqInfo.setCreationTime(serviceStatus.getUpdatedAt());
        RequestInfo requestInfo = requestInfoRepository.saveAndFlush(dbReqInfo);
        databaseSyncToCatch();
        return requestInfo;
    }

    @Override
    public RequestInfo fetchByRequestInfoById(Long serviceId) throws DataNotFoundException {
        Optional<RequestInfo> opsReq = requestInfoRepository.findById(serviceId);
        if (!opsReq.isPresent()) {
            throw new DataNotFoundException("User not available");
        }
        return opsReq.get();
    }

    @Override
    public void deleteRequestInfo(Long serviceId) throws DataNotFoundException {
        Optional<RequestInfo> opsReq = requestInfoRepository.findById(serviceId);
        if (!opsReq.isPresent()) {
            throw new DataNotFoundException("Service not available");
        }
        RequestInfo service = opsReq.get();
        requestInfoRepository.delete(opsReq.get());
        databaseSyncToCatch();
        LOGGER.info("Service [" + service.getServiceName() + "]removed from the system ....");
    }

    @Override
    public RequestInfo updateRequestInfo(Long serviceId, RequestInfo requestInfo) throws DataNotFoundException {
        Optional<RequestInfo> opsReq = requestInfoRepository.findById(serviceId);
        if (!opsReq.isPresent()) {
            throw new DataNotFoundException("User not available");
        }

        if (!IsMatch(requestInfo.getRequestUrl())) {
            throw new DataNotFoundException("Given URL is a not valid ...");
        }

        RequestInfo reqInfoDb = opsReq.get();
        reqInfoDb.setServiceName(requestInfo.getServiceName());
        reqInfoDb.setRequestUrl(requestInfo.getRequestUrl());
        reqInfoDb.setDescription(requestInfo.getDescription());
        reqInfoDb.setMonitorUserId(requestInfo.getMonitorUserId());
        reqInfoDb.setStatus(requestInfo.isStatus());
        reqInfoDb.setCreationTime(new Date());
        reqInfoDb = requestInfoRepository.save(reqInfoDb);
        databaseSyncToCatch();
        return reqInfoDb;
    }

    @Override
    public RequestInfo fetchByRequestInfoByName(String serviceName) {
        return requestInfoRepository.findByServiceName(serviceName);
    }

    @Override
    public List<RequestInfo> fetchDashboardServices() {
        return ServiceList.fetchList();
    }

    @Override
    public List<RequestInfo> fetchDashboardServiceByUserId(Long userId) {
        return ServiceList.fetchList().stream().filter(
                        p -> p.getMonitorUserId() == userId)
                .collect(Collectors.toList());
    }

    public void catchSyncToDatabase() {
        try {
            ServiceStatusCache.getAllServices().forEach((k, v) -> {
                try {
                    updateStatusById(Long.parseLong(v.getServiceID()), v);
                } catch (DataNotFoundException e) {
                    e.printStackTrace();
                }
            });

            LOGGER.info("catch Sync To Database successful...");
        } catch (Exception e) {
            LOGGER.info("Unable to Update status of the service ...." + e.getMessage());
        }
    }

    public void databaseSyncToCatch() {
        try {
            ServiceList.pushList(fetchServices());
        } catch (Exception e) {
            LOGGER.info("Unable to Update status of the service ...." + e.getMessage());
        }
        //LOGGER.info("Database Sync To Catch successful...");
    }

    public boolean urlValidator(String url){
        String[] schemes = {"http","https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        return urlValidator.isValid(url);
    }

    private static boolean IsMatch(String s) {
        try {
            Pattern patt = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
            Matcher matcher = patt.matcher(s);
            return matcher.matches();
        } catch (RuntimeException e) {
            return false;
        }
    }


}
