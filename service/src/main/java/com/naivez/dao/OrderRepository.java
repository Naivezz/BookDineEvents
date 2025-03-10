package com.naivez.dao;

import com.naivez.entity.Order;
import jakarta.persistence.EntityManager;

public class OrderRepository extends RepositoryBase<Long, Order>{

    public OrderRepository(Class<Order> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }
}
