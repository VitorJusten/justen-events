package com.justen.events.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.justen.events.core.dto.TeamHierarchyDto;
import com.justen.events.core.dto.input.TeamHierarchyInputDto;
import com.justen.events.core.enums.TeamRoleEnum;
import com.justen.events.core.types.TeamHierarchyId;
import com.justen.events.domain.service.TeamHierarchyService;

import lombok.AllArgsConstructor;

/**
 *
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
@RestController
@RequestMapping("/team-hierarchy")
@AllArgsConstructor
public class TeamHierarchyController {

	private final TeamHierarchyService teamHierarchyService;

	@PostMapping
	public TeamHierarchyDto create(@RequestBody TeamHierarchyInputDto input) {
		return new TeamHierarchyDto(teamHierarchyService.create(input.toEntity()));
	}

	@GetMapping
	public TeamHierarchyDto getById(@RequestParam UUID teamId, @RequestParam UUID userId) {
		TeamHierarchyId id = new TeamHierarchyId();
		id.setTeamId(teamId);
		id.setUserId(userId);
		return new TeamHierarchyDto(teamHierarchyService.getById(id));
	}

	@GetMapping("/all")
	public Page<TeamHierarchyDto> getAll(Pageable pageable) {
		return teamHierarchyService.getAll(pageable).map(TeamHierarchyDto::new);
	}

	@PutMapping
	public TeamHierarchyDto update(@RequestParam UUID teamId, @RequestParam UUID userId,
			@RequestBody TeamHierarchyInputDto input) {
		TeamHierarchyId id = new TeamHierarchyId();
		id.setTeamId(teamId);
		id.setUserId(userId);
		return new TeamHierarchyDto(teamHierarchyService.update(id, input.toEntity()));
	}

	@DeleteMapping
	public void delete(@RequestParam UUID teamId, @RequestParam UUID userId) {
		TeamHierarchyId id = new TeamHierarchyId();
		id.setTeamId(teamId);
		id.setUserId(userId);
		teamHierarchyService.delete(id);
	}

	@GetMapping("/by-team")
	public List<TeamHierarchyDto> getUsersByTeam(@RequestParam UUID teamId) {
		return teamHierarchyService.getUsersByTeam(teamId).stream().map(TeamHierarchyDto::new).toList();
	}

	@GetMapping("/by-user")
	public List<TeamHierarchyDto> getTeamsByUser(@RequestParam UUID userId) {
		return teamHierarchyService.getTeamsByUser(userId).stream().map(TeamHierarchyDto::new).toList();
	}

	@PostMapping("/invite")
	public TeamHierarchyDto inviteUser(@RequestParam UUID teamId, @RequestParam UUID userId,
			@RequestParam(required = false) TeamRoleEnum role) {
		return new TeamHierarchyDto(teamHierarchyService.inviteUser(teamId, userId, role));
	}

	@PostMapping("/request-join")
	public TeamHierarchyDto requestToJoin(@RequestParam UUID teamId) {
		return new TeamHierarchyDto(teamHierarchyService.requestToJoin(teamId));
	}

	@PostMapping("/user-respond")
	public TeamHierarchyDto userRespondInvite(@RequestParam UUID teamId, @RequestParam boolean accept) {
		return new TeamHierarchyDto(teamHierarchyService.userRespondInvite(teamId, accept));
	}

	@PostMapping("/team-respond")
	public TeamHierarchyDto teamRespondRequest(@RequestParam UUID teamId, @RequestParam UUID userId,
			@RequestParam boolean accept) {
		return new TeamHierarchyDto(teamHierarchyService.teamRespondRequest(teamId, userId, accept));
	}

	@PostMapping("/block")
	public TeamHierarchyDto blockUser(@RequestParam UUID teamId, @RequestParam UUID userId) {
		return new TeamHierarchyDto(teamHierarchyService.blockUser(teamId, userId));
	}

}
