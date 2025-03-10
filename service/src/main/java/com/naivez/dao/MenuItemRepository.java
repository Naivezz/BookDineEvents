package com.naivez.dao;

import com.naivez.entity.MenuItem;
import jakarta.persistence.EntityManager;

public class MenuItemRepository extends RepositoryBase<Long, MenuItem>{

    public MenuItemRepository(Class<MenuItem> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }
}
