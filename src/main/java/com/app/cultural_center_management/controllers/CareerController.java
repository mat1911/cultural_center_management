package com.app.cultural_center_management.controllers;

import com.app.cultural_center_management.dto.ResponseData;
import com.app.cultural_center_management.dto.careersDto.*;
import com.app.cultural_center_management.exceptions.InvalidFormData;
import com.app.cultural_center_management.service.CareerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/careers")
@CrossOrigin(origins = "http://localhost:4200")
public class CareerController {

    private final CareerService careerService;

    @GetMapping("/{jobOfferId}/applicants")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseData<List<GetAllApplicationsDto>> getApplications(@PathVariable Long jobOfferId, @RequestParam int pageSize,
                                                                     @RequestParam int pageNumber, @RequestParam String keyword){
        Page<GetAllApplicationsDto> resultPage = careerService
                .getAllApplications(jobOfferId, pageNumber - 1, pageSize, keyword);
        return ResponseData
                .<List<GetAllApplicationsDto>>builder()
                .data(resultPage.getContent())
                .fullContentSize(resultPage.getTotalElements())
                .build();
    }

    @GetMapping("/{jobOfferId}/applicants/{applicantId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public GetApplicationDto getApplicantById(@PathVariable Long jobOfferId, @PathVariable Long applicantId){
        return careerService.getApplication(jobOfferId, applicantId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Long createJobOffer(@Valid @ModelAttribute UpdateJobOfferDto updateJobOfferDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        return careerService.createJobOffer(updateJobOfferDto);
    }

    @PutMapping("/{jobOfferId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Long updateJobOffer(@PathVariable Long jobOfferId, @Valid @ModelAttribute UpdateJobOfferDto updateJobOfferDto,
                               BindingResult bindingResult){
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        return careerService.updateJobOffer(updateJobOfferDto, jobOfferId);
    }

    @DeleteMapping("/{jobOfferId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Long deleteJobOffer(@PathVariable Long jobOfferId){
        return careerService.deleteJobOfferById(jobOfferId);
    }

    @DeleteMapping("/{jobOfferId}/applicants/{applicantId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Long deleteApplicant(@PathVariable Long jobOfferId, @PathVariable Long applicantId){
        return careerService.deleteApplication(jobOfferId, applicantId);
    }

    @PostMapping("/{jobOfferId}/applicants")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Long createApplicant(@PathVariable Long jobOfferId, @RequestParam Long userId,
                                @Valid @ModelAttribute UpdateApplicationDto updateApplicationDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        return careerService.createApplication(updateApplicationDto, jobOfferId, userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<List<GetAllJobOffersDto>> getAll(@RequestParam int pageSize, @RequestParam int pageNumber,
                                                         @RequestParam(defaultValue = "") String keyword){
        Page<GetAllJobOffersDto> resultPage = careerService
                .getAllJobOffers(pageNumber - 1, pageSize, keyword);
        return ResponseData
                .<List<GetAllJobOffersDto>>builder()
                .data(resultPage.getContent())
                .fullContentSize(resultPage.getTotalElements())
                .build();
    }

    @GetMapping("/{jobOfferId}")
    @ResponseStatus(HttpStatus.OK)
    public GetJobOfferDto getJobOfferById(@PathVariable Long jobOfferId){
        return careerService.getJobOfferById(jobOfferId);
    }

}
