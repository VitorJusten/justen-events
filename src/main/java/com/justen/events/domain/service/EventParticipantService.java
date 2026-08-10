package com.justen.events.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.justen.events.domain.entity.EventCategory;
import com.justen.events.domain.entity.EventParticipant;
import com.justen.events.domain.repository.EventCategoryRepository;
import com.justen.events.domain.repository.EventParticipantRepository;

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
public class EventParticipantService {

	private final EventParticipantRepository eventParticipantRepository;
	private final EventCategoryRepository eventCategoryRepository;

	public EventParticipant create(EventParticipant participant) {
		return eventParticipantRepository.save(participant);
	}

	public EventParticipant getById(UUID id) {
		return eventParticipantRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("EventParticipant not found with id: " + id));
	}

	public Page<EventParticipant> getAll(Pageable pageable, String filter) {
		if (filter != null && !filter.isBlank()) {
			return eventParticipantRepository.findByNameContainingIgnoreCase(filter, pageable);
		}
		return eventParticipantRepository.findAll(pageable);
	}

	public EventParticipant update(UUID id, EventParticipant participant) {
		EventParticipant existing = getById(id);
		existing.setName(participant.getName());
		existing.setUserId(participant.getUserId());
		existing.setTeam(participant.getTeam());
		existing.setMetadata(participant.getMetadata());
		return eventParticipantRepository.save(existing);
	}

	public void delete(UUID id) {
		getById(id);
		eventParticipantRepository.deleteById(id);
	}

	public List<EventCategory> getCategoriesByParticipantId(UUID participantId) {
		getById(participantId);
		return eventCategoryRepository.findByParticipants_Id(participantId);
	}

}
