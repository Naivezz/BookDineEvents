package com.naivez.dao;

import com.naivez.entity.Payment;
import jakarta.persistence.EntityManager;

public class PaymentRepository extends RepositoryBase<Long, Payment>{

    public PaymentRepository(Class<Payment> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }
}
