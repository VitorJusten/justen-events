package com.justen.events.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.justen.events.core.types.TeamHierarchyId;
import com.justen.events.domain.entity.TeamHierarchy;
import com.justen.events.domain.repository.TeamHierarchyRepository;

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

	public TeamHierarchy create(TeamHierarchy teamHierarchy) {
		return teamHierarchyRepository.save(teamHierarchy);
	}

	public TeamHierarchy getById(TeamHierarchyId id) {
		return teamHierarchyRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("TeamHierarchy not found"));
	}

	public Page<TeamHierarchy> getAll(Pageable pageable) {
		return teamHierarchyRepository.findAll(pageable);
	}

	public TeamHierarchy update(TeamHierarchyId id, TeamHierarchy teamHierarchy) {
		TeamHierarchy existing = getById(id);
		existing.setRole(teamHierarchy.getRole());
		return teamHierarchyRepository.save(existing);
	}

	public void delete(TeamHierarchyId id) {
		getById(id);
		teamHierarchyRepository.deleteById(id);
	}

	public List<TeamHierarchy> getUsersByTeam(UUID teamId) {
		return teamHierarchyRepository.findByTeam_Id(teamId);
	}

	public List<TeamHierarchy> getTeamsByUser(UUID userId) {
		return teamHierarchyRepository.findByUserId(userId);
	}

}
