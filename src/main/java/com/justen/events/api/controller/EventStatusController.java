package com.justen.events.api.controller;

import java.util.List;
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

import com.justen.events.core.dto.EventStatusDto;
import com.justen.events.core.dto.input.EventStatusInputDto;
import com.justen.events.domain.service.EventStatusService;

import lombok.AllArgsConstructor;

/**
 *
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
@RestController
@RequestMapping("/event-status")
@AllArgsConstructor
public class EventStatusController {

	private final EventStatusService eventStatusService;

	@PostMapping
	public EventStatusDto create(@RequestBody EventStatusInputDto input) {
		return new EventStatusDto(eventStatusService.create(input.toEntity()));
	}

	@GetMapping("/{id}")
	public EventStatusDto getById(@PathVariable UUID id) {
		return new EventStatusDto(eventStatusService.getById(id));
	}

	@GetMapping
	public Page<EventStatusDto> getAll(Pageable pageable) {
		return eventStatusService.getAll(pageable).map(EventStatusDto::new);
	}

	@PutMapping("/{id}")
	public EventStatusDto update(@PathVariable UUID id, @RequestBody EventStatusInputDto input) {
		return new EventStatusDto(eventStatusService.update(id, input.toEntity()));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable UUID id) {
		eventStatusService.delete(id);
	}

	@GetMapping("/by-event")
	public List<EventStatusDto> getByEventId(@RequestParam UUID eventId) {
		return eventStatusService.getByEventId(eventId).stream().map(EventStatusDto::new).toList();
	}

}
