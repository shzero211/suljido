package com.kck.suljido.user.controller;

import com.kck.suljido.common.Result;
import com.kck.suljido.common.annotation.LoginUser;
import com.kck.suljido.user.dto.UserDto;
import com.kck.suljido.user.entity.User;
import com.kck.suljido.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/my-page")
    public ResponseEntity<UserDto.MyPageResponse> myPage(@LoginUser User user){
        UserDto.MyPageResponse response= userService.getMyPage(user.getId());
        return ResponseEntity.ok(response);
    }
}
