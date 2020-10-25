package com.app.cultural_center_management.controllers;

import com.app.cultural_center_management.dto.ResponseData;
import com.app.cultural_center_management.dto.newsDto.GetAllNewsDto;
import com.app.cultural_center_management.dto.newsDto.GetNewsDto;
import com.app.cultural_center_management.dto.newsDto.UpdateNewsDto;
import com.app.cultural_center_management.exceptions.InvalidFormData;
import com.app.cultural_center_management.service.NewsService;
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
@RequestMapping("/news")
@CrossOrigin(origins = "http://localhost:4200")
public class NewsController {

    private final NewsService newsService;

    @GetMapping("edit/{newsId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public UpdateNewsDto getNewsToUpdate(@PathVariable long newsId){
        return newsService.getNewsToUpdateByIdDto(newsId);
    }

    @PutMapping(value = "/{newsId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Long updateNews(@PathVariable long newsId, @ModelAttribute("updateNewsDto")
    @Valid UpdateNewsDto updateNewsDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        return newsService.updateNewsById(updateNewsDto, newsId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public Long createNews(@Valid @ModelAttribute UpdateNewsDto updateNewsDto){
        return newsService.createNews(updateNewsDto);
    }

    @DeleteMapping(value = "/{newsId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Long deleteNews(@PathVariable long newsId){
        return newsService.deleteNewsById(newsId);
    }

    @GetMapping("/{newsId}")
    @ResponseStatus(HttpStatus.OK)
    public GetNewsDto getNewsById(@PathVariable long newsId){
        return newsService.getNewsById(newsId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<List<GetAllNewsDto>> getAll(@RequestParam(defaultValue = "1") int pageNumber,
                                      @RequestParam(defaultValue = "5") int pageSize){
        Page<GetAllNewsDto> resultPage = newsService.getAllNews(pageNumber - 1, pageSize);
        return ResponseData
                .<List<GetAllNewsDto>>builder()
                .data(resultPage.getContent())
                .fullContentSize(resultPage.getTotalElements())
                .build();
    }
}
