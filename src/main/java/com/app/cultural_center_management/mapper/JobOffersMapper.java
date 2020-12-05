package com.app.cultural_center_management.mapper;

import com.app.cultural_center_management.dto.careersDto.*;
import com.app.cultural_center_management.entity.Application;
import com.app.cultural_center_management.entity.JobOffer;

import java.util.List;
import java.util.stream.Collectors;

public interface JobOffersMapper {

    static List<GetAllJobOffersDto> fromJobOfferListToGetAllJobOffersDtoList(List<JobOffer> jobOffers) {
        return jobOffers.stream()
                .map(jobOffer -> GetAllJobOffersDto.builder()
                        .id(jobOffer.getId())
                        .name(jobOffer.getName())
                        .workingHours(jobOffer.getWorkingHours())
                        .sinceDate(jobOffer.getSinceDate())
                        .numberOfApplications(getNumberOfApplications(jobOffer))
                        .build())
                .collect(Collectors.toList());
    }

    static JobOffer fromUpdateJobOfferDtoToJobOffer(UpdateJobOfferDto updateJobOfferDto) {
        return JobOffer.builder()
                .name(updateJobOfferDto.getName())
                .description(updateJobOfferDto.getDescription())
                .workingHours(updateJobOfferDto.getWorkingHours())
                .build();
    }

    static GetJobOfferDto fromJobOfferToGetJobOfferDto(JobOffer jobOffer) {
        return GetJobOfferDto.builder()
                .id(jobOffer.getId())
                .name(jobOffer.getName())
                .description(jobOffer.getDescription())
                .sinceDate(jobOffer.getSinceDate())
                .workingHours(jobOffer.getWorkingHours())
                .build();
    }

    static List<GetAllApplicationsDto> fromApplicationListToGetAllApplicationsDto(List<Application> applications) {
        return applications.stream()
                .map(application -> GetAllApplicationsDto.builder()
                        .userId(application.getUser().getId())
                        .applicantName(application.getUser().getName())
                        .applicantSurname(application.getUser().getSurname())
                        .sinceDate(application.getSinceDate())
                        .build())
                .collect(Collectors.toList());
    }

    static GetApplicationDto fromApplicationToGetApplicationDto(Application application) {
        return GetApplicationDto.builder()
                .userId(application.getUser().getId())
                .applicantName(application.getUser().getName())
                .applicantSurname(application.getUser().getSurname())
                .sinceDate(application.getSinceDate())
                .userFileUrl(application.getUserFileUrl())
                .userComment(application.getUserComment())
                .build();
    }

    private static Long getNumberOfApplications(JobOffer jobOffer) {
        return jobOffer.getApplications() == null ? 0L : (long) jobOffer.getApplications().size();
    }
}
