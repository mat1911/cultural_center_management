package com.app.cultural_center_management.controllers;

import com.app.cultural_center_management.dto.ResponseData;
import com.app.cultural_center_management.dto.articlesDto.GetArticleDto;
import com.app.cultural_center_management.service.ArticlesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
@CrossOrigin(origins = "http://localhost:4200")
public class ArticleController {

    private final ArticlesService articlesService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<List<GetArticleDto>> getAll(@RequestParam(defaultValue = "1") int pageNumber,
                                                    @RequestParam(defaultValue = "5") int pageSize,
                                                    @RequestParam String keyword){
        Page<GetArticleDto> resultPage = articlesService.getAllAcceptedArticles(pageNumber - 1, pageSize, keyword);
        return ResponseData
                .<List<GetArticleDto>>builder()
                .data(resultPage.getContent())
                .fullContentSize(resultPage.getTotalElements())
                .build();
    }

    @PatchMapping("/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    public Long updateAffairRate(@PathVariable Long articleId, @RequestParam Long userId,
                                 @RequestParam(defaultValue = "-1") Long articleRate){
        return articlesService.updateArticleRate(articleId, userId, articleRate);
    }
}
