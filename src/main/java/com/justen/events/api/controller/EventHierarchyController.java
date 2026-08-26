package com.justen.events.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.justen.events.core.dto.EventHierarchyDto;
import com.justen.events.core.dto.input.EventHierarchyInputDto;
import com.justen.events.core.enums.EventRoleEnum;
import com.justen.events.core.types.EventHierarchyId;
import com.justen.events.domain.service.EventHierarchyService;

import lombok.AllArgsConstructor;

/**
 *
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
@RestController
@RequestMapping("/event-hierarchy")
@AllArgsConstructor
public class EventHierarchyController {

	private final EventHierarchyService eventHierarchyService;

	@PostMapping
	public EventHierarchyDto create(@RequestBody EventHierarchyInputDto input) {
		return new EventHierarchyDto(eventHierarchyService.create(input.toEntity()));
	}

	@GetMapping
	public EventHierarchyDto getById(@RequestParam UUID eventId, @RequestParam UUID userId) {
		EventHierarchyId id = new EventHierarchyId(userId, eventId);
		return new EventHierarchyDto(eventHierarchyService.getById(id));
	}

	@GetMapping("/all")
	public Page<EventHierarchyDto> getAll(Pageable pageable) {
		return eventHierarchyService.getAll(pageable).map(EventHierarchyDto::new);
	}

	@PutMapping
	public EventHierarchyDto update(@RequestParam UUID eventId, @RequestParam UUID userId,
			@RequestBody EventHierarchyInputDto input) {
		EventHierarchyId id = new EventHierarchyId(userId, eventId);
		return new EventHierarchyDto(eventHierarchyService.update(id, input.toEntity()));
	}

	@DeleteMapping
	public void delete(@RequestParam UUID eventId, @RequestParam UUID userId) {
		EventHierarchyId id = new EventHierarchyId(userId, eventId);
		eventHierarchyService.delete(id);
	}

	@GetMapping("/by-event")
	public List<EventHierarchyDto> getUsersByEvent(@RequestParam UUID eventId) {
		return eventHierarchyService.getUsersByEvent(eventId).stream().map(EventHierarchyDto::new).toList();
	}

	@GetMapping("/by-user")
	public List<EventHierarchyDto> getEventsByUser(@RequestParam UUID userId) {
		return eventHierarchyService.getEventsByUser(userId).stream().map(EventHierarchyDto::new).toList();
	}

	@PostMapping("/invite")
	public EventHierarchyDto inviteUser(@RequestParam UUID eventId, @RequestParam UUID userId,
			@RequestParam(required = false) EventRoleEnum role) {
		return new EventHierarchyDto(eventHierarchyService.inviteUser(eventId, userId, role));
	}

	@PostMapping("/request-join")
	public EventHierarchyDto requestToJoin(@RequestParam UUID eventId) {
		return new EventHierarchyDto(eventHierarchyService.requestToJoin(eventId));
	}

	@PostMapping("/user-respond")
	public EventHierarchyDto userRespondInvite(@RequestParam UUID eventId, @RequestParam boolean accept) {
		return new EventHierarchyDto(eventHierarchyService.userRespondInvite(eventId, accept));
	}

	@PostMapping("/event-respond")
	public EventHierarchyDto eventRespondRequest(@RequestParam UUID eventId, @RequestParam UUID userId,
			@RequestParam boolean accept) {
		return new EventHierarchyDto(eventHierarchyService.eventRespondRequest(eventId, userId, accept));
	}

	@PostMapping("/block")
	public EventHierarchyDto blockUser(@RequestParam UUID eventId, @RequestParam UUID userId) {
		return new EventHierarchyDto(eventHierarchyService.blockUser(eventId, userId));
	}

}
