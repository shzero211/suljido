package com.kck.suljido.runner;

import com.kck.suljido.store.entity.Store;
import com.kck.suljido.store.repository.StoreRepository;
import com.kck.suljido.user.entity.User;
import com.kck.suljido.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Profile("test")
@Component
@AllArgsConstructor
public class TestRunner implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    @Override
    public void run(String... args) throws Exception {
        GeometryFactory geometryFactory =new GeometryFactory();
        /*유저생성*/
        User user1 = User.builder()
                .username("test1")
                .password(passwordEncoder.encode("test1234"))
                .deleted(Boolean.FALSE)
                .nickname("nickname1")
                .build();
        User user2 = User.builder()
                .username("test2")
                .password(passwordEncoder.encode("test5678"))
                .deleted(Boolean.FALSE)
                .nickname("nickname2")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        /*스토어 생성 */
        Store store1=Store.builder()
                .jibunAddress("서울시 금천구 시흥2동")
                .roadAddress("시흥대로 77길 ")
                .location(geometryFactory.createPoint(new Coordinate(126.9913485,37.5662952)))
                .name("을지맥주")
                .thumbnailImage("https://via.placeholder.com/150")
                .build();

        Store store2=Store.builder()
                .jibunAddress("서울시 금천구 시흥3동")
                .roadAddress("시흥대로 76길 ")
                .location(geometryFactory.createPoint(new Coordinate(125.9913485,36.5662952)))
                .name("을맥맥주")
                .thumbnailImage("https://via.placeholder.com/150")
                .build();
        storeRepository.save(store1);
        storeRepository.save(store2);
    }
}
