package com.kck.suljido.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer age;
    public Member(){}
    public Member(String name,Integer age){
        this.name=name;
        this.age=age;
    }
    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public Integer getAge(){
        return age;
    }


}
