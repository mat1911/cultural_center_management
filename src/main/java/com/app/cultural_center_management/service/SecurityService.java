package com.app.cultural_center_management.service;

import com.app.cultural_center_management.dto.securityDto.RegisterUserDto;
import com.app.cultural_center_management.entity.Role;
import com.app.cultural_center_management.entity.User;
import com.app.cultural_center_management.exception.ObjectNotFoundException;
import com.app.cultural_center_management.mapper.UsersMapper;
import com.app.cultural_center_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class SecurityService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Long register(RegisterUserDto registerUserDto) {

        if (userRepository.findByUsername(registerUserDto.getUsername()).isPresent()) {
            throw new SecurityException("Username already exists");
        }

        if (userRepository.findByEmail(registerUserDto.getEmail()).isPresent()) {
            throw new SecurityException("Email already exists");
        }

        if(!registerUserDto.getPassword().equals(registerUserDto.getRepeatedPassword())){
            throw new SecurityException("Passwords are not the same");
        }

         var user = UsersMapper.fromRegisterUserToUser(registerUserDto);
         user.setPassword(passwordEncoder.encode(user.getPassword()));
         user.setIsEnabled(false);
         user.setRoles(Set.of(Role.ROLE_USER));
         return userRepository
                 .save(user)
                 .getId();
    }

    public Boolean activateAccount(Long userId){
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ObjectNotFoundException("user with given id does not exist!"));
        user.setIsEnabled(true);
        return user.getIsEnabled();
    }
}
