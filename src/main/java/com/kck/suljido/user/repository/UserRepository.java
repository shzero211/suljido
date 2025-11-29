package com.kck.suljido.user.repository;

import com.kck.suljido.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
