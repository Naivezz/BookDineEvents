package com.naivez.dto.restaurant;

import com.naivez.dto.review.ReviewReadDto;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class RestaurantReadDto {

    Long id;
    String name;
    String address;
    String phoneNumber;
    String image;
    List<ReviewReadDto> reviews;
}
