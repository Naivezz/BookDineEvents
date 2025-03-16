package com.naivez.integration.entity;

import com.naivez.entity.Payment;
import com.naivez.entity.Reservation;
import com.naivez.entity.enums.PaymentStatus;
import com.naivez.entity.enums.ReservationStatus;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PaymentIT extends IntegrationTestBase {

    @Test
    void addPayment() {
        var reservation = Reservation.builder()
                .guests(4)
                .status(ReservationStatus.CONFIRMED)
                .build();

        session.persist(reservation);
        session.flush();

        var payment = Payment.builder()
                .amount(new BigDecimal("150.00"))
                .status(PaymentStatus.PENDING)
                .reservation(reservation)
                .build();
        session.persist(payment);
        session.clear();

        var actualPayment = session.get(Payment.class, payment.getId());

        assertThat(actualPayment).isNotNull();
        assertThat(actualPayment.getId()).isNotNull();
        assertEquals(PaymentStatus.PENDING, actualPayment.getStatus());
    }

    @Test
    void getPayment() {
        var reservation = Reservation.builder()
                .guests(3)
                .status(ReservationStatus.PENDING)
                .build();
        session.persist(reservation);
        session.flush();

        var payment = Payment.builder()
                .amount(new BigDecimal("200.00"))
                .status(PaymentStatus.PENDING)
                .reservation(reservation)
                .build();
        session.persist(payment);
        session.clear();

        var actualPayment = session.getReference(Payment.class, payment.getId());
        assertEquals(payment, actualPayment);
    }

    @Test
    void updatePayment() {
        var reservation = Reservation.builder()
                .guests(2)
                .status(ReservationStatus.CONFIRMED)
                .build();
        session.persist(reservation);
        session.flush();

        var payment = Payment.builder()
                .amount(new BigDecimal("100.00"))
                .status(PaymentStatus.PENDING)
                .reservation(reservation)
                .build();
        session.persist(payment);
        session.clear();

        payment.setStatus(PaymentStatus.PENDING);
        session.merge(payment);
        session.flush();
        session.clear();

        var updatedPayment = session.get(Payment.class, payment.getId());

        assertEquals(PaymentStatus.PENDING, updatedPayment.getStatus());
    }

    @Test
    void deletePayment() {
        var reservation = Reservation.builder()
                .guests(5)
                .status(ReservationStatus.CONFIRMED)
                .build();
        session.persist(reservation);
        session.flush();

        var payment = Payment.builder()
                .amount(new BigDecimal("120.00"))
                .status(PaymentStatus.PENDING)
                .reservation(reservation)
                .build();
        session.persist(payment);
        session.flush();
        session.clear();

        Payment managedPayment = session.get(Payment.class, payment.getId());
        assertThat(managedPayment).isNotNull();

        session.remove(managedPayment);
        session.flush();
        session.clear();

        assertNull(session.get(Payment.class, payment.getId()));
    }
}
