package com.kck.suljido.config.security.filter;

import com.kck.suljido.config.security.dto.CustomUserDetails;
import com.kck.suljido.config.security.util.JwtUtil;
import com.kck.suljido.user.entity.enums.Role;
import com.kck.suljido.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // ★ 이 로그가 콘솔에 찍히는지 확인
        log.info("📢 [JwtAuthFilter] Request URI: {}", request.getRequestURI());

        String authorizationHeader=request.getHeader("Authorization");

        log.info("authorizationHeader:${}",authorizationHeader);

        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            logger.info("JwtAuth Filter 작동");
            String token=authorizationHeader.substring(7);
            if(jwtUtil.validateToken(token)){
                Authentication authentication=jwtUtil.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("✅ Security Context에 '{}' 인증 정보를 저장했습니다", authentication.getName());
            }else{
                log.info("유효한 JWT 토큰이 없습니다, uri: {}", request.getRequestURI());
            }

        }else{
            log.info("올바른 JWT 토큰이 아닙니다.");
            log.info("현재는 테스트 중이여서 테스트 유저 주입해드립니다.{}","user1");

            SimpleGrantedAuthority authority=new SimpleGrantedAuthority(Role.ADMIN.toString());
            CustomUserDetails principal = new CustomUserDetails(1L, "HunZang9957@naver.com","", Collections.singleton(authority));
            Authentication auth =new UsernamePasswordAuthenticationToken(principal,"", Collections.singleton(authority));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request,response);
    }
}
