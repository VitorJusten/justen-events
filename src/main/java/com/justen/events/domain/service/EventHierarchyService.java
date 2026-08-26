package com.justen.events.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justen.events.core.enums.EventRoleEnum;
import com.justen.events.core.enums.HierarchyStatusEnum;
import com.justen.events.core.types.EventHierarchyId;
import com.justen.events.domain.entity.Event;
import com.justen.events.domain.entity.EventHierarchy;
import com.justen.events.domain.exception.BusinessException;
import com.justen.events.domain.exception.EntityNotFoundException;
import com.justen.events.domain.repository.EventHierarchyRepository;
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
public class EventHierarchyService {

	private final EventHierarchyRepository eventHierarchyRepository;
	private final EventService eventService;
	private final SecurityUtils securityUtils;

	@Transactional
	public EventHierarchy create(EventHierarchy eventHierarchy) {
		Event event = eventService.getById(eventHierarchy.getEvent().getId());
		eventService.validateCanManageEvent(event);
		return eventHierarchyRepository.save(eventHierarchy);
	}

	public EventHierarchy getById(EventHierarchyId id) {
		return eventHierarchyRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("EventHierarchy not found"));
	}

	public Page<EventHierarchy> getAll(Pageable pageable) {
		return eventHierarchyRepository.findAll(pageable);
	}

	@Transactional
	public EventHierarchy update(EventHierarchyId id, EventHierarchy eventHierarchy) {
		EventHierarchy existing = getById(id);
		eventService.validateCanManageEvent(existing.getEvent());
		existing.setRole(eventHierarchy.getRole());
		existing.setStatus(eventHierarchy.getStatus());
		return eventHierarchyRepository.save(existing);
	}

	@Transactional
	public void delete(EventHierarchyId id) {
		EventHierarchy existing = getById(id);
		UUID loggedUserId = securityUtils.getLoggedUserId();
		boolean isSelf = existing.getUserId().equals(loggedUserId);

		if (!isSelf) {
			eventService.validateCanManageEvent(existing.getEvent());
		}

		eventHierarchyRepository.deleteById(id);
	}

	public List<EventHierarchy> getUsersByEvent(UUID eventId) {
		return eventHierarchyRepository.findByEvent_Id(eventId);
	}

	public List<EventHierarchy> getEventsByUser(UUID userId) {
		return eventHierarchyRepository.findByUserId(userId);
	}

	@Transactional
	public EventHierarchy inviteUser(UUID eventId, UUID userId, EventRoleEnum role) {
		Event event = eventService.getById(eventId);
		eventService.validateCanManageEvent(event);

		EventHierarchyId id = new EventHierarchyId(userId, eventId);
		EventHierarchy hierarchy = eventHierarchyRepository.findById(id).orElseGet(() -> {
			EventHierarchy eh = new EventHierarchy();
			eh.setId(id);
			eh.setEvent(event);
			eh.setUserId(userId);
			return eh;
		});

		if (HierarchyStatusEnum.BLOCKED.equals(hierarchy.getStatus())) {
			throw new BusinessException("Cannot invite a blocked user");
		}

		hierarchy.setRole(role != null ? role : EventRoleEnum.MEMBER);
		hierarchy.setStatus(HierarchyStatusEnum.USER_DECISION);
		return eventHierarchyRepository.save(hierarchy);
	}

	@Transactional
	public EventHierarchy requestToJoin(UUID eventId) {
		Event event = eventService.getById(eventId);
		UUID loggedUserId = securityUtils.getLoggedUserId();

		EventHierarchyId id = new EventHierarchyId(loggedUserId, eventId);
		EventHierarchy hierarchy = eventHierarchyRepository.findById(id).orElseGet(() -> {
			EventHierarchy eh = new EventHierarchy();
			eh.setId(id);
			eh.setEvent(event);
			eh.setUserId(loggedUserId);
			return eh;
		});

		if (HierarchyStatusEnum.BLOCKED.equals(hierarchy.getStatus())) {
			throw new BusinessException("User is blocked from joining this event hierarchy");
		}

		if (HierarchyStatusEnum.ACCEPTED.equals(hierarchy.getStatus())) {
			throw new BusinessException("User is already part of this event hierarchy");
		}

		hierarchy.setRole(EventRoleEnum.MEMBER);
		hierarchy.setStatus(HierarchyStatusEnum.TEAM_DECISION);
		return eventHierarchyRepository.save(hierarchy);
	}

	@Transactional
	public EventHierarchy userRespondInvite(UUID eventId, boolean accept) {
		UUID loggedUserId = securityUtils.getLoggedUserId();
		EventHierarchyId id = new EventHierarchyId(loggedUserId, eventId);
		EventHierarchy hierarchy = getById(id);

		if (!HierarchyStatusEnum.USER_DECISION.equals(hierarchy.getStatus())) {
			throw new BusinessException("There is no pending invite for this user");
		}

		hierarchy.setStatus(accept ? HierarchyStatusEnum.ACCEPTED : HierarchyStatusEnum.DENIED);
		return eventHierarchyRepository.save(hierarchy);
	}

	@Transactional
	public EventHierarchy eventRespondRequest(UUID eventId, UUID userId, boolean accept) {
		Event event = eventService.getById(eventId);
		eventService.validateCanManageEvent(event);

		EventHierarchyId id = new EventHierarchyId(userId, eventId);
		EventHierarchy hierarchy = getById(id);

		if (!HierarchyStatusEnum.TEAM_DECISION.equals(hierarchy.getStatus())) {
			throw new BusinessException("There is no pending request for this user");
		}

		hierarchy.setStatus(accept ? HierarchyStatusEnum.ACCEPTED : HierarchyStatusEnum.DENIED);
		return eventHierarchyRepository.save(hierarchy);
	}

	@Transactional
	public EventHierarchy blockUser(UUID eventId, UUID userId) {
		Event event = eventService.getById(eventId);
		eventService.validateCanManageEvent(event);

		EventHierarchyId id = new EventHierarchyId(userId, eventId);
		EventHierarchy hierarchy = eventHierarchyRepository.findById(id).orElseGet(() -> {
			EventHierarchy eh = new EventHierarchy();
			eh.setId(id);
			eh.setEvent(event);
			eh.setUserId(userId);
			eh.setRole(EventRoleEnum.MEMBER);
			return eh;
		});

		hierarchy.setStatus(HierarchyStatusEnum.BLOCKED);
		return eventHierarchyRepository.save(hierarchy);
	}

}
