package com.app.cultural_center_management.service;

import com.app.cultural_center_management.dto.securityDto.RegisterUserDto;
import com.app.cultural_center_management.mapper.Mappers;
import com.app.cultural_center_management.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SecurityService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Long register(RegisterUserDto registerUserDto) {

        // TODO walidacja

        if (userRepository.findByUsername(registerUserDto.getUsername()).isPresent()) {
            throw new SecurityException("Username already exists");
        }

        if (userRepository.findByUsername(registerUserDto.getEmail()).isPresent()) {
            throw new SecurityException("Email already exists");
        }

         var user = Mappers.fromRegisterUserToUser(registerUserDto);
         user.setPassword(passwordEncoder.encode(user.getPassword()));
         return userRepository
                 .save(user)
                 .getId();
    }
}
