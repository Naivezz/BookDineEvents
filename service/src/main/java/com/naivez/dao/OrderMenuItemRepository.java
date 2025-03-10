package com.naivez.dao;

import com.naivez.entity.OrderMenuItem;
import jakarta.persistence.EntityManager;

public class OrderMenuItemRepository extends RepositoryBase<Long, OrderMenuItem>{

    public OrderMenuItemRepository(Class<OrderMenuItem> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }
}
