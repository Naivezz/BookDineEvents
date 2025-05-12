package com.naivez.dto.event;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class EventReadDto {

    Long id;
    String name;
    String description;
    LocalDateTime time;
    Long restaurantId;
    String restaurantName;
}
