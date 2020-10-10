package com.app.cultural_center_management.mapper;

import com.app.cultural_center_management.dto.articlesDto.GetArticleDto;
import com.app.cultural_center_management.entities.Article;
import com.app.cultural_center_management.entities.ArticleRating;

import java.util.List;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;

public interface ArticlesMapper {

    static List<GetArticleDto> fromArticleListToGetArticleDtoList(List<Article> articles) {
        return articles.stream()
                .map(article -> GetArticleDto.builder()
                        .id(article.getId())
                        .content(article.getContent())
                        .title(article.getTitle())
                        .pictureUrl(article.getPictureUrl())
                        .sinceDate(article.getSinceDate())
                        .authorName(article.getAuthor().getName())
                        .authorSurname(article.getAuthor().getSurname())
                        .rate(calculateAverageRate(article.getArticleRatings()))
                        .build())
                .collect(Collectors.toList());
    }

    static private Double calculateAverageRate(Set<ArticleRating> articleRating) {
        OptionalDouble rate = articleRating.stream()
                .mapToDouble(ArticleRating::getRating)
                .average();
        return rate.isPresent() ? rate.getAsDouble() : 0.0;
    }
}
