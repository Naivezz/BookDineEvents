package com.naivez.dao;

import com.naivez.entity.User;
import jakarta.persistence.EntityManager;

public class UserRepository extends RepositoryBase<Long, User>{

    public UserRepository(Class<User> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }
}
