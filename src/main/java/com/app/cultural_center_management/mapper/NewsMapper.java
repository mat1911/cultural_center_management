package com.app.cultural_center_management.mapper;

import com.app.cultural_center_management.dto.securityDto.security.RegisterUserDto;
import com.app.cultural_center_management.dto.securityDto.news.GetAllNewsDto;
import com.app.cultural_center_management.dto.securityDto.news.GetNewsDto;
import com.app.cultural_center_management.dto.securityDto.news.UpdateNewsDto;
import com.app.cultural_center_management.entities.News;
import com.app.cultural_center_management.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public interface NewsMapper {

    static User fromRegisterUserToUser(RegisterUserDto registerUserDto) {
        return User
                .builder()
                .age(registerUserDto.getAge())
                .name(registerUserDto.getName())
                .surname(registerUserDto.getSurname())
                .phoneNumber(registerUserDto.getPhoneNumber())
                .username(registerUserDto.getUsername())
                .email(registerUserDto.getEmail())
                .password(registerUserDto.getPassword())
                .roles(registerUserDto.getRoles())
                .build();
    }

    static List<GetAllNewsDto> fromNewsListToGetAllNewsDtoList(List<News> newsList){
        return newsList
                .stream()
                .map(news -> GetAllNewsDto
                        .builder()
                        .id(news.getId())
                        .title(news.getTitle())
                        .shortDescription(news.getShortDescription())
                        .pictureUrl(news.getPictureUrl())
                        .dateOfAdd(news.getDateOfAdd())
                        .build())
                .collect(Collectors.toList());
    }

    static GetNewsDto fromNewsToGetNewsDto(News news){
        return GetNewsDto
                .builder()
                .title(news.getTitle())
                .description(news.getDescription())
                .dateOfAdd(news.getDateOfAdd())
                .pictureUrl(news.getPictureUrl())
                .build();
    }

    static UpdateNewsDto fromNewsToUpdateNewsDto(News news){
        return UpdateNewsDto
                .builder()
                .title(news.getTitle())
                .description(news.getDescription())
                .shortDescription(news.getShortDescription())
                .build();
    }

    static News fromUpdateNewsDtoToNews(UpdateNewsDto updateNewsDto){
        return News
                .builder()
                .title(updateNewsDto.getTitle())
                .description(updateNewsDto.getDescription())
                .shortDescription(updateNewsDto.getShortDescription())
                .build();
    }
}
