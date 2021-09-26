package com.kry.monitor.impl;

import com.kry.monitor.entity.RequestInfo;
import com.kry.monitor.error.DataNotFoundException;
import com.kry.monitor.repository.RequestInfoRepository;
import com.kry.monitor.rest.ServiceList;
import com.kry.monitor.rest.ServiceStatus;
import com.kry.monitor.service.RequestInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class RequestInfoServiceImpl implements RequestInfoService {

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestUserServiceImpl.class);

    @Autowired
    RequestInfoRepository requestInfoRepository;

    private static boolean IsMatch(String s) {
        try {
            Pattern patt = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
            Matcher matcher = patt.matcher(s);
            return matcher.matches();
        } catch (RuntimeException e) {
            return false;
        }
    }

    @Override
    public List<RequestInfo> fetchServices() {
        return requestInfoRepository.findAll();
    }

    @Override
    public List<RequestInfo> fetchServiceByStatus(boolean b) {
        return requestInfoRepository.findAll();
    }

    @Override
    public RequestInfo saveRequestInfo(RequestInfo requestInfo) {
        RequestInfo requestInfo1 = requestInfoRepository.save(requestInfo);
        if (requestInfo1 != null) {
            ServiceList.pushList(fetchServiceByStatus(true));
        }
        return requestInfo1;
    }

    @Override
    public RequestInfo updateStatusById(Long serviceId, String status) throws DataNotFoundException {
        LOGGER.info("Updating status of the service ....");
        Optional<RequestInfo> opsReq = requestInfoRepository.findById(serviceId);
        if (!opsReq.isPresent()) {
            throw new DataNotFoundException("Request record not available");
        }
        RequestInfo dbReqInfo = opsReq.get();
        dbReqInfo.setServiceStatus(status);
        dbReqInfo.setCreationTime(new Date());
        RequestInfo requestInfo = requestInfoRepository.saveAndFlush(dbReqInfo);
        ServiceList.pushList(fetchServiceByStatus(true));
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
            throw new DataNotFoundException("User not available");
        }
        RequestInfo service = opsReq.get();
        requestInfoRepository.delete(opsReq.get());
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
        reqInfoDb.setReqStatus(requestInfo.isReqStatus());
        reqInfoDb.setCreationTime(new Date());
        return requestInfoRepository.save(reqInfoDb);
    }

    @Override
    public RequestInfo fetchByRequestInfoByName(String serviceName) {
        return requestInfoRepository.findByServiceName(serviceName);
    }

    @Override
    public RequestInfo fetchByRequestInfoByNameByIgnoreCase(String serviceName) {
        return requestInfoRepository.findByServiceNameIgnoreCase(serviceName);
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

    @Override
    public void updateDatabase(Map<String, ServiceStatus> servicesMap) {
        servicesMap.forEach((serviceId, serviceStatus) -> {
            try {
                updateStatusById(Long.parseLong(serviceId), serviceStatus.getStatus());
                ServiceList.pushList(fetchServiceByStatus(true));
            } catch (DataNotFoundException e) {
                LOGGER.info("Unable to Update status of the service ...." + e.getMessage());
            }
        });
    }

}
