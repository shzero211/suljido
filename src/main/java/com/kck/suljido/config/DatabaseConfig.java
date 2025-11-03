package com.kck.suljido.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@MapperScan(basePackages = "com.kck.suljido.mapper")
@EntityScan(basePackages = "com.kck.suljido.entity")
@EnableJpaRepositories(basePackages = "com.kck.suljido.repository")
public class DatabaseConfig {
}
