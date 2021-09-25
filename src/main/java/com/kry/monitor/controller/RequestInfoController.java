package com.kry.monitor.controller;

import com.kry.monitor.entity.RequestInfo;
import com.kry.monitor.error.DataNotFoundException;
import com.kry.monitor.service.RequestInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/")
public class RequestInfoController {

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestInfoController.class);

    @Autowired
    private RequestInfoService requestInfoService;

    @GetMapping("/service/dashboard")
    public List<RequestInfo> fetchDashboardServiceById() {
        return requestInfoService.fetchDashboardServices();
    }

    @GetMapping("/service/dashboard/{id}")
    public List<RequestInfo> fetchDashboardServiceByUserId(@PathVariable Long userId) {
        return requestInfoService.fetchDashboardServiceByUserId(userId);
    }

    @GetMapping("/service")
    public List<RequestInfo> fetchServices() throws DataNotFoundException {
        return requestInfoService.fetchServices();
    }

    @PostMapping("/service")
    @ResponseBody
    public RequestInfo saveRequestInfo(@Valid @RequestBody RequestInfo requestInfo) {
        LOGGER.info("Save ServiceTask");
        return requestInfoService.saveRequestInfo(requestInfo);
    }

    @GetMapping("/service/{id}")
    public RequestInfo fetchByRequestInfoServiceById(@PathVariable Long serviceId) throws DataNotFoundException {
        return requestInfoService.fetchByRequestInfoById(serviceId);
    }

    @DeleteMapping("/service/{id}")
    public String deleteRequestInfoService(@PathVariable("id") Long serviceId) throws DataNotFoundException {
        requestInfoService.deleteRequestInfo(serviceId);
        return "ServiceTask Delete successfully";
    }

    @PutMapping("/service/{id}")
    public RequestInfo updateRequestInfoService(@PathVariable("id") Long serviceId, @RequestBody RequestInfo requestInfo) throws DataNotFoundException {
        return requestInfoService.updateRequestInfo(serviceId, requestInfo);
    }

    @GetMapping("/service/name/{name}")
    public RequestInfo fetchByRequestInfoServiceByName(@PathVariable("name") String serviceName) {
        return requestInfoService.fetchByRequestInfoByName(serviceName);
    }

    @GetMapping("/service/casename/{name}")
    public RequestInfo fetchByRequestInfoServiceByNameByIgnoreCase(@PathVariable("name") String serviceName) {
        return requestInfoService.fetchByRequestInfoByNameByIgnoreCase(serviceName);
    }
}