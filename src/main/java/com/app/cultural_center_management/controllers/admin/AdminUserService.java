package com.app.cultural_center_management.controllers.admin;

import com.app.cultural_center_management.dto.ResponseData;
import com.app.cultural_center_management.dto.usersDto.GetUserDto;
import com.app.cultural_center_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminUserService {

    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<List<GetUserDto>> getAllUsers(@RequestParam(defaultValue = "1") int pageNumber,
                                                      @RequestParam(defaultValue = "5") int pageSize, @RequestParam String filter){
        Page<GetUserDto> resultPage = userService.getAllUsers(pageNumber - 1, pageSize, filter);
        return ResponseData
                .<List<GetUserDto>>builder()
                .data(resultPage.getContent())
                .fullContentSize(resultPage.getTotalElements())
                .build();
    }
}
