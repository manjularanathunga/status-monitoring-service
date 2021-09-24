package com.kry.monitor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long serviceID;

    @NotBlank(message = "Please add the ServiceTask Name")
    @Length(max = 50, min = 0)
    @UniqueElements
    private String serviceName;
    private String requestUrl;
    @NotBlank(message = "Please add the ServiceTask Description")
    @Length(max = 250, min = 0)
    private String description;
    private String serviceStatus;
    private Date creationTime;
    private Long MonitorUserId;
}
