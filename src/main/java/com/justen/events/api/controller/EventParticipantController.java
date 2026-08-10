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

import com.justen.events.core.dto.EventCategoryDto;
import com.justen.events.core.dto.EventParticipantDto;
import com.justen.events.core.dto.input.EventParticipantInputDto;
import com.justen.events.domain.service.EventParticipantService;

import lombok.AllArgsConstructor;

/**
 *
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
@RestController
@RequestMapping("/event-participant")
@AllArgsConstructor
public class EventParticipantController {

	private final EventParticipantService eventParticipantService;

	@PostMapping
	public EventParticipantDto create(@RequestBody EventParticipantInputDto input) {
		return new EventParticipantDto(eventParticipantService.create(input.toEntity()));
	}

	@GetMapping("/{id}")
	public EventParticipantDto getById(@PathVariable UUID id) {
		return new EventParticipantDto(eventParticipantService.getById(id));
	}

	@GetMapping
	public Page<EventParticipantDto> getAll(Pageable pageable, @RequestParam(required = false) String filter) {
		return eventParticipantService.getAll(pageable, filter).map(EventParticipantDto::new);
	}

	@PutMapping("/{id}")
	public EventParticipantDto update(@PathVariable UUID id, @RequestBody EventParticipantInputDto input) {
		return new EventParticipantDto(eventParticipantService.update(id, input.toEntity()));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable UUID id) {
		eventParticipantService.delete(id);
	}

	@GetMapping("/{id}/categories")
	public List<EventCategoryDto> getCategoriesById(@PathVariable UUID id) {
		return eventParticipantService.getCategoriesByParticipantId(id).stream().map(EventCategoryDto::new).toList();
	}

}
