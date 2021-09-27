package com.kry.monitor.repository;

import com.kry.monitor.entity.RequestUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestUserRepository extends JpaRepository<RequestUser, Long> {

    RequestUser findByUserName(String userName);
}
