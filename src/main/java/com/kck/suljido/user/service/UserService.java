package com.kck.suljido.user.service;

import com.kck.suljido.config.security.dto.CustomUserInfoDto;
import com.kck.suljido.config.security.util.JwtUtil;
import com.kck.suljido.user.dto.UserDto;
import com.kck.suljido.user.entity.User;
import com.kck.suljido.user.entity.enums.Role;
import com.kck.suljido.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {
    @Value("${jwt.valid_time}")
    private long validTime;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

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

    public UserDto.LoginResponseDto login(UserDto.LoginRequestDto requestDto) {
        User user=userRepository.findByEmail(requestDto.email()).orElseThrow(()->new IllegalArgumentException("가입되지 않은 이메일 입니다."));

        if(!passwordEncoder.matches(requestDto.password(),user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        CustomUserInfoDto customUserInfoDto=CustomUserInfoDto.builder()
                .email(user.getEmail())
                .id(user.getId())
                .role(user.getRole())
                .build();

        String accessToken= jwtUtil.createToken(customUserInfoDto);


        Date now=new Date();

        return UserDto.LoginResponseDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .expiredIn(new Date(now.getTime() + validTime).getTime())
                .build();
    }

    public UserDto.MyPageResponse getMyPage(Long id) {
        User user=userRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("[나의페이지] 유저가 존재하지않습니다."));
        return UserDto.MyPageResponse.from(user);
    }
}
