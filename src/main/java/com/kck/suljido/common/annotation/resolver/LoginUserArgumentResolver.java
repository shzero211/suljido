package com.kck.suljido.common.annotation.resolver;

import com.kck.suljido.common.annotation.LoginUser;
import com.kck.suljido.user.entity.User;
import com.kck.suljido.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
@Slf4j
@Component
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final UserRepository userRepository;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // @LoginUser 어노테이션이 붙어있고, 타입이 User 클래스인 경우 동작
       boolean isLoginUserAnnotation=parameter.getParameterAnnotation(LoginUser.class) !=null;
       boolean isUserClass = User.class.equals(parameter.getParameterType());
       return isLoginUserAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        log.info("사용자정보:"+authentication.toString());
        if(authentication ==null || authentication.getPrincipal().equals("anonymousUser")){
            throw new IllegalStateException("로그인이 필요한 서비스 입니다.");
        }
        Object principal=authentication.getPrincipal();

        UserDetails userDetails=(UserDetails)principal;
        String userIdStr=userDetails.getUsername();
        Long userId=Long.parseLong(userIdStr);

        log.info("ResolveArgument 로깅 : ${}",userIdStr);
        return userRepository.findById(userId).orElseThrow(()->new IllegalStateException("존재하지 않는 회원 입니다."));
    }
}
