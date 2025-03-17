package com.naivez.integration;

import com.naivez.config.RepositoryConfig;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public abstract class IntegrationTestBase {
    protected static EntityManager entityManager;
    protected static AnnotationConfigApplicationContext context;

    @BeforeAll
    static void init() {
        context = new AnnotationConfigApplicationContext(RepositoryConfig.class);
        entityManager = context.getBean(EntityManager.class);
    }
    @AfterAll
    static void close() {
        context.close();
    }

    @BeforeEach
    void startTransaction() {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
    }

    @AfterEach
    void rollbackTransaction() {
        entityManager.getTransaction().rollback();
    }
}
