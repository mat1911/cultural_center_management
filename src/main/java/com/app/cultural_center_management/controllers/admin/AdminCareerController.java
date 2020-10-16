package com.app.cultural_center_management.controllers.admin;
import com.app.cultural_center_management.dto.ResponseData;
import com.app.cultural_center_management.dto.careersDto.GetAllApplicationsDto;
import com.app.cultural_center_management.dto.careersDto.GetApplicationDto;
import com.app.cultural_center_management.dto.careersDto.UpdateJobOfferDto;
import com.app.cultural_center_management.exceptions.InvalidFormData;
import com.app.cultural_center_management.service.CareerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/careers")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminCareerController {

    private final CareerService careerService;

    @GetMapping("/{jobOfferId}/applicants")
    @ResponseStatus(HttpStatus.OK)
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
    @ResponseStatus(HttpStatus.OK)
    public GetApplicationDto getApplicantById(@PathVariable Long jobOfferId, @PathVariable Long applicantId){
        return careerService.getApplication(jobOfferId, applicantId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Long createJobOffer(@Valid @ModelAttribute UpdateJobOfferDto updateJobOfferDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        return careerService.createJobOffer(updateJobOfferDto);
    }

    @PutMapping("/{jobOfferId}")
    @ResponseStatus(HttpStatus.OK)
    public Long updateJobOffer(@PathVariable Long jobOfferId, @Valid @ModelAttribute UpdateJobOfferDto updateJobOfferDto,
                               BindingResult bindingResult){
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        return careerService.updateJobOffer(updateJobOfferDto, jobOfferId);
    }

    @DeleteMapping("/{jobOfferId}")
    @ResponseStatus(HttpStatus.OK)
    public Long deleteJobOffer(@PathVariable Long jobOfferId){
        return careerService.deleteJobOfferById(jobOfferId);
    }

    @DeleteMapping("/{jobOfferId}/applicants/{applicantId}")
    @ResponseStatus(HttpStatus.OK)
    public Long deleteJobOffer(@PathVariable Long jobOfferId, @PathVariable Long applicantId){
        return careerService.deleteApplication(jobOfferId, applicantId);
    }
}


