package com.app.cultural_center_management.dto.affairsDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetEnrolledForAffairUser {
    private Long id;
    private String name;
    private String surname;
    private String avatarUrl;
    private String phoneNumber;
    private String email;
}
