package com.app.cultural_center_management.dto.competitionsDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCompetitionContestantsDto {
    private Long userId;
    private String userName;
    private String userSurname;
    private String userComment;
    private String resultUrl;
    private Long votesNumber;
}
