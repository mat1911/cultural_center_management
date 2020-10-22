package com.app.cultural_center_management.dto.usersDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserProfileDto {
    @NotNull(message = "Email must be given")
    @Email(message = "Email must have appropriate format")
    private String email;
    @NotNull(message = "Username must be given")
    @Size(min = 3, message = "Username should contain at least 3 letters")
    private String username;
    @NotNull(message = "Name must be given")
    @Size(min = 3, message = "Username should contain at least 3 letters")
    private String name;
    @NotNull(message = "Surname must be given")
    @Size(min = 3, message = "Username should contain at least 3 letters")
    private String surname;
    @NotNull(message = "Age must be given")
    @Min(value = 13, message = "User must be at least 13 years old")
    private Integer age;
    @NotNull(message = "Phone number must be given")
    @Pattern(regexp = "[0-9]{9}")
    private String phoneNumber;
}
