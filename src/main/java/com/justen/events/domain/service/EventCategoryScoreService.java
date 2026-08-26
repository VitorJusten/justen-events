package com.justen.events.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justen.events.domain.entity.EventCategory;
import com.justen.events.domain.entity.EventCategoryScore;
import com.justen.events.domain.exception.BusinessException;
import com.justen.events.domain.exception.EntityNotFoundException;
import com.justen.events.domain.repository.EventCategoryRepository;
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
	private final EventCategoryRepository eventCategoryRepository;
	private final EventService eventService;

	@Transactional
	public EventCategoryScore create(EventCategoryScore score) {
		if (score.getCategory() == null || score.getCategory().getId() == null) {
			throw new BusinessException("Category is required for score creation");
		}
		EventCategory category = eventCategoryRepository.findById(score.getCategory().getId())
				.orElseThrow(() -> new EntityNotFoundException("Category not found"));
		if (category.getEvent() != null) {
			eventService.validateCanManageEvent(category.getEvent());
		}
		return eventCategoryScoreRepository.save(score);
	}

	public EventCategoryScore getById(UUID id) {
		return eventCategoryScoreRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("EventCategoryScore not found with id: " + id));
	}

	public Page<EventCategoryScore> getAll(Pageable pageable) {
		return eventCategoryScoreRepository.findAll(pageable);
	}

	@Transactional
	public EventCategoryScore update(UUID id, EventCategoryScore score) {
		EventCategoryScore existing = getById(id);
		if (existing.getCategory() != null && existing.getCategory().getEvent() != null) {
			eventService.validateCanManageEvent(existing.getCategory().getEvent());
		}

		existing.setPosition(score.getPosition());
		existing.setPoints(score.getPoints());
		existing.setScoreType(score.getScoreType());
		return eventCategoryScoreRepository.save(existing);
	}

	@Transactional
	public void delete(UUID id) {
		EventCategoryScore existing = getById(id);
		if (existing.getCategory() != null && existing.getCategory().getEvent() != null) {
			eventService.validateCanManageEvent(existing.getCategory().getEvent());
		}
		eventCategoryScoreRepository.deleteById(id);
	}

	public List<EventCategoryScore> getByCategoryId(UUID categoryId) {
		return eventCategoryScoreRepository.findByCategory_Id(categoryId);
	}

}
