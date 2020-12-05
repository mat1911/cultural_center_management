package com.app.cultural_center_management.controller;

import com.app.cultural_center_management.dto.ResponseData;
import com.app.cultural_center_management.dto.competitionsDto.*;
import com.app.cultural_center_management.exception.InvalidFormData;
import com.app.cultural_center_management.service.CompetitionsService;
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
@RequestMapping("/competitions")
@CrossOrigin(origins = "http://localhost:4200")
public class CompetitionController {

    private final CompetitionsService competitionsService;

    @GetMapping("/{competitionId}/contestants/not-accepted")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseData<List<GetCompetitionContestantsDto>> getNotAcceptedContestants(@PathVariable Long competitionId,
                                                                                      @RequestParam(defaultValue = "1") int pageNumber,
                                                                                      @RequestParam(defaultValue = "5") int pageSize){
        Page<GetCompetitionContestantsDto> resultPage = competitionsService
                .getNotAcceptedCompetitionContestants(competitionId, pageNumber - 1, pageSize);
        return ResponseData
                .<List<GetCompetitionContestantsDto>>builder()
                .data(resultPage.getContent())
                .fullContentSize(resultPage.getTotalElements())
                .build();
    }

    @PutMapping(value = "/{competitionId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Long updateCompetition(@PathVariable Long competitionId, @ModelAttribute
    @Valid UpdateCompetitionDto updateCompetitionDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        return competitionsService.updateCompetitionById(updateCompetitionDto, competitionId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public Long createCompetition(@Valid @ModelAttribute UpdateCompetitionDto updateCompetitionDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        return competitionsService.createCompetition(updateCompetitionDto);
    }

    @PatchMapping("/{competitionId}/contestants/{contestantId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Long changeContestantAcceptance(@PathVariable Long competitionId, @PathVariable Long contestantId,
                                           @RequestParam Boolean isAccepted){
        return competitionsService.changeContestantAcceptance(competitionId, contestantId, isAccepted);
    }

    @DeleteMapping(value = "/{competitionId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Long deleteCompetition(@PathVariable Long competitionId){
        return competitionsService.deleteCompetitionById(competitionId);
    }

    @DeleteMapping(value = "/{competitionId}/contestants/{contestantId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Long deleteContestant(@PathVariable Long competitionId, @PathVariable Long contestantId){
        return competitionsService.deleteContestant(competitionId, contestantId);
    }

    @PostMapping("/{competitionId}/contestants")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Long createContestant(@PathVariable Long competitionId, @RequestParam Long userId,
                                 @Valid @ModelAttribute UpdateContestantsDto updateContestantsDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        return competitionsService.createContestant(competitionId, userId, updateContestantsDto);
    }

    @PostMapping("/{competitionId}/contestants/{contestantId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Long voteForContestant(@PathVariable Long competitionId, @PathVariable Long contestantId,
                                 @RequestParam Long voterId){
        return competitionsService.voteForContestant(competitionId, contestantId, voterId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<List<GetAllCompetitionsDto>> getAll(@RequestParam(defaultValue = "1") int pageNumber,
                                                            @RequestParam(defaultValue = "5") int pageSize){
        Page<GetAllCompetitionsDto> resultPage = competitionsService.getAllCompetitions(pageNumber - 1, pageSize);
        return ResponseData
                .<List<GetAllCompetitionsDto>>builder()
                .data(resultPage.getContent())
                .fullContentSize(resultPage.getTotalElements())
                .build();
    }

    @GetMapping("/{competitionId}")
    @ResponseStatus(HttpStatus.OK)
    public GetCompetitionDto getCompetitionById(@PathVariable Long competitionId){
        return competitionsService.getCompetitionById(competitionId);
    }

    @GetMapping("/{competitionId}/contestants")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<List<GetCompetitionContestantsDto>> getCompetitionContestants(@PathVariable Long competitionId,
                                                                                      @RequestParam(defaultValue = "1") int pageNumber,
                                                                                      @RequestParam(defaultValue = "5") int pageSize,
                                                                                      @RequestParam(defaultValue = "") String keyword){
        Page<GetCompetitionContestantsDto> resultPage = competitionsService
                .getAcceptedCompetitionContestants(competitionId, pageNumber - 1, pageSize, keyword);
        return ResponseData
                .<List<GetCompetitionContestantsDto>>builder()
                .data(resultPage.getContent())
                .fullContentSize(resultPage.getTotalElements())
                .build();
    }

    @GetMapping("/{competitionId}/contestants/{contestantId}")
    @ResponseStatus(HttpStatus.OK)
    public GetContestantResultDto getContestantResult(@PathVariable Long competitionId, @PathVariable Long contestantId){
        return competitionsService.getContestantResult(competitionId, contestantId);
    }
}
