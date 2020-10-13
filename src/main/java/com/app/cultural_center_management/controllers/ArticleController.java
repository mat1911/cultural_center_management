package com.app.cultural_center_management.controllers;

import com.app.cultural_center_management.dto.ResponseData;
import com.app.cultural_center_management.dto.articlesDto.GetAllArticlesDto;
import com.app.cultural_center_management.dto.articlesDto.GetArticleDto;
import com.app.cultural_center_management.dto.articlesDto.UpdateArticleDto;
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
    public ResponseData<List<GetAllArticlesDto>> getAll(@RequestParam(defaultValue = "1") int pageNumber,
                                                        @RequestParam(defaultValue = "5") int pageSize,
                                                        @RequestParam String keyword){
        Page<GetAllArticlesDto> resultPage = articlesService.getAllAcceptedArticles(pageNumber - 1, pageSize, keyword);
        return ResponseData
                .<List<GetAllArticlesDto>>builder()
                .data(resultPage.getContent())
                .fullContentSize(resultPage.getTotalElements())
                .build();
    }

    @GetMapping("/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    public GetArticleDto getAll(@PathVariable Long articleId){
        return articlesService.getArticleById(articleId);
    }

    @PatchMapping("/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    public Long updateAffairRate(@PathVariable Long articleId, @RequestParam Long userId,
                                 @RequestParam(defaultValue = "-1") Long articleRate){
        return articlesService.updateArticleRate(articleId, userId, articleRate);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createArticle(@RequestParam Long authorId, @ModelAttribute UpdateArticleDto updateArticleDto){
        return articlesService.createArticle(updateArticleDto, authorId);
    }

    @PutMapping("/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    public Long updateArticle(@PathVariable Long articleId, @ModelAttribute UpdateArticleDto updateArticleDto){
        return articlesService.updateArticle(updateArticleDto, articleId);
    }

    @DeleteMapping("/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    public Long deleteArticle(@PathVariable Long articleId, @RequestParam Long userId){
        return articlesService.deleteArticle(articleId, userId);
    }
}
