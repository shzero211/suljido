package com.kck.suljido.config.security.dto;

import com.kck.suljido.user.entity.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomUserInfoDto {
    private Long id;
    private String email;
    private Role role;
}
