package com.app.cultural_center_management.service;

import com.app.cultural_center_management.dto.newsDto.GetAllNewsDto;
import com.app.cultural_center_management.dto.newsDto.GetNewsDto;
import com.app.cultural_center_management.dto.newsDto.UpdateNewsDto;
import com.app.cultural_center_management.entities.News;
import com.app.cultural_center_management.exceptions.ObjectNotFoundException;
import com.app.cultural_center_management.mapper.NewsMapper;
import com.app.cultural_center_management.repositories.NewsRepository;
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
public class NewsService {

    private final String DEFAULT_PICTURE_URL = "https://www.dropbox.com/s/ru4od6mqj5alo34/default.jpg?raw=1";
    private final NewsRepository newsRepository;
    private final DropboxService dropboxService;

    public Page<GetAllNewsDto> getAllNews(int pageNumber, int pageSize){
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<News> page = newsRepository.findAllByOrderByDateOfAddDesc(pageRequest);
        List<GetAllNewsDto> resultContent = NewsMapper.fromNewsListToGetAllNewsDtoList(page.getContent());

        return new PageImpl<>(resultContent, pageRequest, page.getTotalElements());
    }

    public GetNewsDto getNewsById(long newsId){
        return NewsMapper.fromNewsToGetNewsDto(newsRepository.findById(newsId).orElseThrow(() ->
                new ObjectNotFoundException("News with given id " + newsId + " not found")));
    }

    public UpdateNewsDto getNewsToUpdateByIdDto(long newsId){
        return NewsMapper.fromNewsToUpdateNewsDto(newsRepository.findById(newsId).orElseThrow(() ->
                new ObjectNotFoundException("News with given id " + newsId + " not found")));
    }

    public Long updateNewsById(UpdateNewsDto updateNewsDto, long newsId){
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new ObjectNotFoundException("News with given id " + newsId + " not found"));

        news.setTitle(updateNewsDto.getTitle());
        news.setDescription(updateNewsDto.getDescription());
        news.setShortDescription(updateNewsDto.getShortDescription());
        news.setDateOfAdd(LocalDate.now());

        if (Objects.nonNull(updateNewsDto.getPicture())) {
            if(!news.getPictureUrl().equals(DEFAULT_PICTURE_URL)) {
                dropboxService.deleteFile(news.getPictureUrl());
            }
            news.setPictureUrl(dropboxService.uploadFile(updateNewsDto.getPicture()));
        }
        return news.getId();
    }

    public Long deleteNewsById(long newsId){
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new ObjectNotFoundException("News with given id " + newsId + " not found"));

        if (news.getPictureUrl().compareTo(DEFAULT_PICTURE_URL) != 0) {dropboxService.deleteFile(news.getPictureUrl());}
        newsRepository.delete(news);
        return newsId;
    }

    public Long createNews(UpdateNewsDto updateNewsDto){
        News newsToCreate = NewsMapper.fromUpdateNewsDtoToNews(updateNewsDto);
        newsToCreate.setDateOfAdd(LocalDate.now());
        newsToCreate.setPictureUrl(DEFAULT_PICTURE_URL);

        if (Objects.nonNull(updateNewsDto.getPicture())){
            newsToCreate.setPictureUrl(dropboxService.uploadFile(updateNewsDto.getPicture()));
        }
        return newsRepository.save(newsToCreate).getId();
    }

}
