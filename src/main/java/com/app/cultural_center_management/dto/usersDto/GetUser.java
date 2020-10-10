package com.app.cultural_center_management.dto.usersDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUser {
    private Long id;
    private String username;
    private String name;
    private String surname;
    private Integer age;
    private String email;
    private String phoneNumber;
}
