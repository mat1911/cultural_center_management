package com.app.cultural_center_management.controllers;

import com.app.cultural_center_management.dto.usersDto.GetUserDto;
import com.app.cultural_center_management.dto.usersDto.UpdateUserPasswordDto;
import com.app.cultural_center_management.dto.usersDto.UpdateUserProfileDto;
import com.app.cultural_center_management.dto.usersDto.UserMessageDto;
import com.app.cultural_center_management.exceptions.InvalidFormData;
import com.app.cultural_center_management.service.EmailService;
import com.app.cultural_center_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public GetUserDto getUserById(@PathVariable Long userId){
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Long updateUserProfile(@PathVariable Long userId, @Valid @RequestBody UpdateUserProfileDto updateUserProfileDto,
                                  BindingResult bindingResult){
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        return userService.updateUserProfile(userId, updateUserProfileDto);
    }

    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Long updateUserPassword(@PathVariable Long userId, @Valid @RequestBody UpdateUserPasswordDto updateUserPasswordDto,
                                  BindingResult bindingResult){
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        return userService.updatePassword(userId, updateUserPasswordDto);
    }

    @PostMapping("/messages")
    @ResponseStatus(HttpStatus.OK)
    public void sendMessage(@Valid @RequestBody UserMessageDto userMessageDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        emailService.sendUserMessage(userMessageDto);
    }
}
