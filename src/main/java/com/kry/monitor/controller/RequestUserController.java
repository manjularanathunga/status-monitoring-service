package com.kry.monitor.controller;

import com.kry.monitor.entity.RequestUser;
import com.kry.monitor.error.DataNotFoundException;
import com.kry.monitor.rest.AuthRequest;
import com.kry.monitor.rest.AuthResponse;
import com.kry.monitor.service.RequestUserService;
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
        RequestUser user = requestUserService.saveUser(requestUser);
        if (user != null) {
            return ResponseEntity.status(201)
                    .body(user);
        }
        return ResponseEntity.status(401).
                header("Unable to create the user").build();

    }

    @GetMapping("/user/{id}")
    public ResponseEntity<RequestUser> fetchByUserServiceById(@PathVariable Long id) throws DataNotFoundException {
        RequestUser requestUser = requestUserService.fetchByUserById(id);
        if (requestUser == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(requestUser);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(requestUser);

        }
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
        RequestUser requestUser = requestUserService.fetchByUserByName(userName);
        if(requestUser == null ){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok()
                .body(requestUser);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        LOGGER.info("authenticate Name : {} " + request.getUsername());
        AuthResponse authResponse = requestUserService.authenticate(request);
        if (authResponse != null) {
            return ResponseEntity.status(200)
                    .body(authResponse);
        }
        return ResponseEntity.status(401).
                header("Logging fail").build();

    }
}


