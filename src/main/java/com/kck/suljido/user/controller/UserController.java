package com.kck.suljido.user.controller;

import com.kck.suljido.common.Result;
import com.kck.suljido.user.dto.UserDto;
import com.kck.suljido.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private UserService userService;
    public Result<HttpStatus> signUp(@RequestBody UserDto.UserSignUpRequest request){
        Long userId=userService.signUp(request);
        return new Result(0, HttpStatus.OK);
    }
}
