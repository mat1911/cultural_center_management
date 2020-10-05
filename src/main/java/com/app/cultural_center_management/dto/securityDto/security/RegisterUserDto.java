package com.app.cultural_center_management.dto.securityDto.security;

import com.app.cultural_center_management.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserDto {
    @Email(message = "Email must have appropriate format")
    private String email;
    @Size(min = 3, message = "Username should contain at least 3 letters")
    private String username;
    @Size(min = 3, message = "Username should contain at least 3 letters")
    private String password;
    private String repeatedPassword;
    @Size(min = 3, message = "Username should contain at least 3 letters")
    private String name;
    @Size(min = 3, message = "Username should contain at least 3 letters")
    private String surname;
    @Min(value = 13, message = "User must be at least 13 years old")
    private Integer age;
    @Pattern(regexp = "[0-9]{9}")
    private String phoneNumber;
    private Set<Role> roles;
}
