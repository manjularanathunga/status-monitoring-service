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
public class AuthResponse {
    private Long userID;
    private String userName;
    private String displayName;
    private Boolean userStatus;
    private Date dateCreated;
}
