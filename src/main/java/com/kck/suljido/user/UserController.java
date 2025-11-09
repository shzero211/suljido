package com.kck.suljido.user;

import com.kck.suljido.user.dto.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity registerUser(@RequestBody SignUpRequestDto requestDto){
        userService.registerUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
