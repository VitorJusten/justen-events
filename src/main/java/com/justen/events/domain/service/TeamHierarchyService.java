package com.justen.events.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justen.events.core.enums.HierarchyStatusEnum;
import com.justen.events.core.enums.TeamRoleEnum;
import com.justen.events.core.types.TeamHierarchyId;
import com.justen.events.domain.entity.Team;
import com.justen.events.domain.entity.TeamHierarchy;
import com.justen.events.domain.exception.BusinessException;
import com.justen.events.domain.exception.EntityNotFoundException;
import com.justen.events.domain.repository.TeamHierarchyRepository;
import com.justen.events.domain.repository.TeamRepository;
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
public class TeamHierarchyService {

	private final TeamHierarchyRepository teamHierarchyRepository;
	private final TeamRepository teamRepository;
	private final TeamService teamService;
	private final SecurityUtils securityUtils;

	@Transactional
	public TeamHierarchy create(TeamHierarchy teamHierarchy) {
		UUID teamId = teamHierarchy.getTeam() != null ? teamHierarchy.getTeam().getId()
				: (teamHierarchy.getId() != null ? teamHierarchy.getId().getTeamId() : null);
		if (teamId == null) {
			throw new BusinessException("Team id is required");
		}
		teamService.validateCanManageTeam(teamId);
		return teamHierarchyRepository.save(teamHierarchy);
	}

	public TeamHierarchy getById(TeamHierarchyId id) {
		return teamHierarchyRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("TeamHierarchy not found"));
	}

	public Page<TeamHierarchy> getAll(Pageable pageable) {
		return teamHierarchyRepository.findAll(pageable);
	}

	@Transactional
	public TeamHierarchy update(TeamHierarchyId id, TeamHierarchy teamHierarchy) {
		TeamHierarchy existing = getById(id);
		teamService.validateCanManageTeam(id.getTeamId());
		existing.setRole(teamHierarchy.getRole());
		existing.setStatus(teamHierarchy.getStatus());
		return teamHierarchyRepository.save(existing);
	}

	@Transactional
	public void delete(TeamHierarchyId id) {
		TeamHierarchy existing = getById(id);
		UUID loggedUserId = securityUtils.getLoggedUserId();
		boolean isSelf = existing.getUserId().equals(loggedUserId);

		if (!isSelf) {
			teamService.validateCanManageTeam(id.getTeamId());
		}

		teamHierarchyRepository.deleteById(id);
	}

	public List<TeamHierarchy> getUsersByTeam(UUID teamId) {
		return teamHierarchyRepository.findByTeam_Id(teamId);
	}

	public List<TeamHierarchy> getTeamsByUser(UUID userId) {
		return teamHierarchyRepository.findByUserId(userId);
	}

	@Transactional
	public TeamHierarchy inviteUser(UUID teamId, UUID userId, TeamRoleEnum role) {
		teamService.validateCanManageTeam(teamId);
		Team team = teamService.getById(teamId);

		TeamHierarchyId id = new TeamHierarchyId(userId, teamId);
		TeamHierarchy hierarchy = teamHierarchyRepository.findById(id).orElseGet(() -> {
			TeamHierarchy th = new TeamHierarchy();
			th.setId(id);
			th.setTeam(team);
			th.setUserId(userId);
			return th;
		});

		if (HierarchyStatusEnum.BLOCKED.equals(hierarchy.getStatus())) {
			throw new BusinessException("Cannot invite a blocked user to team");
		}

		hierarchy.setRole(role != null ? role : TeamRoleEnum.MEMBER);
		hierarchy.setStatus(HierarchyStatusEnum.USER_DECISION);
		return teamHierarchyRepository.save(hierarchy);
	}

	@Transactional
	public TeamHierarchy requestToJoin(UUID teamId) {
		Team team = teamService.getById(teamId);
		UUID loggedUserId = securityUtils.getLoggedUserId();

		TeamHierarchyId id = new TeamHierarchyId(loggedUserId, teamId);
		TeamHierarchy hierarchy = teamHierarchyRepository.findById(id).orElseGet(() -> {
			TeamHierarchy th = new TeamHierarchy();
			th.setId(id);
			th.setTeam(team);
			th.setUserId(loggedUserId);
			return th;
		});

		if (HierarchyStatusEnum.BLOCKED.equals(hierarchy.getStatus())) {
			throw new BusinessException("User is blocked from joining this team");
		}

		if (HierarchyStatusEnum.ACCEPTED.equals(hierarchy.getStatus())) {
			throw new BusinessException("User is already a member of this team");
		}

		hierarchy.setRole(TeamRoleEnum.MEMBER);
		hierarchy.setStatus(HierarchyStatusEnum.TEAM_DECISION);
		return teamHierarchyRepository.save(hierarchy);
	}

	@Transactional
	public TeamHierarchy userRespondInvite(UUID teamId, boolean accept) {
		UUID loggedUserId = securityUtils.getLoggedUserId();
		TeamHierarchyId id = new TeamHierarchyId(loggedUserId, teamId);
		TeamHierarchy hierarchy = getById(id);

		if (!HierarchyStatusEnum.USER_DECISION.equals(hierarchy.getStatus())) {
			throw new BusinessException("There is no pending invite for this user");
		}

		hierarchy.setStatus(accept ? HierarchyStatusEnum.ACCEPTED : HierarchyStatusEnum.DENIED);
		return teamHierarchyRepository.save(hierarchy);
	}

	@Transactional
	public TeamHierarchy teamRespondRequest(UUID teamId, UUID userId, boolean accept) {
		teamService.validateCanManageTeam(teamId);
		TeamHierarchyId id = new TeamHierarchyId(userId, teamId);
		TeamHierarchy hierarchy = getById(id);

		if (!HierarchyStatusEnum.TEAM_DECISION.equals(hierarchy.getStatus())) {
			throw new BusinessException("There is no pending request for this user");
		}

		hierarchy.setStatus(accept ? HierarchyStatusEnum.ACCEPTED : HierarchyStatusEnum.DENIED);
		return teamHierarchyRepository.save(hierarchy);
	}

	@Transactional
	public TeamHierarchy blockUser(UUID teamId, UUID userId) {
		teamService.validateCanManageTeam(teamId);
		Team team = teamService.getById(teamId);

		TeamHierarchyId id = new TeamHierarchyId(userId, teamId);
		TeamHierarchy hierarchy = teamHierarchyRepository.findById(id).orElseGet(() -> {
			TeamHierarchy th = new TeamHierarchy();
			th.setId(id);
			th.setTeam(team);
			th.setUserId(userId);
			th.setRole(TeamRoleEnum.MEMBER);
			return th;
		});

		hierarchy.setStatus(HierarchyStatusEnum.BLOCKED);
		return teamHierarchyRepository.save(hierarchy);
	}

}
