package com.naivez.dto.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Value
@Builder
public class EventCreateEditDto {

    @NotBlank(message = "Name is required")
    String name;

    String description;

    @NotNull(message = "Time is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime time;

    @NotNull(message = "Restaurant ID is required")
    Long restaurantId;
}

