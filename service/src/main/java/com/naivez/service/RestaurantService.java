package com.naivez.service;

import com.naivez.dto.restaurant.RestaurantCreateEditDto;
import com.naivez.dto.restaurant.RestaurantFilter;
import com.naivez.dto.restaurant.RestaurantReadDto;
import com.naivez.entity.Restaurant;
import com.naivez.mapper.restaurant.RestaurantCreateEditMapper;
import com.naivez.mapper.restaurant.RestaurantReadMapper;
import com.naivez.repository.RestaurantRepository;
import com.naivez.repository.predicate.QPredicate;
import jakarta.validation.Valid;
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

import static com.naivez.entity.QRestaurant.restaurant;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantReadMapper restaurantReadMapper;
    private final RestaurantCreateEditMapper restaurantCreateEditMapper;
    private final ImageService imageService;

    public Page<RestaurantReadDto> findAll(RestaurantFilter filter, Pageable pageable) {
        var predicate = QPredicate.builder()
                .add(filter.getName(), restaurant.name::containsIgnoreCase)
                .add(filter.getAddress(), restaurant.address::containsIgnoreCase)
                .buildOr();

        return restaurantRepository.findAll(predicate, pageable)
                .map(restaurantReadMapper::toDto);
    }

    public List<RestaurantReadDto> findAll() {
        return restaurantRepository.findAll().stream()
                .map(restaurantReadMapper::toDto)
                .toList();
    }

    public Optional<RestaurantReadDto> findById(Long id) {
        return restaurantRepository.findById(id)
                .map(restaurantReadMapper::toDto);
    }

    @Transactional
    public RestaurantReadDto create(@Valid RestaurantCreateEditDto dto) {
        return Optional.of(dto)
                .map(restaurantCreateEditMapper::toEntity)
                .map(restaurant -> {
                    uploadImage(dto.getImage());
                    return restaurantRepository.save(restaurant);
                })
                .map(restaurantReadMapper::toDto)
                .orElseThrow();
    }

    @Transactional
    public Optional<RestaurantReadDto> update(Long id, @Valid RestaurantCreateEditDto dto) {
        return restaurantRepository.findById(id)
                .map(entity -> {
                    restaurantCreateEditMapper.toEntity(entity, dto);
                    uploadImage(dto.getImage());
                    return restaurantRepository.save(entity);
                })
                .map(restaurantReadMapper::toDto);
    }

    @Transactional
    public boolean delete(Long id) {
        return restaurantRepository.findById(id)
                .map(entity -> {
                    restaurantRepository.delete(entity);
                    restaurantRepository.flush();
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
        return restaurantRepository.findById(id)
                .map(Restaurant::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }
}
