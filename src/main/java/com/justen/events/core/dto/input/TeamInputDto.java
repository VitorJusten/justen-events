package com.justen.events.core.dto.input;

import java.util.Collections;
import java.util.List;

import com.justen.events.domain.entity.Team;

import lombok.Data;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-events
 * @Year 2026
 *
 */
@Data
public class TeamInputDto {

	private String name;

	private String metadata;

	private List<TeamHierarchyInputDto> teamHierarchy;

	public Team toEntity() {
		Team team = new Team();
		team.setName(this.name);
		team.setMetadata(this.metadata);

		team.setTeamHierarchy(teamHierarchy == null ? Collections.emptyList()
				: teamHierarchy.stream().map(TeamHierarchyInputDto::toEntity).toList());

		return team;
	}

}
