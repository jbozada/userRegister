package com.nisum.userregister;

import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import com.nisum.userregister.utils.GenericException;
import com.nisum.userregister.dtos.request.PhoneRequest;
import com.nisum.userregister.dtos.request.UserRequest;
import com.nisum.userregister.entities.UserNisum;
import com.nisum.userregister.repositories.UserNisumRepository;
import com.nisum.userregister.services.UserRegisterService;
import com.nisum.userregister.utils.JwtUtil;
import com.nisum.userregister.dtos.response.UserResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
@TestPropertySource(properties = { "user.nisum.password.regex=^(?=.*\\\\d)(?=.*[a-zA-Z]).{7,}$" })
@TestPropertySource(properties = { "user.nisum.email.regex=^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$" })
public class UserRegisterServiceTest {
    
    
    @Autowired
    @MockBean
    UserNisumRepository userRepository;

    @Autowired
    UserRegisterService userService;

    @Test(expected = GenericException.class)
    public void addUserEmailError(){
        PhoneRequest phonesRequest = PhoneRequest.builder()
                .number("1234567")
                .cityCode("1")
                .countryCode("57")
                .build();

        UserRequest userRequest = UserRequest.builder()
                .name("Juan Rodriguez")
                .email("juan.rodriguez.org")
                .password("hunter2")
                .phones(List.of(phonesRequest))
                .build();

        userService.addUserRegister(userRequest);
    }

    @Test(expected = GenericException.class)
    public void addUserPasswordError(){
        PhoneRequest phonesRequest = PhoneRequest.builder()
                .number("1234567")
                .cityCode("1")
                .countryCode("57")
                .build();

        UserRequest userRequest = UserRequest.builder()
                .email("juan@rodriguezz.org")
                .name("name")
                .password("1234")
                .phones(List.of(phonesRequest))
                .build();

        userService.addUserRegister(userRequest);
    }

    @Test(expected = GenericException.class)
    public void addUserAlreadyExistException(){
        PhoneRequest phonesRequest = PhoneRequest.builder()
                .number("1234567")
                .cityCode("1")
                .countryCode("57")
                .build();

        UserRequest userRequest = UserRequest.builder()
                .name("Juan Rodriguez")
                .email("juan@rodriguez.org")
                .password("hunter2")
                .phones(List.of(phonesRequest))
                .build();

        Optional<UserNisum> user = Optional.ofNullable(userRequest.getUserEntity());

        Mockito.when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(user);

        userService.addUserRegister(userRequest);
    }
    
    @Test
    public void addUserSuccessfullyCreated(){
        PhoneRequest phoneRequest = PhoneRequest.builder()
                .number("1234567")
                .cityCode("1")
                .countryCode("57")
                .build();
        UserRequest userRequest = UserRequest.builder()
                .email("juan@rodriguezzz.org")
                .name("Juan Rodriguez")
                .password("hunter2")
                .phones(List.of(phoneRequest))
                .build();
        UserNisum user = userRequest.getUserEntity();
        UUID uuid = UUID.randomUUID();
        UserNisum savedUser = user;
        savedUser.setUuid(uuid);

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imp1YW5Acm9kcmlndWV6Lm9yZyIsImlhdCI6MTcxNTExNzc5NCwiZXhwIjoxNzE1MTE3ODU0fQ.ijC2aQGjHxkspE5wdKdPl8mw4LiQcrJ8vol9JbjMBlw";

        JwtUtil jwtUtil = Mockito.mock(JwtUtil.class);
        when(jwtUtil.generateToken(user)).thenReturn(token);

        user.setToken(token);
        savedUser.setToken(token);

        when(userRepository.save(Mockito.any())).thenReturn(savedUser);

        @SuppressWarnings("rawtypes")
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse().getUserResponse(savedUser));

        Assert.assertEquals( responseEntity, userService.addUserRegister(userRequest));

    }
    
    @Test(expected = GenericException.class)
    public void getAllUsersExpiredToken(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imp1YW5Acm9kcmlndWV6Lm9yZyIsImlhdCI6MTcxNTExNzc5NCwiZXhwIjoxNzE1MTE3ODU0fQ.ijC2aQGjHxkspE5wdKdPl8mw4LiQcrJ8vol9JbjMBlw";
        userService.getAllUsers(token);

    }
    
    @Test(expected = GenericException.class)
    public void getAllUsersInvalidToken(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imp1YW5Acm9kcmlndWV6Lm9yZyIsImlhdCI6MTcxNTExNzc5NCwiZXhwIjoxNzE1MTE3ODU0fQ.ijC2aQGjHxkspE5wdKdPl8mw4LiQcrJ8vol9JbjMBlw444";
        JwtUtil jwtUtil = Mockito.mock(JwtUtil.class);
        when(jwtUtil.isTokenExpired(token)).thenReturn(false);
        userService.getAllUsers(token);

    }
    
    @SuppressWarnings({ "rawtypes" })
    @Test
    public void getAllUsers(){
        PhoneRequest phoneRequest = PhoneRequest.builder()
                .number("1234567")
                .cityCode("1")
                .countryCode("57")
                .build();
        UserRequest userRequest = UserRequest.builder()
                .email("juan@rodriguezzz.org")
                .name("Juan Rodriguez")
                .password("hunter2")
                .phones(List.of(phoneRequest))
                .build();
        UserNisum user = userRequest.getUserEntity();
        String token = new JwtUtil().generateToken(user);
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(new ArrayList());
        Assert.assertEquals( responseEntity, userService.getAllUsers(token));
    }
}
