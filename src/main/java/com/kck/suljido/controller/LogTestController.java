package com.kck.suljido.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/api/test")
public class LogTestController {
    @GetMapping("/fatal")
    public String triggerFatalError(){
        log.info("시스템 치명적 에러 테스트 시작");
        String text=null;
        return text.toUpperCase();
    }

    @GetMapping("/custom")
    public String triggerCustomError(){
        log.info("비즈니스 예외 테스트 시작");
        throw new IllegalArgumentException("테스트 커스텀 에러입니다.");
    }
    // 3. 파라미터 유효성 검사 (Argument) -> 400, WARN 로그
    @GetMapping("/args")
    public String triggerArgError(@RequestParam Integer number) {
        // 호출할 때 문자를 넣으면 TypeMismatch 발생
        return "입력받은 숫자: " + number;
    }

    // 4. 슬로우 쿼리 테스트 (Latency) -> AOP가 잘 잡는지 확인
    @GetMapping("/slow")
    public String triggerSlowApi() throws InterruptedException {
        log.info("🐢 느린 API 테스트 시작");
        Thread.sleep(3000); // 3초 대기
        return "3초 뒤 응답 완료";
    }
}
