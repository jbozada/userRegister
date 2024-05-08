package com.nisum.userregister.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.nisum.userregister.dtos.request.UserRequest;
import com.nisum.userregister.services.UserRegisterService;

public interface  UserRegisterService {
    
    @SuppressWarnings("rawtypes")
    ResponseEntity addUserRegister(UserRequest newUserNisum);
    
    @SuppressWarnings("rawtypes")
    ResponseEntity getAllUsers(String token);

}
