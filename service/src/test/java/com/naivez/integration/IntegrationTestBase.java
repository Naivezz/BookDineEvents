package com.naivez.integration;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.Proxy;

public abstract class IntegrationTestBase {

    private static SessionFactory sessionFactory;
    protected static Session session;

    @BeforeAll
    static void init() {
        var configuration = new Configuration();
        configuration.configure();
        sessionFactory = configuration.buildSessionFactory();
        session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args) -> method.invoke(sessionFactory.getCurrentSession(), args));
    }

    @AfterAll
    static void close() {
        sessionFactory.close();
    }

    @BeforeEach
    void getSession() {
        session.beginTransaction();
    }

    @AfterEach
    void rollbackAndClose() {
        session.getTransaction().rollback();
    }

}
