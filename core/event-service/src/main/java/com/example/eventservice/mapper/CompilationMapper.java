package com.example.eventservice.mapper;

import com.example.eventservice.dto.CompilationDto;
import com.example.eventservice.dto.EventShortDto;
import com.example.eventservice.dto.UserShortDto;
import com.example.eventservice.model.Compilation;
import com.example.eventservice.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompilationMapper {

    private final EventMapper eventMapper;

    public CompilationDto toDto(Compilation compilation, Map<Long, UserShortDto> usersMap) {
        Set<Event> events = compilation.getEvents();
        Set<EventShortDto> eventShorts = events == null ? Collections.emptySet() : events.stream()
            .map(e -> eventMapper.toShortDto(e, usersMap.getOrDefault(e.getInitiatorId(),
                UserShortDto.builder().id(e.getInitiatorId()).name("").build())))
            .collect(Collectors.toSet());
        return CompilationDto.builder()
            .id(compilation.getId())
            .events(eventShorts)
            .pinned(compilation.getPinned())
            .title(compilation.getTitle())
            .build();
    }
}
