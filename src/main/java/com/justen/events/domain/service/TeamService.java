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
public class TeamService {

	private final TeamRepository teamRepository;
	private final TeamHierarchyRepository teamHierarchyRepository;
	private final SecurityUtils securityUtils;

	@Transactional
	public Team create(Team team) {
		Team saved = teamRepository.save(team);
		UUID loggedUserId = securityUtils.getLoggedUserId();

		TeamHierarchy hierarchy = new TeamHierarchy();
		TeamHierarchyId hierarchyId = new TeamHierarchyId();
		hierarchyId.setTeamId(saved.getId());
		hierarchyId.setUserId(loggedUserId);
		hierarchy.setId(hierarchyId);
		hierarchy.setTeam(saved);
		hierarchy.setUserId(loggedUserId);
		hierarchy.setRole(TeamRoleEnum.LEADER);
		hierarchy.setStatus(HierarchyStatusEnum.ACCEPTED);
		teamHierarchyRepository.save(hierarchy);

		return saved;
	}

	public Team getById(UUID id) {
		return teamRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + id));
	}

	public Page<Team> getAll(Pageable pageable, String filter) {
		if (filter != null && !filter.isBlank()) {
			return teamRepository.findByNameContainingIgnoreCase(filter, pageable);
		}
		return teamRepository.findAll(pageable);
	}

	@Transactional
	public Team update(UUID id, Team team) {
		Team existing = getById(id);
		validateCanManageTeam(id);

		existing.setName(team.getName());
		existing.setMetadata(team.getMetadata());
		return teamRepository.save(existing);
	}

	@Transactional
	public void delete(UUID id) {
		getById(id);
		validateCanManageTeam(id);
		teamRepository.deleteById(id);
	}

	public void validateCanManageTeam(UUID teamId) {
		if (Boolean.TRUE.equals(securityUtils.validateRoles(List.of(RoleEnum.ADM, RoleEnum.DEV)))) {
			return;
		}

		UUID loggedUserId = securityUtils.getLoggedUserId();
		boolean isManager = teamHierarchyRepository.existsByTeam_IdAndUserIdAndRoleInAndStatus(
				teamId, loggedUserId, List.of(TeamRoleEnum.LEADER, TeamRoleEnum.ADM), HierarchyStatusEnum.ACCEPTED);

		if (!isManager) {
			throw new BusinessException("User does not have permission to manage this team");
		}
	}

}
