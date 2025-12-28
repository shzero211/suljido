package com.kck.suljido.config.security;

import com.kck.suljido.config.security.filter.JwtAuthFilter;
import com.kck.suljido.config.security.filter.TestMockFilter;
import com.kck.suljido.config.security.util.JwtUtil;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Optional; // ★ 이게 꼭 있어야 해!

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // Optional로 감싸서, 빈이 없을 때(prod 환경 등) 에러 안 나게 처리
    private final Optional<TestMockFilter> testMockFilter;
    private final JwtUtil jwtUtil;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                // ★ [중요] CORS 설정을 Security에도 알려줘야 함!
                .cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers->headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->auth.requestMatchers("/api/users/**").permitAll().anyRequest().authenticated())
                .addFilterBefore(new JwtAuthFilter(jwtUtil),UsernamePasswordAuthenticationFilter.class);

        // 필터가 존재할 때만(Test 환경일 때만) 체인에 추가
//        testMockFilter.ifPresent(filter ->
//                http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
//        );

        return http.build();
    }

    // ★ [중요] CORS 허용 규칙 정의 (이게 있어야 403 안 뜸)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 프론트엔드 주소들 (배포된 주소 + 로컬 주소)
        configuration.addAllowedOriginPattern("*"); // 모든 출처 허용 (보안상 특정 도메인만 넣는 게 좋지만 일단 해결을 위해)
        configuration.addAllowedMethod("*");        // GET, POST, PUT, DELETE 등 모두 허용
        configuration.addAllowedHeader("*");        // 모든 헤더 허용
        configuration.setAllowCredentials(true);    // 쿠키, 인증정보 포함 허용
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public FilterRegistrationBean<TestMockFilter> disableAutoRegistration(Optional<TestMockFilter> filter){
        FilterRegistrationBean<TestMockFilter> registration=new FilterRegistrationBean<>();
        filter.ifPresent(registration::setFilter);
        registration.setEnabled(false);
        return registration;
    }
}