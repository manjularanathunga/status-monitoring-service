package com.kry.monitor.service;

import com.kry.monitor.entity.RequestUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RequestUserService {

    List<RequestUser> fetchUsers();

    RequestUser saveUser(RequestUser serviceTask);

    RequestUser fetchByUserServiceById(Long id);

    void deleteUserService(Long departmentId);

    RequestUser updateUserService(Long departmentId, RequestUser requestUser);

    RequestUser fetchByUserServiceByName(String departmentName);

    RequestUser fetchByUserServiceByNameByIgnoreCase(String departmentName);
}
