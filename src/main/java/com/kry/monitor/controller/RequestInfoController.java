package com.kry.monitor.controller;

import com.kry.monitor.entity.RequestInfo;
import com.kry.monitor.entity.RequestUser;
import com.kry.monitor.error.DataNotFoundException;
import com.kry.monitor.service.RequestInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<RequestInfo>> fetchDashboardServiceById() {
        return ResponseEntity.ok()
                .body(requestInfoService.fetchDashboardServices());
    }

    @GetMapping("/service/dashboard/{id}")
    public ResponseEntity<List<RequestInfo>> fetchDashboardServiceByUserId(@PathVariable("id") Long requestId) {
        List<RequestInfo> requestInfo = requestInfoService.fetchDashboardServiceByUserId(requestId);
        if (requestInfo == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(requestInfo);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(requestInfo);
        }
    }

    @GetMapping("/service")
    public ResponseEntity<List<RequestInfo>> fetchServices() throws DataNotFoundException {
        return ResponseEntity.ok()
                .body(requestInfoService.fetchServices());
    }

    @PostMapping("/service")
    @ResponseBody
    public ResponseEntity<RequestInfo> saveRequestInfo(@Valid @RequestBody RequestInfo requestInfo) throws DataNotFoundException {
        RequestInfo info;
        try{
            info = requestInfoService.saveRequestInfo(requestInfo);
            return ResponseEntity.status(201)
                    .body(info);
        }catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/service/{id}")
    public ResponseEntity<RequestInfo> fetchByRequestInfoServiceById(@PathVariable("id") Long serviceId) throws DataNotFoundException {
        RequestInfo requestInfo = requestInfoService.fetchByRequestInfoById(serviceId);
        if (requestInfo == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(requestInfo);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(requestInfo);
        }
    }

    @GetMapping("/servicedel/{id}")
    public ResponseEntity<String> deleteRequestInfoService(@PathVariable("id") Long serviceId) throws DataNotFoundException {
        requestInfoService.deleteRequestInfo(serviceId);
        return ResponseEntity.ok()
                .body("ServiceTask Delete successfully");
    }

    @PutMapping("/service/{id}")
    public ResponseEntity<RequestInfo> updateRequestInfoService(@PathVariable("id") Long serviceId, @RequestBody RequestInfo requestInfo) throws DataNotFoundException {
        RequestInfo requestInfo1 = requestInfoService.updateRequestInfo(serviceId, requestInfo);
        if (requestInfo == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(requestInfo1);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(requestInfo1);
        }
    }

    @GetMapping("/service/name/{name}")
    public ResponseEntity<RequestInfo> fetchByRequestInfoServiceByName(@PathVariable("name") String serviceName) {
        RequestInfo requestInfo1 = requestInfoService.fetchByRequestInfoByName(serviceName);
        if (requestInfo1 == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(requestInfo1);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(requestInfo1);
        }
    }
}