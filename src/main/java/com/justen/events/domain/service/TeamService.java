package com.justen.events.domain.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.justen.events.domain.entity.Team;
import com.justen.events.domain.repository.TeamRepository;

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

	public Team create(Team team) {
		return teamRepository.save(team);
	}

	public Team getById(UUID id) {
		return teamRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Team not found with id: " + id));
	}

	public Page<Team> getAll(Pageable pageable, String filter) {
		if (filter != null && !filter.isBlank()) {
			return teamRepository.findByNameContainingIgnoreCase(filter, pageable);
		}
		return teamRepository.findAll(pageable);
	}

	public Team update(UUID id, Team team) {
		Team existing = getById(id);
		existing.setName(team.getName());
		existing.setMetadata(team.getMetadata());
		return teamRepository.save(existing);
	}

	public void delete(UUID id) {
		getById(id);
		teamRepository.deleteById(id);
	}

}
