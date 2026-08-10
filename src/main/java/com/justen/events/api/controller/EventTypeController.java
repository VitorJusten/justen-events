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

import com.justen.events.core.dto.EventTypeDto;
import com.justen.events.core.dto.input.EventTypeInputDto;
import com.justen.events.domain.service.EventTypeService;

import lombok.AllArgsConstructor;

/**
 *
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
@RestController
@RequestMapping("/event-type")
@AllArgsConstructor
public class EventTypeController {

	private final EventTypeService eventTypeService;

	@PostMapping
	public EventTypeDto create(@RequestBody EventTypeInputDto input) {
		return new EventTypeDto(eventTypeService.create(input.toEntity()));
	}

	@GetMapping("/{id}")
	public EventTypeDto getById(@PathVariable UUID id) {
		return new EventTypeDto(eventTypeService.getById(id));
	}

	@GetMapping
	public Page<EventTypeDto> getAll(Pageable pageable, @RequestParam(required = false) String filter) {
		return eventTypeService.getAll(pageable, filter).map(EventTypeDto::new);
	}

	@PutMapping("/{id}")
	public EventTypeDto update(@PathVariable UUID id, @RequestBody EventTypeInputDto input) {
		return new EventTypeDto(eventTypeService.update(id, input.toEntity()));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable UUID id) {
		eventTypeService.delete(id);
	}

}
