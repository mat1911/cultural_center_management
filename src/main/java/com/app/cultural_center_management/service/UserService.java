package com.app.cultural_center_management.service;

import com.app.cultural_center_management.dto.usersDto.GetUser;
import com.app.cultural_center_management.entities.User;
import com.app.cultural_center_management.mapper.UsersMapper;
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
public class UserService {

    private final UserRepository userRepository;

    public Page<GetUser> getAllUsers(int pageNumber, int pageSize, String keyword){
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        Page<User> page;
        if(Objects.nonNull(keyword) && !keyword.isEmpty()){
            page = userRepository.findAllAndFiltered(pageRequest, keyword);
        }else {
            System.out.println("TAK");
            page = userRepository.findAllByOrderById(pageRequest);
        }

        List<GetUser> resultContent = UsersMapper.fromUserListToGetUserList(page.getContent());
        return new PageImpl<>(resultContent, pageRequest, page.getTotalElements());
    }
}
