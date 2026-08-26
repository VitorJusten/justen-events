package com.justen.events.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justen.events.domain.entity.Event;
import com.justen.events.domain.entity.EventCategory;
import com.justen.events.domain.exception.BusinessException;
import com.justen.events.domain.exception.EntityNotFoundException;
import com.justen.events.domain.repository.EventCategoryRepository;

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
public class EventCategoryService {

	private final EventCategoryRepository eventCategoryRepository;
	private final EventService eventService;

	@Transactional
	public EventCategory create(EventCategory eventCategory) {
		if (eventCategory.getEvent() == null || eventCategory.getEvent().getId() == null) {
			throw new BusinessException("Event is required for category creation");
		}
		eventService.validateCanManageEventById(eventCategory.getEvent().getId());
		return eventCategoryRepository.save(eventCategory);
	}

	public EventCategory getById(UUID id) {
		return eventCategoryRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("EventCategory not found with id: " + id));
	}

	public Page<EventCategory> getAll(Pageable pageable, String filter) {
		if (filter != null && !filter.isBlank()) {
			return eventCategoryRepository.findByNameContainingIgnoreCase(filter, pageable);
		}
		return eventCategoryRepository.findAll(pageable);
	}

	@Transactional
	public EventCategory update(UUID id, EventCategory eventCategory) {
		EventCategory existing = getById(id);
		if (existing.getEvent() != null) {
			eventService.validateCanManageEvent(existing.getEvent());
		}

		existing.setName(eventCategory.getName());
		existing.setOrder(eventCategory.getOrder());
		existing.setParticipantsLimit(eventCategory.getParticipantsLimit());
		existing.setTeamsLimit(eventCategory.getTeamsLimit());
		return eventCategoryRepository.save(existing);
	}

	@Transactional
	public void delete(UUID id) {
		EventCategory existing = getById(id);
		if (existing.getEvent() != null) {
			eventService.validateCanManageEvent(existing.getEvent());
		}
		eventCategoryRepository.deleteById(id);
	}

	public List<EventCategory> getByEventId(UUID eventId) {
		return eventCategoryRepository.findByEvent_Id(eventId);
	}

}
