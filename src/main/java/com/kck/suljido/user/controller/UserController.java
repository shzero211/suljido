package com.kck.suljido.user.controller;

import com.kck.suljido.common.Result;
import com.kck.suljido.user.dto.UserDto;
import com.kck.suljido.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/sign-up")
    public Result<HttpStatus> signUp(@RequestBody UserDto.UserSignUpRequest request){
        Long userId=userService.signUp(request);
        return new Result(0, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto.LoginResponseDto> login(@RequestBody UserDto.LoginRequestDto requestDto){
        UserDto.LoginResponseDto responseDTO=userService.login(requestDto);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,"Bearer "+ responseDTO.accessToken())
                .body(responseDTO);
    }

}
