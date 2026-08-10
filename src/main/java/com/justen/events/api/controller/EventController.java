package com.justen.events.api.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.justen.events.core.dto.EventDto;
import com.justen.events.core.dto.input.EventInputDto;
import com.justen.events.domain.service.EventService;

import lombok.AllArgsConstructor;

/**
 *
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
@RestController
@RequestMapping("/event")
@AllArgsConstructor
public class EventController {

	private final EventService eventService;

	@PostMapping
	public EventDto create(@RequestBody EventInputDto input) {
		return new EventDto(eventService.create(input.toEntity()));
	}

	@GetMapping("/{id}")
	public EventDto getById(@PathVariable UUID id) {
		return new EventDto(eventService.getById(id));
	}

	@GetMapping
	public Page<EventDto> getAll(Pageable pageable, @RequestParam(required = false) String filter) {
		return eventService.getAll(pageable, filter).map(EventDto::new);
	}

	@PutMapping("/{id}")
	public EventDto update(@PathVariable UUID id, @RequestBody EventInputDto input) {
		return new EventDto(eventService.update(id, input.toEntity()));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable UUID id) {
		eventService.delete(id);
	}

}
