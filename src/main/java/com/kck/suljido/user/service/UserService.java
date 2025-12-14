package com.kck.suljido.user.service;

import com.kck.suljido.user.dto.UserDto;
import com.kck.suljido.user.entity.User;
import com.kck.suljido.user.entity.enums.Role;
import com.kck.suljido.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Long signUp(UserDto.UserSignUpRequest request){
        //중복 이메일 검증
        if(userRepository.existsByEmail(request.email())){
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword=passwordEncoder.encode(request.password());

        User user = User.builder()
                .email(request.email())
                .password(encodedPassword)
                .nickname(request.nickname())
                .role(Role.USER)
                .build();
        return userRepository.save(user).getId();
    }
}
