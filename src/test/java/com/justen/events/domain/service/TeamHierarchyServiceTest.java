package com.justen.events.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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
import com.justen.events.core.types.TeamHierarchyId;
import com.justen.events.domain.entity.Team;
import com.justen.events.domain.entity.TeamHierarchy;
import com.justen.events.domain.exception.BusinessException;
import com.justen.events.domain.repository.TeamHierarchyRepository;
import com.justen.infrastructure.utils.SecurityUtils;

@ExtendWith(MockitoExtension.class)
class TeamHierarchyServiceTest {

	@Mock
	private TeamHierarchyRepository teamHierarchyRepository;

	@Mock
	private TeamService teamService;

	@Mock
	private SecurityUtils securityUtils;

	@InjectMocks
	private TeamHierarchyService teamHierarchyService;

	private UUID teamId;
	private UUID userId;

	@BeforeEach
	void setUp() {
		teamId = UUID.randomUUID();
		userId = UUID.randomUUID();
	}

	@Test
	void shouldInviteUserWithUserDecisionStatus() {
		doNothing().when(teamService).validateCanManageTeam(teamId);
		Team team = new Team();
		team.setId(teamId);
		when(teamService.getById(teamId)).thenReturn(team);

		TeamHierarchyId id = new TeamHierarchyId(userId, teamId);
		when(teamHierarchyRepository.findById(id)).thenReturn(Optional.empty());
		when(teamHierarchyRepository.save(any(TeamHierarchy.class))).thenAnswer(i -> i.getArgument(0));

		TeamHierarchy hierarchy = teamHierarchyService.inviteUser(teamId, userId, TeamRoleEnum.MEMBER);

		assertEquals(HierarchyStatusEnum.USER_DECISION, hierarchy.getStatus());
		assertEquals(TeamRoleEnum.MEMBER, hierarchy.getRole());
	}

	@Test
	void shouldRequestToJoinWithTeamDecisionStatus() {
		Team team = new Team();
		team.setId(teamId);
		when(teamService.getById(teamId)).thenReturn(team);
		when(securityUtils.getLoggedUserId()).thenReturn(userId);

		TeamHierarchyId id = new TeamHierarchyId(userId, teamId);
		when(teamHierarchyRepository.findById(id)).thenReturn(Optional.empty());
		when(teamHierarchyRepository.save(any(TeamHierarchy.class))).thenAnswer(i -> i.getArgument(0));

		TeamHierarchy hierarchy = teamHierarchyService.requestToJoin(teamId);

		assertEquals(HierarchyStatusEnum.TEAM_DECISION, hierarchy.getStatus());
		assertEquals(TeamRoleEnum.MEMBER, hierarchy.getRole());
	}

	@Test
	void shouldAllowUserToAcceptInvite() {
		when(securityUtils.getLoggedUserId()).thenReturn(userId);

		TeamHierarchy hierarchy = new TeamHierarchy();
		hierarchy.setId(new TeamHierarchyId(userId, teamId));
		hierarchy.setStatus(HierarchyStatusEnum.USER_DECISION);

		when(teamHierarchyRepository.findById(new TeamHierarchyId(userId, teamId))).thenReturn(Optional.of(hierarchy));
		when(teamHierarchyRepository.save(any(TeamHierarchy.class))).thenAnswer(i -> i.getArgument(0));

		TeamHierarchy accepted = teamHierarchyService.userRespondInvite(teamId, true);

		assertEquals(HierarchyStatusEnum.ACCEPTED, accepted.getStatus());
	}

	@Test
	void shouldAllowTeamToBlockUser() {
		doNothing().when(teamService).validateCanManageTeam(teamId);
		Team team = new Team();
		team.setId(teamId);
		when(teamService.getById(teamId)).thenReturn(team);

		TeamHierarchyId id = new TeamHierarchyId(userId, teamId);
		when(teamHierarchyRepository.findById(id)).thenReturn(Optional.empty());
		when(teamHierarchyRepository.save(any(TeamHierarchy.class))).thenAnswer(i -> i.getArgument(0));

		TeamHierarchy blocked = teamHierarchyService.blockUser(teamId, userId);

		assertEquals(HierarchyStatusEnum.BLOCKED, blocked.getStatus());
	}

	@Test
	void shouldNotAllowInvitingBlockedUser() {
		doNothing().when(teamService).validateCanManageTeam(teamId);
		Team team = new Team();
		team.setId(teamId);
		when(teamService.getById(teamId)).thenReturn(team);

		TeamHierarchyId id = new TeamHierarchyId(userId, teamId);
		TeamHierarchy existing = new TeamHierarchy();
		existing.setId(id);
		existing.setStatus(HierarchyStatusEnum.BLOCKED);

		when(teamHierarchyRepository.findById(id)).thenReturn(Optional.of(existing));

		assertThrows(BusinessException.class, () -> teamHierarchyService.inviteUser(teamId, userId, TeamRoleEnum.MEMBER));
	}

}
