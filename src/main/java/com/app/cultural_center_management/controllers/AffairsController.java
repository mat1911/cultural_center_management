package com.app.cultural_center_management.controllers;

import com.app.cultural_center_management.dto.securityDto.ResponseData;
import com.app.cultural_center_management.dto.securityDto.affairs.GetAllAffairsDto;
import com.app.cultural_center_management.service.AffairsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/affairs")
@CrossOrigin(origins = "http://localhost:4200")
public class AffairsController {

    private final AffairsService affairsService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<List<GetAllAffairsDto>> getAll(@RequestParam(defaultValue = "1") int pageNumber,
                                             @RequestParam(defaultValue = "5") int pageSize){
        Page<GetAllAffairsDto> resultPage = affairsService.getAllAffairs(pageNumber - 1, pageSize);
        return ResponseData
                .<List<GetAllAffairsDto>>builder()
                .data(resultPage.getContent())
                .fullContentSize(resultPage.getTotalElements())
                .build();
    }

    @PatchMapping("/{affairId}")
    @ResponseStatus(HttpStatus.OK)
    public Double updateAffairRate(@PathVariable int affairId, @RequestBody int affairRate){
        return affairsService.updateAffairRate(affairId, affairRate);
    }
}
