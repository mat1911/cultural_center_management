package com.app.cultural_center_management.service;

import com.app.cultural_center_management.dto.articlesDto.GetArticleDto;
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

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticlesService {

    private final ArticleRepository articleRepository;
    private final ArticleRatingRepository articleRatingRepository;
    private final UserRepository userRepository;

    public Page<GetArticleDto> getAllAcceptedArticles(int pageNumber, int pageSize, String keyword){
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Article> page;
        if(Objects.nonNull(keyword) && !keyword.isEmpty()){
            page = articleRepository.findAllFilteredAndAccepted(pageRequest, keyword);
        }else {
            page = articleRepository.findAllByOrderBySinceDateDesc(pageRequest);
        }
        List<GetArticleDto> resultContent = ArticlesMapper.fromArticleListToGetArticleDtoList(page.getContent());
        return new PageImpl<>(resultContent, pageRequest, page.getTotalElements());
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


}
