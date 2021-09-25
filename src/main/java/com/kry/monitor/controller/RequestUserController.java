package com.kry.monitor.controller;

import com.kry.monitor.entity.RequestUser;
import com.kry.monitor.error.DataNotFoundException;
import com.kry.monitor.service.RequestUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<RequestUser>> fetchUserServices() throws DataNotFoundException {
        return ResponseEntity.ok()
                .body(requestUserService.fetchUsers());
    }

    @PostMapping("/user")
    @ResponseBody
    public ResponseEntity<RequestUser> saveUser(@Valid @RequestBody RequestUser requestUser) {
        return ResponseEntity.ok()
                .body(requestUserService.saveUser(requestUser));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<RequestUser> fetchByUserServiceById(@PathVariable Long id) throws DataNotFoundException {
        return ResponseEntity.ok()
                .body(requestUserService.fetchByUserById(id));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUserService(@PathVariable("id") Long userId) throws DataNotFoundException {
        requestUserService.deleteUser(userId);
        return ResponseEntity.ok()
                .body("User has been removed successfully");
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<RequestUser> updateUserService(@PathVariable("id") Long userId, @RequestBody RequestUser requestUser) throws DataNotFoundException {
        return ResponseEntity.ok()
                .body(requestUserService.updateUser(userId, requestUser));
    }

    @GetMapping("/user/name/{name}")
    public ResponseEntity<RequestUser> fetchByUserServiceByName(@PathVariable("name") String userName) {
        return ResponseEntity.ok()
                .body(requestUserService.fetchByUserByName(userName));
    }

    @GetMapping("/user/casename/{name}")
    public ResponseEntity<RequestUser> fetchByUserServiceByNameByIgnoreCase(@PathVariable("name") String userName) {
        return ResponseEntity.ok()
                .body(requestUserService.fetchByUserByNameByIgnoreCase(userName));
    }
}
