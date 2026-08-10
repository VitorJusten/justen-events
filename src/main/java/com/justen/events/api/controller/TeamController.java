package com.justen.events.api.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.justen.events.core.dto.TeamDto;
import com.justen.events.core.dto.input.TeamInputDto;
import com.justen.events.domain.service.TeamService;

import lombok.AllArgsConstructor;

/**
 *
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
@RestController
@RequestMapping("/team")
@AllArgsConstructor
public class TeamController {

	private final TeamService teamService;

	@PostMapping
	public TeamDto create(@RequestBody TeamInputDto input) {
		return new TeamDto(teamService.create(input.toEntity()));
	}

	@GetMapping("/{id}")
	public TeamDto getById(@PathVariable UUID id) {
		return new TeamDto(teamService.getById(id));
	}

	@GetMapping
	public Page<TeamDto> getAll(Pageable pageable, @RequestParam(required = false) String filter) {
		return teamService.getAll(pageable, filter).map(TeamDto::new);
	}

	@PutMapping("/{id}")
	public TeamDto update(@PathVariable UUID id, @RequestBody TeamInputDto input) {
		return new TeamDto(teamService.update(id, input.toEntity()));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable UUID id) {
		teamService.delete(id);
	}

}
