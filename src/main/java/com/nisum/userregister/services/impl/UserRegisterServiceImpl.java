package com.nisum.userregister.services.impl;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nisum.userregister.dtos.request.UserRequest;
import com.nisum.userregister.dtos.response.UserResponse;
import com.nisum.userregister.entities.UserNisum;
import com.nisum.userregister.repositories.UserNisumRepository;
import com.nisum.userregister.services.UserRegisterService;
import com.nisum.userregister.utils.GenericException;
import com.nisum.userregister.utils.JwtUtil;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {
    
    private final Logger log = LoggerFactory.getLogger(UserRegisterService.class);

    @Autowired
    private UserNisumRepository userRepository;
    
    @Value("${user.nisum.email.regex}")
    private String emailRegex;

    @Value("${user.nisum.password.regex}")
    private String passwordRegex;

    @SuppressWarnings("rawtypes")
    @Override
    public ResponseEntity addUserRegister(UserRequest newUserNisum) {
        
        validations(newUserNisum);
        
        UserNisum user = newUserNisum.getUserEntity();
        
        user.setToken(new JwtUtil().generateToken(user));
        
        try {
            user = userRepository.save(user);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new GenericException("Error sql",
                                       HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse().getUserResponse(user));
    }
    
    public boolean isValid(String string, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(string)
                .matches();
    }

    public void validations(UserRequest newUserNisum) {
        if(userRepository.findByEmail(newUserNisum.getEmail()).isPresent()){
            log.error("El correo ya está registrado");
            throw new GenericException("El correo ya está registrado.",
                                       HttpStatus.BAD_REQUEST);
        }else if( !isValid(newUserNisum.getEmail(), emailRegex)) {
            log.error("El email no tiene el formato correcto, ejemplo: juan@rodriguez.org");
            throw new GenericException("El email no tiene el formato correcto, ejemplo: juan@rodriguez.org",
                                       HttpStatus.BAD_REQUEST);
        }else if( !isValid(newUserNisum.getPassword(), passwordRegex)){
            log.error("La password no cumple las medidas de seguridad requeridas.");
            throw new GenericException("La password no cumple las medidas de seguridad requeridas.",
                                       HttpStatus.BAD_REQUEST);
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResponseEntity getAllUsers(String token) {
        new JwtUtil().decodeToken(token);

        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }

}
