package com.naivez.dto.restaurant;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
@Builder
public class RestaurantCreateEditDto {

    @NotBlank(message = "Restaurant name is required")
    String name;

    @NotBlank(message = "Address is required")
    String address;

    @NotBlank(message = "Phone number is required")
    String phoneNumber;

    MultipartFile image;
}
