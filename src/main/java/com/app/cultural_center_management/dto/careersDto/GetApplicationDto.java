package com.app.cultural_center_management.dto.careersDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetApplicationDto {
    private Long userId;
    private String applicantName;
    private String applicantSurname;
    private String userComment;
    private LocalDate sinceDate;
    private String userFileUrl;
}
