package com.kck.suljido.runner;

import com.kck.suljido.common.Address;
import com.kck.suljido.review.dto.ReviewDto;
import com.kck.suljido.review.entity.Review;
import com.kck.suljido.review.repository.ReviewRepository;
import com.kck.suljido.review.service.ReviewService;
import com.kck.suljido.store.entity.Store;
import com.kck.suljido.store.repository.StoreRepository;
import com.kck.suljido.store.service.StoreService;
import com.kck.suljido.user.dto.UserDto;
import com.kck.suljido.user.entity.User;
import com.kck.suljido.user.entity.enums.Role;
import com.kck.suljido.user.repository.UserRepository;
import com.kck.suljido.user.service.UserService;
import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Profile({"prod","test"})
@Component
@AllArgsConstructor
public class SampleSaveRunner implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;

    private final UserService userService;
    private final StoreService storeService;
    private final ReviewService reviewService;

    @Override
    public void run(String... args) throws Exception {
        GeometryFactory geometryFactory =new GeometryFactory();
        /*2가지 유저생성(ADMIN,USER)*/

        UserDto.UserSignUpRequest signUpRequest1 = UserDto.UserSignUpRequest.builder()
                .email("shkim1@naver.com")
                .nickname("kim1")
                .password(passwordEncoder.encode("test1234"))
                .role(Role.ADMIN)
                .build();

        UserDto.UserSignUpRequest signUpRequest2 = UserDto.UserSignUpRequest.builder()
                .email("shkim2@naver.com")
                .nickname("kim2")
                .password(passwordEncoder.encode("test5678"))
                .role(Role.USER)
                .build();


        Long userId1=userService.signUp(signUpRequest1);
        Long userId2=userService.signUp(signUpRequest2);

        User user1=userRepository.findById(userId1).orElseThrow(()->new IllegalArgumentException("테스트 User1 존재 X"));
        User user2=userRepository.findById(userId2).orElseThrow(()->new IllegalArgumentException("테스트 User2 존재 X"));

        /*3가지 가게 생성*/

        /*리뷰 가게 마다 2개 씩 생성 */
        ReviewDto.ReviewCreateRequest reviewCreateRequest1_1= ReviewDto.ReviewCreateRequest.builder()
                .storeName("무지개맥주 금천롯데캐슬점")
                .storeAddress("서울 금천구 시흥대로 291")
                .longitude(126.896700286465)
                .latitude(37.459956139883)
                .visitTime(LocalDateTime.now())
                .category("beer")
                .rating(5)
                .content("맥주 굿 store1_user1")
                .province("서울")
                .city("금천구")
                .district("시흥대로")
                .build();
        ReviewDto.ReviewCreateRequest reviewCreateRequest1_2= ReviewDto.ReviewCreateRequest.builder()
                .storeName("무지개맥주 금천롯데캐슬점")
                .storeAddress("서울 금천구 시흥대로 291")
                .longitude(126.896700286465)
                .latitude(37.459956139883)
                .visitTime(LocalDateTime.now())
                .category("beer")
                .rating(5)
                .content("맥주 굿 store1_user2")
                .province("서울")
                .city("금천구")
                .district("시흥대로")
                .build();

        ReviewDto.ReviewCreateRequest reviewCreateRequest2_1= ReviewDto.ReviewCreateRequest.builder()
                .storeName("도피")
                .storeAddress("서울 금천구 시흥대로63길 3")
                .longitude(126.899354358131)
                .latitude(37.4547875205822)
                .visitTime(LocalDateTime.now())
                .category("soju")
                .rating(5)
                .content("소주 굿 store2_user1")
                .province("서울")
                .city("금천구")
                .district("시흥대로")
                .build();
        ReviewDto.ReviewCreateRequest reviewCreateRequest2_2= ReviewDto.ReviewCreateRequest.builder()
                .storeName("도피")
                .storeAddress("서울 금천구 시흥대로63길 3")
                .longitude(126.899354358131)
                .latitude(37.4547875205822)
                .visitTime(LocalDateTime.now())
                .category("soju")
                .rating(5)
                .content("소주이즈 굿 store2_user2")
                .province("서울")
                .city("금천구")
                .district("시흥대로")
                .build();
        ReviewDto.ReviewCreateRequest reviewCreateRequest3_1= ReviewDto.ReviewCreateRequest.builder()
                .storeName("생활맥주 가산디지털점")
                .storeAddress("서울 금천구 벚꽃로 298")
                .longitude(126.883894765749)
                .latitude(37.4814351460181)
                .visitTime(LocalDateTime.now())
                .category("whisky")
                .rating(5)
                .content("위스키 굿 store1_user1")
                .province("서울")
                .city("금천구")
                .district("벚꽃로")
                .build();
        ReviewDto.ReviewCreateRequest reviewCreateRequest3_2= ReviewDto.ReviewCreateRequest.builder()
                .storeName("생활맥주 가산디지털점")
                .storeAddress("서울 금천구 벚꽃로 298")
                .longitude(126.883894765749)
                .latitude(37.4814351460181)
                .visitTime(LocalDateTime.now())
                .category("whisky")
                .rating(5)
                .content("위스키 굿 store1_user2")
                .province("서울")
                .city("금천구")
                .district("벚꽃로")
                .build();

        reviewService.createReview(user1,reviewCreateRequest1_1, List.of());
        reviewService.createReview(user2,reviewCreateRequest1_2, List.of());

        reviewService.createReview(user1,reviewCreateRequest2_1, List.of());
        reviewService.createReview(user2,reviewCreateRequest2_2, List.of());

        reviewService.createReview(user1,reviewCreateRequest3_1, List.of());
        reviewService.createReview(user2,reviewCreateRequest3_2, List.of());


    }
}
