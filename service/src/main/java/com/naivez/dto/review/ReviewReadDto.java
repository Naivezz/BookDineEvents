package com.naivez.dto.review;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class ReviewReadDto {
    Long id;
    Integer rating;
    String description;
    String image;
    Instant time;
    String authorUsername;
}
