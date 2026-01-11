package com.kck.suljido.config.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e, HttpServletRequest request){
        String errorMessage=e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.error("[Validation Fail] {} | URI: {}",errorMessage,request.getRequestURI());
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                        .errorCode("INTERNAL_SERVER_ERROR")
                        .message("알 수 없는 서버 오류가 발생했습니다. 관리자에게 문의하세요.\"")
                        .traceId(MDC.get("traceId"))
                .build());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(Exception e, HttpServletRequest request){
        // [중요] log.error를 찍을 때 마지막 파라미터로 'e'를 넘겨야 Stack Trace가 로그에 남습니다.
        log.error("[Unhandled Exception] {} occurred at {}", e.getClass().getSimpleName(), request.getRequestURI(), e);

        return ResponseEntity
                .internalServerError()
                .body(ErrorResponse.builder()
                        .errorCode("INTERNAL_SERVER_ERROR")
                        .message("알 수 없는 서버 오류가 발생했습니다. 관리자에게 문의하세요.")
                        .traceId(MDC.get("traceId")) // 사용자가 이 ID를 캡처해서 문의하면 바로 찾을 수 있음
                        .build());
    }

    // [추가] 존재하지 않는 URL 요청 (404) 처리
    // 이 핸들러가 없으면 'Exception' 핸들러로 넘어가서 500 에러 및 ERROR 로그가 찍힘
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFound(NoResourceFoundException e, HttpServletRequest request) {
        // 보안상 404는 로그를 안 남기거나, DEBUG 레벨로 낮추는 게 일반적입니다.
        // 공격 로그가 너무 많다면 아예 로그를 주석 처리해도 됩니다.
        log.warn("[404 Not Found] URI: {}", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .errorCode("NOT_FOUND")
                        .message("요청하신 리소스를 찾을 수 없습니다.")
                        .traceId(MDC.get("traceId"))
                        .build());
    }
}
@Getter
@Builder
class ErrorResponse{
    private String errorCode;
    private String message;
    private String traceId;
}
