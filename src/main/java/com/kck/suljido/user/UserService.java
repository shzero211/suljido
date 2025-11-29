package com.kck.suljido.user;

import com.kck.suljido.user.dto.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(SignUpRequestDto requestDto){
        String encodedPassword=passwordEncoder.encode(requestDto.password());
        User user =User.builder()
                .username(requestDto.username())
                .password(encodedPassword)
                .email(requestDto.email())
                .name(requestDto.name())
                .role(requestDto.role())
                .status(requestDto.status())
                .build();
        userRepository.save(user);
    }
}
