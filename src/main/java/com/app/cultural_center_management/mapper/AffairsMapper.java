package com.app.cultural_center_management.mapper;
import com.app.cultural_center_management.dto.securityDto.affairs.GetAllAffairsDto;
import com.app.cultural_center_management.entities.Affair;

import java.util.List;
import java.util.stream.Collectors;

public interface AffairsMapper {

    static List<GetAllAffairsDto> fromAffairListToGetAllAffairsList(List<Affair> affairs) {

        return affairs
                .stream()
                .map(affair -> GetAllAffairsDto
                        .builder()
                        .id(affair.getId())
                        .description(affair.getDescription())
                        .pictureUrl(affair.getPictureUrl())
                        .rate(affair.getRate())
                        .sinceDate(affair.getSinceDate())
                        .title(affair.getTitle())
                        .build())
                .collect(Collectors.toList());
    }
}
