package com.kck.suljido.repository;

import com.kck.suljido.config.DatabaseConfig;
import com.kck.suljido.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest(
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = DatabaseConfig.class
        )
)
@ActiveProfiles("test")
public class MemberRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("Save() 메서드로 Member 를 저장하면 성공")
    public void testSaveMember(){
        Member member =new Member("testKim",20);
        Member savedMember=memberRepository.save(member);

        Assertions.assertThat(savedMember).isNotNull();
        Assertions.assertThat(savedMember.getName()).isEqualTo("testKim");
        Assertions.assertThat(savedMember.getAge()).isEqualTo(20);
    }

    @Test
    @DisplayName("findByName() 쿼리 메서드로 Member 조회한다")
    public void testFindByName(){
        Member member=new Member("testKim",25);
        testEntityManager.persist(member);
        testEntityManager.flush();

        Optional<Member> foundMember=memberRepository.findByName("testKim");
        Assertions.assertThat(foundMember).isPresent();
        Assertions.assertThat(foundMember.get().getAge()).isEqualTo(25);
    }

    @Test
    @DisplayName("findByAge() 쿼리 메서드로 Member 조회하기")
    public void testFindByAge(){
        Member member=new Member("testKim",25);
        testEntityManager.persist(member);
        testEntityManager.flush();
        Optional<Member> findMember=memberRepository.findByAge(25);
        Assertions.assertThat(findMember).isPresent();
        Assertions.assertThat(findMember.get().getName()).isEqualTo("testKim");
    }
}
