package com.kry.monitor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
    @Size(max = 20, min = 1, message = "{user.name.invalid}")
    @NotEmpty(message = "Please enter service name")
    private String serviceName;
    private String requestUrl;
    private String description;
    private String serviceStatus;
    private Date creationTime;
    private Long monitorUserId;
    private boolean status;
}
