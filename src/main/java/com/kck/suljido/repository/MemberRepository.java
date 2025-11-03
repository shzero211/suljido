package com.kck.suljido.repository;

import com.kck.suljido.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByName(String name);
    Optional<Member> findByAge(Integer age);
}
