package com.justen.events.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justen.events.domain.entity.EventCategory;
import com.justen.events.domain.entity.EventParticipant;
import com.justen.events.domain.exception.BusinessException;
import com.justen.events.domain.exception.EntityNotFoundException;
import com.justen.events.domain.repository.EventCategoryRepository;
import com.justen.events.domain.repository.EventParticipantRepository;
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
public class EventParticipantService {

	private final EventParticipantRepository eventParticipantRepository;
	private final EventCategoryRepository eventCategoryRepository;
	private final EventService eventService;
	private final SecurityUtils securityUtils;

	@Transactional
	public EventParticipant create(EventParticipant participant) {
		if (participant.getUserId() == null) {
			participant.setUserId(securityUtils.getLoggedUserId());
		}
		return eventParticipantRepository.save(participant);
	}

	public EventParticipant getById(UUID id) {
		return eventParticipantRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("EventParticipant not found with id: " + id));
	}

	public Page<EventParticipant> getAll(Pageable pageable, String filter) {
		if (filter != null && !filter.isBlank()) {
			return eventParticipantRepository.findByNameContainingIgnoreCase(filter, pageable);
		}
		return eventParticipantRepository.findAll(pageable);
	}

	@Transactional
	public EventParticipant update(UUID id, EventParticipant participant) {
		EventParticipant existing = getById(id);
		validateCanManageParticipant(existing, false);

		existing.setName(participant.getName());
		existing.setUserId(participant.getUserId());
		existing.setTeam(participant.getTeam());
		existing.setMetadata(participant.getMetadata());
		return eventParticipantRepository.save(existing);
	}

	@Transactional
	public void delete(UUID id) {
		EventParticipant existing = getById(id);
		validateCanManageParticipant(existing, true);
		eventParticipantRepository.deleteById(id);
	}

	public List<EventCategory> getCategoriesByParticipantId(UUID participantId) {
		getById(participantId);
		return eventCategoryRepository.findByParticipants_Id(participantId);
	}

	private void validateCanManageParticipant(EventParticipant participant, boolean allowSelfUnsubscribe) {
		if (Boolean.TRUE.equals(securityUtils.validateRoles(List.of(RoleEnum.ADM, RoleEnum.DEV)))) {
			return;
		}

		UUID loggedUserId = securityUtils.getLoggedUserId();
		if (allowSelfUnsubscribe && participant.getUserId() != null && participant.getUserId().equals(loggedUserId)) {
			return;
		}

		List<EventCategory> categories = eventCategoryRepository.findByParticipants_Id(participant.getId());
		if (categories != null && !categories.isEmpty()) {
			boolean canManageAny = categories.stream().anyMatch(cat -> {
				try {
					if (cat.getEvent() != null) {
						eventService.validateCanManageEvent(cat.getEvent());
						return true;
					}
				} catch (Exception ignored) {
				}
				return false;
			});
			if (canManageAny) {
				return;
			}
		}

		throw new BusinessException("User does not have permission to modify or delete this participant");
	}

}
