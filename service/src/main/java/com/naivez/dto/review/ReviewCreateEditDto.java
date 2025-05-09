package com.naivez.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
@Builder
public class ReviewCreateEditDto {

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    Integer rating;

    @NotBlank(message = "Description is required")
    String description;

    MultipartFile image;

    @NotNull(message = "Restaurant Id is required")
    Long restaurantId;
}
