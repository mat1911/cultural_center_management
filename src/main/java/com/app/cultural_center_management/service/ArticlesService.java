package com.app.cultural_center_management.service;

import com.app.cultural_center_management.dto.articlesDto.GetAllArticlesDto;
import com.app.cultural_center_management.dto.articlesDto.GetArticleDto;
import com.app.cultural_center_management.dto.articlesDto.UpdateArticleDto;
import com.app.cultural_center_management.entities.*;
import com.app.cultural_center_management.exceptions.NotAllowedOperationException;
import com.app.cultural_center_management.exceptions.ObjectNotFoundException;
import com.app.cultural_center_management.mapper.ArticlesMapper;
import com.app.cultural_center_management.repositories.ArticleRatingRepository;
import com.app.cultural_center_management.repositories.ArticleRepository;
import com.app.cultural_center_management.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticlesService {

    private final String DEFAULT_PICTURE_URL = "https://www.dropbox.com/s/ru4od6mqj5alo34/default.jpg?raw=1";
    private final ArticleRepository articleRepository;
    private final ArticleRatingRepository articleRatingRepository;
    private final UserRepository userRepository;
    private final DropboxService dropboxService;

    public Long createArticle(UpdateArticleDto updateArticleDto, Long authorId){
        Article articleToCreate = ArticlesMapper.fromUpdateArticleDtoToArticle(updateArticleDto);

        articleToCreate.setSinceDate(LocalDate.now());
        articleToCreate.setPictureUrl(DEFAULT_PICTURE_URL);
        articleToCreate.setIsAccepted(false);
        articleToCreate.setAuthor(userRepository.findById(authorId)
                .orElseThrow(() -> new ObjectNotFoundException("Article with given id does not exist!")));

        if (Objects.nonNull(updateArticleDto.getPicture())){
            articleToCreate.setPictureUrl(dropboxService.uploadFile(updateArticleDto.getPicture()));
        }

        return articleRepository.save(articleToCreate).getId();
    }

    public Page<GetAllArticlesDto> getAllAcceptedArticles(int pageNumber, int pageSize, String keyword){
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Article> page;
        if(Objects.nonNull(keyword) && !keyword.isEmpty()){
            page = articleRepository.findAllFilteredAndAccepted(pageRequest, keyword);
        }else {
            page = articleRepository.findAllByIsAcceptedTrueOrderBySinceDateDesc(pageRequest);
        }
        List<GetAllArticlesDto> resultContent = ArticlesMapper.fromArticleListToGetAllArticlesDtoList(page.getContent());
        return new PageImpl<>(resultContent, pageRequest, page.getTotalElements());
    }

    public Page<GetAllArticlesDto> getAllNotAcceptedArticles(int pageNumber, int pageSize){
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Article> page = articleRepository.findAllByIsAcceptedFalseOrderBySinceDateDesc(pageRequest);
        List<GetAllArticlesDto> resultContent = ArticlesMapper.fromArticleListToGetAllArticlesDtoList(page.getContent());
        return new PageImpl<>(resultContent, pageRequest, page.getTotalElements());
    }

    public GetArticleDto getArticleById(Long articleId){
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ObjectNotFoundException("Article with given id does not exist!"));

        return ArticlesMapper.fromArticleToGetArticleDto(article);
    }

    public Long changeArticleAcceptance(Long articleId, Boolean isAccepted){
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ObjectNotFoundException("Article with given id does not exist!"));
        article.setIsAccepted(isAccepted);
        return article.getId();
    }

    public Long updateArticleRate(Long articleId, Long userId, Long articleRate) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ObjectNotFoundException("Article with given id does not exist!"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User with given id does not exist!"));

        if (articleRatingRepository.existsByArticle_IdAndUser_Id(articleId, userId)){
            throw new NotAllowedOperationException("User already voted!");
        }

        ArticleRating articleRating = ArticleRating.builder()
                .article(article)
                .user(user)
                .rating(articleRate)
                .build();

        return articleRatingRepository.save(articleRating).getId();
    }

    public Long updateArticle(UpdateArticleDto updateArticleDto, Long articleId){
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ObjectNotFoundException("Article with given id does not exist!"));

        article.setIsAccepted(false);
        article.setContent(updateArticleDto.getContent());
        article.setTitle(updateArticleDto.getTitle());

        if(Objects.nonNull(updateArticleDto.getPicture())){
            dropboxService.deleteFile(article.getPictureUrl());
            article.setPictureUrl(dropboxService.uploadFile(updateArticleDto.getPicture()));
        }

        return article.getId();
    }

    public Long deleteArticle(Long articleId, Long userId){
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ObjectNotFoundException("Article with given id does not exist!"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User with given id does not exist!"));

        if(!user.getRoles().contains(Role.ROLE_ADMIN) && !user.getId().equals(article.getAuthor().getId())){
            throw new NotAllowedOperationException("Article can be removed only by author or admin!");
        }
        user.getArticles().remove(article);
        articleRepository.delete(article);

        return article.getId();
    }

}
