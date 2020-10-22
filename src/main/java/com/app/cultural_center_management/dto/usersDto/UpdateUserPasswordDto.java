package com.app.cultural_center_management.dto.usersDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserPasswordDto {
    @Size(min = 3, message = "Password should contain at least 3 letters")
    private String password;
    @Size(min = 3, message = "Password should contain at least 3 letters")
    private String repeatedPassword;
}
