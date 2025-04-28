package com.naivez.dto.reservation;

import com.naivez.entity.enums.PaymentStatus;
import com.naivez.entity.enums.ReservationStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
public class ReservationCreateEditDto {

    @NotBlank(message = "Time is required")
    Instant time;

    @NotBlank(message = "Number of guests is required")
    @Min(value = 1, message = "Number of guests must be at least 1")
    Integer guests;

    ReservationStatus status;

    BigDecimal amount;

    Instant paymentTime;

    PaymentStatus paymentStatus;

    @NotBlank(message = "Restaurant Id is required")
    Long restaurantId;
}
