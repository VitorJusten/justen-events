package com.justen.events.domain.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.justen.events.domain.entity.Event;
import com.justen.events.domain.entity.EventCategory;
import com.justen.events.domain.entity.EventParticipant;
import com.justen.events.domain.exception.BusinessException;
import com.justen.events.domain.repository.EventCategoryRepository;
import com.justen.events.domain.repository.EventParticipantRepository;
import com.justen.infrastructure.enums.RoleEnum;
import com.justen.infrastructure.utils.SecurityUtils;

@ExtendWith(MockitoExtension.class)
class EventParticipantServiceTest {

	@Mock
	private EventParticipantRepository eventParticipantRepository;

	@Mock
	private EventCategoryRepository eventCategoryRepository;

	@Mock
	private EventService eventService;

	@Mock
	private SecurityUtils securityUtils;

	@InjectMocks
	private EventParticipantService eventParticipantService;

	private UUID participantId;
	private UUID loggedUserId;

	@BeforeEach
	void setUp() {
		participantId = UUID.randomUUID();
		loggedUserId = UUID.randomUUID();
	}

	@Test
	void shouldCreateParticipantSettingLoggedUserIfMissing() {
		when(securityUtils.getLoggedUserId()).thenReturn(loggedUserId);

		EventParticipant participant = new EventParticipant();
		participant.setName("Participant 1");

		when(eventParticipantRepository.save(any(EventParticipant.class))).thenAnswer(i -> i.getArgument(0));

		EventParticipant created = eventParticipantService.create(participant);

		assertNotNull(created);
		assertEquals(loggedUserId, created.getUserId());
	}

	@Test
	void shouldAllowParticipantToUnsubscribeSelf() {
		when(securityUtils.validateRoles(List.of(RoleEnum.ADM, RoleEnum.DEV))).thenReturn(false);
		when(securityUtils.getLoggedUserId()).thenReturn(loggedUserId);

		EventParticipant existing = new EventParticipant();
		existing.setId(participantId);
		existing.setUserId(loggedUserId);

		when(eventParticipantRepository.findById(participantId)).thenReturn(Optional.of(existing));

		assertDoesNotThrow(() -> eventParticipantService.delete(participantId));
		verify(eventParticipantRepository).deleteById(participantId);
	}

	@Test
	void shouldAllowGlobalAdmToDeleteParticipant() {
		when(securityUtils.validateRoles(List.of(RoleEnum.ADM, RoleEnum.DEV))).thenReturn(true);

		EventParticipant existing = new EventParticipant();
		existing.setId(participantId);
		existing.setUserId(UUID.randomUUID());

		when(eventParticipantRepository.findById(participantId)).thenReturn(Optional.of(existing));

		assertDoesNotThrow(() -> eventParticipantService.delete(participantId));
		verify(eventParticipantRepository).deleteById(participantId);
	}

	@Test
	void shouldThrowExceptionWhenUnauthorizedUserTriesToDeleteParticipant() {
		UUID otherUserId = UUID.randomUUID();
		when(securityUtils.validateRoles(List.of(RoleEnum.ADM, RoleEnum.DEV))).thenReturn(false);
		when(securityUtils.getLoggedUserId()).thenReturn(loggedUserId);

		EventParticipant existing = new EventParticipant();
		existing.setId(participantId);
		existing.setUserId(otherUserId);

		when(eventParticipantRepository.findById(participantId)).thenReturn(Optional.of(existing));
		when(eventCategoryRepository.findByParticipants_Id(participantId)).thenReturn(Collections.emptyList());

		assertThrows(BusinessException.class, () -> eventParticipantService.delete(participantId));
	}

}
