package com.app.cultural_center_management.controllers.admin;

import com.app.cultural_center_management.dto.ResponseData;
import com.app.cultural_center_management.dto.usersDto.GetUser;
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
    public ResponseData<List<GetUser>> getAllUsers(@RequestParam(defaultValue = "1") int pageNumber,
                                     @RequestParam(defaultValue = "5") int pageSize, @RequestParam String filter){
        Page<GetUser> resultPage = userService.getAllUsers(pageNumber - 1, pageSize, filter);
        return ResponseData
                .<List<GetUser>>builder()
                .data(resultPage.getContent())
                .fullContentSize(resultPage.getTotalElements())
                .build();
    }
}
