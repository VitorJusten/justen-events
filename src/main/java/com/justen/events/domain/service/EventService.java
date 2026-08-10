package com.justen.events.domain.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.justen.events.domain.entity.Event;
import com.justen.events.domain.repository.EventRepository;

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

	public Event create(Event event) {
		return eventRepository.save(event);
	}

	public Event getById(UUID id) {
		return eventRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
	}

	public Page<Event> getAll(Pageable pageable, String filter) {
		if (filter != null && !filter.isBlank()) {
			return eventRepository.findByNameContainingIgnoreCase(filter, pageable);
		}
		return eventRepository.findAll(pageable);
	}

	public Event update(UUID id, Event event) {
		Event existing = getById(id);
		existing.setName(event.getName());
		existing.setDescription(event.getDescription());
		existing.setRegulationFile(event.getRegulationFile());
		existing.setType(event.getType());
		existing.setParent(event.getParent());
		existing.setUpdatedAt(event.getUpdatedAt());
		return eventRepository.save(existing);
	}

	public void delete(UUID id) {
		getById(id);
		eventRepository.deleteById(id);
	}

}
