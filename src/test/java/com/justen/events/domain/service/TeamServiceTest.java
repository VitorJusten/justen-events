package com.justen.events.domain.service;

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

import com.justen.events.core.enums.HierarchyStatusEnum;
import com.justen.events.core.enums.TeamRoleEnum;
import com.justen.events.domain.entity.Team;
import com.justen.events.domain.entity.TeamHierarchy;
import com.justen.events.domain.exception.BusinessException;
import com.justen.events.domain.repository.TeamHierarchyRepository;
import com.justen.events.domain.repository.TeamRepository;
import com.justen.infrastructure.enums.RoleEnum;
import com.justen.infrastructure.utils.SecurityUtils;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

	@Mock
	private TeamRepository teamRepository;

	@Mock
	private TeamHierarchyRepository teamHierarchyRepository;

	@Mock
	private SecurityUtils securityUtils;

	@InjectMocks
	private TeamService teamService;

	private UUID loggedUserId;
	private UUID teamId;

	@BeforeEach
	void setUp() {
		loggedUserId = UUID.randomUUID();
		teamId = UUID.randomUUID();
	}

	@Test
	void shouldCreateTeamAndRegisterCreatorAsLeader() {
		when(securityUtils.getLoggedUserId()).thenReturn(loggedUserId);

		Team team = new Team();
		team.setName("Alpha Team");

		Team saved = new Team();
		saved.setId(teamId);
		saved.setName("Alpha Team");

		when(teamRepository.save(any(Team.class))).thenReturn(saved);

		Team result = teamService.create(team);

		assertNotNull(result);
		assertEquals(teamId, result.getId());
		verify(teamHierarchyRepository).save(any(TeamHierarchy.class));
	}

	@Test
	void shouldAllowUpdateWhenUserIsLeaderOrAdm() {
		when(securityUtils.validateRoles(List.of(RoleEnum.ADM, RoleEnum.DEV))).thenReturn(false);
		when(securityUtils.getLoggedUserId()).thenReturn(loggedUserId);

		Team existing = new Team();
		existing.setId(teamId);
		existing.setName("Old Name");

		when(teamRepository.findById(teamId)).thenReturn(Optional.of(existing));
		when(teamHierarchyRepository.existsByTeam_IdAndUserIdAndRoleInAndStatus(
				teamId, loggedUserId, List.of(TeamRoleEnum.LEADER, TeamRoleEnum.ADM), HierarchyStatusEnum.ACCEPTED))
				.thenReturn(true);
		when(teamRepository.save(any(Team.class))).thenAnswer(i -> i.getArgument(0));

		Team updateInput = new Team();
		updateInput.setName("New Name");

		Team updated = teamService.update(teamId, updateInput);
		assertEquals("New Name", updated.getName());
	}

	@Test
	void shouldThrowExceptionWhenUserCannotManageTeam() {
		when(securityUtils.validateRoles(List.of(RoleEnum.ADM, RoleEnum.DEV))).thenReturn(false);
		when(securityUtils.getLoggedUserId()).thenReturn(loggedUserId);

		Team existing = new Team();
		existing.setId(teamId);

		when(teamRepository.findById(teamId)).thenReturn(Optional.of(existing));
		when(teamHierarchyRepository.existsByTeam_IdAndUserIdAndRoleInAndStatus(
				teamId, loggedUserId, List.of(TeamRoleEnum.LEADER, TeamRoleEnum.ADM), HierarchyStatusEnum.ACCEPTED))
				.thenReturn(false);

		assertThrows(BusinessException.class, () -> teamService.delete(teamId));
	}

}
