package com.kck.suljido.config.security.util;

import com.kck.suljido.config.security.dto.CustomUserInfoDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret_key}")
    private String secretKey;

    @Value("${jwt.valid_time}")
    private long validTime;

    public String createToken(CustomUserInfoDto userInfoDto){
        Claims claims= Jwts.claims()
                .setSubject(String.valueOf(userInfoDto.getId()));
        claims.put("email",userInfoDto.getEmail());
        claims.put("role",userInfoDto.getRole());

        Date now=new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+validTime))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public Authentication getAuthentication(String token) {
        Claims claims=Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String userIdStr=claims.getSubject();
        String email= claims.get("email",String.class);
        String role = claims.get("role",String.class);

        Collection<? extends GrantedAuthority> authorities=Collections.singleton(new SimpleGrantedAuthority("ROLE_"+role));

        UserDetails principal = new User(userIdStr,"",authorities);
        return new UsernamePasswordAuthenticationToken(principal,"",authorities);
    }
}
