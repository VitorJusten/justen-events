package com.justen.events.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.justen.events.domain.entity.EventCategory;
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

	public EventCategory create(EventCategory eventCategory) {
		return eventCategoryRepository.save(eventCategory);
	}

	public EventCategory getById(UUID id) {
		return eventCategoryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("EventCategory not found with id: " + id));
	}

	public Page<EventCategory> getAll(Pageable pageable, String filter) {
		if (filter != null && !filter.isBlank()) {
			return eventCategoryRepository.findByNameContainingIgnoreCase(filter, pageable);
		}
		return eventCategoryRepository.findAll(pageable);
	}

	public EventCategory update(UUID id, EventCategory eventCategory) {
		EventCategory existing = getById(id);
		existing.setName(eventCategory.getName());
		existing.setOrder(eventCategory.getOrder());
		existing.setParticipantsLimit(eventCategory.getParticipantsLimit());
		return eventCategoryRepository.save(existing);
	}

	public void delete(UUID id) {
		getById(id);
		eventCategoryRepository.deleteById(id);
	}

	public List<EventCategory> getByEventId(UUID eventId) {
		return eventCategoryRepository.findByEvent_Id(eventId);
	}

}
