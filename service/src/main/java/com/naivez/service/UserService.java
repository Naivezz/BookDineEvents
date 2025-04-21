package com.naivez.service;

import com.naivez.dto.user.UserCreateEditDto;
import com.naivez.dto.user.UserFilter;
import com.naivez.dto.user.UserReadDto;
import com.naivez.mapper.user.UserCreateEditMapper;
import com.naivez.mapper.user.UserReadMapper;
import com.naivez.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;

    public List<UserReadDto> findAll(UserFilter filter) {
        return userRepository.findAllByFilter(filter).stream()
                .map(userReadMapper::toDto)
                .toList();
    }

    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(userReadMapper::toDto)
                .toList();
    }

    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userReadMapper::toDto);
    }

    @Transactional
    public UserReadDto create(@Valid UserCreateEditDto userCreateEditDto) {
        return Optional.of(userCreateEditDto)
                .map(userCreateEditMapper::toEntity)
                .map(userRepository::save)
                .map(userReadMapper::toDto)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, @Valid UserCreateEditDto userCreateEditDto) {
        return userRepository.findById(id)
                .map(entity -> {
                    if (userCreateEditDto.getPassword() == null || userCreateEditDto.getPassword().isEmpty()) {
                        userCreateEditDto.setPassword(entity.getPassword());
                    }
                    userCreateEditMapper.toEntity(userCreateEditDto, entity);
                    return entity;
                })
                .map(userRepository::save)
                .map(userReadMapper::toDto);
    }

    @Transactional
    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
