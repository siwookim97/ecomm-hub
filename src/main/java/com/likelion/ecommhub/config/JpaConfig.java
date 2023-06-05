package com.likelion.ecommhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class JpaConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}