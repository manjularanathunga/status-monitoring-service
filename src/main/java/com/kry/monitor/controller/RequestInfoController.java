package com.kry.monitor.controller;

import com.kry.monitor.entity.RequestInfo;
import com.kry.monitor.error.DataNotFoundException;
import com.kry.monitor.service.RequestInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<RequestInfo>> fetchDashboardServiceByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok()
                .body(requestInfoService.fetchDashboardServiceByUserId(userId));
    }

    @GetMapping("/service")
    public ResponseEntity<List<RequestInfo>> fetchServices() throws DataNotFoundException {
        return ResponseEntity.ok()
                .body(requestInfoService.fetchServices());
    }

    @PostMapping("/service")
    @ResponseBody
    public ResponseEntity<RequestInfo> saveRequestInfo(@Valid @RequestBody RequestInfo requestInfo) {
        return ResponseEntity.ok()
                .body(requestInfoService.saveRequestInfo(requestInfo));

    }

    @GetMapping("/service/{id}")
    public ResponseEntity<RequestInfo> fetchByRequestInfoServiceById(@PathVariable Long serviceId) throws DataNotFoundException {
        return ResponseEntity.ok()
                .body(requestInfoService.fetchByRequestInfoById(serviceId));
    }

    @DeleteMapping("/service/{id}")
    public ResponseEntity<String> deleteRequestInfoService(@PathVariable("id") Long serviceId) throws DataNotFoundException {
        requestInfoService.deleteRequestInfo(serviceId);
        return ResponseEntity.ok()
                .body("ServiceTask Delete successfully");
    }

    @PutMapping("/service/{id}")
    public ResponseEntity<RequestInfo> updateRequestInfoService(@PathVariable("id") Long serviceId, @RequestBody RequestInfo requestInfo) throws DataNotFoundException {
        return ResponseEntity.ok()
                .body(requestInfoService.updateRequestInfo(serviceId, requestInfo));
    }

    @GetMapping("/service/name/{name}")
    public ResponseEntity<RequestInfo> fetchByRequestInfoServiceByName(@PathVariable("name") String serviceName) {
        return ResponseEntity.ok()
                .body(requestInfoService.fetchByRequestInfoByName(serviceName));
    }

    @GetMapping("/service/casename/{name}")
    public ResponseEntity<RequestInfo> fetchByRequestInfoServiceByNameByIgnoreCase(@PathVariable("name") String serviceName) {
        return ResponseEntity.ok()
                .body(requestInfoService.fetchByRequestInfoByNameByIgnoreCase(serviceName));
    }
}