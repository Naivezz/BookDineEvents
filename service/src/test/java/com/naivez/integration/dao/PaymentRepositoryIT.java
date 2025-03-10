package com.naivez.integration.dao;

import com.naivez.dao.PaymentRepository;
import com.naivez.entity.Payment;
import com.naivez.entity.enums.PaymentStatus;
import com.naivez.integration.entity.IntegrationTestBase;
import com.naivez.util.DataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentRepositoryIT extends IntegrationTestBase {

    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository(Payment.class, session);
    }

    @Test
    void createPayment() {
        var reservation = DataBuilder.createReservation();
        session.persist(reservation);
        var testPayment = DataBuilder.createPayment();
        testPayment.setReservation(reservation);

        paymentRepository.save(testPayment);
        session.clear();

        assertThat(session.get(Payment.class, testPayment.getId())).isNotNull();
    }

    @Test
    void updatePayment() {
        var reservation = DataBuilder.createReservation();
        session.persist(reservation);
        var testPayment = DataBuilder.createPayment();
        testPayment.setReservation(reservation);
        paymentRepository.save(testPayment);
        session.clear();

        testPayment.setStatus(PaymentStatus.COMPLETED);
        paymentRepository.update(testPayment);
        session.flush();
        session.clear();
        Payment updatedPayment = session.get(Payment.class, testPayment.getId());

        assertThat(updatedPayment).isEqualTo(testPayment);
    }

    @Test
    void findPaymentById() {
        var reservation = DataBuilder.createReservation();
        session.persist(reservation);
        var testPayment = DataBuilder.createPayment();
        testPayment.setReservation(reservation);
        paymentRepository.save(testPayment);
        session.clear();

        var expectedPayment = paymentRepository.findById(testPayment.getId());

        assertThat(expectedPayment).isEqualTo(Optional.of(testPayment));
    }

    @Test
    void findAllPayments() {
        var reservation1 = DataBuilder.createReservation();
        session.persist(reservation1);
        session.flush();
        var reservation2 = DataBuilder.createReservation();
        session.persist(reservation2);
        session.flush();
        var testPayment1 = DataBuilder.createPayment();
        testPayment1.setReservation(reservation1);
        paymentRepository.save(testPayment1);
        session.flush();
        var testPayment2 = DataBuilder.createPayment();
        testPayment2.setReservation(reservation2);
        paymentRepository.save(testPayment2);
        session.flush();
        session.clear();

        List<Payment> payments = paymentRepository.findAll();

        assertThat(payments)
                .isNotEmpty()
                .containsExactlyInAnyOrder(testPayment1, testPayment2);
    }

    @Test
    void deletePayment() {
        var reservation = DataBuilder.createReservation();
        session.persist(reservation);
        var testPayment = DataBuilder.createPayment();
        testPayment.setReservation(reservation);
        paymentRepository.save(testPayment);
        session.clear();

        paymentRepository.delete(testPayment.getId());

        assertThat(session.get(Payment.class, testPayment.getId())).isNull();
    }
}
