package com.app.cultural_center_management.mapper;

import com.app.cultural_center_management.dto.affairsDto.GetEnrolledForAffairUser;
import com.app.cultural_center_management.dto.securityDto.RegisterUserDto;
import com.app.cultural_center_management.dto.usersDto.GetUserDto;
import com.app.cultural_center_management.entity.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface UsersMapper {

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

    static Set<GetEnrolledForAffairUser> fromUserSetToGetEnrolledForAffairUserSet(Set<User> users) {
        return users.stream()
                .map(user -> GetEnrolledForAffairUser.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .surname(user.getSurname())
                        .email(user.getEmail())
                        .phoneNumber(user.getPhoneNumber())
                        .build())
                .collect(Collectors.toSet());
    }

    static List<GetUserDto> fromUserListToGetUserDtoList(List<User> users) {
        return users.stream()
                .map(user -> GetUserDto.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .surname(user.getSurname())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .phoneNumber(user.getPhoneNumber())
                        .age(user.getAge())
                        .build())
                .collect(Collectors.toList());
    }

    static GetUserDto fromUserToGetUserDto(User user){
        return GetUserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .username(user.getUsername())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .age(user.getAge())
                .build();
    }

}
