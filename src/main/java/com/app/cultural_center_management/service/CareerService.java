package com.app.cultural_center_management.service;

import com.app.cultural_center_management.dto.careersDto.*;
import com.app.cultural_center_management.entities.Application;
import com.app.cultural_center_management.entities.JobOffer;
import com.app.cultural_center_management.entities.User;
import com.app.cultural_center_management.exceptions.ObjectNotFoundException;
import com.app.cultural_center_management.mapper.JobOffersMapper;
import com.app.cultural_center_management.repositories.ApplicationRepository;
import com.app.cultural_center_management.repositories.JobOfferRepository;
import com.app.cultural_center_management.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CareerService {

    private final JobOfferRepository jobOfferRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final DropboxService dropboxService;

    public Long createJobOffer(UpdateJobOfferDto updateJobOfferDto){
        JobOffer jobOffer = JobOffersMapper.fromUpdateJobOfferDtoToJobOffer(updateJobOfferDto);
        jobOffer.setSinceDate(LocalDate.now());
        jobOfferRepository.save(jobOffer);
        return jobOffer.getId();
    }

    public Long createApplication(UpdateApplicationDto updateApplicationDto, Long jobOfferId, Long userId){
        JobOffer jobOffer = jobOfferRepository.findById(jobOfferId)
                .orElseThrow(() -> new ObjectNotFoundException("Job offer with given id does not exist!"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User with given id does not exist!"));
        Application applicationToCreate = new Application();
        applicationToCreate.setUserComment(updateApplicationDto.getUserComment());
        applicationToCreate.setSinceDate(LocalDate.now());
        applicationToCreate.setUser(user);
        applicationToCreate.setJobOffer(jobOffer);
        applicationToCreate.setUserFileUrl(dropboxService.uploadFile(updateApplicationDto.getUserFile()));
        return applicationRepository.save(applicationToCreate).getId();
    }

    public Long updateJobOffer(UpdateJobOfferDto updateJobOfferDto, Long jobOfferId){
        JobOffer jobOffer = jobOfferRepository.findById(jobOfferId)
                .orElseThrow(() -> new ObjectNotFoundException("Job offer with given id does not exist!"));
        jobOffer.setName(updateJobOfferDto.getName());
        jobOffer.setDescription(updateJobOfferDto.getDescription());
        jobOffer.setWorkingHours(updateJobOfferDto.getWorkingHours());
        return jobOffer.getId();
    }

    public Page<GetAllJobOffersDto> getAllJobOffers(int pageNumber, int pageSize, String keyword){
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<JobOffer> page;
        if(Objects.nonNull(keyword) && !keyword.isEmpty()){
            page = jobOfferRepository.findAllFilteredAndAccepted(pageRequest, keyword);
        }else {
            page = jobOfferRepository.findAllByOrderBySinceDateDesc(pageRequest);
        }
        List<GetAllJobOffersDto> resultContent = JobOffersMapper.fromJobOfferListToGetAllJobOffersDtoList(page.getContent());
        return new PageImpl<>(resultContent, pageRequest, page.getTotalElements());
    }

    public Page<GetAllApplicationsDto> getAllApplications(Long jobOfferId, int pageNumber, int pageSize, String keyword){
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Application> page;
        if(Objects.nonNull(keyword) && !keyword.isEmpty()){
            page = applicationRepository.findAllFilteredByJobOffer_Id(pageRequest, jobOfferId, keyword);
        }else {
            page = applicationRepository.findAllByJobOffer_Id(pageRequest, jobOfferId);
        }
        List<GetAllApplicationsDto> resultContent = JobOffersMapper.fromApplicationListToGetAllApplicationsDto(page.getContent());
        return new PageImpl<>(resultContent, pageRequest, page.getTotalElements());
    }

    public GetApplicationDto getApplication(Long jobOfferId, Long applicantId){
        Application application = applicationRepository.findByUser_IdAndJobOffer_Id(applicantId, jobOfferId)
                .orElseThrow(() -> new ObjectNotFoundException("Application with given ids does not exist!"));
        return JobOffersMapper.fromApplicationToGetApplicationDto(application);
    }

    public GetJobOfferDto getJobOfferById(Long jobOfferId){
        JobOffer jobOffer = jobOfferRepository.findById(jobOfferId)
                .orElseThrow(() -> new ObjectNotFoundException("Job offer with given id does not exist!"));
        return JobOffersMapper.fromJobOfferToGetJobOfferDto(jobOffer);
    }

    public Long deleteJobOfferById(Long jobOfferId){
        JobOffer jobOffer = jobOfferRepository.findById(jobOfferId)
                .orElseThrow(() -> new ObjectNotFoundException("Job offer with given id does not exist!"));
        for(Application application: jobOffer.getApplications()){
            deleteApplication(jobOfferId, application.getUser().getId());
        }
        jobOfferRepository.delete(jobOffer);
        return jobOffer.getId();
    }

    public Long deleteApplication(Long jobOfferId, Long applicantId){
        Application application = applicationRepository.findByUser_IdAndJobOffer_Id(applicantId, jobOfferId)
                .orElseThrow(() -> new ObjectNotFoundException("Application with given ids does not exist!"));
        dropboxService.deleteFile(application.getUserFileUrl());
        applicationRepository.delete(application);
        return application.getId();
    }

}
