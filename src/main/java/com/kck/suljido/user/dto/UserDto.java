package com.kck.suljido.user.dto;

import com.kck.suljido.user.entity.User;
import com.kck.suljido.user.entity.enums.Role;
import lombok.Builder;
import lombok.Getter;



public class UserDto {

    @Builder
    public record UserSignUpRequest(String email, String password, String nickname, Role role){}
    @Builder
    public record UserSignUpResponse() {
    }
    @Builder
    public record LoginRequestDto(String email,String password){

    }
    @Builder
    public record  LoginResponseDto(String grantType,String accessToken,Long expiredIn){

    }
    @Builder
    public record MyPageResponse(String nickName) {
        public static MyPageResponse from(User user) {
            return MyPageResponse.builder()
                    .nickName(user.getNickname())
                    .build();
        }
    }
}
