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

@Service
public class RequestInfoServiceImpl implements RequestInfoService {

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestUserServiceImpl.class);

    @Autowired
    RequestInfoRepository requestInfoRepository;

    @Override
    public List<RequestInfo> fetchServiceByStatus(boolean b) {
        return requestInfoRepository.findAllByReqStatus(true);
    }

    @Override
    public RequestInfo saveRequestInfo(RequestInfo requestInfo) {
        return null;
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
