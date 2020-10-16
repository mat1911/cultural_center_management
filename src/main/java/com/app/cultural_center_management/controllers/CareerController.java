package com.app.cultural_center_management.controllers;

import com.app.cultural_center_management.dto.ResponseData;
import com.app.cultural_center_management.dto.careersDto.GetAllJobOffersDto;
import com.app.cultural_center_management.dto.careersDto.GetJobOfferDto;
import com.app.cultural_center_management.dto.careersDto.UpdateApplicationDto;
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
@RequestMapping("/careers")
@CrossOrigin(origins = "http://localhost:4200")
public class CareerController {

    private final CareerService careerService;

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

    @PostMapping("/{jobOfferId}/applicants")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createApplicant(@PathVariable Long jobOfferId, @RequestParam Long userId,
                                @Valid @ModelAttribute UpdateApplicationDto updateApplicationDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        return careerService.createApplication(updateApplicationDto, jobOfferId, userId);
    }

}
