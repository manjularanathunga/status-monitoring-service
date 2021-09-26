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
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userID;
    @NotBlank(message = "Please add the ServiceTask Name")
    @Length(max = 50, min = 0, message = "Monitor User name maximum length 50")
    private String userName;
    private String password;
    private String displayName;
    private Boolean userStatus;
    private Date dateCreated;
}

