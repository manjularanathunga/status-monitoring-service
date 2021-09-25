package com.kry.monitor.service;

import com.kry.monitor.entity.RequestUser;
import com.kry.monitor.error.DataNotFoundException;

import java.util.List;

/**
 * <p>Title: RequestUserService.java</p>
 * <p>Description: Methods provided related to the user CRUD services</p>
 *
 * @author Manjula Ranathunga
 * @version 1.0
 */


public interface RequestUserService {

    List<RequestUser> fetchUsers() throws DataNotFoundException;

    RequestUser saveUser(RequestUser requestUser);

    RequestUser fetchByUserById(Long id) throws DataNotFoundException;

    void deleteUser(Long userId) throws DataNotFoundException;

    RequestUser updateUser(Long userId, RequestUser requestUser) throws DataNotFoundException;

    RequestUser fetchByUserByName(String userName);

    RequestUser fetchByUserByNameByIgnoreCase(String userName);
}
