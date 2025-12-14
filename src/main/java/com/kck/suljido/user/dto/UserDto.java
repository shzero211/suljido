package com.kck.suljido.user.dto;

import lombok.Builder;
import lombok.Getter;


public class UserDto {

    @Builder
    public record UserSignUpRequest(String email,String password,String nickname){}
    @Builder
    public record UserSignUpResponse() {
    }
}
