package com.naivez.mapper.review;

import com.naivez.dto.review.ReviewCreateEditDto;
import com.naivez.entity.Review;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring")
public interface ReviewCreateEditMapper {

    @Mapping(target = "restaurant.id", source = "restaurantId")
    @Mapping(target = "image", source = "image", qualifiedByName = "multipartToFilename")
    Review toEntity(ReviewCreateEditDto reviewCreateEditDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "restaurant.id", source = "restaurantId")
    @Mapping(target = "image", source = "image", qualifiedByName = "multipartToFilename")
    void toEntity(ReviewCreateEditDto reviewCreateEditDto, @MappingTarget Review review);

    @Named("multipartToFilename")
    static String mapImage(MultipartFile image) {
        return (image != null && !image.isEmpty()) ? image.getOriginalFilename() : null;
    }
}
