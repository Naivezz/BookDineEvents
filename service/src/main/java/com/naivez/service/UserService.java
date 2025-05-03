package com.naivez.service;

import com.naivez.dto.user.UserCreateEditDto;
import com.naivez.dto.user.UserFilter;
import com.naivez.dto.user.UserReadDto;
import com.naivez.entity.User;
import com.naivez.mapper.user.UserCreateEditMapper;
import com.naivez.mapper.user.UserReadMapper;
import com.naivez.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;
    private final PasswordEncoder passwordEncoder;

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
                .map(dto -> {
                    User entity = userCreateEditMapper.toEntity(dto);
                    userCreateEditMapper.mapPassword(dto, entity, passwordEncoder);
                    return entity;
                })
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
                    } else {
                        userCreateEditMapper.mapPassword(userCreateEditDto, entity, passwordEncoder);
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRole().getAuthority())
                .build();
    }

    public boolean hasAccessToUser(Long userId, Authentication authentication) {
        String email = authentication.getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return currentUser.getId().equals(userId) || currentUser.getRole().getAuthority().equals("ADMIN");
    }

}
