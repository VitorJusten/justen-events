package com.justen.events.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justen.events.domain.entity.EventCategory;
import com.justen.events.domain.entity.EventCategoryResult;
import com.justen.events.domain.exception.BusinessException;
import com.justen.events.domain.exception.EntityNotFoundException;
import com.justen.events.domain.repository.EventCategoryRepository;
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
	private final EventCategoryRepository eventCategoryRepository;
	private final EventService eventService;

	@Transactional
	public EventCategoryResult create(EventCategoryResult result) {
		if (result.getCategory() == null || result.getCategory().getId() == null) {
			throw new BusinessException("Category is required for result creation");
		}
		EventCategory category = eventCategoryRepository.findById(result.getCategory().getId())
				.orElseThrow(() -> new EntityNotFoundException("Category not found"));
		if (category.getEvent() != null) {
			eventService.validateCanManageEvent(category.getEvent());
		}
		return eventCategoryResultRepository.save(result);
	}

	public EventCategoryResult getById(UUID id) {
		return eventCategoryResultRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("EventCategoryResult not found with id: " + id));
	}

	public Page<EventCategoryResult> getAll(Pageable pageable) {
		return eventCategoryResultRepository.findAll(pageable);
	}

	@Transactional
	public EventCategoryResult update(UUID id, EventCategoryResult result) {
		EventCategoryResult existing = getById(id);
		if (existing.getCategory() != null && existing.getCategory().getEvent() != null) {
			eventService.validateCanManageEvent(existing.getCategory().getEvent());
		}

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

	@Transactional
	public void delete(UUID id) {
		EventCategoryResult existing = getById(id);
		if (existing.getCategory() != null && existing.getCategory().getEvent() != null) {
			eventService.validateCanManageEvent(existing.getCategory().getEvent());
		}
		eventCategoryResultRepository.deleteById(id);
	}

	public List<EventCategoryResult> getByCategoryId(UUID categoryId) {
		return eventCategoryResultRepository.findByCategory_Id(categoryId);
	}

}
