package com.app.cultural_center_management.controllers;

import com.app.cultural_center_management.dto.securityDto.ResponseData;
import com.app.cultural_center_management.dto.securityDto.news.GetAllNewsDto;
import com.app.cultural_center_management.dto.securityDto.news.GetNewsDto;
import com.app.cultural_center_management.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
@CrossOrigin(origins = "http://localhost:4200")
public class NewsController {

    private final NewsService newsService;

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
