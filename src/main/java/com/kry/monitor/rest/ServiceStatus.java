package com.kry.monitor.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceStatus {
    private String serviceName;
    private String serviceID;
    private String status;
    private Date updatedAt;
}
