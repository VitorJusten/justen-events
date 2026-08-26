package com.justen.events.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justen.events.domain.entity.EventStatus;
import com.justen.events.domain.exception.BusinessException;
import com.justen.events.domain.exception.EntityNotFoundException;
import com.justen.events.domain.repository.EventStatusRepository;

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
public class EventStatusService {

	private final EventStatusRepository eventStatusRepository;
	private final EventService eventService;

	@Transactional
	public EventStatus create(EventStatus eventStatus) {
		if (eventStatus.getEvent() == null || eventStatus.getEvent().getId() == null) {
			throw new BusinessException("Event is required for status creation");
		}
		eventService.validateCanManageEventById(eventStatus.getEvent().getId());
		return eventStatusRepository.save(eventStatus);
	}

	public EventStatus getById(UUID id) {
		return eventStatusRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("EventStatus not found with id: " + id));
	}

	public Page<EventStatus> getAll(Pageable pageable) {
		return eventStatusRepository.findAll(pageable);
	}

	@Transactional
	public EventStatus update(UUID id, EventStatus eventStatus) {
		EventStatus existing = getById(id);
		if (existing.getEvent() != null) {
			eventService.validateCanManageEvent(existing.getEvent());
		}

		existing.setStatus(eventStatus.getStatus());
		existing.setStartDate(eventStatus.getStartDate());
		existing.setFinishDate(eventStatus.getFinishDate());
		return eventStatusRepository.save(existing);
	}

	@Transactional
	public void delete(UUID id) {
		EventStatus existing = getById(id);
		if (existing.getEvent() != null) {
			eventService.validateCanManageEvent(existing.getEvent());
		}
		eventStatusRepository.deleteById(id);
	}

	public List<EventStatus> getByEventId(UUID eventId) {
		return eventStatusRepository.findByEvent_Id(eventId);
	}

}
