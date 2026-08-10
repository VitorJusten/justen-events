package com.justen.events.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.justen.events.domain.entity.EventCategoryResult;
import com.justen.events.domain.repository.EventCategoryResultRepository;

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
public class EventCategoryResultService {

	private final EventCategoryResultRepository eventCategoryResultRepository;

	public EventCategoryResult create(EventCategoryResult result) {
		return eventCategoryResultRepository.save(result);
	}

	public EventCategoryResult getById(UUID id) {
		return eventCategoryResultRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("EventCategoryResult not found with id: " + id));
	}

	public Page<EventCategoryResult> getAll(Pageable pageable) {
		return eventCategoryResultRepository.findAll(pageable);
	}

	public EventCategoryResult update(UUID id, EventCategoryResult result) {
		EventCategoryResult existing = getById(id);
		existing.setScoreType(result.getScoreType());
		existing.setUserId(result.getUserId());
		existing.setParticipantName(result.getParticipantName());
		existing.setTeamId(result.getTeamId());
		existing.setTeamName(result.getTeamName());
		existing.setPosition(result.getPosition());
		existing.setPoints(result.getPoints());
		existing.setStatus(result.getStatus());
		existing.setMetadata(result.getMetadata());
		return eventCategoryResultRepository.save(existing);
	}

	public void delete(UUID id) {
		getById(id);
		eventCategoryResultRepository.deleteById(id);
	}

	public List<EventCategoryResult> getByCategoryId(UUID categoryId) {
		return eventCategoryResultRepository.findByCategory_Id(categoryId);
	}

}
