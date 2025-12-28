package com.kck.suljido.config.security.filter;

import com.kck.suljido.user.entity.User;
import com.kck.suljido.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
@Slf4j
@Component
@RequiredArgsConstructor
public class TestMockFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String email=request.getHeader("X-TEST-EMAIL");
        logger.info("doFilter-email:"+email);
        if(email !=null && !email.isBlank()){
            User user = userRepository.findByEmail(email).orElseThrow();

            SimpleGrantedAuthority authority=new SimpleGrantedAuthority(user.getRole().name());
            Authentication auth =new UsernamePasswordAuthenticationToken(user,null, Collections.singleton(authority));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request,response);
    }
}
