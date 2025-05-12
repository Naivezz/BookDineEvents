package com.naivez.service;

import com.naivez.dto.review.ReviewCreateEditDto;
import com.naivez.dto.review.ReviewFilter;
import com.naivez.dto.review.ReviewReadDto;
import com.naivez.entity.Review;
import com.naivez.entity.User;
import com.naivez.mapper.review.ReviewCreateEditMapper;
import com.naivez.mapper.review.ReviewReadMapper;
import com.naivez.repository.ReviewRepository;
import com.naivez.repository.UserRepository;
import com.naivez.repository.predicate.QPredicate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

import static com.naivez.entity.QReview.review;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewReadMapper reviewReadMapper;
    private final ReviewCreateEditMapper reviewCreateEditMapper;
    private final ImageService imageService;
    private final UserRepository userRepository;

    public Page<ReviewReadDto> findAll(ReviewFilter filter, Pageable pageable) {
        var predicate = QPredicate.builder()
                .add(filter.getRestaurantId(), review.restaurant.id::eq)
                .add(filter.getRating(), review.rating::eq)
                .build();

        return reviewRepository.findAll(predicate, pageable)
                .map(reviewReadMapper::toDto);
    }

    public List<ReviewReadDto> findAll() {
        return reviewRepository.findAll().stream()
                .map(reviewReadMapper::toDto)
                .toList();
    }

    public Optional<ReviewReadDto> findById(Long id) {
        return reviewRepository.findById(id)
                .map(reviewReadMapper::toDto);
    }

    @Transactional
    public ReviewReadDto create(ReviewCreateEditDto dto, String userEmail) {
        return Optional.of(dto)
                .map(reviewCreateEditMapper::toEntity)
                .map(review -> {
                    User user = userRepository.findByEmail(userEmail)
                            .orElseThrow(() -> new IllegalArgumentException("User not found: " + userEmail));
                    review.setUser(user);

                    uploadImage(dto.getImage());

                    return reviewRepository.save(review);
                })
                .map(reviewReadMapper::toDto)
                .orElseThrow();
    }

    @Transactional
    public boolean delete(Long id, String email) {
        return reviewRepository.findById(id)
                .filter(review -> review.getUser().getEmail().equals(email))
                .map(entity -> {
                    reviewRepository.delete(entity);
                    reviewRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    public Optional<byte[]> findImage(Long id) {
        return reviewRepository.findById(id)
                .map(Review::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }
}

