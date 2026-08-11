package com.justen.events.domain.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.justen.events.domain.entity.EventType;
import com.justen.events.domain.exception.EntityNotFoundException;
import com.justen.events.domain.repository.EventTypeRepository;

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
public class EventTypeService {

	private final EventTypeRepository eventTypeRepository;

	public EventType create(EventType eventType) {
		return eventTypeRepository.save(eventType);
	}

	public EventType getById(UUID id) {
		return eventTypeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("EventType not found with id: " + id));
	}

	public Page<EventType> getAll(Pageable pageable, String filter) {
		if (filter != null && !filter.isBlank()) {
			return eventTypeRepository.findByNameContainingIgnoreCase(filter, pageable);
		}
		return eventTypeRepository.findAll(pageable);
	}

	public EventType update(UUID id, EventType eventType) {
		EventType existing = getById(id);
		existing.setName(eventType.getName());
		return eventTypeRepository.save(existing);
	}

	public void delete(UUID id) {
		getById(id);
		eventTypeRepository.deleteById(id);
	}

}
