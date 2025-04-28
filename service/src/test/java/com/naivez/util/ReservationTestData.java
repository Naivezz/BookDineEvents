package com.naivez.util;

import com.naivez.dto.reservation.ReservationCreateEditDto;
import com.naivez.dto.reservation.ReservationReadDto;
import com.naivez.entity.enums.PaymentStatus;
import com.naivez.entity.enums.ReservationStatus;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.Instant;

@UtilityClass
public class ReservationTestData {

    public static ReservationCreateEditDto buildReservationCreateEditDto() {
        return ReservationCreateEditDto.builder()
                .time(Instant.now())
                .guests(3)
                .status(ReservationStatus.PENDING)
                .amount(BigDecimal.valueOf(100.50))
                .paymentTime(Instant.now())
                .paymentStatus(PaymentStatus.COMPLETED)
                .restaurantId(1L)
                .build();
    }

    public static ReservationReadDto buildReservationReadDto() {
        return ReservationReadDto.builder()
                .id(1L)
                .time(Instant.now())
                .guests(3)
                .status(ReservationStatus.PENDING)
                .amount(BigDecimal.valueOf(100.50))
                .paymentTime(Instant.now())
                .paymentStatus(PaymentStatus.REJECTED)
                .restaurantId(1L)
                .build();
    }

    public static ReservationReadDto buildUpdatedReservationReadDto() {
        return ReservationReadDto.builder()
                .id(1L)
                .time(Instant.now().plusSeconds(3600))
                .guests(5)
                .status(ReservationStatus.CONFIRMED)
                .amount(BigDecimal.valueOf(150.75))
                .paymentTime(Instant.now().plusSeconds(1800))
                .paymentStatus(PaymentStatus.PENDING)
                .restaurantId(1L)
                .build();
    }
}
