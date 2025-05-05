package com.naivez.mapper.restaurant;

import com.naivez.dto.restaurant.RestaurantCreateEditDto;
import com.naivez.entity.Restaurant;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring")
public interface RestaurantCreateEditMapper {

    @Mapping(target = "image", source = "image", qualifiedByName = "multipartToFilename")
    Restaurant toEntity(RestaurantCreateEditDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "image", source = "image", qualifiedByName = "multipartToFilename")
    void toEntity(@MappingTarget Restaurant restaurant, RestaurantCreateEditDto dto);

    @Named("multipartToFilename")
    static String mapImage(MultipartFile image) {
        return (image != null && !image.isEmpty()) ? image.getOriginalFilename() : null;
    }
}
