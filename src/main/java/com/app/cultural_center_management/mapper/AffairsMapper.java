package com.app.cultural_center_management.mapper;
import com.app.cultural_center_management.dto.affairsDto.GetAffairDto;
import com.app.cultural_center_management.dto.affairsDto.GetAllAffairsDto;
import com.app.cultural_center_management.dto.affairsDto.GetAllUserAffairs;
import com.app.cultural_center_management.dto.affairsDto.UpdateAffairDto;
import com.app.cultural_center_management.entities.Affair;
import com.app.cultural_center_management.entities.AffairRating;

import java.util.List;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;

public interface AffairsMapper {

    static List<GetAllAffairsDto> fromAffairListToGetAllAffairsList(List<Affair> affairs) {

        return affairs
                .stream()
                .map(affair -> GetAllAffairsDto
                        .builder()
                        .id(affair.getId())
                        .description(affair.getDescription())
                        .shortDescription(affair.getShortDescription())
                        .pictureUrl(affair.getPictureUrl())
                        .sinceDate(affair.getSinceDate())
                        .title(affair.getTitle())
                        .availableSeats(affair.getAvailableSeats())
                        .rate(calculateAverageRate(affair.getAffairRatings()))
                        .build())
                .collect(Collectors.toList());
    }

    static List<GetAllUserAffairs> fromAffairsListToGetAllUserAffairs(Set<Affair> affairs){
        return affairs
                .stream()
                .map(affair -> GetAllUserAffairs
                        .builder()
                        .id(affair.getId())
                        .description(affair.getDescription())
                        .shortDescription(affair.getShortDescription())
                        .pictureUrl(affair.getPictureUrl())
                        .title(affair.getTitle())
                        .build())
                .collect(Collectors.toList());
    }

    static GetAffairDto fromAffairToGetAffairDto(Affair affair){
        return GetAffairDto.builder()
                .title(affair.getTitle())
                .availableSeats(affair.getAvailableSeats())
                .shortDescription(affair.getShortDescription())
                .ownerName(affair.getOwner().getName())
                .ownerSurname(affair.getOwner().getSurname())
                .build();
    }

    static Affair fromUpdateAffairDtoToAffair(UpdateAffairDto updateAffairDto){
        return Affair.builder()
                .availableSeats(updateAffairDto.getAvailableSeats())
                .shortDescription(updateAffairDto.getShortDescription())
                .title(updateAffairDto.getTitle())
                .build();
    }

    static private Double calculateAverageRate(Set<AffairRating> affairRating){
        OptionalDouble rate = affairRating.stream()
                .mapToDouble(AffairRating::getRating)
                .average();
        return rate.isPresent() ? rate.getAsDouble() : 0.0;
    }
}
