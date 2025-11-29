package com.kck.suljido.user;

import com.kck.suljido.config.DatabaseConfig;
import com.kck.suljido.config.SecurityConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest(
        excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = DatabaseConfig.class
         )
)
@Import(SecurityConfig.class)
@ActiveProfiles("test")
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("User 저장 테스트 ")
    public void saveUser(){
        String encodedPassword=passwordEncoder.encode("test1234");
        User user =User.builder()
                .username("shzero211")
                .password(encodedPassword)
                .name("김상훈")
                .email("shzero211@naver.com")
                .status(User.Status.ACTIVE)
                .role(User.Role.USER)
                .build();
        userRepository.save(user);
        User findUser=userRepository.findByUsername(user.getUsername()).orElseThrow(()->new IllegalStateException());
        Assertions.assertThat(findUser.getEmail()).isEqualTo(user.getEmail());
    }

}
