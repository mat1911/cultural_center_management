package com.app.cultural_center_management.dto.usersDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMessageDto {
    @Email(message = "Email should be given in a appropriate format")
    private String userEmail;
    @NotNull(message = "Subject should be given")
    private String subject;
    @NotNull(message = "User message should be given")
    private String message;

}
