package com.app.cultural_center_management.controllers.admin;

import com.app.cultural_center_management.dto.securityDto.news.UpdateNewsDto;
import com.app.cultural_center_management.exceptions.InvalidFormData;
import com.app.cultural_center_management.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/news")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminNewsController {

    private final NewsService newsService;

    @GetMapping("/{newsId}")
    @ResponseStatus(HttpStatus.OK)
    public UpdateNewsDto getNewsToUpdate(@PathVariable long newsId){
        return newsService.getNewsToUpdateByIdDto(newsId);
    }

    @PutMapping(value = "/{newsId}")
    @ResponseStatus(HttpStatus.OK)
    public Long updateNews(@PathVariable long newsId, @ModelAttribute("updateNewsDto")
        @Valid UpdateNewsDto updateNewsDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        return newsService.updateNewsById(updateNewsDto, newsId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createNews(@Valid @ModelAttribute UpdateNewsDto updateNewsDto){
        return newsService.createNews(updateNewsDto);
    }

    @DeleteMapping(value = "/{newsId}")
    @ResponseStatus(HttpStatus.OK)
    public Long deleteNews(@PathVariable long newsId){
        return newsService.deleteNewsById(newsId);
    }

}