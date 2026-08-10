package com.justen.events.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.justen.events.domain.entity.EventCategoryScore;
import com.justen.events.domain.repository.EventCategoryScoreRepository;

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
public class EventCategoryScoreService {

	private final EventCategoryScoreRepository eventCategoryScoreRepository;

	public EventCategoryScore create(EventCategoryScore score) {
		return eventCategoryScoreRepository.save(score);
	}

	public EventCategoryScore getById(UUID id) {
		return eventCategoryScoreRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("EventCategoryScore not found with id: " + id));
	}

	public Page<EventCategoryScore> getAll(Pageable pageable) {
		return eventCategoryScoreRepository.findAll(pageable);
	}

	public EventCategoryScore update(UUID id, EventCategoryScore score) {
		EventCategoryScore existing = getById(id);
		existing.setPosition(score.getPosition());
		existing.setPoints(score.getPoints());
		existing.setScoreType(score.getScoreType());
		return eventCategoryScoreRepository.save(existing);
	}

	public void delete(UUID id) {
		getById(id);
		eventCategoryScoreRepository.deleteById(id);
	}

	public List<EventCategoryScore> getByCategoryId(UUID categoryId) {
		return eventCategoryScoreRepository.findByCategory_Id(categoryId);
	}

}
