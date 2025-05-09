package com.naivez.dto.review;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewFilter {

    Integer rating;
}
