package com.kry.monitor.service;

import com.kry.monitor.entity.RequestUser;
import com.kry.monitor.error.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RequestUserService {


    List<RequestUser> fetchUsers() throws DataNotFoundException;

    RequestUser saveUser(RequestUser requestUser);

    RequestUser fetchByUserById(Long id) throws DataNotFoundException;

    void deleteUser(Long userId);

    RequestUser updateUser(Long userId, RequestUser requestUser) throws DataNotFoundException;

    RequestUser fetchByUserByName(String userName);

    RequestUser fetchByUserByNameByIgnoreCase(String userName);
}
