package com.kry.monitor.controller;

import com.kry.monitor.entity.RequestUser;
import com.kry.monitor.error.DataNotFoundException;
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

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestUserController.class);

    @Autowired
    private RequestUserService requestUserService;

    @GetMapping("/users")
    public List<RequestUser> fetchUserServices() throws DataNotFoundException {
        return requestUserService.fetchUsers();
    }

    @PostMapping("/user")
    @ResponseBody
    public RequestUser saveUser(@Valid @RequestBody RequestUser requestUser) {
        LOGGER.info("Save ServiceTask");
        return requestUserService.saveUser(requestUser);
    }

    @GetMapping("/user/{id}")
    public RequestUser fetchByUserServiceById(@PathVariable Long id) throws DataNotFoundException {
        return requestUserService.fetchByUserById(id);
    }

    @DeleteMapping("/user/{id}")
    public String deleteUserService(@PathVariable("id") Long userId) {
        requestUserService.deleteUser(userId);
        return "ServiceTask Delete successfully";
    }

    @PutMapping("/user/{id}")
    public RequestUser updateUserService(@PathVariable("id") Long userId, @RequestBody RequestUser requestUser) throws DataNotFoundException {
        return requestUserService.updateUser(userId, requestUser);
    }

    @GetMapping("/user/name/{name}")
    public RequestUser fetchByUserServiceByName(@PathVariable("name") String userName) {
        return requestUserService.fetchByUserByName(userName);
    }

    @GetMapping("/user/casename/{name}")
    public RequestUser fetchByUserServiceByNameByIgnoreCase(@PathVariable("name") String userName) {
        return requestUserService.fetchByUserByNameByIgnoreCase(userName);
    }
}
