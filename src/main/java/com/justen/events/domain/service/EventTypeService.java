package com.justen.events.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justen.events.domain.entity.EventType;
import com.justen.events.domain.exception.BusinessException;
import com.justen.events.domain.exception.EntityNotFoundException;
import com.justen.events.domain.repository.EventTypeRepository;
import com.justen.infrastructure.enums.RoleEnum;
import com.justen.infrastructure.utils.SecurityUtils;

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
	private final SecurityUtils securityUtils;

	@Transactional
	public EventType create(EventType eventType) {
		validateAdmOrDev();
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

	@Transactional
	public EventType update(UUID id, EventType eventType) {
		validateAdmOrDev();
		EventType existing = getById(id);
		existing.setName(eventType.getName());
		return eventTypeRepository.save(existing);
	}

	@Transactional
	public void delete(UUID id) {
		validateAdmOrDev();
		getById(id);
		eventTypeRepository.deleteById(id);
	}

	private void validateAdmOrDev() {
		if (!Boolean.TRUE.equals(securityUtils.validateRoles(List.of(RoleEnum.ADM, RoleEnum.DEV)))) {
			throw new BusinessException("Only ADM or DEV can manage event types");
		}
	}

}
