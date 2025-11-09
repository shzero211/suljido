package com.kck.suljido.user;

import com.kck.suljido.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Builder
@AllArgsConstructor
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false,length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = false,nullable = false,length = 100)
    private String email;

    @Column(nullable = false,length = 30)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 10)
    @ColumnDefault("'USER'")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 10)
    @ColumnDefault("'ACTIVE'")
    private Status status;

    public enum Role {
        USER, //일반유저
        ADMIN //관리자
    }
    public enum Status{
        ACTIVE, //활성
        DORMANT, //휴면
        WITHDRAWN //탈퇴
    }
}
