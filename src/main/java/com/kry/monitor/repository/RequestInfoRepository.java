package com.kry.monitor.repository;

import com.kry.monitor.entity.RequestInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestInfoRepository extends JpaRepository<RequestInfo, Long> {

    @Override
    List<RequestInfo> findAll();

    List<RequestInfo> findAllByStatus(Boolean status);

    List<RequestInfo> findAllByMonitorUserId(Long monitorUserId);

    RequestInfo findByServiceName(String serviceName);

    RequestInfo findByServiceNameIgnoreCase(String serviceName);
}
