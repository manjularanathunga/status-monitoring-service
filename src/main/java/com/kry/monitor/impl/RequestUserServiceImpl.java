package com.kry.monitor.impl;

import com.kry.monitor.entity.RequestUser;
import com.kry.monitor.error.DataNotFoundException;
import com.kry.monitor.repository.RequestUserRepository;
import com.kry.monitor.rest.AuthRequest;
import com.kry.monitor.rest.AuthResponse;
import com.kry.monitor.service.RequestUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.util.List;
import java.util.Optional;

/**
 * <p>Title: RequestUserServiceImpl.java</p>
 * <p>Description: Methods provided related to the Request CRUD services</p>
 *
 * @author Manjula Ranathunga
 * @version 1.0
 */

@Service
public class RequestUserServiceImpl implements RequestUserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestUserServiceImpl.class);

    @Autowired
    private RequestUserRepository requestUserRepository;

    @Override
    public List<RequestUser> fetchUsers() throws DataNotFoundException {
        LOGGER.info("Calling fetchUsers");
        List<RequestUser> usrList = requestUserRepository.findAll();
        if (usrList.isEmpty())
            throw new DataNotFoundException("Records not available");

        return requestUserRepository.findAll();
    }

    @Override
    public RequestUser saveUser(RequestUser requestUser) {
        return requestUserRepository.save(requestUser);
    }

    @Override
    public RequestUser fetchByUserById(Long id) throws DataNotFoundException {
        Optional<RequestUser> opsUsr = requestUserRepository.findById(id);
        if (!opsUsr.isPresent()) {
            throw new DataNotFoundException("User not available");
        }
        return opsUsr.get();
    }

    @Override
    public void deleteUser(Long userId) throws DataNotFoundException {
        Optional<RequestUser> opsUsr = requestUserRepository.findById(userId);
        if (!opsUsr.isPresent()) {
            throw new DataNotFoundException("User not available");
        }
        RequestUser user = opsUsr.get();
        requestUserRepository.delete(opsUsr.get());
        LOGGER.info("User [" + user.getUserName() + "]removed from the system ....");
    }

    @Override
    public RequestUser updateUser(Long userId, RequestUser requestUser) throws DataNotFoundException, TransactionSystemException {
        Optional<RequestUser> opsUsr = requestUserRepository.findById(userId);
        if (!opsUsr.isPresent()) {
            throw new DataNotFoundException("User not available");
        }
        RequestUser dbUser = opsUsr.get();
        dbUser.setUserName(requestUser.getUserName());
        dbUser.setPassword(requestUser.getPassword());
        dbUser.setUserStatus(requestUser.getUserStatus());
        return requestUserRepository.save(dbUser);
    }

    @Override
    public RequestUser fetchByUserByName(String userName) {
        return requestUserRepository.findByUserName(userName);
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        RequestUser dbUsr = requestUserRepository.findByUserName(request.getUsername());
        if (dbUsr == null) return null;

        if (!dbUsr.getPassword().equalsIgnoreCase(request.getPassword())) {
            return null;
        }
        return AuthResponse.builder().displayName(dbUsr.getDisplayName())
                .userID(dbUsr.getUserID())
                .userName(dbUsr.getUserName()).build();
    }
}
