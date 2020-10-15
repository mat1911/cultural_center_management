package com.app.cultural_center_management.controllers.admin;

import com.app.cultural_center_management.dto.ResponseData;
import com.app.cultural_center_management.dto.competitionsDto.GetAllCompetitionsDto;
import com.app.cultural_center_management.dto.competitionsDto.GetCompetitionContestantsDto;
import com.app.cultural_center_management.dto.competitionsDto.UpdateCompetitionDto;
import com.app.cultural_center_management.exceptions.InvalidFormData;
import com.app.cultural_center_management.service.CompetitionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/competitions")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminCompetitionController {

    private final CompetitionsService competitionsService;

    @GetMapping("/{competitionId}/contestants")
    @ResponseStatus(HttpStatus.OK)
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
    public Long updateCompetition(@PathVariable Long competitionId, @ModelAttribute
    @Valid UpdateCompetitionDto updateCompetitionDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        return competitionsService.updateCompetitionById(updateCompetitionDto, competitionId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createNews(@Valid @ModelAttribute UpdateCompetitionDto updateCompetitionDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        return competitionsService.createCompetition(updateCompetitionDto);
    }

    @PatchMapping("/{competitionId}/contestants/{contestantId}")
    @ResponseStatus(HttpStatus.OK)
    public Long changeContestantAcceptance(@PathVariable Long competitionId, @PathVariable Long contestantId,
                                           @RequestParam Boolean isAccepted){
        return competitionsService.changeContestantAcceptance(competitionId, contestantId, isAccepted);
    }

    @DeleteMapping(value = "/{competitionId}")
    @ResponseStatus(HttpStatus.OK)
    public Long deleteNews(@PathVariable Long competitionId){
        return competitionsService.deleteCompetitionById(competitionId);
    }

    @DeleteMapping(value = "/{competitionId}/contestants/{contestantId}")
    @ResponseStatus(HttpStatus.OK)
    public Long deleteContestant(@PathVariable Long competitionId, @PathVariable Long contestantId){
        return competitionsService.deleteContestant(competitionId, contestantId);
    }
}
