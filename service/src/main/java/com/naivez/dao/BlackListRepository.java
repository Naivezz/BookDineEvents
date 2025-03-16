package com.naivez.dao;

import com.naivez.entity.BlackList;
import jakarta.persistence.EntityManager;

public class BlackListRepository extends RepositoryBase<Long, BlackList>{

    public BlackListRepository(Class<BlackList> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }
}
