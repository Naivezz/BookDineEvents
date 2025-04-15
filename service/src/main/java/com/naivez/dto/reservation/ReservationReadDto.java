package com.naivez.dto.reservation;

import com.naivez.entity.enums.PaymentStatus;
import com.naivez.entity.enums.ReservationStatus;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
public class ReservationReadDto {

    Long id;
    Instant time;
    Integer guests;
    ReservationStatus status;
    BigDecimal amount;
    Instant paymentTime;
    PaymentStatus paymentStatus;
    Long restaurantId;
}
