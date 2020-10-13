package com.app.cultural_center_management.controllers.admin;

import com.app.cultural_center_management.dto.ResponseData;
import com.app.cultural_center_management.dto.articlesDto.GetAllArticlesDto;
import com.app.cultural_center_management.service.ArticlesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/articles")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminArticlesController {

    private final ArticlesService articlesService;

    @PatchMapping("/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    public Long changeArticleAcceptance(@PathVariable Long articleId, @RequestParam Boolean isAccepted){
        return articlesService.changeArticleAcceptance(articleId, isAccepted);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<List<GetAllArticlesDto>> getAllNotAccepted(@RequestParam(defaultValue = "1") int pageNumber,
                                  @RequestParam(defaultValue = "5") int pageSize){
        Page<GetAllArticlesDto> resultPage = articlesService.getAllNotAcceptedArticles(pageNumber - 1, pageSize);

        return ResponseData
                .<List<GetAllArticlesDto>>builder()
                .data(resultPage.getContent())
                .fullContentSize(resultPage.getTotalElements())
                .build();
    }

}
