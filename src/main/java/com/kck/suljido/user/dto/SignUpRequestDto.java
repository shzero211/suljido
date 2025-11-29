package com.kck.suljido.user.dto;

import com.kck.suljido.user.User;
import com.kck.suljido.user.User.Role;
import com.kck.suljido.user.User.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequestDto(
        @NotBlank(message = "아이디는 필수 입력 값입니다.")
        @Size(min = 4,max = 20,message = "아이디는 4자 이상 20자 이하로 입력해주세요 .")
        String username,

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Size(min = 8,message = "비밀번호는 8자 이상으로 입력해주세요.")
        String password,

        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email,

        @NotBlank(message = "이름은 필수 입력 값입니다.")
        @Size(max = 30,message = "이름은 30자 이하입니다.")
        String name,

        @NotBlank(message = "권한은 필수 입력 값입니다.")
        @Size(max = 10,message = "권한은 10자 이하입니다.")
        Role role,

        @NotBlank(message = "상태는 필수 입력 값입니다.")
        @Size(max = 10,message = "상태는 10자 이하입니다.")
        Status status
) {
}
