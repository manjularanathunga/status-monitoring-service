package com.kry.monitor.service;

import com.kry.monitor.entity.RequestUser;
import com.kry.monitor.error.DataNotFoundException;
import com.kry.monitor.repository.RequestUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RequestUserServiceImpl implements RequestUserService{

    private final Logger LOGGER = LoggerFactory.getLogger(RequestUserServiceImpl.class);

    @Autowired
    private RequestUserRepository requestUserRepository;

    @Override
    public List<RequestUser> fetchUsers() throws DataNotFoundException {
        LOGGER.info("Calling fetchUsers");
        List<RequestUser> usrList = requestUserRepository.findAll();
        if(usrList.isEmpty())
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
        if(!opsUsr.isPresent()){
            throw new DataNotFoundException("User not available");
        }
        return opsUsr.get();
    }

    @Override
    public void deleteUser(Long userId) {

    }

    @Override
    public RequestUser updateUser(Long userId, RequestUser requestUser) throws DataNotFoundException {
        Optional<RequestUser> opsUsr = requestUserRepository.findById(userId);
        if(!opsUsr.isPresent()){
            throw new DataNotFoundException("User not available");
        }
        RequestUser dbUser = opsUsr.get();

        if(!dbUser.getUserName().equalsIgnoreCase(requestUser.getUserName())){
            dbUser.setUserName(requestUser.getUserName());
        }

        if(!dbUser.getPassword().equalsIgnoreCase(requestUser.getPassword())){
            dbUser.setPassword(requestUser.getPassword());
        }

        if(!dbUser.getUserStatus().equalsIgnoreCase(requestUser.getUserStatus())){
            dbUser.setUserStatus(requestUser.getUserStatus());
        }

        return requestUserRepository.save(dbUser);
    }

    @Override
    public RequestUser fetchByUserByName(String userName) {
        return requestUserRepository.findByUserName(userName);
    }

    @Override
    public RequestUser fetchByUserByNameByIgnoreCase(String userName) {
        return requestUserRepository.findByUserNameIgnoreCase(userName);
    }
}
