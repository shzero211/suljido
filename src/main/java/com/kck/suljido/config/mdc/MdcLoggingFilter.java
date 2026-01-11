package com.kck.suljido.config.mdc;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Order;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MdcLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest req=(HttpServletRequest) request;
        //1.UUID 정보 기입
        String traceId= UUID.randomUUID().toString().substring(0,8);
        MDC.put("traceId",traceId);
        //2.사용자 IP 정보 기입
        String clientIp=req.getHeader("X-Forwarded-For");
        if(clientIp==null) clientIp=req.getRemoteAddr();
        MDC.put("clientIp",clientIp);
        //3.로그인한 사용자 ID 기입
        try{
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            if(authentication!=null &&authentication.isAuthenticated()){

                String username=authentication.getName();
                MDC.put("userId",username);
            }else{
                MDC.put("userId","anonymousUser");
            }
        }catch (Exception e){
            MDC.put("userId","unknownUser");
        }

        try{
            filterChain.doFilter(request,response);
        }finally {
            MDC.clear();
        }
    }
}
