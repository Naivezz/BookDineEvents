package com.naivez.dao;

import com.naivez.entity.Spot;
import jakarta.persistence.EntityManager;

public class SpotRepository extends RepositoryBase<Integer, Spot>{

    public SpotRepository(Class<Spot> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }
}
