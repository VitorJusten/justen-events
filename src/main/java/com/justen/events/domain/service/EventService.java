package com.justen.events.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justen.events.core.enums.EventRoleEnum;
import com.justen.events.core.enums.HierarchyStatusEnum;
import com.justen.events.core.types.EventHierarchyId;
import com.justen.events.domain.entity.Event;
import com.justen.events.domain.entity.EventHierarchy;
import com.justen.events.domain.exception.BusinessException;
import com.justen.events.domain.exception.EntityNotFoundException;
import com.justen.events.domain.repository.EventHierarchyRepository;
import com.justen.events.domain.repository.EventRepository;
import com.justen.infrastructure.enums.RoleEnum;
import com.justen.infrastructure.utils.SecurityUtils;

import lombok.AllArgsConstructor;

/**
 *
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
@Service
@AllArgsConstructor
public class EventService {

	private final EventRepository eventRepository;
	private final EventHierarchyRepository eventHierarchyRepository;
	private final SecurityUtils securityUtils;

	@Transactional
	public Event create(Event event) {
		UUID loggedUserId = securityUtils.getLoggedUserId();
		String loggedUsername = securityUtils.getLoggedUsername();

		event.setAuthorId(loggedUserId);
		event.setAuthorName(loggedUsername);
		Event savedEvent = eventRepository.save(event);

		EventHierarchy hierarchy = new EventHierarchy();
		EventHierarchyId hierarchyId = new EventHierarchyId();
		hierarchyId.setEventId(savedEvent.getId());
		hierarchyId.setUserId(loggedUserId);
		hierarchy.setId(hierarchyId);
		hierarchy.setEvent(savedEvent);
		hierarchy.setUserId(loggedUserId);
		hierarchy.setRole(EventRoleEnum.EVENT_ADM);
		hierarchy.setStatus(HierarchyStatusEnum.ACCEPTED);
		eventHierarchyRepository.save(hierarchy);

		return savedEvent;
	}

	public Event getById(UUID id) {
		return eventRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id));
	}

	public Page<Event> getAll(Pageable pageable, String filter) {
		if (filter != null && !filter.isBlank()) {
			return eventRepository.findByNameContainingIgnoreCase(filter, pageable);
		}
		return eventRepository.findAll(pageable);
	}

	@Transactional
	public Event update(UUID id, Event event) {
		Event existing = getById(id);
		validateCanManageEvent(existing);

		existing.setName(event.getName());
		existing.setDescription(event.getDescription());
		existing.setRegulationFile(event.getRegulationFile());
		existing.setType(event.getType());
		existing.setParent(event.getParent());
		existing.setUpdatedAt(event.getUpdatedAt());
		return eventRepository.save(existing);
	}

	@Transactional
	public void delete(UUID id) {
		Event existing = getById(id);
		validateCanManageEvent(existing);
		eventRepository.deleteById(id);
	}

	public void validateCanManageEvent(Event event) {
		if (Boolean.TRUE.equals(securityUtils.validateRoles(List.of(RoleEnum.ADM, RoleEnum.DEV)))) {
			return;
		}

		UUID loggedUserId = securityUtils.getLoggedUserId();
		if (event.getAuthorId() != null && event.getAuthorId().equals(loggedUserId)) {
			return;
		}

		boolean isEventAdm = eventHierarchyRepository.existsByEvent_IdAndUserIdAndRoleAndStatus(
				event.getId(), loggedUserId, EventRoleEnum.EVENT_ADM, HierarchyStatusEnum.ACCEPTED);

		if (!isEventAdm) {
			throw new BusinessException("User does not have permission to manage this event");
		}
	}

	public void validateCanManageEventById(UUID eventId) {
		Event event = getById(eventId);
		validateCanManageEvent(event);
	}

}
