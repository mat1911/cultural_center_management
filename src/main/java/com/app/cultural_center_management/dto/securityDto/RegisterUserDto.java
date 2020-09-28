package com.app.cultural_center_management.dto.securityDto;

import com.app.cultural_center_management.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserDto {
    private String username;
    private String password;
    private String passwordConfirmation;
    private String name;
    private String surname;
    private Integer age;
    private String email;
    private Set<Role> roles;
}
