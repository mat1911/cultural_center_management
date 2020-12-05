package com.app.cultural_center_management.controller;

import com.app.cultural_center_management.dto.ResponseData;
import com.app.cultural_center_management.dto.usersDto.GetUserDto;
import com.app.cultural_center_management.dto.usersDto.UpdateUserPasswordDto;
import com.app.cultural_center_management.dto.usersDto.UpdateUserProfileDto;
import com.app.cultural_center_management.dto.usersDto.UserMessageDto;
import com.app.cultural_center_management.exception.InvalidFormData;
import com.app.cultural_center_management.service.EmailService;
import com.app.cultural_center_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseData<List<GetUserDto>> getAllUsers(@RequestParam(defaultValue = "1") int pageNumber,
                                                      @RequestParam(defaultValue = "5") int pageSize, @RequestParam String filter){
        Page<GetUserDto> resultPage = userService.getAllUsers(pageNumber - 1, pageSize, filter);
        return ResponseData
                .<List<GetUserDto>>builder()
                .data(resultPage.getContent())
                .fullContentSize(resultPage.getTotalElements())
                .build();
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public GetUserDto getUserById(@PathVariable Long userId){
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Long updateUserProfile(@PathVariable Long userId, @Valid @RequestBody UpdateUserProfileDto updateUserProfileDto,
                                  BindingResult bindingResult){
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        return userService.updateUserProfile(userId, updateUserProfileDto);
    }

    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
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
