package com.app.cultural_center_management.controller;

import com.app.cultural_center_management.dto.ResponseData;
import com.app.cultural_center_management.dto.affairsDto.*;
import com.app.cultural_center_management.exception.InvalidFormData;
import com.app.cultural_center_management.service.AffairsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/affairs")
@CrossOrigin(origins = "http://localhost:4200")
public class AffairsController {

    private final AffairsService affairsService;

    @GetMapping(value = "/{affairId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public GetAffairDto getAffairById(@PathVariable Long affairId) {
        return affairsService.getAffairById(affairId);
    }

    @DeleteMapping(value = "/{affairId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Long deleteAffair(@PathVariable Long affairId){
        return affairsService.deleteAffair(affairId);
    }

    @GetMapping(value = "/{affairId}/enrolled")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Set<GetEnrolledForAffairUser> getEnrolledForAffairUsers(@PathVariable Long affairId){
        return affairsService.getEnrolledForAffairUsers(affairId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createAffair(@RequestParam Long ownerId, @ModelAttribute("updateAffairDto")@Valid UpdateAffairDto updateAffairDto,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        return affairsService.createAffair(updateAffairDto, ownerId);
    }

    @PutMapping(value = "/{affairId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Long updateAffair(@PathVariable Long affairId, @ModelAttribute("updateAffairDto")
    @Valid UpdateAffairDto updateAffairDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        return affairsService.updateAffair(updateAffairDto, affairId);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public List<GetAllUserAffairs> getAllByUserId(@PathVariable Long userId) {
        return affairsService.getAllAffairsByUserId(userId);
    }

    @PatchMapping("/{affairId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public Long updateAffairRate(@PathVariable Long affairId, @RequestParam Long userId,
                                 @RequestParam(defaultValue = "-1") Long affairRate) {
        return affairsService.updateAffairRate(affairId, userId, affairRate);
    }

    @PatchMapping("enroll/{affairId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public Long enrollUsersToAffair(@PathVariable Long affairId, @RequestBody List<Long> usersId) {
        return affairsService.enrollUsersToAffair(affairId, usersId);
    }

    @DeleteMapping(value = "delist/{affairId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public Long delistUserFromAffair(@PathVariable Long affairId, @RequestParam Long userId){
        return affairsService.delistUserFromAffair(affairId, userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<List<GetAllAffairsDto>> getAll(@RequestParam(defaultValue = "1") int pageNumber,
                                                       @RequestParam(defaultValue = "5") int pageSize) {
        Page<GetAllAffairsDto> resultPage = affairsService.getAllAffairs(pageNumber - 1, pageSize);
        return ResponseData
                .<List<GetAllAffairsDto>>builder()
                .data(resultPage.getContent())
                .fullContentSize(resultPage.getTotalElements())
                .build();
    }
}
