package com.naivez.mapper.review;

import com.naivez.dto.review.ReviewReadDto;
import com.naivez.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewReadMapper {

    @Mapping(source = "user.email", target = "authorUsername")
    ReviewReadDto toDto(Review review);
}
