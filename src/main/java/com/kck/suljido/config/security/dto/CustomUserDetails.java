package com.kck.suljido.config.security.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUserDetails extends User {
    private final Long id;
    private final String email;

    public CustomUserDetails(Long id, String email,String role, Collection<? extends GrantedAuthority> authorities) {
        super(email, "", authorities);
        this.id=id;
        this.email=email;
    }
}
