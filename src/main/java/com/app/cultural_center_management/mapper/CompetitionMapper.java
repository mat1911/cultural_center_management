package com.app.cultural_center_management.mapper;

import com.app.cultural_center_management.dto.competitionsDto.*;
import com.app.cultural_center_management.entities.Competition;
import com.app.cultural_center_management.entities.Contestant;

import java.util.List;
import java.util.stream.Collectors;

public interface CompetitionMapper {

    static List<GetAllCompetitionsDto> fromCompetitionListToGetAllCompetitionsDtoList(List<Competition> competitions){
        return competitions.stream()
                .map(competition -> GetAllCompetitionsDto.builder()
                        .id(competition.getId())
                        .title(competition.getTitle())
                        .sinceDate(competition.getSinceDate())
                        .pictureUrl(competition.getPictureUrl())
                        .build())
                .collect(Collectors.toList());
    }

    static GetCompetitionDto fromCompetitionToGetCompetitionDto(Competition competition){
        return GetCompetitionDto.builder()
                        .id(competition.getId())
                        .title(competition.getTitle())
                        .description(competition.getDescription())
                        .build();
    }

    static List<GetCompetitionContestantsDto> fromContestantListToGetCompetitionContestantsDtoList(List<Contestant> contestants){
        return contestants.stream()
                .map(contestant -> GetCompetitionContestantsDto.builder()
                        .userId(contestant.getUser().getId())
                        .userName(contestant.getUser().getName())
                        .userSurname(contestant.getUser().getSurname())
                        .userComment(contestant.getUserComment())
                        .resultUrl(contestant.getResultUrl())
                        .votesNumber(contestant.getVotesNumber())
                        .build())
                .collect(Collectors.toList());
    }

    static GetContestantResultDto fromContestantToGetContestantResultDto(Contestant contestant){
        return GetContestantResultDto.builder()
                .userName(contestant.getUser().getName())
                .userSurname(contestant.getUser().getSurname())
                .userComment(contestant.getUserComment())
                .resultUrl(contestant.getResultUrl())
                .build();
    }

    static Competition fromUpdateCompetitionDtoToCompetition(UpdateCompetitionDto updateCompetitionDto){
        return Competition.builder()
                .title(updateCompetitionDto.getTitle())
                .description(updateCompetitionDto.getDescription())
                .build();
    }
}
