package com.app.cultural_center_management.controllers.admin;

import com.app.cultural_center_management.dto.affairsDto.GetEnrolledForAffairUser;
import com.app.cultural_center_management.dto.affairsDto.UpdateAffairDto;
import com.app.cultural_center_management.exceptions.InvalidFormData;
import com.app.cultural_center_management.service.AffairsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/affairs")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminAffairsController {

    private final AffairsService affairsService;

    @GetMapping(value = "/{affairId}")
    @ResponseStatus(HttpStatus.OK)
    public Set<GetEnrolledForAffairUser> getEnrolledForAffairUsers(@PathVariable Long affairId){
        return affairsService.getEnrolledForAffairUsers(affairId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createAffair(@RequestParam Long ownerId, @ModelAttribute("updateAffairDto")@Valid UpdateAffairDto updateAffairDto,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        return affairsService.createAffair(updateAffairDto, ownerId);
    }

    @PutMapping(value = "/{affairId}")
    @ResponseStatus(HttpStatus.OK)
    public Long updateAffair(@PathVariable Long affairId, @ModelAttribute("updateAffairDto")
    @Valid UpdateAffairDto updateAffairDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        return affairsService.updateAffair(updateAffairDto, affairId);
    }

    @DeleteMapping(value = "/{affairId}")
    @ResponseStatus(HttpStatus.OK)
    public Long deleteAffair(@PathVariable Long affairId){
        return affairsService.deleteAffair(affairId);
    }

    @DeleteMapping(value = "delist/{affairId}")
    @ResponseStatus(HttpStatus.OK)
    public Long delistUserFromAffair(@PathVariable Long affairId, @RequestParam Long userId){
        return affairsService.delistUserFromAffair(affairId, userId);
    }
}
