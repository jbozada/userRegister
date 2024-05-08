package com.nisum.userregister.dtos.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;
import com.nisum.userregister.entities.UserNisum;
import com.nisum.userregister.entities.Phone;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    private String name;
    private String email;
    private String password;
    private List<PhoneRequest> phones;
    
    public UserNisum getUserEntity(){

        List<Phone> phonesEntity = this.getPhones().stream()
                .map(phone-> Phone.builder()
                        .number(phone.getNumber())
                        .cityCode(phone.getCityCode())
                        .countryCode(phone.getCountryCode())
                        .build())
                .collect(Collectors.toList());

        UserNisum userRegister = UserNisum.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .created(LocalDate.now())
                .modified(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .isActive(true)
                .phones(phonesEntity)
                .build();

        userRegister.getPhones().forEach(phone ->  phone.setUser(userRegister));

        return userRegister;
    }
}
