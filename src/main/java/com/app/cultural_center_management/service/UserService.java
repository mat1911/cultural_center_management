package com.app.cultural_center_management.service;

import com.app.cultural_center_management.dto.usersDto.GetUserDto;
import com.app.cultural_center_management.dto.usersDto.UpdateUserPasswordDto;
import com.app.cultural_center_management.dto.usersDto.UpdateUserProfileDto;
import com.app.cultural_center_management.entity.User;
import com.app.cultural_center_management.exception.NotAllowedOperationException;
import com.app.cultural_center_management.exception.ObjectNotFoundException;
import com.app.cultural_center_management.mapper.UsersMapper;
import com.app.cultural_center_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<GetUserDto> getAllUsers(int pageNumber, int pageSize, String keyword){
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        Page<User> page;
        if(Objects.nonNull(keyword) && !keyword.isEmpty()){
            page = userRepository.findAllAndFiltered(pageRequest, keyword);
        }else {
            page = userRepository.findAllByOrderById(pageRequest);
        }

        List<GetUserDto> resultContent = UsersMapper.fromUserListToGetUserDtoList(page.getContent());
        return new PageImpl<>(resultContent, pageRequest, page.getTotalElements());
    }

    public GetUserDto getUserById(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User with given id does not exist!"));
        return UsersMapper.fromUserToGetUserDto(user);
    }

    public Long updateUserProfile(Long userId, UpdateUserProfileDto updateUserProfileDto){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User with given id does not exist!"));

        if(!user.getUsername().equals(updateUserProfileDto.getUsername())
                && userRepository.findByUsername(updateUserProfileDto.getUsername()).isPresent()){
            throw new NotAllowedOperationException("User with given username exists!");
        }

        user.setUsername(updateUserProfileDto.getUsername());
        user.setName(updateUserProfileDto.getName());
        user.setSurname(updateUserProfileDto.getSurname());
        user.setAge(updateUserProfileDto.getAge());
        user.setEmail(updateUserProfileDto.getEmail());
        user.setPhoneNumber(updateUserProfileDto.getPhoneNumber());
        return user.getId();
    }

    public Long updatePassword(Long userId, UpdateUserPasswordDto updateUserPasswordDto){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User with given id does not exist!"));

        if(!updateUserPasswordDto.getPassword().equals(updateUserPasswordDto.getRepeatedPassword())){
            throw new SecurityException("Passwords are not the same");
        }
        user.setPassword(passwordEncoder.encode(updateUserPasswordDto.getPassword()));
        return user.getId();
    }
}
