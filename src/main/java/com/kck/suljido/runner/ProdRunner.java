package com.kck.suljido.runner;

import com.kck.suljido.common.Address;
import com.kck.suljido.store.entity.Store;
import com.kck.suljido.store.repository.StoreRepository;
import com.kck.suljido.user.entity.User;
import com.kck.suljido.user.entity.enums.Role;
import com.kck.suljido.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Profile("prod")
@Component
@AllArgsConstructor
public class ProdRunner implements CommandLineRunner {
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
                .nickname("nickname1")
                .email("shzero211@naver.com")
                .role(Role.USER)
                .build();
        User user2 = User.builder()
                .username("test2")
                .password(passwordEncoder.encode("test5678"))
                .nickname("nickname2")
                .email("shzero2311@naver.com")
                .role(Role.USER)
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        /*스토어 생성 */
        Store store1=Store.builder()
                .address(Address.builder().fullAddress("서울시 금천구 시흥2동").build())
                .location(geometryFactory.createPoint(new Coordinate(126.88,37.45)))
                .name("을지맥주")
                .thumbnailImage("https://via.placeholder.com/150")
                .build();

        Store store2=Store.builder()
                .address(Address.builder().fullAddress("서울시 금천구 시흥3동").build())
                .location(geometryFactory.createPoint(new Coordinate(126.89,37.48)))
                .name("을맥맥주")
                .thumbnailImage("https://via.placeholder.com/150")
                .build();
        storeRepository.save(store1);
        storeRepository.save(store2);
    }
}
