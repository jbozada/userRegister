package com.nisum.userregister.dtos.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nisum.userregister.entities.UserNisum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate created;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime modified;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonProperty("last_login")
    private LocalDateTime lastLogin;

    private String token;

    private Boolean isActive;

    public UserResponse getUserResponse(UserNisum user){

        return UserResponse.builder()
                .id(user.getUuid().toString())
                .created(user.getCreated())
                .modified(user.getModified())
                .lastLogin(user.getLastLogin())
                .token(user.getToken())
                .isActive(user.getIsActive())
                .build();
    }

}
