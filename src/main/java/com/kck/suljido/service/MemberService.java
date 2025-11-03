package com.kck.suljido.service;

import com.kck.suljido.entity.Member;
import com.kck.suljido.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member getUserByName(String name){
        Member member=memberRepository.findByName(name).orElseThrow(()->new IllegalArgumentException("이름으로 사용자를 찾을수없습니다."));
        log.debug("member: "+ member.toString());
        return member;
    }

    public Member getUserByAge(Integer age){
        Member member= memberRepository.findByAge(age).orElseThrow(()->new IllegalArgumentException("나이로 사용자를 찾을수없습니다."));
        log.debug("member: "+ member.toString());
        return member;
    }

}
