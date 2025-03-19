package com.naivez.repository;

import com.naivez.entity.MenuItem;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class MenuItemRepository extends RepositoryBase<Long, MenuItem>{
    public MenuItemRepository(EntityManager entityManager) {
        super(MenuItem.class, entityManager);
    }
}
