package com.app.cultural_center_management.controller;

import com.app.cultural_center_management.dto.ResponseData;
import com.app.cultural_center_management.dto.articlesDto.GetAllArticlesDto;
import com.app.cultural_center_management.dto.articlesDto.GetArticleDto;
import com.app.cultural_center_management.dto.articlesDto.UpdateArticleDto;
import com.app.cultural_center_management.exception.InvalidFormData;
import com.app.cultural_center_management.service.ArticlesService;
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
@RequestMapping("/articles")
@CrossOrigin(origins = "http://localhost:4200")
public class ArticleController {

    private final ArticlesService articlesService;

    @PatchMapping("/{articleId}/acceptance")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Long changeArticleAcceptance(@PathVariable Long articleId, @RequestParam Boolean isAccepted){
        return articlesService.changeArticleAcceptance(articleId, isAccepted);
    }

    @GetMapping("/not-accepted")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseData<List<GetAllArticlesDto>> getAllNotAccepted(@RequestParam(defaultValue = "1") int pageNumber,
                                                                   @RequestParam(defaultValue = "5") int pageSize){
        Page<GetAllArticlesDto> resultPage = articlesService.getAllNotAcceptedArticles(pageNumber - 1, pageSize);

        return ResponseData
                .<List<GetAllArticlesDto>>builder()
                .data(resultPage.getContent())
                .fullContentSize(resultPage.getTotalElements())
                .build();
    }

    @PatchMapping("/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Long updateArticleRate(@PathVariable Long articleId, @RequestParam Long userId,
                                 @RequestParam(defaultValue = "-1") Long articleRate){
        return articlesService.updateArticleRate(articleId, userId, articleRate);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Long createArticle(@RequestParam Long authorId, @Valid @ModelAttribute UpdateArticleDto updateArticleDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        return articlesService.createArticle(updateArticleDto, authorId);
    }

    @PutMapping("/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Long updateArticle(@PathVariable Long articleId, @Valid @ModelAttribute UpdateArticleDto updateArticleDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        return articlesService.updateArticle(updateArticleDto, articleId);
    }

    @DeleteMapping("/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Long deleteArticle(@PathVariable Long articleId, @RequestParam Long userId){
        return articlesService.deleteArticle(articleId, userId);
    }

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
    public GetArticleDto getArticle(@PathVariable Long articleId){
        return articlesService.getArticleById(articleId);
    }
}
