package com.kck.suljido;

import com.kck.suljido.mapper.ArticleMapper;
import com.kck.suljido.mapper.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyBatisApplication implements ApplicationRunner {
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        var members=memberMapper.selectAll();
        log.info("회원목록 : {}",members);
        log.info("=============");
        var member=memberMapper.selectById(2L);
        log.info("2번회원: {}",member);
    }
}
