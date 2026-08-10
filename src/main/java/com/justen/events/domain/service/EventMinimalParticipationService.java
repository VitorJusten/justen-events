package com.justen.events.domain.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.justen.events.domain.entity.EventMinimalParticipation;
import com.justen.events.domain.repository.EventMinimalParticipationRepository;

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
public class EventMinimalParticipationService {

	private final EventMinimalParticipationRepository eventMinimalParticipationRepository;

	public EventMinimalParticipation create(EventMinimalParticipation emp) {
		return eventMinimalParticipationRepository.save(emp);
	}

	public EventMinimalParticipation getById(UUID id) {
		return eventMinimalParticipationRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("EventMinimalParticipation not found with id: " + id));
	}

	public Page<EventMinimalParticipation> getAll(Pageable pageable) {
		return eventMinimalParticipationRepository.findAll(pageable);
	}

	public EventMinimalParticipation update(UUID id, EventMinimalParticipation emp) {
		EventMinimalParticipation existing = getById(id);
		existing.setQuantityEvents(emp.getQuantityEvents());
		existing.setMinimalPosition(emp.getMinimalPosition());
		return eventMinimalParticipationRepository.save(existing);
	}

	public void delete(UUID id) {
		getById(id);
		eventMinimalParticipationRepository.deleteById(id);
	}

}
