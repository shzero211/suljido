package com.kck.suljido.user;

import com.kck.suljido.user.entity.User;
import com.kck.suljido.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class UserEntityTest {
    @Autowired
    private UserRepository userRepository;
    @Test
    public void createUserEntityTest(){
        User user=User.builder().username("misterkim").build();
        User savedUser=userRepository.save(user);

        Assertions.assertEquals(savedUser.getUsername(),"misterkim");

    }
}
