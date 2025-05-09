package com.naivez.mapper.review;

import com.naivez.dto.review.ReviewReadDto;
import com.naivez.entity.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewReadMapper {

    ReviewReadDto toDto(Review review);
}
