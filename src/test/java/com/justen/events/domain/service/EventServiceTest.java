package com.justen.events.domain.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.justen.events.core.enums.EventRoleEnum;
import com.justen.events.core.enums.HierarchyStatusEnum;
import com.justen.events.domain.entity.Event;
import com.justen.events.domain.entity.EventHierarchy;
import com.justen.events.domain.exception.BusinessException;
import com.justen.events.domain.repository.EventHierarchyRepository;
import com.justen.events.domain.repository.EventRepository;
import com.justen.infrastructure.enums.RoleEnum;
import com.justen.infrastructure.utils.SecurityUtils;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

	@Mock
	private EventRepository eventRepository;

	@Mock
	private EventHierarchyRepository eventHierarchyRepository;

	@Mock
	private SecurityUtils securityUtils;

	@InjectMocks
	private EventService eventService;

	private UUID loggedUserId;
	private UUID eventId;

	@BeforeEach
	void setUp() {
		loggedUserId = UUID.randomUUID();
		eventId = UUID.randomUUID();
	}

	@Test
	void shouldCreateEventAndRegisterCreatorAsEventAdmHierarchy() {
		when(securityUtils.getLoggedUserId()).thenReturn(loggedUserId);
		when(securityUtils.getLoggedUsername()).thenReturn("testuser");

		Event event = new Event();
		event.setName("Championship 2026");

		Event savedEvent = new Event();
		savedEvent.setId(eventId);
		savedEvent.setName("Championship 2026");
		savedEvent.setAuthorId(loggedUserId);
		savedEvent.setAuthorName("testuser");

		when(eventRepository.save(any(Event.class))).thenReturn(savedEvent);

		Event result = eventService.create(event);

		assertNotNull(result);
		assertEquals(eventId, result.getId());
		assertEquals(loggedUserId, result.getAuthorId());
		verify(eventHierarchyRepository).save(any(EventHierarchy.class));
	}

	@Test
	void shouldAllowUpdateWhenUserIsAuthor() {
		when(securityUtils.validateRoles(List.of(RoleEnum.ADM, RoleEnum.DEV))).thenReturn(false);
		when(securityUtils.getLoggedUserId()).thenReturn(loggedUserId);

		Event existing = new Event();
		existing.setId(eventId);
		existing.setName("Old Name");
		existing.setAuthorId(loggedUserId);

		when(eventRepository.findById(eventId)).thenReturn(Optional.of(existing));
		when(eventRepository.save(any(Event.class))).thenAnswer(i -> i.getArgument(0));

		Event updateInput = new Event();
		updateInput.setName("New Name");

		Event updated = eventService.update(eventId, updateInput);

		assertEquals("New Name", updated.getName());
	}

	@Test
	void shouldAllowUpdateWhenUserIsEventAdm() {
		UUID otherAuthorId = UUID.randomUUID();
		when(securityUtils.validateRoles(List.of(RoleEnum.ADM, RoleEnum.DEV))).thenReturn(false);
		when(securityUtils.getLoggedUserId()).thenReturn(loggedUserId);

		Event existing = new Event();
		existing.setId(eventId);
		existing.setName("Old Name");
		existing.setAuthorId(otherAuthorId);

		when(eventRepository.findById(eventId)).thenReturn(Optional.of(existing));
		when(eventHierarchyRepository.existsByEvent_IdAndUserIdAndRoleAndStatus(
				eventId, loggedUserId, EventRoleEnum.EVENT_ADM, HierarchyStatusEnum.ACCEPTED)).thenReturn(true);
		when(eventRepository.save(any(Event.class))).thenAnswer(i -> i.getArgument(0));

		Event updateInput = new Event();
		updateInput.setName("New Name");

		Event updated = eventService.update(eventId, updateInput);

		assertEquals("New Name", updated.getName());
	}

	@Test
	void shouldAllowDeleteWhenUserIsGlobalAdmOrDev() {
		when(securityUtils.validateRoles(List.of(RoleEnum.ADM, RoleEnum.DEV))).thenReturn(true);

		Event existing = new Event();
		existing.setId(eventId);
		existing.setAuthorId(UUID.randomUUID());

		when(eventRepository.findById(eventId)).thenReturn(Optional.of(existing));

		assertDoesNotThrow(() -> eventService.delete(eventId));
		verify(eventRepository).deleteById(eventId);
	}

	@Test
	void shouldThrowExceptionWhenUserHasNoPermissionToManageEvent() {
		UUID otherAuthorId = UUID.randomUUID();
		when(securityUtils.validateRoles(List.of(RoleEnum.ADM, RoleEnum.DEV))).thenReturn(false);
		when(securityUtils.getLoggedUserId()).thenReturn(loggedUserId);

		Event existing = new Event();
		existing.setId(eventId);
		existing.setAuthorId(otherAuthorId);

		when(eventRepository.findById(eventId)).thenReturn(Optional.of(existing));
		when(eventHierarchyRepository.existsByEvent_IdAndUserIdAndRoleAndStatus(
				eventId, loggedUserId, EventRoleEnum.EVENT_ADM, HierarchyStatusEnum.ACCEPTED)).thenReturn(false);

		assertThrows(BusinessException.class, () -> eventService.delete(eventId));
	}

}
