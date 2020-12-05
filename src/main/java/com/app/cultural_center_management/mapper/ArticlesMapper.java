package com.app.cultural_center_management.mapper;

import com.app.cultural_center_management.dto.articlesDto.GetAllArticlesDto;
import com.app.cultural_center_management.dto.articlesDto.GetArticleDto;
import com.app.cultural_center_management.dto.articlesDto.UpdateArticleDto;
import com.app.cultural_center_management.entity.Article;
import com.app.cultural_center_management.entity.ArticleRating;

import java.util.List;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;

public interface ArticlesMapper {

    static List<GetAllArticlesDto> fromArticleListToGetAllArticlesDtoList(List<Article> articles) {
        return articles.stream()
                .map(article -> GetAllArticlesDto.builder()
                        .id(article.getId())
                        .title(article.getTitle())
                        .pictureUrl(article.getPictureUrl())
                        .sinceDate(article.getSinceDate())
                        .rate(calculateAverageRate(article.getArticleRatings()))
                        .build())
                .collect(Collectors.toList());
    }

    static GetArticleDto fromArticleToGetArticleDto(Article article){
        return GetArticleDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .sinceDate(article.getSinceDate())
                .pictureUrl(article.getPictureUrl())
                .rate(calculateAverageRate(article.getArticleRatings()))
                .authorName(article.getAuthor().getName())
                .authorSurname(article.getAuthor().getSurname())
                .authorId(article.getAuthor().getId())
                .build();
    }

    static Article fromUpdateArticleDtoToArticle(UpdateArticleDto updateArticleDto){
        return Article.builder()
                .title(updateArticleDto.getTitle())
                .content(updateArticleDto.getContent())
                .build();
    }

    static private Double calculateAverageRate(Set<ArticleRating> articleRating) {
        OptionalDouble rate = articleRating.stream()
                .mapToDouble(ArticleRating::getRating)
                .average();
        return rate.isPresent() ? rate.getAsDouble() : 0.0;
    }
}
