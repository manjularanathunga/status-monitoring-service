package com.kry.monitor.controller;

import com.kry.monitor.entity.RequestUser;
import com.kry.monitor.error.RecordNotFoundException;
import com.kry.monitor.service.RequestUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/")
public class RequestUserController {

    private final Logger LOGGER = LoggerFactory.getLogger(RequestUserController.class);

    @Autowired
    private RequestUserService requestUserService;

    @GetMapping("/users")
    public List<RequestUser> fetchUserServices() {
        return requestUserService.fetchUsers();
    }

    @PostMapping("/user")
    @ResponseBody
    public RequestUser saveUser(@Valid @RequestBody RequestUser requestUser) {
        LOGGER.info("Save ServiceTask");
        return requestUserService.saveUser(requestUser);
    }

    @GetMapping("/user/{id}")
    public RequestUser fetchByUserServiceById(@PathVariable Long id) throws RecordNotFoundException {
        return requestUserService.fetchByUserServiceById(id);
    }

    @DeleteMapping("/user/{id}")
    public String deleteUserService(@PathVariable("id") Long departmentId) {
        requestUserService.deleteUserService(departmentId);
        return "ServiceTask Delete successfully";
    }

    @PutMapping("/user/{id}")
    public RequestUser updateUserService(@PathVariable("id") Long departmentId, @RequestBody RequestUser requestUser) {
        return requestUserService.updateUserService(departmentId, requestUser);
    }

    @GetMapping("/user/name/{name}")
    public RequestUser fetchByUserServiceByName(@PathVariable("name") String departmentName) {
        return requestUserService.fetchByUserServiceByName(departmentName);
    }

    @GetMapping("/user/casename/{name}")
    public RequestUser fetchByUserServiceByNameByIgnoreCase(@PathVariable("name") String departmentName) {
        return requestUserService.fetchByUserServiceByNameByIgnoreCase(departmentName);
    }
}
