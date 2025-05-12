package com.naivez.service;

import com.naivez.dto.event.EventCreateEditDto;
import com.naivez.dto.event.EventReadDto;
import com.naivez.mapper.event.EventCreateEditMapper;
import com.naivez.mapper.event.EventReadMapper;
import com.naivez.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventService {

    private final EventRepository eventRepository;
    private final EventReadMapper eventReadMapper;
    private final EventCreateEditMapper eventCreateEditMapper;

    public List<EventReadDto> findAll() {
        return eventRepository.findAll().stream()
                .map(eventReadMapper::toReadDto)
                .toList();
    }

    public Optional<EventReadDto> findById(Long id) {
        return eventRepository.findById(id)
                .map(eventReadMapper::toReadDto);
    }

    public List<EventReadDto> findByRestaurantId(Long restaurantId) {
        return eventRepository.findByRestaurantId(restaurantId).stream()
                .map(eventReadMapper::toReadDto)
                .toList();
    }

    @Transactional
    public EventReadDto create(EventCreateEditDto eventDto) {
        return Optional.of(eventDto)
                .map(eventCreateEditMapper::toEntity)
                .map(eventRepository::save)
                .map(eventReadMapper::toReadDto)
                .orElseThrow();
    }

    @Transactional
    public Optional<EventReadDto> update(Long id, EventCreateEditDto eventDto) {
        return eventRepository.findById(id)
                .map(entity -> {
                    eventCreateEditMapper.toEntity(eventDto, entity);
                    return entity;
                })
                .map(eventRepository::saveAndFlush)
                .map(eventReadMapper::toReadDto);
    }

    @Transactional
    public boolean delete(Long id) {
        return eventRepository.findById(id)
                .map(entity -> {
                    eventRepository.delete(entity);
                    eventRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
